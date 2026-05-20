CREATE TABLE tipos_equipos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(50) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    tecnologia_bateria VARCHAR(50) NULL,
    requiere_horometro TINYINT(1) NOT NULL DEFAULT 0,
    requiere_bateria TINYINT(1) NOT NULL DEFAULT 0,
    requiere_cargador TINYINT(1) NOT NULL DEFAULT 0,
    requiere_transformador TINYINT(1) NOT NULL DEFAULT 0,
    descripcion VARCHAR(500) NULL,
    estado_activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    version INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_tipo_equipo_codigo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE marcas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NULL,
    estado_activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_marca_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE proveedores (
    id BIGINT NOT NULL AUTO_INCREMENT,
    ruc VARCHAR(20) NOT NULL,
    razon_social VARCHAR(255) NOT NULL,
    nombre_comercial VARCHAR(255) NULL,
    direccion VARCHAR(500) NULL,
    telefono VARCHAR(20) NULL,
    correo VARCHAR(255) NULL,
    contacto_nombre VARCHAR(255) NULL,
    contacto_telefono VARCHAR(20) NULL,
    estado_activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    fecha_baja DATETIME NULL,
    usuario_creacion BIGINT NULL,
    version INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_proveedor_ruc (ruc),
    INDEX idx_proveedor_razon (razon_social),
    CONSTRAINT fk_proveedor_usuario_creacion FOREIGN KEY (usuario_creacion) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE motivos_psr (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NULL,
    tipo_equipo_id BIGINT NULL,
    estado_activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_motivo_psr_codigo (codigo),
    INDEX idx_motivo_psr_tipo_equipo (tipo_equipo_id),
    CONSTRAINT fk_motivo_psr_tipo_equipo FOREIGN KEY (tipo_equipo_id) REFERENCES tipos_equipos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tipos_averias (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NULL,
    es_critico TINYINT(1) NOT NULL DEFAULT 0,
    requiere_evidencia TINYINT(1) NOT NULL DEFAULT 0,
    estado_activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_tipo_averia_codigo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE estados_averias (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_estado_averia_codigo (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
