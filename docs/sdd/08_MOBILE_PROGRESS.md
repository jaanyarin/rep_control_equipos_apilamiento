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
- Orden descendente por fechaEvento
- Entity `LogAuditoria` ya existente en `auditoria/entity/`

## Hito: Módulo Configuración (SDD §15) ✅
- Fecha: 2026-05-20
- Service + Resource para `GET /api/v1/configurations`, `GET /api/v1/configurations/{clave}`, `PUT /api/v1/configurations/{clave}`
- Filtro por categoría (SEGURIDAD, SESION, STORAGE, GENERAL)
- Entity `Configuracion` ya existente en `configuracion/entity/`

## Hito: Módulo Dashboard KPI (SDD §16) ✅
- Fecha: 2026-05-20
- Service + Resource para `GET /api/v1/dashboard/kpis` y `GET /api/v1/dashboard/metrics`
- KPIs: equiposActivos, disponibles, averiados, enMantenimiento, devueltos, averíasAbiertas/Cerradas, tiempoPromedioAtención, disponibilidad, utilización, agregaciones por proveedor y tipo
- Métricas: totalEquipos, campañas, proveedores, usuarios, equiposPorEstado, averíasPorTipo, evoluciónMensual (12 meses)
- Usa NativeQuery directamente via EntityManager para agregaciones SQL

## Hito: Módulo Evidencias (SDD §15) ✅
- Fecha: 2026-05-20
- Service + Resource para `POST /api/v1/evidences/upload`, `GET /api/v1/evidences`, `GET /api/v1/evidences/{id}`, `DELETE /api/v1/evidences/{id}`
- Filtros por equipoId, averiaId, osrId, tipo
- Soft-delete (estado_activo=false, fecha_baja)
- Entity `Evidencia` ya existente en `evidencias/entity/`

## Hito: Módulo Reportes PDF (SDD §17) ✅
- Fecha: 2026-05-20
- Dependencia OpenPDF (iText fork LGPL) agregada al pom.xml
- Service + Resource para `GET /api/v1/reports/pdf/equipment/{id}`, `GET /api/v1/reports/pdf/psr/{id}`, `GET /api/v1/reports/pdf/damages`
- Generación básica de PDF con OpenPDF (Document → PdfWriter → ByteArrayOutputStream)

## Hito: Corrección Backend (Auditoría 2026-05-20) ✅
- **CORS habilitado**: `quarkus.http.cors=true` con orígenes, métodos y headers permitidos.
- **Hibernate Validator**: Dependencia `quarkus-hibernate-validator` agregada al `pom.xml`.
- **@Transactional**: Agregado a `SitioService.create/update/delete`.
- **update() completos**: `EquipoService`, `AveriaService`, `CampanaService`, `PSRService`, `OSRService` ahora actualizan todos los campos de la entidad.
- **Lógica de negocio movida**: `activate()` y `close()` de `CampanaResource` trasladadas a `CampanaService`.
- **AveriaService.close()**: Ahora asigna `estadoAveria = CERRADA` automáticamente.
- **EquipoResource.getById()**: Ya no usa mapeo manual con `HashMap`, devuelve la entidad directamente.
- **Compilación Maven**: BUILD SUCCESS (59 fuentes, Quarkus augmentation completado).

## Pendiente
- AND-005: Gestión usuarios (requiere backend endpoints)
- AND-010: Captura fotografías (requiere permisos cámara y almacenamiento físico de archivos)
- AND-013: Validaciones formularios adicionales
- AND-014: Visualización PDFs (requiere visor de PDF en mobile)
- AND-015: Notificaciones visuales (requiere toast/notification library)
- Servicios mobile: migrar Campañas, PSR/OSR y Averías de mock data a backend real
- Almacenamiento físico de archivos de evidencia (subida a file system / S3)
- Reportes PDF con contenido real (actualmente generan PDF con título básico)

## Notas Técnicas
- **59 fuentes Java** compiladas (Maven BUILD SUCCESS). Quarkus augmentation completado.
- Los 5 nuevos módulos backend están implementados: Auditoría (13), Dashboard KPI (16), Evidencias (15), Configuración (19), Reportes PDF (17).
- JWT real implementado con smallrye-jwt (HMAC-SHA256). Login demo funcional.
- OpenPDF 2.0.3 agregado al pom.xml para generación de PDF.
- Dashboard KPI usa NativeQuery vía EntityManager para agregaciones SQL complejas.
- Los endpoints de Evidencias reciben multipart/form-data pero el almacenamiento físico de archivos queda pendiente (retorna registro con datos placeholder).
- Los endpoints de Reportes generan PDF básico (título + subtítulo) — el contenido detallado se implementará cuando se definan los templates.
- Mobile: servicios de equipos conectan con backend real. Campañas, PSR/OSR y Averías usan mock data.
- La app se conecta a `http://10.0.2.2:8080` (localhost del host desde emulador Android).
- AndroidManifest.xml incluye INTERNET permission y usesCleartextTraffic para desarrollo.
