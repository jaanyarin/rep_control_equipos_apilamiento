# Configuración de Build y Despliegue

> Documento técnico de la configuración funcional actual del backend.
> **Propósito**: Servir como referencia para entender por qué cada pieza está configurada como está, y qué debe permanecer intacto para no romper el build.

---

## 1. Stack tecnológico

| Componente | Versión | Justificación |
|---|---|---|
| Java | 21 (Temurin) | LTS, requisito de Quarkus 3.15+. Compatible con registries de contenedores corporativos. |
| Quarkus | 3.15.1 | Framework REST nativo cloud. Compilación rápida, baja huella de memoria, ideal para contenedores. |
| Maven | 3.9.15 | Build tool estándar. Se usa en builder stage del Dockerfile. |
| MySQL | 8.0+ | Base de datos relacional requerida por el SDD. Flyway para control de versiones de esquema. |
| Flyway | 10.x (provisto por Quarkus BOM) | Migraciones de base de datos ejecutadas al arrancar la aplicación. |

**Regla**: No cambiar versiones sin probar el build completo en Docker. Quarkus BOM gestiona versiones de dependencias transitivas.

---

## 2. pom.xml — Dependencias y Build

### 2.1 Quarkus BOM (Líneas 14-16)

```xml
<quarkus.platform.group-id>io.quarkus.platform</quarkus.platform.group-id>
<quarkus.platform.artifact-id>quarkus-bom</quarkus.platform.artifact-id>
<quarkus.platform.version>3.15.1</quarkus.platform.version>
```

Se importa el BOM de Quarkus Platform. Esto fija versiones de TODAS las dependencias del ecosistema Quarkus (Hibernate, RESTEasy, Jackson, etc.) para que sean compatibles entre sí.

**Qué NO hacer**: No sobreescribir versiones de dependencias Quarkus manualmente. Si necesitas una versión distinta de, ej., Jackson, actualiza todo el BOM.

### 2.2 Dependencias seleccionadas

| Artefacto | Propósito |
|---|---|
| `quarkus-rest-jackson` | API REST con serialización JSON via Jackson. Reemplaza a RESTEasy + Jackson. |
| `quarkus-jdbc-mysql` | Driver JDBC para MySQL. |
| `quarkus-flyway` | Migraciones de esquema de BD al arrancar. Sin esto, no se crean tablas. |
| `quarkus-smallrye-openapi` | Genera spec OpenAPI 3.0 en `/openapi` y Swagger UI en `/swagger`. |
| `quarkus-smallrye-health` | Endpoint `/q/health` para health checks del orchestrador. |
| `quarkus-hibernate-orm-panache` | ORM + patrón Active Record (PanacheEntity). Simplifica CRUD. |

**Qué NO hacer**: No quitar `quarkus-flyway` a menos que migres a otro sistema de migraciones. No quitar `quarkus-smallrye-health`, el health check es requerido por docker-compose y orquestadores.

### 2.3 Plugins de build

- **quarkus-maven-plugin**: Compila y empaqueta la aplicación. Sin él, `mvn package` no genera el directorio `quarkus-app/`.
- **maven-surefire-plugin**: Ejecuta tests en `mvn test`.

### 2.4 Ausencia de `packageType=uber-jar`

Decisión deliberada. Se eliminó `quarkus.package.type=uber-jar` porque el empaquetado uber-jar causaba clases faltantes en `quarkus-app/app/` durante la compilación en Docker. El layout `quarkus-app/` (directorios separados: `app/`, `lib/`, `quarkus/`) es el formato recomendado por Quarkus para entornos containerizados y permite:
- Capas de imagen Docker más eficientes (cambia solo `app/` al modificar código)
- Debugging más sencillo (puedes inspeccionar los JARs individuales)

**Qué NO hacer**: No reintroducir `uber-jar` sin probar el build completo en Docker.

---

## 3. Dockerfile — Estrategia multi-stage

### 3.1 Estructura

```
Stage 1: builder (maven:3.9.15-eclipse-temurin-21)
  → Compila y empaqueta la app

Stage 2: runtime (eclipse-temurin:21-jre)
  → Solo JRE, imagen pequeña (~200MB vs ~800MB del builder)
```

### 3.2 Stage builder

```dockerfile
FROM maven:3.9.15-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn -B -DskipTests=true compile quarkus:build
```

**Puntos clave**:
- `COPY backend/pom.xml ./` se copia ANTES que `src/` para aprovechar cache de Docker. Si solo cambia el código fuente, Maven no redescarga dependencias.
- Se usa `compile quarkus:build` en lugar de solo `quarkus:build` o `package`. Esto es crítico: `quarkus:build` NO compila fuentes automáticamente (es un goal de Augmentation, no de compilación). Al anteponer `compile`, aseguramos que los `.java` se compilen a `.class` antes del paso de augmentación.
- Se usa `-B` (batch mode) para evitar prompts interactivos. `-DskipTests=true` porque no hay tests en el stage builder.

### 3.3 Stage runtime

```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/target/quarkus-app ./quarkus-app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/quarkus-app/quarkus-run.jar"]
```

**Puntos clave**:
- `eclipse-temurin:21-jre` es ~200MB, imagen oficial de Eclipse Foundation, sin vulnerabilidades conocidas.
- Se copia TODO `quarkus-app/` (no solo el uber-jar) porque el layout de Quarkus separa el código de la app (`quarkus-run.jar` + `app/`) de las librerías (`lib/`) y el propio framework (`quarkus/`).
- `quarkus-run.jar` es un JAR delgado que carga el resto desde los directorios adjuntos.

**Qué NO hacer**:
- No cambiar a `openjdk:21-jre-slim` (tiene menos librerías y puede fallar con Quarkus).
- No cambiar `ENTRYPOINT` a otro JAR. `quarkus-run.jar` es el punto de entrada correcto.
- No copiar solo `target/*.jar` — eso no incluye `lib/` y `quarkus/`.

---

## 4. docker-compose.yml — Orquestación local

### 4.1 Servicio MySQL

```yaml
mysql:
  image: mysql:8
  ports:
    - "3307:3306"
  healthcheck:
    test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-proot"]
```

- Puerto `3307` host → `3306` container para no interferir con MySQL local del desarrollador.
- Healthcheck con `mysqladmin ping`: el backend espera a que MySQL esté listo antes de arrancar.

### 4.2 Servicio Backend

```yaml
backend:
  build:
    context: .
    dockerfile: backend/Dockerfile
  environment:
    DB_JDBC_URL: jdbc:mysql://mysql:3306/rep_equipos_apilamiento
  depends_on:
    mysql:
      condition: service_healthy
```

- `context: .` (raíz del repo) permite que el Dockerfile acceda a `backend/pom.xml` y `backend/src/`.
- `DB_JDBC_URL` usa hostname `mysql` (nombre del servicio docker-compose) y puerto interno `3306`.
- `condition: service_healthy` asegura que MySQL esté operativo antes de lanzar el backend (evita errores de conexión al arranque).

**Qué NO hacer**:
- No cambiar `context: .` a `context: ./backend` sin reestructurar el COPY en el Dockerfile.
- No cambiar a `depends_on: mysql` sin `condition` — el backend arrancaría antes de que MySQL esté listo.

---

## 5. application.properties — Configuración de runtime

```properties
quarkus.datasource.jdbc.url=${DB_JDBC_URL:jdbc:mysql://localhost:3307/rep_equipos_apilamiento}
quarkus.hibernate-orm.database.generation=none
quarkus.flyway.migrate-at-start=true
quarkus.flyway.validate-on-migrate=true
quarkus.smallrye-openapi.path=/openapi
quarkus.swagger-ui.always-include=true
```

| Propiedad | Valor | Explicación |
|---|---|---|
| `database.generation` | `none` | Hibernate NO crea/modifica tablas. Toda la gestión de esquema es responsabilidad de Flyway. Esto evita inconsistencias entre entities y migraciones. |
| `flyway.migrate-at-start` | `true` | Ejecuta `flyway migrate` al arrancar la app. Sin esto, las tablas nunca se crean. |
| `flyway.validate-on-migrate` | `true` | Valida que las migraciones ya aplicadas no hayan sido modificadas. Previene alteraciones accidentales del historial de migraciones. |
| `flyway.locations` | `db/migration` | Directorio donde residen los scripts SQL de migración. |

### 5.1 Naming strategy

```properties
quarkus.hibernate-orm.physical-naming-strategy=org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
```

Convierte `nombreUsuario` → `nombre_usuario` automáticamente. Las tablas en BD usan `snake_case` y las entidades Java usan `camelCase`. Sin esta strategy, Hibernate esperaría nombres exactos.

**Qué NO hacer**:
- No cambiar `database.generation` a `update` o `drop-and-create` en producción — Borraría datos o crearía tablas duplicadas.
- No cambiar `flyway.locations` sin mover físicamente los archivos SQL.
- No cambiar `validate-on-migrate` a `false` sin justificación — Es una guarda contra modificaciones accidentales.

---

## 6. Variables de entorno (inyectadas en runtime)

| Variable | Default (desarrollo local) | Valor en Docker |
|---|---|---|
| `DB_JDBC_URL` | `jdbc:mysql://localhost:3307/rep_equipos_apilamiento` | `jdbc:mysql://mysql:3306/rep_equipos_apilamiento` |
| `DB_USERNAME` | `root` | `root` |
| `DB_PASSWORD` | `root` | `root` |

El patrón `${VAR:default}` permite correr la app fuera de Docker sin configurar variables de entorno. En Docker, los valores se sobreescriben via `environment:` en docker-compose.yml.

---

## 7. Reglas de mantenimiento

1. **No cambiar versiones del stack sin probar el build Docker completo.**
2. **No reintroducir uber-jar** — el layout `quarkus-app/` es el estándar para contenedores.
3. **No cambiar `database.generation` de `none`** — Flyway es el único responsable del esquema.
4. **No quitar `flyway.migrate-at-start=true`** — Sin migrations, la BD no tiene tablas.
5. **No cambiar el `ENTRYPOINT` del Dockerfile** — `quarkus-run.jar` es el entry point correcto.
6. **Si se agrega una dependencia nueva**, verificar que exista en el BOM de Quarkus Platform. Si no existe, especificar versión manualmente y documentar por qué.
7. **Si se modifica el Dockerfile**, probar con `docker compose build` y verificar que la app arranque con `docker compose up`.
8. **No hardcodear credenciales** en código ni properties. Usar variables de entorno con defaults locales.

---

## 8. Flujo de build completo

```
1. mvn compile quarkus:build     → genera target/quarkus-app/
2. docker compose build          → construye imagen desde Dockerfile
3. docker compose up             → levanta MySQL + Backend
4. curl http://localhost:8080/q/health  → verifica estado
```

Para desarrollo local sin Docker:
```
mvn compile quarkus:dev          → Quarkus dev mode con hot reload
```
