# SOFTWARE DEVELOPMENT DOCUMENT (SDD)
# 06_DATABASE_DESIGN.md

---

# 1. Control Documental

| Campo | Valor |
|---|---|
| Documento | 06_DATABASE_DESIGN.md |
| Proyecto | Sistema de Control Operativo de Equipos de Apilamiento |
| Tipo Documento | Software Development Document - Diseño Base de Datos |
| Estado | Completado |
| Versión | 1.0 |
| Fecha | 2026-05-19 |
| Responsable | Jose Anyarin |
| Repositorio | GitHub |
| Clasificación | Interno |

---

# 2. Objetivo del Documento

El presente documento tiene como finalidad definir el diseño completo de la base de datos del sistema de control operativo de equipos de apilamiento, incluyendo todas las tablas, campos, relaciones, índices y restricciones.

---

# 3. Tecnología Base de Datos

| Aspecto | Valor |
|---------|-------|
| Motor | MySQL 8.x |
| Charset | utf8mb4 |
| Collate | utf8mb4_unicode_ci |
| Engine | InnoDB |
| Timezone | America/Lima (UTC-5) |
| Migraciones | Flyway |

---

# 4. Convenciones de Diseño

## 4.1 Convenciones de Nombres

| Elemento | Convención | Ejemplo |
|----------|------------|---------|
| Tablas | snake_case plural | equipos, campanas, proveedores |
| Primary Key | id (BIGINT AUTO_INCREMENT) | id |
| Foreign Key | tabla_id | sitio_id, campana_id |
| Timestamps | fecha_creacion, fecha_actualizacion | fecha_creacion DATETIME |
| Booleanos | estado_activo | TINYINT(1) |
| Soft Delete | estado_activo + fecha_baja | estado_activo + fecha_baja |
| Índices | idx_tabla_campo | idx_equipo_campana |

## 4.2 Campos Comunes (Todas las Tablas)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT | Primary Key autoincremental |
| estado_activo | TINYINT(1) | 1 = activo, 0 = inactivo |
| fecha_creacion | DATETIME | Fecha de creación del registro |
| fecha_actualizacion | DATETIME | Fecha de última modificación |
| fecha_baja | DATETIME | Fecha de eliminación lógica |
| usuario_creacion | BIGINT | Usuario que creó el registro |
| usuario_actualizacion | BIGINT | Usuario que actualizó el registro |
| version | INT | Optimistic locking |

---

# 5. Modelo de Datos

## 5.1 Lista de Tablas

| # | Tabla | Descripción |
|---|-------|-------------|
| 1 | roles | Roles operativos del sistema (ADMIN, USER) |
| 2 | sitios | Sedes operativas |
| 3 | usuarios | Usuarios del sistema |
| 4 | campanas | Campañas agrícolas |
| 5 | tipos_equipos | Tipos de equipos |
| 6 | marcas | Marcas de equipos |
| 7 | proveedores | Proveedores de equipos |
| 8 | motivos_psr | Motivos de PSR |
| 9 | tipos_averías | Tipos de averías |
| 10 | estados_averías | Estados de averías |
| 11 | psr | Pedidos de Servicio de Requerimiento |
| 12 | osr | Órdenes de Servicio de Requerimiento |
| 13 | equipos | Equipos alquilados |
| 14 | averías | Averías de equipos |
| 15 | evidencias | Evidencias fotográficas |
| 16 | logs_auditoría | Registro de auditoría |
| 17 | configuraciones | Configuraciones del sistema |

---

# 6. Detalle de Tablas

## 6.1 Tablas de Seguridad y Usuarios

### roles

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| nombre | VARCHAR(50) | NO | - | ADMIN, USER |
| descripcion | VARCHAR(255) | YES | - | Descripción del rol |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |

**Índices:** UNIQUE INDEX idx_rol_nombre (nombre)

---

### sitios

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(50) | NO | - | Código operativo único |
| nombre | VARCHAR(255) | NO | - | Nombre de la sede |
| descripcion | VARCHAR(500) | YES | - | Descripción |
| direccion | VARCHAR(500) | YES | - | Dirección |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_sitio_codigo (codigo), INDEX idx_sitio_nombre (nombre)

---

### usuarios

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| id_microsoft | VARCHAR(255) | NO | - | ID Azure AD |
| correo | VARCHAR(255) | NO | - | Correo corporativo |
| nombre | VARCHAR(255) | NO | - | Nombre completo |
| puesto | VARCHAR(255) | YES | - | Puesto Microsoft |
| area | VARCHAR(255) | YES | - | Área Microsoft |
| empresa | VARCHAR(255) | YES | - | Empresa Microsoft |
| departamento | VARCHAR(255) | YES | - | Departamento Microsoft |
| ubicacion | VARCHAR(255) | YES | - | Ubicación Microsoft |
| rol_id | BIGINT | NO | - | FK → roles |
| sitio_id | BIGINT | YES | - | FK → sitios |
| ultimo_acceso | DATETIME | YES | - | Último inicio sesión |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| usuario_actualizacion | BIGINT | YES | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_usuario_correo (correo), UNIQUE idx_usuario_microsoft (id_microsoft), INDEX idx_usuario_rol (rol_id), INDEX idx_usuario_sitio (sitio_id)

**FK:** rol_id → roles, sitio_id → sitios, usuario_creacion → usuarios

---

## 5.2 Tablas de Organización

### campanas

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(50) | NO | - | UVA-2026, ARA-2026 |
| nombre | VARCHAR(255) | NO | - | Nombre campaña |
| tipo | VARCHAR(50) | NO | - | UVA, ARANDANO |
| sitio_id | BIGINT | NO | - | FK → sitios |
| fecha_inicio | DATE | NO | - | Fecha inicio campaña |
| fecha_fin | DATE | YES | - | Fecha fin campaña |
| estado | VARCHAR(50) | NO | ACTIVA | ACTIVA, CERRADA |
| es_activa | TINYINT(1) | NO | 0 | Bandera campaña activa |
| descripcion | VARCHAR(500) | YES | - | Descripción |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_campana_codigo (codigo), INDEX idx_campana_sitio (sitio_id), INDEX idx_campana_estado (estado), INDEX idx_campana_activa (es_activa)

**FK:** sitio_id → sitios, usuario_creacion → usuarios

---

## 5.3 Tablas de Maestros y Catálogos

### tipos_equipos

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(50) | NO | - | Código único |
| nombre | VARCHAR(255) | NO | - | Nombre tipo |
| categoria | VARCHAR(50) | NO | - | TRANSPALETA, APILADOR, MONTACARGAS |
| tecnologia_bateria | VARCHAR(50) | YES | - | LITIO, PLOMO |
| requiere_horometro | TINYINT(1) | NO | 0 | Requiere horómetro |
| requiere_bateria | TINYINT(1) | NO | 0 | Requiere info batería |
| requiere_cargador | TINYINT(1) | NO | 0 | Requiere info cargador |
| requiere_transformador | TINYINT(1) | NO | 0 | Requiere transformador |
| descripcion | VARCHAR(500) | YES | - | Descripción |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_tipo_equipo_codigo (codigo)

---

### marcas

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| nombre | VARCHAR(100) | NO | - | Nombre marca |
| descripcion | VARCHAR(255) | YES | - | Descripción |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |

**Índices:** UNIQUE idx_marca_nombre (nombre)

---

### proveedores

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| ruc | VARCHAR(20) | NO | - | RUC proveedor |
| razon_social | VARCHAR(255) | NO | - | Razón social |
| nombre_comercial | VARCHAR(255) | YES | - | Nombre comercial |
| direccion | VARCHAR(500) | YES | - | Dirección |
| telefono | VARCHAR(20) | YES | - | Teléfono |
| correo | VARCHAR(255) | YES | - | Email contacto |
| contacto_nombre | VARCHAR(255) | YES | - | Nombre contacto |
| contacto_telefono | VARCHAR(20) | YES | - | Teléfono contacto |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_proveedor_ruc (ruc), INDEX idx_proveedor_razon (razon_social)

**FK:** usuario_creacion → usuarios

---

### motivos_psr

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(20) | NO | - | Código motivo |
| nombre | VARCHAR(100) | NO | - | Nombre motivo |
| descripcion | VARCHAR(255) | YES | - | Descripción |
| tipo_equipo_id | BIGINT | YES | - | FK → tipos_equipos |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |

**Índices:** UNIQUE idx_motivo_psr_codigo (codigo), INDEX idx_motivo_psr_tipo_equipo (tipo_equipo_id)

**FK:** tipo_equipo_id → tipos_equipos

---

### tipos_averías

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(20) | NO | - | Código tipo |
| nombre | VARCHAR(100) | NO | - | Nombre tipo |
| descripcion | VARCHAR(255) | YES | - | Descripción |
| es_critico | TINYINT(1) | NO | 0 | Avería crítica |
| requiere_evidencia | TINYINT(1) | NO | 0 | Requiere evidencia |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |

**Índices:** UNIQUE idx_tipo_averia_codigo (codigo)

---

### estados_averías

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(20) | NO | - | Código estado |
| nombre | VARCHAR(50) | NO | - | Nombre |
| descripcion | VARCHAR(255) | YES | - | Descripción |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |

**Índices:** UNIQUE idx_estado_averia_codigo (codigo)

---

## 5.4 Tablas Operativas

### psr

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| numero | VARCHAR(50) | NO | - | PSR-2026-0001 |
| campana_id | BIGINT | NO | - | FK → campanas |
| sitio_id | BIGINT | NO | - | FK → sitios |
| motivo_id | BIGINT | NO | - | FK → motivos_psr |
| descripcion | TEXT | YES | - | Descripción |
| fecha_solicitud | DATE | NO | - | Fecha solicitud |
| estado | VARCHAR(50) | NO | ACTIVO | ACTIVO, CERRADO |
| observaciones | TEXT | YES | - | Observaciones |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_psr_numero (numero), INDEX idx_psr_campana (campana_id), INDEX idx_psr_sitio (sitio_id), INDEX idx_psr_estado (estado)

**FK:** campana_id → campanas, sitio_id → sitios, motivo_id → motivos_psr, usuario_creacion → usuarios

---

### osr

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| numero | VARCHAR(50) | NO | - | OSR-2026-0001 |
| psr_id | BIGINT | NO | - | FK → psr |
| equipo_id | BIGINT | YES | - | FK → equipos |
| fecha_asignacion | DATE | NO | - | Fecha asignación |
| hora_inicio | TIME | YES | - | Hora inicio |
| hora_fin | TIME | YES | - | Hora fin |
| horometro_inicio | DECIMAL(10,2) | YES | - | Horómetro inicio |
| horometro_fin | DECIMAL(10,2) | YES | - | Horómetro fin |
| estado | VARCHAR(50) | NO | PENDIENTE | PENDIENTE, ASIGNADO, COMPLETADO, CANCELADO |
| observaciones | TEXT | YES | - | Observaciones |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_osr_numero (numero), INDEX idx_osr_psr (psr_id), INDEX idx_osr_equipo (equipo_id)

**FK:** psr_id → psr, equipo_id → equipos, usuario_creacion → usuarios

---

### equipos

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(50) | NO | - | EQ-00001 |
| numero_serie | VARCHAR(100) | NO | - | Número serie único |
| marca | VARCHAR(100) | YES | - | Marca equipo |
| modelo | VARCHAR(100) | YES | - | Modelo equipo |
| campana_id | BIGINT | YES | - | FK → campanas |
| osr_id | BIGINT | YES | - | FK → osr |
| proveedor_id | BIGINT | NO | - | FK → proveedores |
| tipo_equipo_id | BIGINT | NO | - | FK → tipos_equipos |
| marca_id | BIGINT | YES | - | FK → marcas |
| estado | VARCHAR(50) | NO | DISPONIBLE | DISPONIBLE, OPERATIVO, AVERIADO, MANTENIMIENTO, DEVUELTO |
| bateria_tipo | VARCHAR(50) | YES | - | Tipo batería |
| bateria_horas | DECIMAL(5,2) | YES | - | Horas batería |
| bateria_voltaje | VARCHAR(20) | YES | - | Voltaje batería |
| cargador_info | VARCHAR(255) | YES | - | Info cargador |
| transformador_info | VARCHAR(255) | YES | - | Info transformador |
| fecha_ingreso | DATE | YES | - | Fecha ingreso |
| fecha_devolucion | DATE | YES | - | Fecha devolución |
| horometro_actual | DECIMAL(10,2) | YES | - | Horómetro actual |
| observaciones | TEXT | YES | - | Observaciones |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_equipo_codigo (codigo), INDEX idx_equipo_serie (numero_serie), INDEX idx_equipo_campana (campana_id), INDEX idx_equipo_proveedor (proveedor_id), INDEX idx_equipo_tipo (tipo_equipo_id), INDEX idx_equipo_estado (estado), INDEX idx_equipo_osr (osr_id)

**FK:** campana_id → campanas, osr_id → osr, proveedor_id → proveedores, tipo_equipo_id → tipos_equipos, marca_id → marcas, usuario_creacion → usuarios

---

### averías

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| codigo | VARCHAR(50) | NO | - | AV-00001 |
| equipo_id | BIGINT | NO | - | FK → equipos |
| tipo_averia_id | BIGINT | NO | - | FK → tipos_averías |
| estado_averia_id | BIGINT | NO | - | FK → estados_averías |
| proveedor_id | BIGINT | NO | - | FK → proveedores |
| descripcion_falla | TEXT | NO | - | Descripción de falla |
| descripcion_atencion | TEXT | YES | - | Descripción atención |
| accion_correctiva | TEXT | YES | - | Acción correctiva |
| fecha_reporte | DATETIME | NO | - | Fecha reporte |
| fecha_atencion | DATETIME | YES | - | Fecha atención inicio |
| fecha_cierre | DATETIME | YES | - | Fecha cierre |
| horas_inactivo | DECIMAL(10,2) | YES | - | Horas inactivo |
| usuario_reporta_id | BIGINT | NO | - | FK → usuarios |
| usuario_atiende_id | BIGINT | YES | - | FK → usuarios |
| observaciones | TEXT | YES | - | Observaciones |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |
| usuario_creacion | BIGINT | NO | - | FK → usuarios |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** UNIQUE idx_averia_codigo (codigo), INDEX idx_averia_equipo (equipo_id), INDEX idx_averia_tipo (tipo_averia_id), INDEX idx_averia_estado (estado_averia_id), INDEX idx_averia_fecha (fecha_reporte), INDEX idx_averia_proveedor (proveedor_id)

**FK:** equipo_id → equipos, tipo_averia_id → tipos_averías, estado_averia_id → estados_averías, proveedor_id → proveedores, usuario_reporta_id → usuarios, usuario_atiende_id → usuarios, usuario_creacion → usuarios

---

### evidencias

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| equipo_id | BIGINT | YES | - | FK → equipos |
| averia_id | BIGINT | YES | - | FK → averías |
| osr_id | BIGINT | YES | - | FK → osr |
| tipo | VARCHAR(50) | NO | - | EQUIPO, AVERÍA, OSR, DEVOLUCION |
| nombre_original | VARCHAR(255) | NO | - | Nombre original |
| nombre_archivo | VARCHAR(255) | NO | - | Nombre UUID |
| ruta | VARCHAR(500) | NO | - | Ruta almacenamiento |
| tamano | INT | NO | - | Tamaño bytes |
| ancho | INT | YES | - | Ancho imagen |
| alto | INT | YES | - | Alto imagen |
| formato | VARCHAR(20) | NO | - | JPG, PNG |
| descripcion | VARCHAR(255) | YES | - | Descripción |
| usuario_id | BIGINT | NO | - | FK → usuarios |
| fecha_carga | DATETIME | NO | CURRENT_TIMESTAMP | Fecha carga |
| estado_activo | TINYINT(1) | NO | 1 | Estado activo |
| fecha_baja | DATETIME | YES | - | Fecha eliminación |
| version | INT | NO | 0 | Optimistic locking |

**Índices:** INDEX idx_evidencia_equipo (equipo_id), INDEX idx_evidencia_averia (averia_id), INDEX idx_evidencia_osr (osr_id), INDEX idx_evidencia_tipo (tipo), INDEX idx_evidencia_fecha (fecha_carga)

**FK:** equipo_id → equipos, averia_id → averías, osr_id → osr, usuario_id → usuarios

---

## 5.5 Tablas de Auditoría y Configuración

### logs_auditoría

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| usuario_id | BIGINT | YES | - | FK → usuarios |
| nombre_usuario | VARCHAR(255) | YES | - | Username |
| modulo | VARCHAR(100) | NO | - | Módulo afectado |
| accion | VARCHAR(50) | NO | - | CREATE, UPDATE, DELETE, LOGIN, LOGOUT |
| tipo_entidad | VARCHAR(100) | YES | - | Tipo entidad |
| id_entidad | BIGINT | YES | - | ID entidad |
| valor_anterior | JSON | YES | - | Valores anteriores |
| valor_nuevo | JSON | YES | - | Valores nuevos |
| ip_cliente | VARCHAR(45) | YES | - | IP cliente |
| user_agent | TEXT | YES | - | User agent |
| fecha_evento | DATETIME | NO | CURRENT_TIMESTAMP | Fecha evento |
| detalles | TEXT | YES | - | Detalles |

**Índices:** INDEX idx_auditoria_usuario (usuario_id), INDEX idx_auditoria_modulo (modulo), INDEX idx_auditoria_entidad (tipo_entidad, id_entidad), INDEX idx_auditoria_fecha (fecha_evento)

**FK:** usuario_id → usuarios

---

### configuraciones

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| id | BIGINT | NO | AUTO_INCREMENT | Primary Key |
| clave | VARCHAR(100) | NO | - | Clave configuración |
| valor | TEXT | NO | - | Valor configuración |
| descripcion | VARCHAR(255) | YES | - | Descripción |
| tipo | VARCHAR(50) | NO | - | STRING, NUMBER, BOOLEAN, JSON |
| categoria | VARCHAR(50) | NO | - | SEGURIDAD, SESION, STORAGE, GENERAL |
| fecha_creacion | DATETIME | NO | CURRENT_TIMESTAMP | Fecha creación |
| fecha_actualizacion | DATETIME | YES | - | Fecha actualización |

**Índices:** UNIQUE idx_config_clave (clave), INDEX idx_config_categoria (categoria)

---

# 7. Relaciones entre Tablas

```
roles
  ↓ (1:N)
usuarios

sitios
  ↓ (1:N)
  ├── campanas
  └── usuarios

sitios ← (1:N) → psr

campanas ← (1:N) → psr
campanas ← (1:N) → equipos

tipos_equipos ← (1:N) → equipos
tipos_equipos ← (1:N) → motivos_psr

marcas ← (1:N) → equipos

proveedores ← (1:N) → equipos
proveedores ← (1:N) → averías

psr ← (1:N) → osr
osr ← (0,1:1) → equipos
osr ← (1:N) → evidencias

equipos ← (1:N) → averías
equipos ← (1:N) → evidencias

averías ← (1:N) → evidencias

tipos_averías ← (1:N) → averías
estados_averías ← (1:N) → averías

usuarios ← (1:N) → averías
usuarios ← (1:N) → evidencias
usuarios ← (1:N) → logs_auditoría
```

---

# 8. Identificadores Funcionales

| Entidad | Formato | Ejemplo |
|---------|---------|---------|
| Campaña Uva | UVA-AAAA | UVA-2026 |
| Campaña Arándano | ARA-AAAA | ARA-2026 |
| Equipo | EQ-AAAAA | EQ-00001 |
| Avería | AV-AAAAA | AV-00001 |
| PSR | PSR-AAAA-NNNN | PSR-2026-0001 |
| OSR | OSR-AAAA-NNNN | OSR-2026-0001 |

---

# 9. Datos Iniciales (Seed)

## roles

| nombre | descripcion | estado_activo |
|--------|-------------|----------------|
| ADMIN | Administrador del sistema - Acceso total | 1 |
| USER | Usuario operativo - Acceso limitado | 1 |

## estados_averías

| codigo | nombre | descripcion |
|--------|--------|-------------|
| PENDIENTE | Pendiente | Avería reportada sin atención |
| EN_ATENCION | En Atención | Avería en proceso de solución |
| CERRADA | Cerrada | Avería resuelta |

## configuraciones

| clave | valor | descripcion | tipo | categoria |
|-------|-------|-------------|------|-----------|
| SESSION_TIMEOUT_MINUTES | 15 | Tiempo de expiración de sesión en minutos | NUMBER | SEGURIDAD |
| REFRESH_TOKEN_HOURS | 24 | Tiempo de expiración del token de refresco | NUMBER | SEGURIDAD |
| MAX_UPLOAD_SIZE_MB | 5 | Tamaño máximo de archivos subidos | NUMBER | STORAGE |
| IMAGE_MAX_WIDTH | 1080 | Ancho máximo de imágenes | NUMBER | STORAGE |
| IMAGE_MAX_HEIGHT | 720 | Alto máximo de imágenes | NUMBER | STORAGE |
| TIMEZONE | America/Lima | Zona horaria del sistema | STRING | GENERAL |
| DATE_FORMAT | yyyy-MM-dd | Formato de fecha | STRING | GENERAL |

---

# 10. Reglas de Integridad

| Regla | Descripción |
|-------|-------------|
| Integridad Referencial | Todas las FK tienen ON DELETE RESTRICT |
| Soft Delete | Eliminación lógica mediante estado_activo + fecha_baja |
| Optimistic Locking | Campo version en tablas operativas |
| Unicidad | Códigos funcionales únicos en cada tabla |
| Timestamps | Todos los registros tienen fecha_creacion y fecha_actualizacion |

---

# 11. Scripts de Base de Datos

El script de creación completo se encuentra en:
```
scripts/01_create_tables.sql
```

---

# 12. Aprobaciones

| Rol | Responsable | Estado |
|---|---|---|
| Responsable Funcional | Jose Anyarin | ✅ Completado |
| Responsable Técnico | Jose Anyarin | ✅ Completado |
| Aprobación Final | Pendiente | Pendiente |

---

# 13. Historial de Cambios

| Versión | Fecha | Descripción | Autor |
|---------|-------|--------------|-------|
| 1.0 | 2026-05-19 | Versión inicial diseño base de datos | Jose Anyarin |

