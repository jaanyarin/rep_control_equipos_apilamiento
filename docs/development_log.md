# LOG DE DESARROLLO (CHECKLIST DE HITOS Y VALIDACIONES)
Este documento es un registro inalterable de los hitos y validaciones de funcionamiento de la plataforma de Control de Equipos de Apilamiento.

---

## [2026-05-20] Hito 1: Setup de Infraestructura, Base de Datos y Compilación del Backend
* **Descripción**: Se configuró el entorno local y de compilación, alineando la versión de Java a la disponible localmente y agregando las dependencias para reportes PDF.
* **Cambios realizados**:
  * Ajuste de `maven.compiler.release` a `21` en `backend/pom.xml`.
  * Integración de dependencias de iText PDF (`kernel`, `io`, `layout`) en `backend/pom.xml`.
  * Configuración del parámetro `createDatabaseIfNotExist=true` en el JDBC URL del backend para evitar errores si la base de datos no existe previamente en el servidor local.
* **Validaciones**:
  * Ejecución exitosa de `mvn clean compile` localmente en el backend utilizando Java 21.
  * Verificación de conectividad al puerto MySQL local 3306 (estado: Escuchando/Activo).
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 2: Módulo Usuarios y Catálogos Base
* **Descripción**: Se implementó el módulo de usuarios, roles y sitios en el backend Quarkus, con endpoints REST para gestión de usuarios, listas de roles y sedes.
* **Cambios realizados**:
  * Creación de `UsuarioResource` y `UsuarioService` en `backend/src/main/java/pe/com/repcontrol/usuario`.
  * Creación de `RolResource` y `SitioResource` para catálogos básicos.
  * Ajuste de migraciones SQL para permitir `usuario_creacion` nulo y sembrar un usuario administrador inicial.
  * Se agregó `RolResponse` y se aprovechó el DTO `SitioResponse` existente.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Verificación de tipos de Panache y normalización de consultas para evitar errores de raw types.
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 3: Implementación de Campañas y Tipos de Equipos
* **Descripción**: Se completó el desarrollo de los servicios REST de campañas y tipos de equipos en el backend Quarkus.
* **Cambios realizados**:
  * Creación de `CampanaService` y `CampanaResource` en `backend/src/main/java/pe/com/repcontrol/campana`.
  * Creación de `TipoEquipoService` y `TipoEquipoResource` en `backend/src/main/java/pe/com/repcontrol/tipoequipo`.
  * Se mantuvo la persistencia activa para campañas y tipos de equipos con filtros `estadoActivo`.
  * Documentación de endpoints y ejecución backend actualizada en `README.md`.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Creación de endpoints REST compatibles con la secuencia de módulos del SDD.
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 4: Módulo Proveedores
* **Descripción**: Se implementó el módulo de proveedores con CRUD completo en el backend Quarkus.
* **Cambios realizados**:
  * Creación de `ProveedorService` y `ProveedorResource` en `backend/src/main/java/pe/com/repcontrol/proveedor`.
  * Definición de validación de RUC único, campos requeridos y búsqueda por estado activo.
  * Adición de endpoints REST para listar, consultar, crear, actualizar y desactivar proveedores.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Verificación de la correcta integración de proveedores con el modelo de usuarios.
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 5: Módulo PSR
* **Descripción**: Se implementó el módulo PSR con CRUD y validaciones de referencia a campaña, sitio, motivo PSR y usuario creador.
* **Cambios realizados**:
  * Creación de `PsrService` y `PsrResource` en `backend/src/main/java/pe/com/repcontrol/psr`.
  * Nuevos DTOs `PsrRequest` y `PsrResponse` en `backend/src/main/java/pe/com/repcontrol/dto/psr`.
  * Validación de número PSR único, existencia de campaña/sitio/motivo y estado activo del usuario creador.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Confirmación de endpoints REST para gestión de PSR.
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 6: Módulo OSR
* **Descripción**: Se implementó el módulo OSR en el backend con CRUD completo y validaciones de referencia a PSR, equipo y usuario creador.
* **Cambios realizados**:
  * Creación de `OsrService` y `OsrResource` en `backend/src/main/java/pe/com/repcontrol/osr`.
  * Creación de DTOs `OsrRequest` y `OsrResponse` en `backend/src/main/java/pe/com/repcontrol/dto/osr`.
  * Definición de validaciones de número único, estado activo y referencias a entidades relacionadas.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Verificación del nuevo endpoint OSR y su compatibilidad con la arquitectura REST del backend.
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 7: Módulo Equipos
* **Descripción**: Se implementó el módulo de Equipos con CRUD completo en el backend Quarkus y referencias a campaña, OSR, proveedor, tipo de equipo y marca.
* **Cambios realizados**:
  * Creación de `EquipoService` y `EquipoResource` en `backend/src/main/java/pe/com/repcontrol/equipo`.
  * Creación de DTOs `EquipoRequest` y `EquipoResponse` en `backend/src/main/java/pe/com/repcontrol/dto/equipo`.
  * Validaciones de código único, referencias a entidades relacionadas y estado activo en proveedores/tipos de equipos.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Confirmación de endpoints REST para gestión de equipos.
* **Estado**: ✅ COMPLETADO

## [2026-05-20] Hito 8: Módulo Averías
* **Descripción**: Se implementó el módulo de averías en el backend Quarkus con registro, consulta, actualización y cierre operativo.
* **Cambios realizados**:
  * Creación de `AveriaService` y `AveriaResource` en `backend/src/main/java/pe/com/repcontrol/averia`.
  * Creación de DTOs `AveriaRequest`, `AveriaCloseRequest` y `AveriaResponse` en `backend/src/main/java/pe/com/repcontrol/dto/averia`.
  * Implementación del endpoint `PUT /api/v1/damages/{id}/close` con cálculo de horas inactivas.
  * Sincronización del estado del equipo a `AVERIADO` al registrar/atender una avería y a `DISPONIBLE` al cerrarla.
  * Implementación de filtros por equipo, tipo de avería, estado, proveedor y rango de fechas.
* **Validaciones**:
  * Compilación exitosa de `mvn -f backend/pom.xml -DskipTests compile`.
  * Confirmación del endpoint `/api/v1/damages` como siguiente bloque funcional posterior al módulo de equipos según el SDD.
* **Estado**: ✅ COMPLETADO
