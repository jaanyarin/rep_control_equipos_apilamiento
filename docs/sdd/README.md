# REP Control Equipos Apilamiento

Sistema empresarial para la gestión operativa, control documental y seguimiento de equipos alquilados utilizados en procesos de apilamiento agrícola.

La plataforma permitirá administrar:
- campañas agrícolas,
- equipos operativos,
- PSR/OSR,
- averías,
- evidencias fotográficas,
- indicadores KPI,
- trazabilidad operacional,
- auditoría de acciones,
- reportes PDF y Excel.

El proyecto está orientado a operación agrícola empresarial bajo arquitectura moderna, escalable y mantenible.

---

# Objetivo del Proyecto

Centralizar y digitalizar el control operativo de equipos alquilados utilizados en campañas agrícolas, permitiendo:
- trazabilidad completa,
- control documental,
- seguimiento de averías,
- gestión de campañas,
- visualización de indicadores,
- control de usuarios y auditoría.

---

# Estado del Proyecto

| Área | Estado |
|---|---|
| Análisis Funcional | ✅ Completo |
| SDD | ✅ Completo |
| Arquitectura Técnica | 🔄 En Proceso |
| Diseño Base Datos | 🔄 Pendiente |
| API Contracts | 🔄 Pendiente |
| Backend | ⏳ Pendiente |
| Frontend Mobile | ⏳ Pendiente |
| Frontend Web | ⏳ Pendiente |
| DevOps | ⏳ Pendiente |

---

# Arquitectura General

## Frontend Mobile

Aplicación móvil para operación en campo.

### Stack
- React Native
- Redux Toolkit
- Axios
- Material Design 3

---

## Frontend Web

Plataforma web administrativa y analítica.

### Stack
- React
- Redux Toolkit
- Recharts
- Material UI
- Axios

---

## Backend

Servicios REST empresariales.

### Stack
- Quarkus Java
- JWT Authentication
- Microsoft Identity
- Flyway
- OpenAPI
- Swagger UI

---

## Base de Datos

- MySQL

---

## Infraestructura

- Docker
- Docker Compose
- GitHub Actions CI/CD
- VPS Linux

---

# Módulos del Sistema

| Código | Módulo |
|---|---|
| MOD-01 | Autenticación |
| MOD-02 | Usuarios |
| MOD-03 | Sedes |
| MOD-04 | Campañas |
| MOD-05 | PSR / OSR |
| MOD-06 | Equipos |
| MOD-07 | Tipos de Equipos |
| MOD-08 | Proveedores |
| MOD-09 | Averías |
| MOD-10 | Evidencias Fotográficas |
| MOD-11 | Dashboard KPI |
| MOD-12 | Reportes PDF |
| MOD-13 | Auditoría |
| MOD-14 | Catálogos |
| MOD-15 | Configuración |

---

# Funcionalidades Principales

## Autenticación Empresarial
- Login corporativo Microsoft
- JWT Authentication
- Control de sesiones
- Gestión de roles

---

## Gestión Operativa
- Registro equipos
- Gestión campañas
- Control PSR/OSR
- Gestión averías
- Control proveedores

---

## Evidencias
- Captura fotografías
- Compresión automática
- Resolución máxima 1080x720
- Eliminación controlada con auditoría

---

## Dashboard KPI
- Indicadores operativos
- Métricas por campaña
- Filtros dinámicos
- Visualización web

---

## Auditoría
- Logs operacionales
- Historial cambios
- Historial accesos
- Trazabilidad completa

---

# Seguridad

La plataforma implementará:
- Microsoft Identity Authentication,
- JWT,
- control de roles,
- auditoría completa,
- expiración de sesión,
- validación centralizada.

---

# Estrategia APIs

## REST APIs
- Versionamiento `/api/v1`
- OpenAPI
- Swagger UI
- Paginación server-side
- Filtros dinámicos
- Manejo centralizado errores

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

# Estrategia Fotografías

| Configuración | Valor |
|---|---|
| Formatos Permitidos | JPG, PNG |
| Resolución Máxima | 1080x720 |
| Compresión | Automática |
| Almacenamiento | Filesystem |
| Eliminación | Manual Administrador |
| Auditoría | Obligatoria |

---

# Estructura Documentación SDD

```text
docs/
 └── sdd/
      ├── 01_SPECIFICATION.md
      ├── 02_PLAN.md
      ├── 03_TASKS.md
      ├── 04_IMPLEMENTATION.md
      ├── 05_TECHNICAL_ARCHITECTURE.md
      ├── 06_DATABASE_DESIGN.md
      └── 07_API_CONTRACTS.md
```

---

# Estrategia Desarrollo

## MVP Inicial

### Incluye
- autenticación,
- usuarios,
- campañas,
- equipos,
- PSR/OSR,
- averías,
- evidencias,
- dashboard básico.

### Futuro
- métricas avanzadas,
- exportaciones avanzadas,
- BI,
- observabilidad avanzada,
- escalabilidad cloud.

---

# Configuración Timezone

| Configuración | Valor |
|---|---|
| Timezone | America/Lima |
| UTC | UTC -5 |

---

# Convenciones

## Git Flow

```text
main
develop
feature/*
hotfix/*
```

---

# CI/CD

GitHub Actions implementará:
- build automático,
- tests,
- validación código,
- dockerización,
- despliegue automatizado.

---

# Observabilidad

La plataforma implementará:
- logs backend,
- logs auditoría,
- healthchecks,
- control errores,
- monitoreo básico.

---

# Roadmap Futuro

- Kubernetes
- Redis
- BI avanzado
- Observabilidad avanzada
- Multiempresa
- Escalabilidad cloud
- Dashboards avanzados

---

# Autor

Proyecto desarrollado para control operacional agrícola empresarial.

---

# Licencia

Pendiente definición.
