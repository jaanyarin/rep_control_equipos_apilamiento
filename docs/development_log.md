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
