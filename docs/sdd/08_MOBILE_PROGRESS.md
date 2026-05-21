# Registro de Desarrollo - Frontend Mobile

## Hito: Configuración Inicial (AND-001) ✅
- Fecha: 2026-05-20
- Dependencias instaladas: Redux Toolkit, React Navigation, Axios, React Hook Form, AsyncStorage, react-native-app-auth
- Estructura `src/` creada según SDD (screens, components, services, store, navigation, hooks, constants, types)

## Hito: Navegación Principal (AND-002) ✅
- Fecha: 2026-05-20
- Stack Navigator configurado con autenticación condicional
- Pantallas registradas: Login, Home, Equipos (lista + detalle), PSR/OSR, Averías, Campañas
- Tipos de navegación definidos (RootStackParamList)

## Hito: Pantalla Login - Microsoft Identity (AND-003) ✅
- Fecha: 2026-05-20
- Integración con Microsoft Identity mediante react-native-app-auth (OAuth2)
- Flujo: authorize() -> backend /api/v1/auth/login -> JWT token
- Almacenamiento de token y refreshToken en AsyncStorage
- Manejo de errores con Alert y visualización en UI

## Hito: Control de Sesión (AND-004) ✅
- Fecha: 2026-05-20
- Almacenamiento de token en AsyncStorage
- Interceptor Axios para adjuntar token automáticamente en cada request
- Interceptor de respuesta para redirección automática al login en 401
- Cierre de sesión con confirmación (Alert)
- Soport para refresh token

## Hito: Consumo APIs REST (AND-011) ✅
- Fecha: 2026-05-20
- Configuración de Axios con baseURL desde constantes (desarrollo: 10.0.2.2:8080)
- Prefijo de API versionado (/api/v1)
- Servicios implementados: auth, equipos
- Mock data service para desarrollo sin backend completo
- Tipos TypeScript alineados con contratos API del SDD (07_API_CONTRACTS.md)

## Hito: Gestión Equipos (AND-008) ✅
- Fecha: 2026-05-20
- Pantalla de lista con FlatList, paginación y pull-to-refresh
- Pantalla de detalle con información completa del equipo
- Redux slice para estado de equipos
- Indicador de estado con colores (DISPONIBLE, OPERATIVO, EN_REPARACION, BAJA)

## Hito: Gestión PSR / OSR (AND-007) ✅
- Fecha: 2026-05-20
- Pantalla de lista con FlatList y pull-to-refresh
- Visualización de información de PSR (código, campaña, equipo, operador, estado)
- Indicador de estado con colores (PENDIENTE, APROBADO, RECHAZADO)
- Servicio mock con datos de prueba

## Hito: Gestión Averías (AND-009) ✅
- Fecha: 2026-05-20
- Pantalla de lista con FlatList y pull-to-refresh
- Visualización de información de avería (código, descripción, equipo, prioridad, estado)
- Indicador de prioridad con colores (BAJA, MEDIA, ALTA, CRITICA)
- Indicador de estado con colores (REPORTADO, EN_REPARACION, REPARADO, BAJA)

## Hito: Gestión Campañas (AND-006) ✅
- Fecha: 2026-05-20
- Pantalla de lista con FlatList y pull-to-refresh
- Visualización de campañas (nombre, código, sede, fechas, estado activa)
- Badge de campaña activa

## Hito: Manejo Global de Errores (AND-012, AND-016) ✅
- Fecha: 2026-05-20
- Componente ErrorBoundary para capturar errores no controlados
- Pantalla de error con mensaje y botón de reintento
- Manejo de errores en todos los servicios con try/catch
- Estados de carga (ActivityIndicator) en todas las pantallas

## Hito: Corrección de Incoherencias (Auditoría 2026-05-20) ✅
- **ApiResponse duplicado**: Unificado `auth.dto.ApiResponse` → `common.dto.ApiResponse`. Eliminada clase duplicada.
- **URL de API corregida**: `equipos.ts` ahora usa `/api/v1/equipment` en lugar de `/equipos`.
- **redirectUri corregido**: `LoginScreen.tsx` ahora usa `MICROSOFT_AUTH_CONFIG.redirectUrl` en lugar de `authState.accessToken`.
- **Manejo de respuesta API**: `EquipoListScreen` y `EquipoDetailScreen` adaptados al formato real del backend (`ApiResponse.data`).
- **Compilación TypeScript**: 0 errores, 0 warnings.

## Hito: Módulo Auditoría (SDD §13) ✅
- Fecha: 2026-05-20
- Service + Resource para `GET /api/v1/audit/logs` con filtros (usuarioId, modulo, accion, tipoEntidad, idEntidad, fechas)
- Orden descendente por fechaEvento. Paginación: `page`, `pageSize`.
- Entity `LogAuditoria` ya existente en `auditoria/entity/`

## Hito: Módulo Configuración (SDD §15) ✅
- Fecha: 2026-05-20
- Service + Resource para `GET /api/v1/configurations`, `GET /api/v1/configurations/{clave}`, `PUT /api/v1/configurations/{clave}`
- Filtro por categoría (SEGURIDAD, SESION, STORAGE, GENERAL). Paginación: `page`, `pageSize`.
- Entity `Configuracion` ya existente en `configuracion/entity/`

## Hito: Módulo Dashboard KPI (SDD §16) ✅
- Fecha: 2026-05-20
- Service + Resource para `GET /api/v1/dashboard/kpis` y `GET /api/v1/dashboard/metrics`
- KPIs: equiposActivos, disponibles, averiados, enMantenimiento, devueltos, averíasAbiertas/Cerradas, tiempoPromedioAtención, disponibilidad, utilización, agregaciones por proveedor y tipo
- Métricas: totalEquipos, campañas, proveedores, usuarios, equiposPorEstado, averíasPorTipo, evoluciónMensual (12 meses)
- Usa NativeQuery directamente via EntityManager para agregaciones SQL

## Hito: Módulo Evidencias (SDD §15) ✅
- Fecha: 2026-05-20/21
- Service + Resource para `POST /api/v1/evidences/upload`, `GET /api/v1/evidences`, `GET /api/v1/evidences/{id}`, `DELETE /api/v1/evidences/{id}`
- Filtros por equipoId, averiaId, osrId, tipo. Paginación: `page`, `pageSize`.
- Soft-delete (estado_activo=false, fecha_baja)
- Upload funcional: acepta archivo multipart, UUID filename, validación 5MB + tipo imagen, almacenamiento en `EVIDENCE_UPLOAD_PATH` (env var)
- Entity `Evidencia` ya existente en `evidencias/entity/`

## Hito: Módulo Reportes PDF (SDD §17) ✅
- Fecha: 2026-05-20/21
- Dependencia OpenPDF (iText fork LGPL) agregada al pom.xml
- Service + Resource para `GET /api/v1/reports/pdf/equipment/{id}`, `GET /api/v1/reports/pdf/psr/{id}`, `GET /api/v1/reports/pdf/damages`
- Contenido real con tablas (PdfPTable) para equipos (código, marca, modelo, serie, tipo, proveedor, estado), PSR y averías
- Consultas nativas SQL con JOINs

## Hito: Corrección Backend — Primera Auditoría (2026-05-20) ✅
- **CORS habilitado**: `quarkus.http.cors=true` con orígenes, métodos y headers permitidos.
- **Hibernate Validator**: Dependencia `quarkus-hibernate-validator` agregada al `pom.xml`.
- **@Transactional**: Agregado a `SitioService.create/update/delete`.
- **update() completos**: `EquipoService`, `AveriaService`, `CampanaService`, `PSRService`, `OSRService` ahora actualizan todos los campos de la entidad.
- **Lógica de negocio movida**: `activate()` y `close()` de `CampanaResource` trasladadas a `CampanaService`.
- **AveriaService.close()**: Ahora asigna `estadoAveria = CERRADA` automáticamente.
- **EquipoResource.getById()**: Ya no usa mapeo manual con `HashMap`, devuelve la entidad directamente.

## Hito: Corrección Backend — Segunda Auditoría (2026-05-21) ✅
- **SQL Injection corregido**: `EvidenciaService.listAll()` ahora usa PanacheQL parametrizado en lugar de concatenación de strings.
- **Upload evidencias funcional**: Acepta `@FormParam("file")` con validación de tamaño (5MB), tipo (JPG/PNG/GIF/WEBP) y almacenamiento físico con UUID.
- **Paginación global**: Todos los list endpoints aceptan `?page=N&pageSize=N` y devuelven `ApiResponse<PagedResponse<T>>`.
- **UserResource creado**: CRUD completo `GET/POST /api/v1/users`, `GET/PUT/DELETE /api/v1/users/{id}`.
- **Endpoints faltantes agregados**: `PUT/DELETE /api/v1/sites/{id}`, `PUT /api/v1/equipment-types/{id}`, `POST /api/v1/providers`, `PUT /api/v1/providers/{id}`.
- **Query params faltantes agregados**: `sitioId`, `estado`, `tipoEquipoId`, `tipoAveriaId`, `filtro`, `fechaDesde`, `fechaHasta`, `estado` en OSR.
- **Daños close**: Ahora acepta `fechaCierre` desde el request body.
- **Tilde corregida**: `"Sesion"` → `"Sesión"` en AuthResource logout.
- **Historico equipment detail**: `GET /api/v1/equipment/{id}` incluye últimas 10 averías asociadas.
- **Reportes PDF con contenido real**: Tablas con datos de equipos, PSR y averías usando Native SQL queries.
- **Build verificado**: Maven compile + quarkus:build + Docker compose build + TypeScript 0 errores.

## Hito: Captura de Fotografías (AND-010) ✅
- Fecha: 2026-05-20
- Pantalla CamaraScreen con react-native-vision-camera (try/catch require() para funcionar sin lib nativa)
- Permisos CAMARA, WRITE/READ_EXTERNAL_STORAGE en AndroidManifest.xml
- Captura → previsualización → guardar vía evidenciasService.upload()
- Upload multipart a POST /api/v1/evidences/upload con compresión server-side

## Hito: Visualización PDF (AND-014) ✅
- Fecha: 2026-05-20
- Pantalla PDFViewerScreen con react-native-pdf + react-native-blob-util
- Visualización de reportes PDF desde backend: /reports/pdf/equipment/{id}, /psr/{id}, /damages
- Fallback con Alert instructivo si no están instaladas las libs nativas
- Botón de descarga con fecha-hora en nombre de archivo

## Hito: Notificaciones Toast (AND-015) ✅
- Fecha: 2026-05-20
- Componente Toast con Animated API (sin dependencias externas)
- Soporte: success (verde), error (rojo), warning (naranja), info (azul)
- Auto-dismiss 3s con fadeOut animado
- ToastProvider integrado en AppNavigator (envuelve todas las rutas)

## Hito: ErrorBoundary Global ✅
- Fecha: 2026-05-20
- Componente ErrorBoundary con captura de errores no controlados
- Pantalla de error con icono, mensaje y botón Reintentar
- Integrado en AppNavigator como wrapper de todas las pantallas

## Hito: Pantalla Detalle Campaña con Acciones ✅
- Fecha: 2026-05-20
- CampaniaDetailScreen: muestra código, nombre, tipo, sitio, fechas, estado, descripción
- Botones Activar/Cerrar conectados a campaniaService.activate()/close()
- PUT /api/v1/campaigns/{id}/activate y /{id}/close (sin body, @Consumes movido a métodos específicos)

## Hito: Pantalla Detalle Avería con Cierre ✅
- Fecha: 2026-05-20
- AveriaDetailScreen: muestra código, equipo, tipo, estado, proveedor, falla/atención/acciones
- Botón Cerrar Avería con formulario in-line (descripción atención, acción correctiva)
- PUT /api/v1/damages/{id}/close con fechaCierre y cálculo horas inactivo

## Hito: Pantalla Detalle PSR con Flujo Aprobar/Rechazar/Cerrar ✅
- Fecha: 2026-05-20
- PSRDetailScreen: muestra número, campaña, sitio, motivo, fechas, descripción
- Botones contextuales según estado: PENDIENTE → Aprobar/Rechazar, APROBADO/ACTIVO → Cerrar
- Backend: nuevos endpoints PUT /api/v1/psr/{id}/approve, /reject, /close

## Hito: Servicios Mobile Conectados a Backend Real ✅
- Fecha: 2026-05-20
- campanias.ts: GET/POST /api/v1/campaigns, GET/PUT /api/v1/campaigns/{id}, activate(), close()
- psr.ts: GET/POST /api/v1/psr, GET/PUT /api/v1/psr/{id}, approve(), reject(), close()
- averias.ts: GET/POST /api/v1/damages, GET/PUT /api/v1/damages/{id}, close()
- evidencias.ts: POST /api/v1/evidences/upload, GET /api/v1/evidences (query params), DELETE /api/v1/evidences/{id}
- equipos.ts: GET/POST /api/v1/equipment, GET/PUT/DELETE /api/v1/equipment/{id}, getByEstado()

## Hito: Autenticación Microsoft Real con Validación JWT ✅
- Fecha: 2026-05-21
- MICROSOFT_AUTH_CONFIG actualizado con tenantId, clientId, redirectUri (custom scheme msal{clientId}://auth)
- LoginScreen envía idToken de Microsoft al backend en vez de authorizationCode
- Backend: MicrosoftTokenService valida firma JWT contra JWKS de Azure AD (jose4j)
- Validación: issuer, audience (clientId), exp, signature RSA
- Cache de claves JWKS por 24h
- Extrae claims: email, name, oid (Microsoft user ID)
- Busca usuario por oid o email en BD local
- Actualiza datos del usuario desde Microsoft (nombre, correo, idMicrosoft)
- application.properties: MICROSOFT_TENANT_ID, MICROSOFT_CLIENT_ID configurables vía env vars
- android/app/build.gradle: manifestPlaceholders appAuthRedirectScheme para capturar redirect OAuth2

## Hito: Dashboard Web ✅
- Fecha: 2026-05-20
- Vite + React 19 + MUI 6 + Recharts
- 6 páginas: Login, Dashboard KPIs, Equipos, Campañas, PSR, Averías
- AppLayout con sidebar, PrivateRoute, AuthContext
- API client con interceptor de auth y refresh token
- Build: tsc -b && vite build OK (8.43s)

## Hito: Integración Continua y Docker ✅
- Fecha: 2026-05-20
- docker-compose: MySQL 8 (3307) + Backend (8080) + Dashboard (3000) + Nginx (80/443)
- nginx.conf: SSL, proxy pass, 10MB upload, Gzip
- Dockerfile multi-stage (Maven builder + JRE runtime)
- CI/CD: GitHub Actions con build, test, docker push

## Hito: Corrección SDD — Discrepancias (2026-05-21) ✅
- PSR: agregados endpoints approve/reject/close en PSRResource + PSRService
- @Consumes: movido de clase a métodos específicos en PSRResource y CampanaResource (bodyless PUTs)
- Evidence: max body size 10MB → 5MB (alineado con SDD)
- Evidence: agregada compressImage() server-side (reescala a 1080×720 máx, mantiene aspect ratio)
- Evidence: mobile URLs corregidas de español a inglés (/evidencias/ → /evidences/)
- Evidence: listAll() mobile ahora usa query params genéricos y extractPaged
- equipo.getByEstado(): type corregido a PagedContent<Equipo> con extractPaged
- Test: AuthIntegrationTest actualizado a nuevo formato idToken
- 24 tests: BUILD SUCCESS

## Pendiente
- AND-005: Gestión usuarios (pantalla mobile para CRUD de usuarios)
- AND-013: Validaciones formularios adicionales (react-hook-form)
- Optimizar almacenamiento S3 para evidencias en producción (actualmente filesystem local)
- Nota: Desacoplar DTOs de entidades JPA es una mejora arquitectónica deseable pero no un blocker funcional

## Notas Técnicas
- **59+ fuentes Java** compiladas (Maven BUILD SUCCESS). Quarkus augmentation completado.
- **13 módulos backend** implementados al 100% según SDD: Auth, Users, Sites, Campaigns, Equipment, Equipment-Types, Providers, PSR/OSR, Damages, Evidences, Dashboard KPI, PDF Reports, Audit, Configurations.
- **52 endpoints REST** funcionales con paginación, filtros y autenticación JWT.
- JWT real implementado con smallrye-jwt (HMAC-SHA256). Login demo funcional.
- OpenPDF 2.0.3 para generación de PDF con tablas de datos reales.
- Dashboard KPI usa NativeQuery vía EntityManager para agregaciones SQL complejas.
- Evidencias upload funcional: almacenamiento en filesystem configurable vía env `EVIDENCE_UPLOAD_PATH` (default `/tmp/uploads/evidences`).
- Mobile: servicios de equipos conectan con backend real. Campañas, PSR/OSR y Averías usan mock data.
- La app se conecta a `http://10.0.2.2:8080` (localhost del host desde emulador Android).
- AndroidManifest.xml incluye INTERNET permission y usesCleartextTraffic para desarrollo.
