SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS rep_equipos_apilamiento
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE rep_equipos_apilamiento;

CREATE TABLE roles (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    nombre              VARCHAR(50)     NOT NULL,
    descripcion         VARCHAR(255)    NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    fecha_baja          DATETIME        NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_rol_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE sitios (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo              VARCHAR(50)     NOT NULL,
    nombre              VARCHAR(255)    NOT NULL,
    descripcion         VARCHAR(500)    NULL,
    direccion           VARCHAR(500)    NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    fecha_baja          DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_sitio_codigo (codigo),
    INDEX idx_sitio_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    id_microsoft        VARCHAR(255)    NOT NULL,
    correo              VARCHAR(255)    NOT NULL,
    nombre              VARCHAR(255)    NOT NULL,
    puesto              VARCHAR(255)    NULL,
    area                VARCHAR(255)    NULL,
    empresa             VARCHAR(255)    NULL,
    departamento        VARCHAR(255)    NULL,
    ubicacion           VARCHAR(255)    NULL,
    rol_id              BIGINT          NOT NULL,
    sitio_id            BIGINT          NULL,
    ultimo_acceso       DATETIME        NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    fecha_baja          DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    usuario_actualizacion BIGINT        NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_usuario_correo (correo),
    UNIQUE INDEX idx_usuario_microsoft (id_microsoft),
    INDEX idx_usuario_rol (rol_id),
    INDEX idx_usuario_sitio (sitio_id),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES roles(id),
    CONSTRAINT fk_usuario_sitio FOREIGN KEY (sitio_id) REFERENCES sitios(id),
    CONSTRAINT fk_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE campañas (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo              VARCHAR(50)     NOT NULL,
    nombre              VARCHAR(255)    NOT NULL,
    tipo                VARCHAR(50)     NOT NULL,
    sitio_id            BIGINT          NOT NULL,
    fecha_inicio        DATE            NOT NULL,
    fecha_fin           DATE            NULL,
    estado              VARCHAR(50)     NOT NULL DEFAULT 'ACTIVA',
    es_activa           TINYINT(1)      NOT NULL DEFAULT 0,
    descripcion         VARCHAR(500)    NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    fecha_baja          DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_campaña_codigo (codigo),
    INDEX idx_campaña_sitio (sitio_id),
    INDEX idx_campaña_estado (estado),
    INDEX idx_campaña_activa (es_activa),
    CONSTRAINT fk_campaña_sitio FOREIGN KEY (sitio_id) REFERENCES sitios(id),
    CONSTRAINT fk_campaña_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tipos_equipos (
    id                      BIGINT          NOT NULL AUTO_INCREMENT,
    codigo                  VARCHAR(50)     NOT NULL,
    nombre                  VARCHAR(255)    NOT NULL,
    categoria               VARCHAR(50)     NOT NULL,
    tecnologia_bateria      VARCHAR(50)     NULL,
    requiere_horometro      TINYINT(1)      NOT NULL DEFAULT 0,
    requiere_bateria        TINYINT(1)      NOT NULL DEFAULT 0,
    requiere_cargador       TINYINT(1)      NOT NULL DEFAULT 0,
    requiere_transformador  TINYINT(1)      NOT NULL DEFAULT 0,
    descripcion             VARCHAR(500)    NULL,
    estado_activo          TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion    DATETIME        NULL,
    version                INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_tipo_equipo_codigo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE marcas (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    nombre          VARCHAR(100)    NOT NULL,
    descripcion     VARCHAR(255)    NULL,
    estado_activo   TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_marca_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE proveedores (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    ruc                 VARCHAR(20)     NOT NULL,
    razon_social        VARCHAR(255)    NOT NULL,
    nombre_comercial    VARCHAR(255)    NULL,
    direccion           VARCHAR(500)    NULL,
    telefono            VARCHAR(20)     NULL,
    correo              VARCHAR(255)    NULL,
    contacto_nombre     VARCHAR(255)    NULL,
    contacto_telefono   VARCHAR(20)     NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    fecha_baja          DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_proveedor_ruc (ruc),
    INDEX idx_proveedor_razon (razon_social),
    CONSTRAINT fk_proveedor_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE motivos_psr (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo              VARCHAR(20)     NOT NULL,
    nombre              VARCHAR(100)    NOT NULL,
    descripcion         VARCHAR(255)    NULL,
    tipo_equipo_id      BIGINT          NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_motivo_psr_codigo (codigo),
    INDEX idx_motivo_psr_tipo_equipo (tipo_equipo_id),
    CONSTRAINT fk_motivo_psr_tipo_equipo FOREIGN KEY (tipo_equipo_id) REFERENCES tipos_equipos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tipos_averias (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo              VARCHAR(20)     NOT NULL,
    nombre              VARCHAR(100)    NOT NULL,
    descripcion         VARCHAR(255)    NULL,
    es_critico          TINYINT(1)      NOT NULL DEFAULT 0,
    requiere_evidencia  TINYINT(1)      NOT NULL DEFAULT 0,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_tipo_averia_codigo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE estados_averias (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    codigo          VARCHAR(20)     NOT NULL,
    nombre          VARCHAR(50)     NOT NULL,
    descripcion     VARCHAR(255)    NULL,
    fecha_creacion  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_estado_averia_codigo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE psr (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    numero              VARCHAR(50)     NOT NULL,
    campaña_id          BIGINT          NOT NULL,
    sitio_id            BIGINT          NOT NULL,
    motivo_id           BIGINT          NOT NULL,
    descripcion         TEXT            NULL,
    fecha_solicitud     DATE            NOT NULL,
    estado              VARCHAR(50)     NOT NULL DEFAULT 'ACTIVO',
    observaciones       TEXT            NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_psr_numero (numero),
    INDEX idx_psr_campaña (campaña_id),
    INDEX idx_psr_sitio (sitio_id),
    INDEX idx_psr_estado (estado),
    CONSTRAINT fk_psr_campaña FOREIGN KEY (campaña_id) REFERENCES campañas(id),
    CONSTRAINT fk_psr_sitio FOREIGN KEY (sitio_id) REFERENCES sitios(id),
    CONSTRAINT fk_psr_motivo FOREIGN KEY (motivo_id) REFERENCES motivos_psr(id),
    CONSTRAINT fk_psr_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE osr (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    numero              VARCHAR(50)     NOT NULL,
    psr_id              BIGINT          NOT NULL,
    equipo_id           BIGINT          NULL,
    fecha_asignacion    DATE            NOT NULL,
    hora_inicio         TIME            NULL,
    hora_fin            TIME            NULL,
    horometro_inicio    DECIMAL(10,2)   NULL,
    horometro_fin       DECIMAL(10,2)   NULL,
    estado              VARCHAR(50)     NOT NULL DEFAULT 'PENDIENTE',
    observaciones       TEXT            NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_osr_numero (numero),
    INDEX idx_osr_psr (psr_id),
    INDEX idx_osr_equipo (equipo_id),
    CONSTRAINT fk_osr_psr FOREIGN KEY (psr_id) REFERENCES psr(id),
    CONSTRAINT fk_osr_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE equipos (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo              VARCHAR(50)     NOT NULL,
    numero_serie        VARCHAR(100)    NOT NULL,
    marca               VARCHAR(100)    NULL,
    modelo              VARCHAR(100)    NULL,
    campaña_id          BIGINT          NULL,
    osr_id              BIGINT          NULL,
    proveedor_id        BIGINT          NOT NULL,
    tipo_equipo_id      BIGINT          NOT NULL,
    marca_id            BIGINT          NULL,
    estado              VARCHAR(50)     NOT NULL DEFAULT 'DISPONIBLE',
    bateria_tipo        VARCHAR(50)     NULL,
    bateria_horas       DECIMAL(5,2)    NULL,
    bateria_voltaje     VARCHAR(20)     NULL,
    cargador_info       VARCHAR(255)    NULL,
    transformador_info  VARCHAR(255)    NULL,
    fecha_ingreso       DATE            NULL,
    fecha_devolucion    DATE            NULL,
    horometro_actual    DECIMAL(10,2)   NULL,
    observaciones       TEXT            NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    fecha_baja          DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_equipo_codigo (codigo),
    INDEX idx_equipo_serie (numero_serie),
    INDEX idx_equipo_campaña (campaña_id),
    INDEX idx_equipo_proveedor (proveedor_id),
    INDEX idx_equipo_tipo (tipo_equipo_id),
    INDEX idx_equipo_estado (estado),
    INDEX idx_equipo_osr (osr_id),
    CONSTRAINT fk_equipo_campaña FOREIGN KEY (campaña_id) REFERENCES campañas(id),
    CONSTRAINT fk_equipo_osr FOREIGN KEY (osr_id) REFERENCES osr(id),
    CONSTRAINT fk_equipo_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedores(id),
    CONSTRAINT fk_equipo_tipo_equipo FOREIGN KEY (tipo_equipo_id) REFERENCES tipos_equipos(id),
    CONSTRAINT fk_equipo_marca FOREIGN KEY (marca_id) REFERENCES marcas(id),
    CONSTRAINT fk_equipo_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE osr ADD CONSTRAINT fk_osr_equipo FOREIGN KEY (equipo_id) REFERENCES equipos(id);

CREATE TABLE averias (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    codigo              VARCHAR(50)     NOT NULL,
    equipo_id           BIGINT          NOT NULL,
    tipo_averia_id      BIGINT          NOT NULL,
    estado_averia_id    BIGINT          NOT NULL,
    proveedor_id        BIGINT          NOT NULL,
    descripcion_falla  TEXT            NOT NULL,
    descripcion_atencion TEXT           NULL,
    accion_correctiva  TEXT            NULL,
    fecha_reporte       DATETIME        NOT NULL,
    fecha_atencion      DATETIME        NULL,
    fecha_cierre       DATETIME        NULL,
    horas_inactivo      DECIMAL(10,2)   NULL,
    usuario_reporta_id  BIGINT          NOT NULL,
    usuario_atiende_id  BIGINT          NULL,
    observaciones       TEXT            NULL,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    usuario_creacion    BIGINT          NOT NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_averia_codigo (codigo),
    INDEX idx_averia_equipo (equipo_id),
    INDEX idx_averia_tipo (tipo_averia_id),
    INDEX idx_averia_estado (estado_averia_id),
    INDEX idx_averia_fecha (fecha_reporte),
    INDEX idx_averia_proveedor (proveedor_id),
    CONSTRAINT fk_averia_equipo FOREIGN KEY (equipo_id) REFERENCES equipos(id),
    CONSTRAINT fk_averia_tipo_averia FOREIGN KEY (tipo_averia_id) REFERENCES tipos_averias(id),
    CONSTRAINT fk_averia_estado_averia FOREIGN KEY (estado_averia_id) REFERENCES estados_averias(id),
    CONSTRAINT fk_averia_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedores(id),
    CONSTRAINT fk_averia_usuario_reporta FOREIGN KEY (usuario_reporta_id) REFERENCES usuarios(id),
    CONSTRAINT fk_averia_usuario_atiende FOREIGN KEY (usuario_atiende_id) REFERENCES usuarios(id),
    CONSTRAINT fk_averia_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE evidencias (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    equipo_id           BIGINT          NULL,
    averia_id           BIGINT          NULL,
    osr_id              BIGINT          NULL,
    tipo                VARCHAR(50)     NOT NULL,
    nombre_original     VARCHAR(255)    NOT NULL,
    nombre_archivo      VARCHAR(255)    NOT NULL,
    ruta                VARCHAR(500)    NOT NULL,
    tamano              INT             NOT NULL,
    ancho               INT             NULL,
    alto                INT             NULL,
    formato             VARCHAR(20)     NOT NULL,
    descripcion         VARCHAR(255)    NULL,
    usuario_id          BIGINT          NOT NULL,
    fecha_carga         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado_activo       TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_baja          DATETIME        NULL,
    version             INT             NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX idx_evidencia_equipo (equipo_id),
    INDEX idx_evidencia_averia (averia_id),
    INDEX idx_evidencia_osr (osr_id),
    INDEX idx_evidencia_tipo (tipo),
    INDEX idx_evidencia_fecha (fecha_carga),
    CONSTRAINT fk_evidencia_equipo FOREIGN KEY (equipo_id) REFERENCES equipos(id),
    CONSTRAINT fk_evidencia_averia FOREIGN KEY (averia_id) REFERENCES averias(id),
    CONSTRAINT fk_evidencia_osr FOREIGN KEY (osr_id) REFERENCES osr(id),
    CONSTRAINT fk_evidencia_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE logs_auditoria (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id      BIGINT          NULL,
    nombre_usuario VARCHAR(255)    NULL,
    modulo          VARCHAR(100)   NOT NULL,
    accion          VARCHAR(50)     NOT NULL,
    tipo_entidad    VARCHAR(100)    NULL,
    id_entidad      BIGINT          NULL,
    valor_anterior  JSON            NULL,
    valor_nuevo     JSON            NULL,
    ip_cliente      VARCHAR(45)     NULL,
    user_agent      TEXT            NULL,
    fecha_evento    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalles        TEXT            NULL,
    PRIMARY KEY (id),
    INDEX idx_auditoria_usuario (usuario_id),
    INDEX idx_auditoria_modulo (modulo),
    INDEX idx_auditoria_entidad (tipo_entidad, id_entidad),
    INDEX idx_auditoria_fecha (fecha_evento),
    CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE configuraciones (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    clave               VARCHAR(100)    NOT NULL,
    valor               TEXT            NOT NULL,
    descripcion         VARCHAR(255)    NULL,
    tipo                VARCHAR(50)     NOT NULL,
    categoria           VARCHAR(50)     NOT NULL,
    fecha_creacion      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME        NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_config_clave (clave),
    INDEX idx_config_categoria (categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO roles (nombre, descripcion, estado_activo) VALUES
('ADMIN', 'Administrador del sistema - Acceso total', 1),
('USER', 'Usuario operativo - Acceso limitado', 1);

INSERT INTO estados_averias (codigo, nombre, descripcion) VALUES
('PENDIENTE', 'Pendiente', 'Averia reportada sin atencion'),
('EN_ATENCION', 'En Atencion', 'Averia en proceso de solucion'),
('CERRADA', 'Cerrada', 'Averia resuelta');

INSERT INTO configuraciones (clave, valor, descripcion, tipo, categoria) VALUES
('SESSION_TIMEOUT_MINUTES', '15', 'Tiempo de expiracion de sesion en minutos', 'NUMBER', 'SEGURIDAD'),
('REFRESH_TOKEN_HOURS', '24', 'Tiempo de expiracion del token de refresco', 'NUMBER', 'SEGURIDAD'),
('MAX_UPLOAD_SIZE_MB', '5', 'Tamano maximo de archivos subidos', 'NUMBER', 'STORAGE'),
('IMAGE_MAX_WIDTH', '1080', 'Ancho maximo de imagenes', 'NUMBER', 'STORAGE'),
('IMAGE_MAX_HEIGHT', '720', 'Alto maximo de imagenes', 'NUMBER', 'STORAGE'),
('TIMEZONE', 'America/Lima', 'Zona horaria del sistema', 'STRING', 'GENERAL'),
('DATE_FORMAT', 'yyyy-MM-dd', 'Formato de fecha', 'STRING', 'GENERAL');

SELECT 'Base de datos creada exitosamente' AS mensaje;