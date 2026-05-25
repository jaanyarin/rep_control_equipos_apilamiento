# SOFTWARE DEVELOPMENT DOCUMENT (SDD)
# 05_TECHNICAL_ARCHITECTURE.md

---

# 1. Control Documental

| Campo | Valor |
|---|---|
| Documento | 05_TECHNICAL_ARCHITECTURE.md |
| Proyecto | Sistema de Control Operativo de Equipos de Apilamiento |
| Tipo Documento | Software Development Document - Arquitectura Técnica |
| Estado | Completado |
| Versión | 1.0 |
| Fecha | 2026-05-19 |
| Responsable | Jose Anyarin |
| Repositorio | GitHub |
| Clasificación | Interno |

---

# 2. Objetivo del Documento

El presente documento tiene como finalidad definir la arquitectura técnica completa del sistema de control operativo de equipos de apilamiento, detallando todos los componentes, tecnologías, patrones y estrategias que conformarán la solución tecnológica.

Este documento establece:
- Arquitectura física y lógica del sistema
- Stack tecnológico por capa
- Estructura de código backend, frontend mobile y frontend web
- Diseño de base de datos
- Estrategia de APIs REST
- Seguridad y autenticación
- Almacenamiento y gestión de fotografías
- Logging y auditoría
- Contenedores y orquestación
- CI/CD y despliegue
- Monitoreo y healthchecks

---

# 3. Referencias

| Documento | Descripción |
|---|---|
| 01_SPECIFICATION.md | Especificación funcional y requerimientos |
| 02_PLAN.md | Planificación y estrategia del proyecto |
| 03_TASKS.md | Backlog técnico y tareas de desarrollo |
| 04_IMPLEMENTATION.md | Estrategia de implementación |
| README.md | Estado general del proyecto |

---

# 4. Arquitectura Física del Sistema

## 4.1 Diagrama de Arquitectura Física

```
┌─────────────────────────────────────────────────────────────────┐
│                        USUARIOS FINALES                         │
│                  (App Android + Web Browser)                    │
└─────────────────────┬───────────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                    NGINX REVERSE PROXY                          │
│                  (Puerto 80/443 - SSL/TLS)                      │
└─────────────────────┬───────────────────────────────────────────┘
                      │
          ┌───────────┴───────────┐
          ▼                       ▼
┌─────────────────────┐   ┌─────────────────────┐
│   BACKEND QUARKUS   │   │  FRONTEND WEB REACT │
│   (Puerto 8080)     │   │   (Puerto 3000)      │
└─────────┬───────────┘   └──────────┬────────────┘
          │                         │
          └───────────┬─────────────┘
                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                     BASE DE DATOS MYSQL                         │
│                (Puerto 3306 - Persistencia)                     │
└─────────────────────────────────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────────┐
│              FILESYSTEM (Almacenamiento Fotografías)           │
│                   (/var/uploads/images)                         │
└─────────────────────────────────────────────────────────────────┘
```

## 4.2 Componentes Físicos

| Componente | Tecnología | Puerto | Descripción |
|------------|------------|--------|-------------|
| Frontend Mobile | React Native | APK | Aplicativo Android operativo |
| Frontend Web | React + Nginx | 80/443 | Plataforma web administrativa |
| Backend | Quarkus Java | 8080 | APIs REST empresariales |
| Base de Datos | MySQL 8 | 3306 | Persistencia relacional |
| Almacenamiento | Filesystem | - | Fotografías y evidencias |
| Proxy | Nginx | 80/443 | Reverse proxy + SSL |

---

# 5. Arquitectura Lógica del Sistema

## 5.1 Diagrama de Arquitectura Lógica

```
┌─────────────────────────────────────────────────────────────────┐
│                      FRONTEND LAYER                            │
│  ┌──────────────────────┐    ┌──────────────────────────────┐   │
│  │   Mobile (React      │    │    Web (React + MUI +      │   │
│  │   Native + Redux)    │    │    Redux + Recharts)       │   │
│  └──────────────────────┘    └──────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API GATEWAY LAYER                          │
│                  (Nginx - Routes /api/v1/*)                     │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND SERVICE LAYER                        │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                   QUARKUS JAVA                            │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────────────┐  │   │
│  │  │ Auth    │ │ Users   │ │ Sites   │ │ Campaigns      │  │   │
│  │  │ Module  │ │ Module  │ │ Module  │ │ Module         │  │   │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────────────┘  │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────────────┐  │   │
│  │  │ PSR/OSR │ │Equipos  │ │ Damages │ │ Evidences      │  │   │
│  │  │ Module  │ │ Module  │ │ Module  │ │ Module         │  │   │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────────────┘  │   │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────────────────────────┐  │   │
│  │  │Dashboard│ │ Reports │ │ Audit + Security          │  │   │
│  │  │ Module  │ │ Module  │ │ Module                    │  │   │
│  │  └─────────┘ └─────────┘ └─────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PERSISTENCE LAYER                            │
│  ┌──────────────────────┐    ┌──────────────────────────────┐   │
│  │   MySQL Database     │    │    Flyway Migrations        │   │
│  │   (Entities + DTOs)  │    │    (db/migration/)          │   │
│  └──────────────────────┘    └──────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## 5.2 Capas de la Arquitectura

| Capa | Responsabilidad | Tecnología |
|------|-----------------|------------|
| Presentación | UI/UX, interacción usuario | React Native / React |
| Negocio | Lógica, reglas, procesos | Quarkus Java |
| Datos | Persistencia, consultas | MySQL + Hibernate |
| Infraestructura | Contenedores, red, servidores | Docker + Nginx |

---

# 6. Stack Tecnológico por Capa

## 6.1 Tabla General de Tecnologías

| Capa | Tecnología | Versión | Propósito |
|------|------------|---------|-----------|
| **Frontend Mobile** | React Native | latest | App Android operativa |
| Estado Global | Redux Toolkit | latest | Manejo estado reactivo |
| Navegación | React Navigation | latest | Routing móvil |
| HTTP Client | Axios | latest | Consumo APIs REST |
| Formularios | React Hook Form | latest | Validación formularios |
| UI Components | Material Design 3 | latest | Componentes visuales |
| **Frontend Web** | React | latest | Dashboard administrativo |
| Estado Global | Redux Toolkit | latest | Manejo estado centralizado |
| UI Framework | Material UI (MUI) | latest | Componentes Material |
| Gráficos | Recharts | latest | Visualización KPIs |
| HTTP Client | Axios | latest | Consumo APIs REST |
| **Backend** | Quarkus Java | 3.x | APIs REST empresariales |
| Seguridad | JWT (jjwt) | latest | Autenticación token |
| Auth | Microsoft Identity | MSAL | OAuth2 corporate |
| Persistencia | Hibernate + Panache | latest | ORM MySQL |
| Migraciones | Flyway | latest | Versionamiento DB |
| Documentación | OpenAPI + Swagger | latest | APIs documentación |
| PDF | iText PDF | 7.x | Generación reportes |
| Logging | JBoss Logging | latest | Logs estructurados |
| **Base Datos** | MySQL | 8.x | Persistencia relacional |
| **Contenedores** | Docker | latest | Containering |
| Orquestación | Docker Compose | latest | Multi-container |
| Proxy | Nginx | latest | Reverse proxy + SSL |
| CI/CD | GitHub Actions | latest | Automatización |
| Hosting | VPS Linux | - | Infraestructura |

---

# 7. Arquitectura Backend (Quarkus Java)

## 7.1 Estructura de Paquetes

```
src/main/java/com/rep/equipos/
├── config/
│   ├── AppConfig.java
│   ├── CorsConfig.java
│   ├── DatabaseConfig.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── UserController.java
│   ├── CampaignController.java
│   ├── SiteController.java
│   ├── EquipmentController.java
│   ├── ProviderController.java
│   ├── PsrOsrController.java
│   ├── DamageController.java
│   ├── EvidenceController.java
│   ├── DashboardController.java
│   └── ReportController.java
├── service/
│   ├── impl/
│   │   ├── AuthServiceImpl.java
│   │   ├── UserServiceImpl.java
│   │   ├── CampaignServiceImpl.java
│   │   └── ...
│   └── interfaces/
│       ├── IAuthService.java
│       └── ...
├── repository/
│   ├── UserRepository.java
│   ├── CampaignRepository.java
│   └── ...
├── entity/
│   ├── User.java
│   ├── Campaign.java
│   ├── Equipment.java
│   └── ...
├── dto/
│   ├── request/
│   │   ├── CreateUserDTO.java
│   │   ├── UpdateEquipmentDTO.java
│   │   └── ...
│   ├── response/
│   │   ├── ApiResponse.java
│   │   ├── UserResponseDTO.java
│   │   └── ...
│   └── common/
│       ├── PageResponse.java
│       └── ErrorResponse.java
├── mapper/
│   ├── UserMapper.java
│   ├── EquipmentMapper.java
│   └── ...
├── security/
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   ├── SecurityService.java
│   └── MicrosoftAuthService.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── BusinessException.java
│   └── ...
├── audit/
│   ├── AuditService.java
│   ├── AuditEvent.java
│   └── AuditAspect.java
├── util/
│   ├── DateUtils.java
│   ├── StringUtils.java
│   └── ...
└── Application.java
```

## 7.2 Patrones de Diseño Backend

| Patrón | Aplicación | Detalle |
|--------|------------|---------|
| **Service Layer** | Lógica negocio | Métodos complejos, transacciones |
| **Repository Pattern** | Persistencia | Panache active record o DAO |
| **DTO Pattern** | Transferencia | Aislamiento entity - API |
| **Mapper Pattern** | Conversión | MapStruct o manual |
| **Singleton** | Configuración | Properties, conexiones |
| **Factory** | DTOs response | ApiResponse factory |

## 7.3 Convenciones Código Backend

| Elemento | Convención | Ejemplo |
|----------|------------|---------|
| Clases | PascalCase | `UserController` |
| Métodos | camelCase | `findById()` |
| Variables | camelCase | `userName` |
| Constantes | UPPER_SNAKE | `MAX_RETRY` |
| Paquetes | lowercase | `com.rep.equipos.controller` |
| DTOs | Sufijo DTO | `UserResponseDTO` |
| Entities | Singular | `User` (no Users) |

## 7.4 Módulos Backend

| Módulo | Responsabilidad | Endpoints principales |
|--------|-----------------|----------------------|
| auth | Autenticación JWT + Microsoft | /auth/login, /auth/refresh, /auth/logout |
| users | Gestión usuarios sistema | /users, /users/{id} |
| sites | Gestión sedes operativas | /sites, /sites/{id} |
| campaigns | Gestión campañas agrícolas | /campaigns, /campaigns/{id} |
| equipment-types | Catálogo tipos equipos | /equipment-types, /equipment-types/{id} |
| providers | Gestión proveedores | /providers, /providers/{id} |
| psr-osr | Gestión documental PSR/OSR | /psr, /osr, /psr/{id}, /osr/{id} |
| equipment | Gestión equipos alquilados | /equipment, /equipment/{id} |
| damages | Gestión averías | /damages, /damages/{id} |
| evidences | Gestión fotografías | /evidences, /evidences/upload |
| dashboard | KPIs y métricas | /dashboard/kpis, /dashboard/metrics |
| reports | Generación PDF | /reports/pdf/equipment, /reports/pdf/psr |
| audit | Trazabilidad | /audit/logs, /audit/export |

---

# 8. Arquitectura Frontend Mobile (React Native)

## 8.1 Estructura de Carpetas

```
src/
├── screens/
│   ├── auth/
│   │   ├── LoginScreen.tsx
│   │   └── SplashScreen.tsx
│   ├── home/
│   │   └── HomeScreen.tsx
│   ├── campaigns/
│   │   ├── CampaignListScreen.tsx
│   │   └── CampaignDetailScreen.tsx
│   ├── equipment/
│   │   ├── EquipmentListScreen.tsx
│   │   ├── EquipmentDetailScreen.tsx
│   │   └── EquipmentFormScreen.tsx
│   ├── damages/
│   │   ├── DamageListScreen.tsx
│   │   └── DamageFormScreen.tsx
│   ├── psr-osr/
│   │   ├── PsrListScreen.tsx
│   │   ├── OsrFormScreen.tsx
│   │   └── ...
│   └── profile/
│       └── ProfileScreen.tsx
├── components/
│   ├── common/
│   │   ├── Button.tsx
│   │   ├── Input.tsx
│   │   ├── Card.tsx
│   │   ├── Loader.tsx
│   │   └── ErrorMessage.tsx
│   ├── forms/
│   │   ├── EquipmentForm.tsx
│   │   └── DamageForm.tsx
│   └── lists/
│       ├── EquipmentList.tsx
│       └── CampaignList.tsx
├── services/
│   ├── api/
│   │   ├── apiClient.ts
│   │   ├── authApi.ts
│   │   ├── userApi.ts
│   │   ├── equipmentApi.ts
│   │   └── ...
│   └── storage/
│       └── secureStorage.ts
├── store/
│   ├── slices/
│   │   ├── authSlice.ts
│   │   ├── userSlice.ts
│   │   ├── campaignSlice.ts
│   │   ├── equipmentSlice.ts
│   │   └── ...
│   └── index.ts
├── navigation/
│   ├── RootNavigator.tsx
│   ├── AppNavigator.tsx
│   ├── AuthNavigator.tsx
│   └── types.ts
├── hooks/
│   ├── useAuth.ts
│   ├── useApi.ts
│   ├── useCamera.ts
│   └── useStorage.ts
├── constants/
│   ├── api.ts
│   ├── colors.ts
│   ├── strings.ts
│   └── config.ts
├── styles/
│   ├── theme.ts
│   └── global.ts
├── utils/
│   ├── dateUtils.ts
│   ├── validationUtils.ts
│   └── imageUtils.ts
└── types/
    ├── api.ts
    └── models.ts
```

## 8.2 Configuración Redux Toolkit

```typescript
// store/slices/authSlice.ts
interface AuthState {
  token: string | null;
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
}
```

## 8.3 Convenciones Frontend Mobile

| Elemento | Convención | Ejemplo |
|----------|------------|---------|
| Componentes | PascalCase | `LoginScreen.tsx` |
| Hooks | prefix use | `useAuth.ts` |
| Estilos | camelCase | `backgroundColor` |
| Rutas | kebab-case | `equipment-list` |
| Constantes | UPPER_SNAKE | `API_BASE_URL` |

## 8.4 Flujos Mobile

| Flujo | Descripción |
|-------|-------------|
| Login | Microsoft Auth → JWT → Store → Home |
| Registro Equipo | Form → API → Camera → Upload → Confirm |
| Avería | List → Form → Photos → Save → Update Status |
| Sincronización | Background → API → Local Store |

---

# 9. Arquitectura Frontend Web (React)

## 9.1 Estructura de Carpetas

```
src/
├── pages/
│   ├── auth/
│   │   ├── LoginPage.tsx
│   │   └── CallbackPage.tsx
│   ├── dashboard/
│   │   └── DashboardPage.tsx
│   ├── users/
│   │   ├── UserListPage.tsx
│   │   └── UserFormPage.tsx
│   ├── campaigns/
│   │   ├── CampaignListPage.tsx
│   │   └── CampaignFormPage.tsx
│   ├── equipment/
│   │   ├── EquipmentListPage.tsx
│   │   └── EquipmentDetailPage.tsx
│   ├── reports/
│   │   ├── ReportListPage.tsx
│   │   └── ReportGeneratePage.tsx
│   ├── audit/
│   │   └── AuditLogPage.tsx
│   └── settings/
│       └── SettingsPage.tsx
├── components/
│   ├── layout/
│   │   ├── MainLayout.tsx
│   │   ├── Header.tsx
│   │   ├── Sidebar.tsx
│   │   └── Footer.tsx
│   ├── common/
│   │   ├── DataGrid.tsx
│   │   ├── FilterPanel.tsx
│   │   ├── ExportButton.tsx
│   │   └── LoadingSpinner.tsx
│   ├── dashboard/
│   │   ├── KPICard.tsx
│   │   ├── ChartComponent.tsx
│   │   └── MetricTable.tsx
│   └── forms/
│       ├── UserForm.tsx
│       └── EquipmentForm.tsx
├── layouts/
│   ├── AuthLayout.tsx
│   └── DashboardLayout.tsx
├── routes/
│   ├── AppRoutes.tsx
│   ├── PrivateRoute.tsx
│   └── PublicRoute.tsx
├── services/
│   ├── api/
│   │   ├── apiClient.ts
│   │   └── endpoints.ts
│   └── auth/
│       └── msalAuth.ts
├── store/
│   ├── slices/
│   │   ├── authSlice.ts
│   │   ├── filterSlice.ts
│   │   └── ...
│   └── api/
│       └── apiSlice.ts (RTK Query)
├── hooks/
│   ├── useAuth.ts
│   ├── useFilters.ts
│   └── useExport.ts
├── styles/
│   ├── theme.ts
│   └── global.css
├── utils/
│   ├── dateUtils.ts
│   ├── formatters.ts
│   └── validators.ts
└── types/
    └── index.ts
```

## 9.2 Componentes Dashboard KPIs

| Componente | Descripción | Librería |
|------------|-------------|----------|
| KPICard | Tarjeta métricas individuales | MUI Card |
| BarChart | Gráfico de barras | Recharts |
| LineChart | Gráfico de líneas | Recharts |
| PieChart | Distribución pastel | Recharts |
| DataGrid | Tabla datos con paginación | MUI DataGrid |
| FilterPanel | Panel filtros globales | Custom |

## 9.3 Filtros Dashboard

| Filtro | Tipo | Descripción |
|--------|------|-------------|
| Sede | Dropdown multi-select | Filtrar por sede operativa |
| Campaña | Dropdown | Filtrar por campaña agrícola |
| Proveedor | Dropdown | Filtrar por proveedor |
| Tipo Equipo | Dropdown | Filtrar por tipo de equipo |
| Rango Fechas | DatePicker | Filtrar por período |

---

# 10. Arquitectura Base de Datos (MySQL)

## 10.1 Convenciones

| Elemento | Convención | Ejemplo |
|----------|------------|---------|
| Tablas | snake_case plural | `users`, `equipment_types` |
| Primary Key | `id` | `id` BIGINT AUTO_INCREMENT |
| Foreign Key | `tabla_id` | `user_id`, `campana_id` |
| Timestamps | `fecha_creacion`, `fecha_actualizacion` | DATETIME |
| Booleanos | `estado_activo` | TINYINT(1) |
| Soft Delete | `estado_activo` + `fecha_baja` | - |
| Índices | idx_tabla_campos | `idx_equipment_campana` |

## 10.2 Campos Estándar (Todas las Tablas)

```sql
CREATE TABLE ejemplo (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    campo1          VARCHAR(255) NOT NULL,
    campo2          TEXT,
    fecha_creacion  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_creacion    BIGINT NOT NULL,
    usuario_actualizacion BIGINT,
    estado_activo   TINYINT(1) NOT NULL DEFAULT 1,
    fecha_baja      DATETIME NULL,
    version         INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_ejemplo_usuario_cre FOREIGN KEY (usuario_creacion) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## 10.3 Modelo Entidad-Relación

### Tablas Principales

| Tabla | Propósito | Clave Foránea Principal |
|-------|-----------|-------------------------|
| users | Usuarios sistema | role_id, site_id |
| roles | Roles operativos | - |
| sites | Sedes operativas | - |
| campaigns | Campañas agrícolas | site_id |
| equipment_types | Tipos equipos | - |
| providers | Proveedores | - |
| equipment | Equipos alquilados | provider_id, equipment_type_id, campana_id, osr_id |
| psr | Pedidos servicio requerimiento | campana_id, site_id |
| osr | Órdenes servicio requerimiento | psr_id, equipment_id |
| damages | Averías equipos | equipment_id, user_id |
| evidence | Evidencias fotográficas | damage_id, equipment_id |
| audit_logs | Trazabilidad operacional | user_id, module |
| configurations | Configuración sistema | - |

### Relaciones entre Entidades

```
users (1) ────── (N) audit_logs
users (1) ────── (N) equipment
users (1) ────── (N) damages
sites (1) ────── (N) campaigns
sites (1) ────── (N) users
campaigns (1) ── (N) equipment
campaigns (1) ─ (N) psr
psr (1) ──────── (N) osr
osr (1) ──────── (N) equipment
equipment (1) ─ (N) damages
equipment (1) ─ (N) evidence
providers (1) ─ (N) equipment
equipment_types (1) ─ (N) equipment
```

## 10.4 Índices Críticos

```sql
CREATE INDEX idx_equipment_campana ON equipment(campana_id);
CREATE INDEX idx_equipment_provider ON equipment(provider_id);
CREATE INDEX idx_damage_equipment ON damages(equipment_id);
CREATE INDEX idx_damage_status ON damages(status);
CREATE INDEX idx_psr_campana ON psr(campana_id);
CREATE INDEX idx_osr_psr ON osr(psr_id);
CREATE INDEX idx_audit_user ON audit_logs(user_id);
CREATE INDEX idx_audit_timestamp ON audit_logs(timestamp);
```

## 10.5 Convenciones Identificadores Funcionales

| Entidad | Formato | Ejemplo |
|---------|---------|---------|
| Campaña Uva | UVA-AAAA | UVA-2026 |
| Campaña Arándano | ARA-AAAA | ARA-2026 |
| Equipo | EQ-AAAAA | EQ-00001 |
| Avería | AV-AAAAA | AV-00001 |
| PSR | PSR-AAAA-NNNN | PSR-2026-0001 |
| OSR | OSR-AAAA-NNNN | OSR-2026-0001 |

## 10.6 Migraciones Flyway

```
src/main/resources/db/migration/
├── V1__initial_schema.sql
├── V2__create_users_tables.sql
├── V3__create_campaigns_tables.sql
├── V4__create_equipment_tables.sql
├── V5__create_psr_osr_tables.sql
├── V6__create_damages_tables.sql
├── V7__create_evidence_tables.sql
├── V8__create_audit_tables.sql
└── V9__seed_initial_data.sql
```

---

# 11. Arquitectura APIs REST

## 11.1 Estándar URL

```
Base URL: https://api.dominio.com/api/v1
```

## 11.2 Estructura Respuestas

### Respuesta Éxito

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": { ... },
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

### Respuesta Error

```json
{
  "success": false,
  "message": "Descripción del error",
  "errorCode": "ERR001",
  "timestamp": "2026-05-19T10:30:00-05:00"
}
```

## 11.3 Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /auth/login | Iniciar sesión |
| POST | /auth/refresh | Renovar token |
| POST | /auth/logout | Cerrar sesión |
| GET | /users | Listar usuarios |
| POST | /users | Crear usuario |
| GET | /users/{id} | Detalle usuario |
| PUT | /users/{id} | Actualizar usuario |
| DELETE | /users/{id} | Desactivar usuario |
| GET | /sites | Listar sedes |
| POST | /sites | Crear sede |
| GET | /campaigns | Listar campañas |
| POST | /campaigns | Crear campaña |
| PUT | /campaigns/{id}/activate | Activar campaña |
| PUT | /campaigns/{id}/close | Cerrar campaña |
| GET | /equipment | Listar equipos |
| POST | /equipment | Crear equipo |
| GET | /equipment/{id} | Detalle equipo |
| PUT | /equipment/{id} | Actualizar equipo |
| GET | /equipment-types | Listar tipos equipos |
| GET | /providers | Listar proveedores |
| GET | /psr | Listar PSR |
| POST | /psr | Crear PSR |
| GET | /osr | Listar OSR |
| POST | /osr | Crear OSR |
| GET | /damages | Listar averías |
| POST | /damages | Crear avería |
| PUT | /damages/{id}/close | Cerrar avería |
| POST | /evidences/upload | Subir fotografía |
| GET | /evidences/{id} | Obtener fotografía |
| DELETE | /evidences/{id} | Eliminar fotografía |
| GET | /dashboard/kpis | Obtener KPIs |
| GET | /dashboard/metrics | Obtener métricas |
| GET | /reports/pdf/equipment | Generar PDF equipo |
| GET | /reports/pdf/psr | Generar PDF PSR |
| GET | /audit/logs | Ver registros auditoría |

## 11.4 Paginación

```json
GET /equipment?page=0&size=20&sort=fecha_creacion,desc

{
  "success": true,
  "data": {
    "content": [ ... ],
    "totalElements": 150,
    "totalPages": 8,
    "currentPage": 0,
    "pageSize": 20
  }
}
```

## 11.5 Versionamiento

| Aspecto | Detalle |
|---------|---------|
| Versión actual | v1 |
| Header | Accept: application/json |
| Deprecated | Retrocompatible dentro de misma versión mayor |
| URL base | /api/v1 |

## 11.6 Documentación APIs

| Herramienta | Propósito | URL |
|-------------|-----------|-----|
| OpenAPI 3.0 | Especificación REST | /openapi |
| Swagger UI | Documentación interactiva | /swagger |

---

# 12. Seguridad

## 12.1 Autenticación

| Elemento | Configuración |
|----------|---------------|
| Provider | Microsoft Identity (Azure AD) |
| Protocolo | OAuth 2.0 + OpenID Connect |
| Token | JWT (JSON Web Token) |
| Expiración | 15 minutos inactividad |
| Refresh | Sí, token válido 24 horas |
| Algoritmo | RS256 |

## 12.2 Flujo Autenticación

```
1. Usuario abre app → Redirige a Microsoft Login
2. Usuario ingresa credenciales corporativas
3. Microsoft retorna authorization code
4. App obtiene el `idToken` de Microsoft y lo envía al backend
5. Backend valida el `idToken` con Azure AD JWKS y genera JWT interno
6. App guarda el JWT interno y lo usa en cada request
7. App guarda tokens (secure storage)
8. Cada request incluye Authorization: Bearer {token}
9. Backend valida token y extrae claims
10. Si expira → refresh token → nuevo access token
11. Si refresh expira → logout →重新login
```

## 12.3 Roles y Permisos

| Rol | Descripción | Permisos |
|-----|-------------|----------|
| ADMIN | Administrador sistema | CRUD total, configuraciones, reportes, auditoría |
| USER | Usuario operativo | Registro equipos, averías, evidencias, consulta |

## 12.4 Validaciones Backend

```java
@GET
@Path("/equipment")
@RolesAllowed({"ADMIN", "USER"})
public Response getEquipment(
    @HeaderParam("Authorization") String authHeader,
    @QueryParam("campaign") Long campaignId) {
    // 1. Validar JWT
    // 2. Verificar rol
    // 3. Validar permisos específicos
    // 4. Ejecutar lógica
}
```

## 12.5 Configuración Seguridad

| Configuración | Valor |
|---------------|-------|
| HTTPS | Obligatorio |
| CORS | Configurado por ambiente |
| Rate Limiting | 100 req/min |
| Max Request Size | 10 MB |
| Headers | X-Frame-Options, CSP, HSTS |

---

# 13. Estrategia Almacenamiento Fotografías

## 13.1 Configuración

| Parámetro | Valor |
|-----------|-------|
| Directorio | /var/uploads/evidences/{año}/{mes}/ |
| Tamaño máximo | 5 MB |
| Formatos | JPG, PNG |
| Resolución máx | 1080 x 720 px |
| Compresión | Automática (80% calidad) |
| Nombre archivo | UUID + extensión original |

## 13.2 Flujo Upload

```
1. Usuario captura/toma foto en app móvil
2. App comprime imagen (client-side)
3. App envía POST /api/v1/evidences/upload
4. Backend valida tipo, tamaño, dimensiones
5. Backend genera nombre único UUID
6. Backend comprime server-side (ImageMagick)
7. Backend guarda en filesystem
8. Backend registra metadata en BD (evidences table)
9. Backend retorna URL pública de la imagen
```

## 13.3 Eliminación Fotografías

| Regla | Detalle |
|-------|---------|
| Permiso | Solo administradores pueden eliminar |
| Auditoría | Toda eliminación registrada en audit_logs |
| Historial | Mantiene registro histórico de eliminaciones |

---

# 14. Estrategia Logging

## 14.1 Archivos Log

| Log | Propósito | Ruta |
|-----|-----------|------|
| application.log | General operación | /var/log/app/application.log |
| security.log | Auth y seguridad | /var/log/app/security.log |
| audit.log | Trazabilidad BD | /var/log/app/audit.log |
| error.log | Errores críticos | /var/log/app/error.log |

## 14.2 Configuración

| Parámetro | Valor |
|-----------|-------|
| Formato | JSON estructurado |
| Retención | 6 meses |
| Rotación | Diaria (max 100MB) |
| Nivel default | INFO |
| Nivel error | ERROR |

## 14.3 Logs Auditoría (Base de Datos)

```sql
CREATE TABLE audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(255),
    module VARCHAR(100),
    action VARCHAR(50),
    entity_type VARCHAR(100),
    entity_id BIGINT,
    old_value JSON,
    new_value JSON,
    ip_address VARCHAR(45),
    user_agent TEXT,
    timestamp DATETIME NOT NULL,
    details TEXT
);
```

---

# 15. Estrategia Docker

## 15.1 Contenedores

| Servicio | Imagen | Puerto | Descripción |
|----------|--------|--------|-------------|
| backend | custom quarkus-app | 8080 | API REST |
| dashboard | custom react-app | 3000 | Dashboard |
| mysql | mysql:8 | 3306 | Base datos |
| nginx | nginx:alpine | 80,443 | Proxy + SSL |

## 15.2 Docker Compose

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8
    volumes:
      - mysql_data:/var/lib/mysql
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
      MYSQL_DATABASE: ${DB_NAME}
    networks:
      - app-network

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - QUARKUS_DATASOURCE_JDBC_URL=jdbc:mysql://mysql:3306/${DB_NAME}
    depends_on:
      - mysql
    networks:
      - app-network

  dashboard:
    build: ./dashboard
    ports:
      - "3000:3000"
    depends_on:
      - backend
    networks:
      - app-network

  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
    depends_on:
      - backend
      - dashboard
    networks:
      - app-network

volumes:
  mysql_data:

networks:
  app-network:
    driver: bridge
```

## 15.3 Redes

| Red | Tipo | Propósito |
|-----|------|-----------|
| app-network | bridge | Comunicación interna entre servicios |

---

# 16. Estrategia CI/CD (GitHub Actions)

## 16.1 Pipeline

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          
      - name: Build Backend
        run: cd backend && ./mvnw clean package -DskipTests
        
      - name: Build Frontend Web
        run: cd dashboard && npm ci && npm run build
        
      - name: Build Frontend Mobile
        run: cd mobile && npm ci
        
      - name: Run Tests
        run: cd backend && ./mvnw test
        
      - name: Docker Build & Push
        run: |
          docker build -t app:${{ github.sha }} .
          docker push registry/app:${{ github.sha }}
```

## 16.2 Ambientes

| Rama | Ambiente | URL |
|------|----------|-----|
| main | Producción | api.produccion.com |
| develop | QA | api.qa.dominio.com |
| feature/* | Desarrollo | api.dev.dominio.com |

---

# 17. Monitoreo y Healthchecks

## 17.1 Endpoints Salud

| Endpoint | Descripción |
|----------|-------------|
| GET /health | Healthcheck general |
| GET /health/ready | Readiness probe |
| GET /health/live | Liveness probe |
| GET /metrics | Métricas Prometheus |

## 17.2 Métricas

| Métrica | Descripción |
|---------|-------------|
| Uptime | Tiempo disponible del sistema |
| Memoria usada | Consumo de memoria JVM |
| Conexiones DB | Pool de conexiones MySQL |
| Requests por segundo | Tráfico entrante |
| Tiempo respuesta promedio | Latencia API |
| Errores 5xx | Tasa de errores服务端 |

---

# 18. Timezone y Localización

| Configuración | Valor |
|---------------|-------|
| Timezone servidor | America/Lima (UTC-5) |
| Timezone DB | America/Lima |
| Formato fecha | ISO 8601 |
| Locale | es-PE |
| Moneda | PEN (S/) |

---

# 19. Convenciones Git

| Elemento | Convención |
|----------|------------|
| Rama principal | main |
| Rama desarrollo | develop |
| Ramas feature | feature/nombre-feature |
| Ramas fix | fix/nombre-fix |
| Ramas hotfix | hotfix/nombre-hotfix |
| Commits | Conventional Commits |

---

# 20. Dependencias Externas

| Servicio | Proveedor | Propósito |
|----------|-----------|-----------|
| Microsoft Identity | Azure AD | Autenticación corporativa |
| VPS Linux | Hetzner/DigitalOcean | Hospedaje servidores |
| Dominio | DNS provider | Nombre de dominio |

---

# 21. Consideraciones de Escalabilidad

## 21.1 Escalabilidad Horizontal (Futuro)

- Balanceador de carga (Nginx/HAProxy)
- Múltiples instancias backend
- Sesiones distribuidas (Redis)
- Read replicas MySQL

## 21.2 Escalabilidad de Almacenamiento (Futuro)

- MinIO/S3 para fotografías
- CDN para entrega contenido estático
- Limpieza automática de archivos antiguos

---

# 22. Aprobaciones

| Rol | Responsable | Estado |
|---|---|---|
| Responsable Funcional | Jose Anyarin | ✅ Completado |
| Responsable Técnico | Jose Anyarin | ✅ Completado |
| Aprobación Final | Pendiente | Pendiente |

---

# 23. Historial de Cambios

| Versión | Fecha | Descripción | Autor |
|---------|-------|--------------|-------|
| 1.0 | 2026-05-19 | Versión inicial arquitectura técnica | Jose Anyarin |


