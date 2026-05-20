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
