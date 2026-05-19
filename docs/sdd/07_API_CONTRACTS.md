# SOFTWARE DEVELOPMENT DOCUMENT (SDD)
# 07_API_CONTRACTS.md

---

# 1. Control Documental

| Campo | Valor |
|---|---|
| Documento | 07_API_CONTRACTS.md |
| Proyecto | Sistema de Control Operativo de Equipos de Apilamiento |
| Tipo Documento | Software Development Document - Contratos de API |
| Estado | Completado |
| Versión | 1.0 |
| Fecha | 2026-05-19 |
| Responsable | Jose Anyarin |
| Repositorio | GitHub |
| Clasificación | Interno |

---

# 2. Objetivo del Documento

El presente documento tiene como finalidad definir los contratos completos de las APIs REST del sistema de control operativo de equipos de apilamiento, incluyendo todos los endpoints, métodos, parámetros, formatos de request y response.

---

# 3. Convenciones Generales

## 3.1 Configuración Base

| Aspecto | Valor |
|---------|-------|
| Base URL | /api/v1 |
| Formato | JSON |
| Autenticación | JWT (Bearer Token) |
| Charset | UTF-8 |
| Timezone | America/Lima (UTC-5) |

## 3.2 Métodos HTTP

| Método | Uso | Descripción |
|--------|-----|-------------|
| GET | Consulta | Obtener recursos |
| POST | Creación | Crear nuevos recursos |
| PUT | Actualización | Actualizar recursos existentes |
| DELETE | Desactivación | Eliminación lógica (soft delete) |

## 3.3 Paginación

Todos los endpoints de lista soportan paginación:

| Parámetro | Tipo | Default | Descripción |
|-----------|------|---------|-------------|
| page | INTEGER | 0 | Número de página |
| size | INTEGER | 20 | Elementos por página |
| sort | STRING | - | Ordenamiento (campo,direction) |

Ejemplo: `GET /api/v1/equipos?page=0&size=20&sort=fecha_creacion,desc`

---

# 4. Formato de Respuestas

## 4.1 Respuesta Exitosa

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": { },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

## 4.2 Respuesta con Paginación

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": {
    "content": [ ],
    "totalElements": 150,
    "totalPages": 8,
    "currentPage": 0,
    "pageSize": 20
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

## 4.3 Respuesta de Error

```json
{
  "success": false,
  "message": "Descripción del error",
  "errorCode": "ERR001",
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

## 4.4 Códigos de Error Comunes

| Código | Descripción |
|--------|-------------|
| ERR001 | Error de validación |
| ERR002 | Recurso no encontrado |
| ERR003 | Token expirado |
| ERR004 | Token inválido |
| ERR005 | Acceso denegado |
| ERR006 | Error de servidor |
| ERR007 | Parámetro inválido |

---

# 5. Endpoints de Autenticación

## 5.1 Iniciar Sesión

```
POST /api/v1/auth/login
```

**Request:**
```json
{
  "email": "usuario@dominioempresa.com"
}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
    "expiresIn": 900,
    "user": {
      "id": 1,
      "nombre": "Juan Perez",
      "correo": "juan@dominioempresa.com",
      "rol": "ADMIN",
      "sitio": "Packing Uva"
    }
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 5.2 Renovar Token

```
POST /api/v1/auth/refresh
```

**Request:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Token renovado",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "expiresIn": 900
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 5.3 Cerrar Sesión

```
POST /api/v1/auth/logout
```

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Sesión cerrada correctamente",
  "data": null,
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

# 6. Endpoints de Usuarios

## 6.1 Listar Usuarios

```
GET /api/v1/users
```

**Parámetros Query:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| page | INTEGER | Página |
| size | INTEGER | Tamaño |
| sort | STRING | Ordenamiento |
| filtro | STRING | Buscar por nombre/correo |
| rol_id | INTEGER | Filtrar por rol |
| sitio_id | INTEGER | Filtrar por sitio |
| estado | STRING | activo, inactivo |

**Response (200):**
```json
{
  "success": true,
  "message": "Usuarios obtenidos",
  "data": {
    "content": [
      {
        "id": 1,
        "nombre": "Juan Perez",
        "correo": "juan@dominioempresa.com",
        "puesto": "Operario",
        "rol": "ADMIN",
        "sitio": "Packing Uva",
        "ultimoAcceso": "2026-05-18T14:30:00-05:00",
        "estado": "activo",
        "fechaCreacion": "2026-01-01T08:00:00-05:00"
      }
    ],
    "totalElements": 25,
    "totalPages": 2
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 6.2 Crear Usuario

```
POST /api/v1/users
```

**Request:**
```json
{
  "correo": "juan@dominioempresa.com",
  "nombre": "Juan Perez",
  "rolId": 1,
  "sitioId": 1
}
```

**Response (201):**
```json
{
  "success": true,
  "message": "Usuario creado correctamente",
  "data": {
    "id": 2,
    "nombre": "Juan Perez",
    "correo": "juan@dominioempresa.com",
    "rolId": 1,
    "estado": "activo"
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 6.3 Obtener Usuario

```
GET /api/v1/users/{id}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Usuario obtenido",
  "data": {
    "id": 1,
    "nombre": "Juan Perez",
    "correo": "juan@dominioempresa.com",
    "puesto": "Operario",
    "area": "Logistica",
    "empresa": "Empresa Corp",
    "departamento": "Operaciones",
    "ubicacion": "Lima",
    "rol": "ADMIN",
    "sitio": "Packing Uva",
    "ultimoAcceso": "2026-05-18T14:30:00-05:00",
    "estado": "activo",
    "fechaCreacion": "2026-01-01T08:00:00-05:00"
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 6.4 Actualizar Usuario

```
PUT /api/v1/users/{id}
```

**Request:**
```json
{
  "nombre": "Juan Perez Actualizado",
  "rolId": 2,
  "sitioId": 2
}
```

---

## 6.5 Desactivar Usuario

```
DELETE /api/v1/users/{id}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Usuario desactivado correctamente",
  "data": null,
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

# 7. Endpoints de Sitios

## 7.1 Listar Sitios

```
GET /api/v1/sites
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| filtro | Buscar por código/nombre |
| estado | activo, inactivo |

---

## 7.2 Crear Sitio

```
POST /api/v1/sites
```

**Request:**
```json
{
  "codigo": "PACK-UVA-01",
  "nombre": "Packing Uva",
  "descripcion": "Planta de empaque de uva",
  "direccion": "Carretera Panamericana km 45"
}
```

---

## 7.3 Obtener Sitio

```
GET /api/v1/sites/{id}
```

---

## 7.4 Actualizar Sitio

```
PUT /api/v1/sites/{id}
```

---

## 7.5 Desactivar Sitio

```
DELETE /api/v1/sites/{id}
```

---

# 8. Endpoints de Campañas

## 8.1 Listar Campañas

```
GET /api/v1/campaigns
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| sitio_id | Filtrar por sitio |
| estado | ACTIVA, CERRADA |
| es_activa | true/false |

---

## 8.2 Crear Campaña

```
POST /api/v1/campaigns
```

**Request:**
```json
{
  "codigo": "UVA-2026",
  "nombre": "Campaña Uva 2026",
  "tipo": "UVA",
  "sitioId": 1,
  "fechaInicio": "2026-01-01",
  "fechaFin": "2026-04-30",
  "descripcion": "Temporada de exportación de uva"
}
```

---

## 8.3 Obtener Campaña

```
GET /api/v1/campaigns/{id}
```

---

## 8.4 Actualizar Campaña

```
PUT /api/v1/campaigns/{id}
```

---

## 8.5 Activar Campaña

```
PUT /api/v1/campaigns/{id}/activate
```

**Response (200):**
```json
{
  "success": true,
  "message": "Campaña activada correctamente",
  "data": {
    "id": 1,
    "estado": "ACTIVA",
    "esActiva": true
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 8.6 Cerrar Campaña

```
PUT /api/v1/campaigns/{id}/close
```

---

# 9. Endpoints de Tipos de Equipos

## 9.1 Listar Tipos de Equipos

```
GET /api/v1/equipment-types
```

---

## 9.2 Crear Tipo de Equipo

```
POST /api/v1/equipment-types
```

**Request:**
```json
{
  "codigo": "TRANS-001",
  "nombre": "Transpaleta Electrica",
  "categoria": "TRANSPALETA",
  "tecnologiaBateria": "LITIO",
  "requiereHorometro": true,
  "requiereBateria": true,
  "descripcion": "Transpaleta para manejo de carga"
}
```

---

## 9.3 Obtener Tipo de Equipo

```
GET /api/v1/equipment-types/{id}
```

---

## 9.4 Actualizar Tipo de Equipo

```
PUT /api/v1/equipment-types/{id}
```

---

# 10. Endpoints de Proveedores

## 10.1 Listar Proveedores

```
GET /api/v1/providers
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| filtro | Buscar por RUC/razón social |
| estado | activo, inactivo |

---

## 10.2 Crear Proveedor

```
POST /api/v1/providers
```

**Request:**
```json
{
  "ruc": "20123456789",
  "razonSocial": "Equipos Agricolas S.A.C.",
  "nombreComercial": "Empresa Proveedora",
  "direccion": "Av. Industrial 123",
  "telefono": "012345678",
  "correo": "contacto@empresaproveedora.com",
  "contactoNombre": "Carlos Mendoza",
  "contactoTelefono": "987654321"
}
```

---

## 10.3 Obtener Proveedor

```
GET /api/v1/providers/{id}
```

---

## 10.4 Actualizar Proveedor

```
PUT /api/v1/providers/{id}
```

---

# 11. Endpoints de Equipos

## 11.1 Listar Equipos

```
GET /api/v1/equipment
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| campaña_id | Filtrar por campaña |
| proveedor_id | Filtrar por proveedor |
| tipo_equipo_id | Filtrar por tipo |
| estado | DISPONIBLE, OPERATIVO, AVERIADO, MANTENIMIENTO, DEVUELTO |
| filtro | Buscar por código/serie |

---

## 11.2 Crear Equipo

```
POST /api/v1/equipment
```

**Request:**
```json
{
  "codigo": "EQ-00001",
  "numeroSerie": "SN-2026-001",
  "marca": "Toyota",
  "modelo": "8FBE15",
  "campañaId": 1,
  "proveedorId": 1,
  "tipoEquipoId": 1,
  "marcaId": 1,
  "estado": "DISPONIBLE",
  "bateriaTipo": "LITIO",
  "bateriaHoras": 500,
  "bateriaVoltaje": "48V",
  "fechaIngreso": "2026-01-15"
}
```

---

## 11.3 Obtener Equipo

```
GET /api/v1/equipment/{id}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Equipo obtenido",
  "data": {
    "id": 1,
    "codigo": "EQ-00001",
    "numeroSerie": "SN-2026-001",
    "marca": "Toyota",
    "modelo": "8FBE15",
    "campaña": "UVA-2026",
    "proveedor": "Empresa Proveedora",
    "tipoEquipo": "Transpaleta Electrica",
    "estado": "OPERATIVO",
    "horometroActual": 150.5,
    "fechaIngreso": "2026-01-15",
    "historico": [
      {
        "tipo": "OSR",
        "numero": "OSR-2026-0001",
        "fecha": "2026-02-01"
      },
      {
        "tipo": "AVERIA",
        "codigo": "AV-00001",
        "descripcion": "Fallo motor",
        "fecha": "2026-03-15"
      }
    ]
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 11.4 Actualizar Equipo

```
PUT /api/v1/equipment/{id}
```

**Request:**
```json
{
  "horometroActual": 175.5,
  "observaciones": "Mantenimiento realizado"
}
```

---

## 11.5 Desactivar Equipo

```
DELETE /api/v1/equipment/{id}
```

---

# 12. Endpoints de PSR

## 12.1 Listar PSR

```
GET /api/v1/psr
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| campaña_id | Filtrar por campaña |
| sitio_id | Filtrar por sitio |
| estado | ACTIVO, CERRADO |
| filtro | Buscar por número |

---

## 12.2 Crear PSR

```
POST /api/v1/psr
```

**Request:**
```json
{
  "numero": "PSR-2026-0001",
  "campañaId": 1,
  "sitioId": 1,
  "motivoId": 1,
  "descripcion": "Se requiere equipo para carga",
  "fechaSolicitud": "2026-01-10",
  "observaciones": "Urgente"
}
```

---

## 12.3 Obtener PSR

```
GET /api/v1/psr/{id}
```

---

## 12.4 Actualizar PSR

```
PUT /api/v1/psr/{id}
```

---

# 13. Endpoints de OSR

## 13.1 Listar OSR

```
GET /api/v1/osr
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| psr_id | Filtrar por PSR |
| equipo_id | Filtrar por equipo |
| estado | PENDIENTE, ASIGNADO, COMPLETADO, CANCELADO |

---

## 13.2 Crear OSR

```
POST /api/v1/osr
```

**Request:**
```json
{
  "numero": "OSR-2026-0001",
  "psrId": 1,
  "equipoId": 1,
  "fechaAsignacion": "2026-01-15",
  "horaInicio": "08:00",
  "horaFin": "12:00",
  "horometroInicio": 100.0,
  "horometroFin": 105.0,
  "estado": "COMPLETADO",
  "observaciones": "Servicio completado"
}
```

---

## 13.3 Obtener OSR

```
GET /api/v1/osr/{id}
```

---

## 13.4 Actualizar OSR

```
PUT /api/v1/osr/{id}
```

---

# 14. Endpoints de Averías

## 14.1 Listar Averías

```
GET /api/v1/damages
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| equipo_id | Filtrar por equipo |
| tipo_averia_id | Filtrar por tipo |
| estado_averia_id | Filtrar por estado |
| proveedor_id | Filtrar por proveedor |
| fechaDesde | Filtrar desde fecha |
| fechaHasta | Filtrar hasta fecha |

---

## 14.2 Crear Avería

```
POST /api/v1/damages
```

**Request:**
```json
{
  "codigo": "AV-00001",
  "equipoId": 1,
  "tipoAveriaId": 1,
  "proveedorId": 1,
  "descripcionFalla": "Motor no responde",
  "fechaReporte": "2026-03-15T10:30:00-05:00"
}
```

**Response (201):**
```json
{
  "success": true,
  "message": "Avería creada y equipo marcado como averiado",
  "data": {
    "id": 1,
    "codigo": "AV-00001",
    "equipoId": 1,
    "estadoAveria": "PENDIENTE",
    "horasInactivo": 0,
    "estadoEquipo": "AVERIADO"
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 14.3 Obtener Avería

```
GET /api/v1/damages/{id}
```

---

## 14.4 Actualizar Avería

```
PUT /api/v1/damages/{id}
```

**Request:**
```json
{
  "tipoAveriaId": 2,
  "descripcionAtencion": "Se reemplazo motor",
  "accionCorrectiva": "Mantenimiento correctivo",
  "usuarioAtiendeId": 2,
  "estadoAveriaId": 2
}
```

---

## 14.5 Cerrar Avería

```
PUT /api/v1/damages/{id}/close
```

**Request:**
```json
{
  "descripcionAtencion": "Reparación completada",
  "accionCorrectiva": "Cambio de componente",
  "fechaCierre": "2026-03-20T14:00:00-05:00"
}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Avería cerrada correctamente",
  "data": {
    "id": 1,
    "estadoAveria": "CERRADA",
    "horasInactivo": 123.5,
    "estadoEquipo": "DISPONIBLE"
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

# 15. Endpoints de Evidencias

## 15.1 Subir Evidencia

```
POST /api/v1/evidences/upload
```

**Content-Type:** multipart/form-data

| Campo | Tipo | Descripción |
|-------|------|-------------|
| file | FILE | Imagen (max 5MB) |
| tipo | STRING | EQUIPO, AVERIA, OSR, DEVOLUCION |
| equipoId | INTEGER | FK equipo (opcional) |
| averiaId | INTEGER | FK avería (opcional) |
| osrId | INTEGER | FK OSR (opcional) |
| descripcion | STRING | Descripción imagen |

**Response (201):**
```json
{
  "success": true,
  "message": "Evidencia cargada correctamente",
  "data": {
    "id": 1,
    "nombreArchivo": "abc123-def456-ghi789.jpg",
    "ruta": "/uploads/2026/05/abc123-def456-ghi789.jpg",
    "tamano": 2048000,
    "formato": "JPG",
    "url": "https://api.dominio.com/uploads/2026/05/abc123-def456-ghi789.jpg"
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 15.2 Obtener Evidencia

```
GET /api/v1/evidences/{id}
```

---

## 15.3 Listar Evidencias

```
GET /api/v1/evidences
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| equipoId | Filtrar por equipo |
| averiaId | Filtrar por avería |
| osrId | Filtrar por OSR |
| tipo | Filtrar por tipo |

---

## 15.4 Eliminar Evidencia

```
DELETE /api/v1/evidences/{id}
```

**Nota:** Solo administradores pueden eliminar evidencias.

---

# 16. Endpoints de Dashboard

## 16.1 Obtener KPIs

```
GET /api/v1/dashboard/kpis
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| campañaId | Filtrar por campaña (opcional) |
| sitioId | Filtrar por sitio (opcional) |
| proveedorId | Filtrar por proveedor (opcional) |
| fechaDesde | Fecha inicio (opcional) |
| fechaHasta | Fecha fin (opcional) |

**Response (200):**
```json
{
  "success": true,
  "message": "KPIs obtenidos",
  "data": {
    "equiposActivos": 45,
    "equiposDisponibles": 30,
    "equiposAveriados": 5,
    "equiposEnMantenimiento": 2,
    "equiposDevueltos": 8,
    "averiasAbiertas": 3,
    "averiasCerradas": 25,
    "tiempoPromedioAtencion": 48.5,
    "disponibilidad": 85.5,
    "utilizacion": 72.3,
    "porProveedor": [
      {
        "proveedor": "Empresa Proveedora",
        "totalEquipos": 20,
        "averias": 5
      }
    ],
    "porTipo": [
      {
        "tipo": "Transpaleta",
        "total": 15,
        "disponibles": 12
      }
    ]
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

## 16.2 Obtener Métricas

```
GET /api/v1/dashboard/metrics
```

**Response (200):**
```json
{
  "success": true,
  "message": "Métricas obtenidas",
  "data": {
    "totalEquipos": 50,
    "totalCampañas": 3,
    "totalProveedores": 8,
    "totalUsuarios": 25,
    "equiposPorEstado": {
      "DISPONIBLE": 30,
      "OPERATIVO": 10,
      "AVERIADO": 5,
      "MANTENIMIENTO": 2,
      "DEVUELTO": 3
    },
    "averiasPorTipo": [
      {
        "tipo": "Motor",
        "cantidad": 10
      }
    ],
    "evolucionMensual": [
      {
        "mes": "2026-01",
        "equipos": 40,
        "averias": 5
      }
    ]
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

# 17. Endpoints de Reportes

## 17.1 Generar PDF de Equipo

```
GET /api/v1/reports/pdf/equipment/{id}
```

**Response:** Binary (application/pdf)

---

## 17.2 Generar PDF de PSR

```
GET /api/v1/reports/pdf/psr/{id}
```

**Response:** Binary (application/pdf)

---

## 17.3 Generar Reporte de Averías

```
GET /api/v1/reports/pdf/damages
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| fechaDesde | Fecha inicio |
| fechaHasta | Fecha fin |
| proveedorId | Filtrar por proveedor |
| estadoAveriaId | Filtrar por estado |

**Response:** Binary (application/pdf)

---

# 18. Endpoints de Auditoría

## 18.1 Ver Registros de Auditoría

```
GET /api/v1/audit/logs
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| usuarioId | Filtrar por usuario |
| modulo | Filtrar por módulo |
| accion | Filtrar por acción |
| tipoEntidad | Filtrar por tipo entidad |
| idEntidad | Filtrar por ID entidad |
| fechaDesde | Fecha inicio |
| fechaHasta | Fecha fin |
| page | Página |
| size | Tamaño |

**Response (200):**
```json
{
  "success": true,
  "message": "Registros de auditoría obtenidos",
  "data": {
    "content": [
      {
        "id": 1,
        "usuario": "Juan Perez",
        "modulo": "EQUIPOS",
        "accion": "CREATE",
        "tipoEntidad": "equipos",
        "idEntidad": 1,
        "valorAnterior": null,
        "valorNuevo": {"codigo": "EQ-00001", ...},
        "ipCliente": "192.168.1.100",
        "fechaEvento": "2026-01-15T10:30:00-05:00"
      }
    ],
    "totalElements": 1500
  },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

---

# 19. Endpoints de Configuración

## 19.1 Listar Configuraciones

```
GET /api/v1/configurations
```

**Parámetros:**
| Parámetro | Descripción |
|-----------|-------------|
| categoria | SEGURIDAD, SESION, STORAGE, GENERAL |

---

## 19.2 Obtener Configuración

```
GET /api/v1/configurations/{clave}
```

---

## 19.3 Actualizar Configuración

```
PUT /api/v1/configurations/{clave}
```

**Request:**
```json
{
  "valor": "30",
  "descripcion": "Tiempo de expiracion de sesion actualizado"
}
```

**Nota:** Solo administradores pueden modificar configuraciones.

---

# 20. Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 400 | Bad Request - Parámetros inválidos |
| 401 | Unauthorized - No autenticado |
| 403 | Forbidden - Sin permisos |
| 404 | Not Found - Recurso no encontrado |
| 409 | Conflict - Conflicto de datos |
| 422 | Unprocessable Entity - Validación fallida |
| 500 | Internal Server Error - Error interno |

---

# 21. Encabezados Requeridos

| Encabezado | Valor | Descripción |
|------------|-------|-------------|
| Content-Type | application/json | Formato de request |
| Accept | application/json | Formato de response |
| Authorization | Bearer {token} | Token de autenticación |
| Timezone | America/Lima | Zona horaria |

---

# 22. Documentación OpenAPI

La documentación completa de las APIs está disponible en:

| Ambiente | URL |
|----------|-----|
| Desarrollo | http://localhost:8080/openapi |
| Desarrollo | http://localhost:8080/swagger |
| QA | http://api.qa.dominio.com/openapi |

---

# 23. Aprobaciones

| Rol | Responsable | Estado |
|---|---|---|
| Responsable Funcional | Jose Anyarin | ✅ Completado |
| Responsable Técnico | Jose Anyarin | ✅ Completado |
| Aprobación Final | Pendiente | Pendiente |

---

# 24. Historial de Cambios

| Versión | Fecha | Descripción | Autor |
|---------|-------|--------------|-------|
| 1.0 | 2026-05-19 | Versión inicial contratos API | Jose Anyarin |