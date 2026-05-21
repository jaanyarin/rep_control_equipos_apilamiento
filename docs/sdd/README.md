# REP Control Equipos Apilamiento

Sistema empresarial para la gestión operativa, control documental y seguimiento de equipos alquilados utilizados en procesos de apilamiento agrícola.

La plataforma permitirá administrar:
- campañas agrícolas,
- equipos operativos,
- PSR/OSR,
- averías,
- evidencias fotográficas,
- indicadores KPI,
- trazabilidad operacional,
- auditoría de acciones,
- reportes PDF y Excel.

El proyecto está orientado a operación agrícola empresarial bajo arquitectura moderna, escalable y mantenible.

---

# Objetivo del Proyecto

Centralizar y digitalizar el control operativo de equipos alquilados utilizados en campañas agrícolas, permitiendo:
- trazabilidad completa,
- control documental,
- seguimiento de averías,
- gestión de campañas,
- visualización de indicadores,
- control de usuarios y auditoría.

---

# Estado del Proyecto

| Área | Estado |
|---|---|
| Análisis Funcional | ✅ Completado |
| SDD | ✅ Completado |
| Arquitectura Técnica | ✅ Completado |
| Diseño Base Datos | ✅ Completado |
| Script SQL BD | ✅ Completado |
| API Contracts | ✅ Completado (v1.1 — auth, PSR, evidencias actualizados) |
| Fase 2: Infraestructura | ✅ Completado (Docker, Nginx, CI/CD) |
| Backend | ✅ Completado (62+ fuentes, 55+ endpoints, 24 tests) |
| Frontend Mobile | ✅ Completado (12 pantallas, 10 servicios, Azure AD real) |
| Frontend Web | ✅ Completado (Vite + React 19 + MUI 6 + Recharts, 6 páginas) |
| DevOps | ✅ Completado (Docker Compose, GitHub Actions, SSL, healthchecks) |

---

# Arquitectura General

## Frontend Mobile

Aplicación móvil para operación en campo (Android 9+, API 28).

### Stack
- React Native 0.85.3
- Redux Toolkit (authSlice)
- React Navigation 7 (12 rutas)
- Axios (interceptor refresh token)
- TypeScript 5.8
- react-native-app-auth (login Microsoft Entra ID)
- react-native-vision-camera (captura fotos)
- react-native-pdf (visor PDF)
- react-native-blob-util (descarga PDF)
- Toast nativo con Animated API
- ErrorBoundary global

---

## Frontend Web

Plataforma web administrativa y analítica.

### Stack
- React 19 + Vite 6
- MUI 6 (Material Design 3)
- Recharts 2 (Bar, Pie, Line charts)
- React Router 7 (6 rutas)
- Axios (interceptor auth + refresh)
- TypeScript 5.8

---

## Backend

Servicios REST empresariales.

### Stack
- Quarkus 3.15.1 + Java 21 (Temurin)
- Hibernate ORM + Panache
- MySQL 8 + Flyway (migraciones)
- JWT (smallrye-jwt, HMAC-SHA256)
- Microsoft Entra ID (validación JWT vía jose4j + JWKS)
- OpenAPI + Swagger UI
- OpenPDF 2.0.3 (reportes PDF)
- 24 tests (unit + integración)

---

## Base de Datos

- MySQL 8 + Flyway (V1__security_and_org.sql)
- H2 in-memory para tests

---

## Infraestructura

- Docker (backend, dashboard web, MySQL 8)
- Docker Compose (volúmenes persistentes, app-network)
- Nginx (reverse proxy, SSL, 10MB upload)
- GitHub Actions CI/CD (build, test, docker push)
- VPS Linux

---

# Módulos del Sistema

| Código | Módulo |
|---|---|
| MOD-01 | Autenticación |
| MOD-02 | Usuarios |
| MOD-03 | Sedes |
| MOD-04 | Campañas |
| MOD-05 | PSR / OSR |
| MOD-06 | Equipos |
| MOD-07 | Tipos de Equipos |
| MOD-08 | Proveedores |
| MOD-09 | Averías |
| MOD-10 | Evidencias Fotográficas |
| MOD-11 | Dashboard KPI |
| MOD-12 | Reportes PDF |
| MOD-13 | Auditoría |
| MOD-14 | Catálogos |
| MOD-15 | Configuración |

---

# Funcionalidades Principales

## Autenticación Empresarial (MOD-01)
- Login Microsoft Entra ID con validación real de JWT vía JWKS + jose4j
- JWT propio de sesión (15 min expiración, 24h refresh)
- Control de sesiones con refresh token automático
- Roles ADMIN / USER con @RolesAllowed en endpoints

---

## Gestión Operativa (MOD-02 a MOD-09)
- 55+ endpoints REST con filtros, paginación y ordenamiento
- Equipos (CRUD, historial averías, estado: DISPONIBLE/OPERATIVO/AVERIADO/MANTENIMIENTO/DEVUELTO)
- Campañas (CRUD, activar/cerrar, validación única activa)
- PSR/OSR (CRUD + flujo: PENDIENTE → APROBAR/RECHAZAR → CERRAR)
- Averías (CRUD + cierre con cálculo horas inactivo)
- Sedes, Tipos Equipo, Proveedores (CRUD completo)

---

## Evidencias Fotográficas (MOD-10)
- Captura desde cámara (react-native-vision-camera)
- Upload multipart con validación: 5MB máx, solo JPG/PNG/GIF/WEBP (detección por magic bytes)
- Compresión automática server-side: reescala a 1080x720 máx, mantiene aspect ratio
- Almacenamiento filesystem en UUID con ruta configurable (EVIDENCE_UPLOAD_PATH)
- Soft-delete solo ADMIN, con registro en auditoría

---

## Dashboard KPI (MOD-11)
- Web: Vite + React 19 + MUI 6 + Recharts (Bar, Pie, Line)
- KPIs: equipos activos/disponibles/averiados, averías abiertas/cerradas, tiempo promedio atención, disponibilidad, utilización
- Métricas: distribución por estado/tipo, evolución mensual 12 meses
- Backend: NativeQuery vía EntityManager para agregaciones SQL

---

## Reportes PDF (MOD-12)
- Generación con OpenPDF (iText fork LGPL)
- Reportes: equipo (código, marca, modelo, serie, tipo, proveedor, estado), PSR, averías
- Consultas nativas SQL con JOINs

---

## Auditoría (MOD-13)
- Logs de login/logout, CRUD, cambios de estado, errores críticos
- Filtros por usuario, módulo, acción, entidad, fechas
- Paginación y orden descendente por fecha

---

## Seguridad

La plataforma implementa:
- Microsoft Entra ID — validación real de JWT (firma RSA, issuer, audience, exp)
- jose4j con JWKS caching (24h) desde `/discovery/v2.0/keys`
- JWT propio HMAC-SHA256 para sesiones internas
- Control de roles ADMIN/USER con @RolesAllowed
- CORS restringido por entorno, rate limiting, max body 5MB
- SQL injection prevenido (PanacheQL parametrizado)
- DTO desacoplados de entidades JPA
- Auditoría completa de todas las operaciones críticas

---

# Estrategia APIs

## REST APIs
- Versionamiento `/api/v1`
- OpenAPI
- Swagger UI
- Paginación server-side
- Filtros dinámicos
- Manejo centralizado errores

---

## Estándar Respuesta APIs

### Respuesta Exitosa

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": {},
  "timestamp": "2026-05-18T10:00:00Z"
}
```

### Respuesta Error

```json
{
  "success": false,
  "message": "Descripción error",
  "errorCode": "ERR001",
  "timestamp": "2026-05-18T10:00:00Z"
}
```

---

# Estrategia Fotografías

| Configuración | Valor |
|---|---|
| Formatos Permitidos | JPG, PNG |
| Resolución Máxima | 1080x720 |
| Compresión | Automática |
| Almacenamiento | Filesystem |
| Eliminación | Manual Administrador |
| Auditoría | Obligatoria |

---

# Estructura Documentación SDD

```text
docs/
 └── sdd/
      ├── 01_SPECIFICATION.md
      ├── 02_PLAN.md
      ├── 03_TASKS.md
      ├── 04_IMPLEMENTATION.md
      ├── 05_TECHNICAL_ARCHITECTURE.md
      ├── 06_DATABASE_DESIGN.md
      └── 07_API_CONTRACTS.md
```

---

# Estrategia Desarrollo

## MVP Inicial

### Incluye
- autenticación,
- usuarios,
- campañas,
- equipos,
- PSR/OSR,
- averías,
- evidencias,
- dashboard básico.

### Futuro
- métricas avanzadas,
- exportaciones avanzadas,
- BI,
- observabilidad avanzada,
- escalabilidad cloud.

---

# Configuración Timezone

| Configuración | Valor |
|---|---|
| Timezone | America/Lima |
| UTC | UTC -5 |

---

# Convenciones

## Git Flow

```text
main
develop
feature/*
hotfix/*
```

---

# CI/CD

GitHub Actions implementará:
- build automático,
- tests,
- validación código,
- dockerización,
- despliegue automatizado.

---

# Observabilidad

La plataforma implementará:
- logs backend,
- logs auditoría,
- healthchecks,
- control errores,
- monitoreo básico.

---

# Roadmap Futuro

- Kubernetes
- Redis
- BI avanzado
- Observabilidad avanzada
- Multiempresa
- Escalabilidad cloud
- Dashboards avanzados

---

# Autor

Proyecto desarrollado para control operacional agrícola empresarial.

---

# Licencia

Pendiente definición.

