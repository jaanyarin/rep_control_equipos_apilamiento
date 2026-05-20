CREATE TABLE logs_auditoria (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NULL,
    nombre_usuario VARCHAR(255) NULL,
    modulo VARCHAR(100) NOT NULL,
    accion VARCHAR(50) NOT NULL,
    tipo_entidad VARCHAR(100) NULL,
    id_entidad BIGINT NULL,
    valor_anterior JSON NULL,
    valor_nuevo JSON NULL,
    ip_cliente VARCHAR(45) NULL,
    user_agent TEXT NULL,
    fecha_evento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalles TEXT NULL,
    PRIMARY KEY (id),
    INDEX idx_auditoria_usuario (usuario_id),
    INDEX idx_auditoria_modulo (modulo),
    INDEX idx_auditoria_entidad (tipo_entidad, id_entidad),
    INDEX idx_auditoria_fecha (fecha_evento),
    CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE configuraciones (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(100) NOT NULL,
    valor TEXT NOT NULL,
    descripcion VARCHAR(255) NULL,
    tipo VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX idx_config_clave (clave),
    INDEX idx_config_categoria (categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO roles (nombre, descripcion, estado_activo) VALUES
('ADMIN', 'Administrador del sistema - Acceso total', 1),
('USER', 'Usuario operativo - Acceso limitado', 1);

INSERT INTO usuarios (id_microsoft, correo, nombre, rol_id, estado_activo, fecha_creacion, usuario_creacion) VALUES
('admin@dominioempresa.com', 'admin@dominioempresa.com', 'Administrador Inicial', 1, 1, CURRENT_TIMESTAMP, NULL);

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
