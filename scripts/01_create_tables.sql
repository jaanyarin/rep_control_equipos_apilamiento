SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS rep_equipos_apilamiento
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE rep_equipos_apilamiento;

SOURCE ../backend/src/main/resources/db/migration/V1__security_and_org.sql;
SOURCE ../backend/src/main/resources/db/migration/V2__catalogs_and_assets.sql;
SOURCE ../backend/src/main/resources/db/migration/V3__operations.sql;
SOURCE ../backend/src/main/resources/db/migration/V4__audit_config_and_seed.sql;

SELECT 'Base de datos creada exitosamente' AS mensaje;
