# REP Control Equipos Apilamiento

Sistema empresarial para la gestión operativa y control de equipos alquilados utilizados en procesos de apilamiento agrícola.

La plataforma centraliza:
- control operacional,
- trazabilidad de equipos,
- campañas agrícolas,
- PSR / OSR,
- averías,
- evidencias fotográficas,
- indicadores KPI,
- auditoría y trazabilidad.

---

# Descripción General

El proyecto busca digitalizar y estandarizar el control de equipos operativos utilizados durante campañas agrícolas, permitiendo una gestión centralizada, trazable y escalable.

La solución contempla:
- aplicación móvil para operación en campo,
- plataforma web administrativa y analítica,
- backend empresarial REST,
- gestión documental y operacional,
- dashboard KPI,
- auditoría completa.

---

# Objetivo del Proyecto

Centralizar y digitalizar el control operativo de equipos alquilados utilizados durante campañas agrícolas, permitiendo:

- trazabilidad completa,
- control documental,
- seguimiento operacional,
- gestión de averías,
- control de campañas,
- monitoreo KPI,
- auditoría de acciones,
- generación reportes operativos.

---

# Arquitectura General

## Frontend Mobile

Aplicación móvil para operación de campo y captura operacional.

### Tecnologías

- React Native
- Redux Toolkit
- Axios
- Material Design 3

### Responsabilidades

- Registro operacional
- Captura fotografías
- Gestión averías
- Consulta equipos
- Consulta campañas
- Operación campo

---

## Frontend Web

Plataforma administrativa y analítica.

### Tecnologías

- React
- Redux Toolkit
- Material UI
- Recharts
- Axios

### Responsabilidades

- Dashboard KPI
- Gestión administrativa
- Gestión usuarios
- Gestión campañas
- Reportes
- Auditoría

---

## Backend

Servicios REST empresariales.

### Tecnologías

- Quarkus Java
- JWT
- Microsoft Identity
- Flyway
- OpenAPI
- Swagger UI

### Responsabilidades

- APIs REST
- Seguridad
- Reglas negocio
- Auditoría
- Integración Microsoft
- Gestión sesiones

---

## Base de Datos

### Tecnología

- MySQL

### Responsabilidades

- Persistencia operacional
- Auditoría
- Históricos
- Integridad relacional

---

## Infraestructura

### Tecnologías

- Docker
- Docker Compose
- GitHub Actions
- VPS Linux

### Responsabilidades

- Contenerización
- Despliegue
- Automatización
- Integración continua

---

# Funcionalidades Principales

## Autenticación Empresarial

- Login Microsoft corporativo
- JWT Authentication
- Gestión sesiones
- Expiración automática
- Control roles
- Trazabilidad accesos

---

## Gestión Usuarios

- Gestión usuarios
- Gestión roles
- Activación/desactivación
- Control accesos
- Historial accesos

---

## Gestión Campañas

- Campaña activa
- Históricos campañas
- Gestión operacional
- Control campañas

---

## Gestión Equipos

- Registro equipos
- Historial operacional
- Gestión proveedores
- Historial averías
- Control estado operativo

---

## Gestión PSR / OSR

- Registro PSR
- Registro OSR
- Asociación equipos
- Historial documental
- Exportación PDF

---

## Gestión Averías

- Registro averías
- Seguimiento averías
- Historial averías
- Evidencias fotográficas
- Control estados

---

## Evidencias Fotográficas

- Captura móvil
- Compresión automática
- Resolución optimizada
- Gestión almacenamiento
- Eliminación auditada

---

## Dashboard KPI

- Métricas operativas
- Indicadores campaña
- Filtros dinámicos
- Indicadores averías
- Indicadores equipos

---

## Auditoría

- Logs operacionales
- Historial cambios
- Historial accesos
- Trazabilidad acciones
- Auditoría eliminación

---

# Estado Proyecto

| Área | Estado |
|---|---|
| Análisis Funcional | ✅ Completado |
| SDD | ✅ Completado |
| Arquitectura Técnica | ✅ Completado |
| Diseño Base Datos | ✅ Completado |
| Script SQL BD | ✅ Completado |
| API Contracts | ✅ Completado |
| Fase 2: Infraestructura | 🔄 En Proceso |
| Backend | ⏳ Pendiente |
| Frontend Mobile | ⏳ Pendiente |
| Frontend Web | ⏳ Pendiente |
| DevOps | ⏳ Pendiente |

---

# Stack Tecnológico

| Capa | Tecnología |
|---|---|
| Frontend Mobile | React Native |
| Frontend Web | React |
| Backend | Quarkus Java |
| Base Datos | MySQL |
| Seguridad | JWT |
| Auth | Microsoft Identity |
| APIs | REST |
| Infraestructura | Docker |
| CI/CD | GitHub Actions |
| Observabilidad | Healthchecks + Logging |

---

# Módulos Sistema

| Código | Módulo |
|---|---|
| MOD-01 | Autenticación |
| MOD-02 | Usuarios |
| MOD-03 | Sedes |
| MOD-04 | Campañas |
| MOD-05 | PSR / OSR |
| MOD-06 | Equipos |
| MOD-07 | Tipos Equipos |
| MOD-08 | Proveedores |
| MOD-09 | Averías |
| MOD-10 | Evidencias Fotográficas |
| MOD-11 | Dashboard KPI |
| MOD-12 | Reportes PDF |
| MOD-13 | Auditoría |
| MOD-14 | Catálogos |
| MOD-15 | Configuración |

---

# Estrategia APIs

## Arquitectura APIs

La plataforma implementará APIs REST empresariales bajo arquitectura versionada.

### Características

- Versionamiento `/api/v1`
- JWT Authentication
- OpenAPI
- Swagger UI
- Paginación server-side
- Filtros dinámicos
- Manejo centralizado errores
- Respuestas estandarizadas

---

## Estándar Respuesta APIs

### Respuesta Exitosa

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": {},
  "timestamp": "2026-05-18T10:00:00Z"
}
```

### Respuesta Error

```json
{
  "success": false,
  "message": "Descripción error",
  "errorCode": "ERR001",
  "timestamp": "2026-05-18T10:00:00Z"
}
```

---

# Seguridad

La plataforma implementará controles de seguridad empresariales.

## Seguridad Implementada

- Microsoft Identity Authentication
- JWT Authentication
- Expiración sesiones
- Control roles
- Auditoría completa
- Trazabilidad acciones
- Validación centralizada
- Versionamiento APIs

---

# Gestión Fotografías

## Configuración Fotografías

| Configuración | Valor |
|---|---|
| Formatos Permitidos | JPG, PNG |
| Resolución Máxima | 1080x720 |
| Compresión | Automática |
| Almacenamiento | Filesystem |
| Eliminación | Manual Administrador |
| Auditoría | Obligatoria |

---

# Configuración Regional

| Configuración | Valor |
|---|---|
| Timezone | America/Lima |
| UTC | UTC -5 |

---

# Convenciones Proyecto

## Git Flow

```text
main
develop
feature/*
hotfix/*
```

---

## Convenciones Backend

- Arquitectura modular
- DTO Pattern
- Repository Pattern
- Service Layer
- Exception Handling Centralizado
- APIs REST versionadas

---

## Convenciones Frontend

- Arquitectura modular
- Redux Toolkit
- Componentización reutilizable
- Manejo centralizado APIs

---

## Convenciones Base Datos

- Migraciones Flyway
- Auditoría operacional
- Soft delete
- Optimistic locking
- Constraints relacionales

---

# Observabilidad

La plataforma implementará:

- logs backend,
- logs auditoría,
- healthchecks,
- control errores,
- monitoreo básico.

---

# CI/CD

GitHub Actions implementará:

- build automático,
- validación código,
- dockerización,
- despliegue automatizado,
- validación ramas,
- integración continua.

---

# MVP Inicial

## Alcance MVP

- autenticación,
- usuarios,
- campañas,
- equipos,
- PSR / OSR,
- averías,
- evidencias,
- dashboard básico.

---

# Roadmap Futuro

## Evolución Plataforma

- Redis
- Kubernetes
- BI avanzado
- Observabilidad avanzada
- Dashboards avanzados
- Escalabilidad cloud
- Multiempresa

---

# Estructura Repositorio

```text
rep_control_equipos_apilamiento/
│
├── backend/                  # Backend Quarkus Java
│
├── mobile/                  # Frontend React Native
│
├── dashboard/               # Frontend Web React
│
├── infrastructure/          # Configuración infraestructura
│
├── scripts/                 # Scripts SQL y utilidades
│   └── 01_create_tables.sql
│
├── docs/
│   └── sdd/
│       ├── 01_SPECIFICATION.md    # Especificación funcional
│       ├── 02_PLAN.md             # Planificación proyecto
│       ├── 03_TASKS.md           # Backlog tareas técnicas
│       ├── 04_IMPLEMENTATION.md # Estrategia implementación
│       ├── 05_TECHNICAL_ARCHITECTURE.md  # Arquitectura técnica
│       ├── 06_DATABASE_DESIGN.md # Diseño base de datos
│       └── 07_API_CONTRACTS.md   # Contratos APIs REST
│
├── docs_usuario/            # Documentación usuario
│
├── .github/                 # GitHub Actions CI/CD
│
├── docker-compose.yml       # Orquestación contenedores
│
└── README.md
```

---

# Documentación Técnica

Toda la documentación funcional y técnica se encuentra en:

```text
docs/sdd/
```

## Documentos Principales

| Documento | Descripción |
|---|---|
| 01_SPECIFICATION.md | Especificación funcional |
| 02_PLAN.md | Plan proyecto |
| 03_TASKS.md | Gestión tareas |
| 04_IMPLEMENTATION.md | Estrategia implementación |
| 05_TECHNICAL_ARCHITECTURE.md | Arquitectura técnica |
| 06_DATABASE_DESIGN.md | Diseño base datos |
| 07_API_CONTRACTS.md | Contratos APIs |

---

# Estrategia Desarrollo

## Fases Desarrollo

### Fase 1 ✅ Completado
- Arquitectura técnica (05_TECHNICAL_ARCHITECTURE.md)
- Diseño BD (06_DATABASE_DESIGN.md)
- Contratos APIs (07_API_CONTRACTS.md)
- Script SQL (scripts/01_create_tables.sql)

### Fase 2 🔄 En Proceso
- Docker Compose
- Variables entorno
- Configuración Docker
- Ramas GitHub

### Fase 3
- Setup backend
- Setup frontend

### Fase 4
- Desarrollo MVP
- Integraciones
- Seguridad

### Fase 5
- Testing
- Hardening
- Optimización

### Fase 6
- Despliegue
- Validación campo
- Producción

---

# Autor

Proyecto orientado a operación agrícola empresarial.

---

# Licencia

Pendiente definición.
