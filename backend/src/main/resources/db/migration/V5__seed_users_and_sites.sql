SET FOREIGN_KEY_CHECKS = 0;

INSERT IGNORE INTO sitios (codigo, nombre, descripcion, direccion, estado_activo, usuario_creacion, version)
VALUES ('PACK-UVA-01', 'Packing Uva', 'Planta de procesamiento de uva', 'Carretera Panamericana km 45', 1, 1, 0);

INSERT IGNORE INTO usuarios (id_microsoft, correo, nombre, rol_id, sitio_id, estado_activo, usuario_creacion, version)
VALUES ('demo-microsoft-id', 'usuario.demo@dominioempresa.com', 'Usuario Demo', (SELECT id FROM roles WHERE nombre = 'ADMIN'), (SELECT id FROM sitios WHERE codigo = 'PACK-UVA-01'), 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
