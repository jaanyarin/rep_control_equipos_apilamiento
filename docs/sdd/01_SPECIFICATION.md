# SOFTWARE SPECIFICATION DOCUMENT
# Sistema de Control de Equipos de Apilamiento

# Tabla de Contenido

- [1. Información General](#1-información-general)
- [2. Objetivo del Sistema](#2-objetivo-del-sistema)
- [3. Alcance del Sistema](#3-alcance-del-sistema)
- [4. Problemática Actual](#4-problemática-actual)
- [5. Actores del Sistema](#5-actores-del-sistema)
- [6. Roles y Permisos](#6-roles-y-permisos)
- [7. Módulos del Sistema](#7-módulos-del-sistema)
- [8. Requerimientos Funcionales](#8-requerimientos-funcionales)
- [9. Reglas de Negocio](#9-reglas-de-negocio)
- [10. Requerimientos No Funcionales](#10-requerimientos-no-funcionales)
- [11. Entidades Principales](#11-entidades-principales)
- [12. Flujos Operativos](#12-flujos-operativos)
- [13. Reportes](#13-reportes)
- [14. Consideraciones Técnicas](#14-consideraciones-técnicas)
- [15. Pendientes Funcionales](#15-pendientes-funcionales)
- [16. Anexos](#16-anexos)



# 1. Información General

| Campo | Detalle |
| Proyecto | Sistema de Control de Equipos de Apilamiento |
| Tipo de Sistema | Plataforma Full Stack Empresarial |
| Plataforma | Android + Web |
| Versión Documento | 1.0 |
| Estado | En elaboración |
| Fecha | 2026-05-18 |
| Responsable | Jose Anyarin |



# 2. OBJETIVO DEL SISTEMA

## 2.1 Objetivo General

Desarrollar una plataforma digital empresarial para el control operativo de equipos de apilamiento alquilados, permitiendo gestionar el ciclo completo de solicitud, asignación, operación, averías, devoluciones y evidencias fotográficas, garantizando trazabilidad, control de tiempos de inactividad y soporte documental para la validación operacional y financiera del servicio.



## 2.2 Objetivos Específicos

- Centralizar el registro operativo de equipos alquilados.
- Controlar la entrega y devolución de equipos mediante evidencias fotográficas.
- Registrar y monitorear averías operativas.
- Calcular automáticamente tiempos de inactividad.
- Reducir sobrecostos asociados a sobre-uso y averías.
- Mantener trazabilidad histórica de cada equipo.
- Permitir consulta y emisión de reportes operativos.
- Facilitar la supervisión y auditoría de equipos alquilados.
- Digitalizar procesos manuales actualmente gestionados en Excel.



# 3. ALCANCE DEL SISTEMA

## 3.1 Incluye

- Aplicativo móvil Android para operación en campo.
- Plataforma web para visualización y gestión administrativa.
- Gestión de usuarios y autenticación mediante cuenta Microsoft corporativa.
- Gestión de roles y permisos.
- Gestión multi-sede operativa.
- Registro manual de PSR y OSR.
- Gestión de campañas operativas.
- Registro de equipos alquilados.
- Gestión de múltiples tipos de equipos.
- Registro de accesorios y componentes asociados.
- Registro y gestión de averías.
- Control de estados operativos de equipos.
- Registro de evidencias fotográficas.
- Generación de reportes PDF.
- Dashboard web con indicadores operativos.
- Gestión de proveedores.
- Gestión de marcas y catálogos.
- Historial operativo de equipos.
- Auditoría y trazabilidad de operaciones mediante autenticación y registros de actividad.
- Validaciones operativas en tiempo real.
- Mensajes visuales de confirmación, validación y error durante las operaciones ejecutadas por el usuario.



## 3.2 No Incluye

- Integración con NISIRA.
- Operación offline.
- Geolocalización.
- Telemetría en tiempo real.
- Integración ERP.
- Facturación electrónica.
- Workflow de aprobaciones.
- Notificaciones push.
- Integraciones con WhatsApp.
- Integraciones con correo automático.
- Gestión financiera.
- Módulo de mantenimiento predictivo.



# 4. PROBLEMÁTICA ACTUAL

## 4.1 Situación Actual

Actualmente el control operativo de equipos de apilamiento alquilados se realiza mediante procesos manuales y registros dispersos en archivos Excel, lo que genera limitaciones en la trazabilidad, control operativo y validación de información relacionada con el uso de equipos.

No existe una plataforma centralizada que permita gestionar el ciclo completo de los equipos, desde la generación de solicitudes PSR/OSR hasta la entrega, operación, averías y devolución final.

La gestión actual presenta dificultades para controlar:

- tiempos reales de uso,
- tiempos de inactividad,
- evidencias fotográficas,
- historial de averías,
- validación de responsabilidades,
- control documental,
- indicadores operativos.

Esto ocasiona riesgos operativos, pagos indebidos por sobre-uso, dificultades de auditoría y baja capacidad de análisis para la toma de decisiones.



## 4.2 Problemas Identificados

| Código | Problema |
|||
| PRB-001 | No existe trazabilidad centralizada de equipos alquilados |
| PRB-002 | No existe control estructurado de averías |
| PRB-003 | No existe validación visual documentada mediante evidencias |
| PRB-004 | Existen riesgos de pagos indebidos por tiempos inactivos |
| PRB-005 | El proceso depende de archivos Excel manuales |
| PRB-006 | No existen indicadores operativos centralizados |
| PRB-007 | No existe historial operacional consolidado por equipo |
| PRB-008 | No existe control estructurado de responsabilidades operativas |

# 5. Actores del Sistema

| Actor | Descripción |
|---|---|
| Administrador | Responsable de la administración general del sistema, configuración, monitoreo operativo, reportes e indicadores |
| Usuario | Responsable del registro operativo de equipos, averías, actualización de estados y evidencias fotográficas desde el aplicativo móvil |

# 6. Roles y Permisos

| Rol | Descripción |
|---|---|
| ADMIN | Acceso total a configuración, reportes, dashboard, catálogos, campañas, auditoría y monitoreo operativo |
| USER | Acceso operativo para registro de equipos, averías, evidencias fotográficas y actualización de estados |

# 7. Módulos del Sistema

| Código | Módulo | Descripción |
|---|---|---|
| MOD-01 | Autenticación | Gestión de autenticación mediante cuenta Microsoft corporativa y control de sesiones |
| MOD-02 | Usuarios | Administración de usuarios, roles y accesos al sistema |
| MOD-03 | Sedes | Gestión de sedes operativas como Packing Uva y Packing Arándano |
| MOD-04 | Campañas | Gestión de campañas operativas |
| MOD-05 | PSR / OSR | Registro y control documental de solicitudes operativas |
| MOD-06 | Equipos | Gestión operativa de equipos alquilados |
| MOD-07 | Tipos de Equipos | Administración de categorías y tipos de equipos |
| MOD-08 | Proveedores | Gestión de proveedores asociados a equipos |
| MOD-09 | Averías | Registro, seguimiento y control de averías operativas |
| MOD-10 | Evidencias Fotográficas | Gestión de fotografías asociadas a operaciones y averías |
| MOD-11 | Dashboard KPI | Visualización de indicadores y métricas operativas |
| MOD-12 | Reportes PDF | Generación y exportación de reportes operativos en PDF |
| MOD-13 | Auditoría | Registro de trazabilidad y actividad operacional del sistema |
| MOD-14 | Catálogos | Administración de datos maestros y configuraciones auxiliares |
| MOD-15 | Configuración | Configuración general del sistema |

# 8. REQUERIMIENTOS FUNCIONALES

# MOD-01 — AUTENTICACIÓN

| Código | Módulo | Nombre | Descripción | Actor | Prioridad |
|---|---|---|---|---|---|
| RF-001 | Autenticación | Inicio de sesión Microsoft | El sistema deberá permitir el inicio de sesión únicamente mediante cuentas Microsoft corporativas pertenecientes al dominio empresarial autorizado | Usuario, Administrador | Alta |
| RF-002 | Autenticación | Validación de dominio corporativo | El sistema deberá validar que la cuenta autenticada pertenezca al dominio corporativo autorizado antes de permitir el acceso | Usuario, Administrador | Alta |
| RF-003 | Autenticación | Validación de usuario registrado | El sistema deberá permitir el acceso únicamente a usuarios previamente registrados y habilitados en la base de datos interna del sistema | Usuario, Administrador | Alta |
| RF-004 | Autenticación | Validación de usuario activo | El sistema deberá validar que el usuario se encuentre en estado activo dentro del sistema antes de autorizar el acceso | Usuario, Administrador | Alta |
| RF-005 | Autenticación | Validación de estado Microsoft | El sistema deberá validar que la cuenta corporativa del usuario se encuentre habilitada dentro del entorno Microsoft empresarial | Usuario, Administrador | Alta |
| RF-006 | Autenticación | Asignación de roles | El sistema deberá recuperar y aplicar el rol asignado al usuario autenticado para controlar el acceso a funcionalidades y módulos | Usuario, Administrador | Alta |
| RF-007 | Autenticación | Persistencia de sesión | El sistema deberá mantener la sesión activa mediante mecanismos seguros de autenticación mientras el token de sesión permanezca vigente | Usuario, Administrador | Alta |
| RF-008 | Autenticación | Recuperación automática de sesión | El sistema deberá permitir la recuperación automática de sesión sin requerir nuevo inicio de sesión mientras las credenciales de autenticación continúen vigentes | Usuario, Administrador | Media |
| RF-009 | Autenticación | Expiración de sesión | El sistema deberá cerrar automáticamente la sesión del usuario después de un periodo configurable de inactividad | Usuario, Administrador | Alta |
| RF-010 | Autenticación | Cierre manual de sesión | El sistema deberá permitir al usuario cerrar sesión manualmente desde la aplicación móvil y plataforma web | Usuario, Administrador | Alta |
| RF-011 | Autenticación | Registro de auditoría de acceso | El sistema deberá registrar eventos de autenticación incluyendo inicio de sesión, cierre de sesión, fecha, hora y usuario asociado | Usuario, Administrador | Alta |
| RF-012 | Autenticación | Acceso multi-dispositivo | El sistema deberá permitir que un usuario autenticado pueda acceder desde múltiples dispositivos simultáneamente | Usuario, Administrador | Media |
| RF-013 | Autenticación | Bloqueo de acceso no autorizado | El sistema deberá denegar el acceso a usuarios no registrados, inactivos o sin permisos asignados | Usuario, Administrador | Alta |
| RF-014 | Autenticación | Administración de usuarios | El sistema deberá permitir al administrador registrar, habilitar, deshabilitar y actualizar usuarios autorizados | Administrador | Alta |
| RF-015 | Autenticación | Administración de roles | El sistema deberá permitir al administrador asignar y modificar roles de usuario dentro del sistema | Administrador | Alta |
| RF-016 | Autenticación | Control de permisos por rol | El sistema deberá restringir el acceso a funcionalidades y módulos según el rol asignado al usuario autenticado | Usuario, Administrador | Alta |
| RF-017 | Autenticación | Mensajes de validación de acceso | El sistema deberá mostrar mensajes visuales de confirmación o error durante el proceso de autenticación y validación de acceso | Usuario, Administrador | Media |
| RF-018 | Autenticación | Seguridad de tokens | El sistema deberá utilizar mecanismos seguros de autenticación basados en tokens para proteger las sesiones activas | Usuario, Administrador | Alta |
| RF-019 | Autenticación | Validación de sede asignada | El sistema deberá validar que el usuario autenticado tenga al menos una sede operativa asignada antes de permitir el acceso al sistema | Usuario, Administrador | Alta |
| RF-020 | Autenticación | Sincronización de información corporativa | El sistema deberá sincronizar información básica del usuario autenticado desde Microsoft corporativo, incluyendo nombre y correo electrónico | Usuario, Administrador | Media |
| RF-021 | Autenticación | Registro de intentos fallidos | El sistema deberá registrar intentos fallidos de autenticación para fines de auditoría y seguridad | Usuario, Administrador | Media |
| RF-022 | Autenticación | Restricción de navegación por rol | El sistema deberá mostrar únicamente las funcionalidades autorizadas según el rol asignado al usuario autenticado | Usuario, Administrador | Alta |
| RF-023 | Autenticación | Revocación inmediata de acceso | El sistema deberá invalidar sesiones activas cuando un usuario sea deshabilitado por un administrador | Administrador | Alta |

# MOD-02 — USUARIOS

| Código | Módulo | Nombre | Descripción | Actor | Prioridad |
|---|---|---|---|---|---|
| RF-024 | Usuarios | Registro de usuarios | El sistema deberá permitir al administrador registrar usuarios autorizados para el acceso al sistema | Administrador | Alta |
| RF-025 | Usuarios | Actualización de usuarios | El sistema deberá permitir al administrador actualizar información asociada a usuarios registrados | Administrador | Alta |
| RF-026 | Usuarios | Desactivación de usuarios | El sistema deberá permitir desactivar usuarios sin eliminar físicamente su información histórica | Administrador | Alta |
| RF-027 | Usuarios | Reactivación de usuarios | El sistema deberá permitir reactivar usuarios previamente deshabilitados | Administrador | Media |
| RF-028 | Usuarios | Asignación de roles | El sistema deberá permitir asignar roles operativos a usuarios registrados | Administrador | Alta |
| RF-029 | Usuarios | Modificación de roles | El sistema deberá permitir modificar roles asignados a usuarios existentes | Administrador | Alta |
| RF-030 | Usuarios | Asignación de sede | El sistema deberá permitir asignar una sede operativa a cada usuario registrado | Administrador | Alta |
| RF-031 | Usuarios | Cambio de sede operativa | El sistema deberá permitir modificar la sede asignada a un usuario registrado | Administrador | Media |
| RF-032 | Usuarios | Visualización de información corporativa | El sistema deberá mostrar información corporativa sincronizada desde Microsoft incluyendo nombre, correo, puesto, área, empresa, departamento y ubicación de trabajo | Administrador | Alta |
| RF-033 | Usuarios | Sincronización de información Microsoft | El sistema deberá sincronizar información corporativa del usuario desde Microsoft corporativo | Administrador | Alta |
| RF-034 | Usuarios | Edición de información local | El sistema deberá permitir editar información administrativa local asociada al usuario | Administrador | Media |
| RF-035 | Usuarios | Consulta de usuarios | El sistema deberá permitir visualizar el listado de usuarios registrados | Administrador | Alta |
| RF-036 | Usuarios | Búsqueda de usuarios | El sistema deberá permitir realizar búsquedas de usuarios mediante filtros de texto | Administrador | Alta |
| RF-037 | Usuarios | Filtro por sede | El sistema deberá permitir filtrar usuarios por sede operativa | Administrador | Media |
| RF-038 | Usuarios | Filtro por rol | El sistema deberá permitir filtrar usuarios según el rol asignado | Administrador | Media |
| RF-039 | Usuarios | Visualización de último acceso | El sistema deberá mostrar la fecha y hora del último acceso realizado por cada usuario | Administrador | Media |
| RF-040 | Usuarios | Registro de fecha de creación | El sistema deberá almacenar la fecha y hora de creación de cada usuario registrado | Administrador | Alta |
| RF-041 | Usuarios | Historial de cambios de usuario | El sistema deberá registrar cambios realizados sobre información de usuarios para fines de auditoría | Administrador | Alta |
| RF-042 | Usuarios | Visualización de estado de usuario | El sistema deberá mostrar el estado actual del usuario como activo o inactivo | Administrador | Media |
| RF-043 | Usuarios | Validación de usuarios duplicados | El sistema deberá impedir el registro de usuarios con el mismo correo corporativo ya existente en el sistema | Administrador | Alta |
| RF-044 | Usuarios | Validación de rol asignado | El sistema deberá requerir que todo usuario tenga un rol asignado antes de habilitar su acceso | Administrador | Alta |
| RF-045 | Usuarios | Validación de sede asignada | El sistema deberá requerir que todo usuario tenga una sede operativa asignada antes de habilitar su acceso | Administrador | Alta |
| RF-046 | Usuarios | Validación de existencia corporativa | El sistema deberá validar que el usuario registrado exista dentro del entorno corporativo Microsoft antes de habilitar su acceso | Administrador | Alta |
| RF-047 | Usuarios | Consulta de detalle de usuario | El sistema deberá permitir visualizar el detalle completo de información asociada a un usuario registrado | Administrador | Media |
| RF-048 | Usuarios | Ordenamiento de usuarios | El sistema deberá permitir ordenar el listado de usuarios por nombre, sede, rol o estado | Administrador | Baja |
| RF-049 | Usuarios | Restricción de acceso por estado | El sistema deberá impedir el acceso de usuarios que se encuentren en estado inactivo | Administrador | Alta |
| RF-050 | Usuarios | Visualización de rol asignado | El sistema deberá mostrar el rol operativo asignado a cada usuario dentro del módulo de administración | Administrador | Media |
| RF-051 | Usuarios | Visualización de sede asignada | El sistema deberá mostrar la sede operativa asociada a cada usuario registrado | Administrador | Media |
| RF-052 | Usuarios | Mensajes de validación administrativa | El sistema deberá mostrar mensajes visuales de confirmación y error durante las operaciones administrativas de usuarios | Administrador | Media |

# MOD-03 — SEDES

| Código | Módulo | Nombre | Descripción | Actor | Prioridad |
|---|---|---|---|---|---|
| RF-053 | Sedes | Registro de sedes | El sistema deberá permitir registrar nuevas sedes operativas dentro de la plataforma | Administrador | Alta |
| RF-054 | Sedes | Actualización de sedes | El sistema deberá permitir actualizar información asociada a sedes registradas | Administrador | Alta |
| RF-055 | Sedes | Visualización de sedes | El sistema deberá permitir visualizar el listado de sedes registradas | Administrador | Alta |
| RF-056 | Sedes | Búsqueda de sedes | El sistema deberá permitir realizar búsquedas de sedes mediante filtros de texto | Administrador | Media |
| RF-057 | Sedes | Ordenamiento de sedes | El sistema deberá permitir ordenar sedes por nombre o código operativo | Administrador | Baja |
| RF-058 | Sedes | Registro de código operativo | El sistema deberá permitir asignar un código único a cada sede registrada | Administrador | Alta |
| RF-059 | Sedes | Validación de código único | El sistema deberá impedir el registro de sedes con códigos operativos duplicados | Administrador | Alta |
| RF-060 | Sedes | Validación de nombre único | El sistema deberá impedir el registro de sedes con nombres duplicados | Administrador | Alta |
| RF-061 | Sedes | Estado operativo de sede | El sistema deberá permitir visualizar y administrar el estado operativo de una sede como activa o inactiva | Administrador | Media |
| RF-062 | Sedes | Restricción de eliminación | El sistema deberá impedir la eliminación física de sedes que tengan registros operativos asociados | Administrador | Alta |
| RF-063 | Sedes | Filtro global por sede | El sistema deberá permitir filtrar información operativa global mediante sede | Usuario, Administrador | Alta |
| RF-064 | Sedes | Visualización en dashboard | El sistema deberá permitir visualizar indicadores y métricas filtradas por sede operativa | Usuario, Administrador | Alta |
| RF-065 | Sedes | Inclusión de sede en reportes PDF | El sistema deberá incluir la sede operativa asociada dentro de los reportes PDF generados | Usuario, Administrador | Media |
| RF-066 | Sedes | Registro de sede en auditoría | El sistema deberá registrar la sede operativa asociada dentro de los eventos de auditoría | Usuario, Administrador | Alta |
| RF-067 | Sedes | Visualización de usuarios asociados | El sistema deberá mostrar la cantidad de usuarios asociados a cada sede | Administrador | Media |
| RF-068 | Sedes | Visualización de campañas asociadas | El sistema deberá mostrar las campañas relacionadas a cada sede operativa | Administrador | Media |
| RF-069 | Sedes | Historial de modificaciones | El sistema deberá registrar cambios realizados sobre la información de sedes para fines de auditoría | Administrador | Alta |
| RF-070 | Sedes | Configuración de periodo operativo | El sistema deberá permitir configurar periodos operativos referenciales para cada sede | Administrador | Baja |
| RF-071 | Sedes | Visualización de estado de sede | El sistema deberá mostrar el estado actual de cada sede como activa o inactiva | Administrador | Media |
| RF-072 | Sedes | Mensajes de validación administrativa | El sistema deberá mostrar mensajes visuales de confirmación y error durante las operaciones administrativas de sedes | Administrador | Media |

# MOD-04 — CAMPAÑAS

| Código | Módulo | Nombre | Descripción | Actor | Prioridad |
|---|---|---|---|---|---|
| RF-073 | Campañas | Registro de campañas | El sistema deberá permitir registrar nuevas campañas operativas dentro de la plataforma | Administrador | Alta |
| RF-074 | Campañas | Actualización de campañas | El sistema deberá permitir actualizar información asociada a campañas registradas | Administrador | Alta |
| RF-075 | Campañas | Visualización de campañas | El sistema deberá permitir visualizar el listado de campañas registradas | Usuario, Administrador | Alta |
| RF-076 | Campañas | Búsqueda de campañas | El sistema deberá permitir realizar búsquedas de campañas mediante filtros de texto | Usuario, Administrador | Media |
| RF-077 | Campañas | Ordenamiento de campañas | El sistema deberá permitir ordenar campañas por nombre, código o estado operativo | Usuario, Administrador | Baja |
| RF-078 | Campañas | Registro de código operativo | El sistema deberá permitir asignar un código único a cada campaña registrada | Administrador | Alta |
| RF-079 | Campañas | Validación de código único | El sistema deberá impedir el registro de campañas con códigos duplicados | Administrador | Alta |
| RF-080 | Campañas | Validación de nombre único | El sistema deberá impedir el registro de campañas con nombres duplicados | Administrador | Alta |
| RF-081 | Campañas | Estado de campaña | El sistema deberá permitir administrar el estado operativo de una campaña como activa o cerrada | Administrador | Alta |
| RF-082 | Campañas | Validación de campaña activa única | El sistema deberá permitir únicamente una campaña activa dentro de la plataforma | Administrador | Alta |
| RF-083 | Campañas | Asignación automática de campaña actual | El sistema deberá asociar automáticamente la campaña activa a los nuevos registros operativos | Usuario, Administrador | Alta |
| RF-084 | Campañas | Restricción de operaciones en campañas cerradas | El sistema deberá impedir el registro de nuevas operaciones sobre campañas cerradas | Usuario, Administrador | Alta |
| RF-085 | Campañas | Restricción de eliminación | El sistema deberá impedir la eliminación física de campañas que tengan registros operativos asociados | Administrador | Alta |
| RF-086 | Campañas | Activación manual de campaña | El sistema deberá permitir activar manualmente una campaña operativa | Administrador | Alta |
| RF-087 | Campañas | Cierre manual de campaña | El sistema deberá permitir cerrar manualmente campañas operativas para mantener historial histórico | Administrador | Alta |
| RF-088 | Campañas | Asociación de equipos a campaña | El sistema deberá permitir asociar equipos operativos a campañas activas | Administrador | Alta |
| RF-089 | Campañas | Asociación de equipos históricos | El sistema deberá permitir asociar equipos previamente registrados a nuevas campañas operativas | Administrador | Media |
| RF-090 | Campañas | Asociación de PSR/OSR a campaña | El sistema deberá asociar registros PSR y OSR a la campaña activa correspondiente | Usuario, Administrador | Alta |
| RF-091 | Campañas | Visualización de campaña activa | El sistema deberá mostrar visualmente la campaña activa actual dentro de la plataforma | Usuario, Administrador | Media |
| RF-092 | Campañas | Visualización de estado de campaña | El sistema deberá mostrar el estado actual de cada campaña como activa o cerrada | Usuario, Administrador | Media |
| RF-093 | Campañas | Historial de modificaciones | El sistema deberá registrar cambios realizados sobre campañas para fines de auditoría | Administrador | Alta |
| RF-094 | Campañas | Filtro global por campaña | El sistema deberá permitir filtrar información operativa global mediante campaña | Usuario, Administrador | Alta |
| RF-095 | Campañas | Visualización de indicadores por campaña | El sistema deberá permitir visualizar indicadores y métricas asociadas a campañas operativas | Usuario, Administrador | Alta |
| RF-096 | Campañas | Inclusión de campaña en reportes PDF | El sistema deberá incluir la campaña operativa asociada dentro de los reportes PDF generados | Usuario, Administrador | Media |
| RF-097 | Campañas | Registro de campaña en auditoría | El sistema deberá registrar la campaña operativa asociada dentro de los eventos de auditoría | Usuario, Administrador | Alta |
| RF-098 | Campañas | Visualización de campañas activas e históricas | El sistema deberá permitir visualizar campañas activas e históricas dentro del sistema | Usuario, Administrador | Media |
| RF-099 | Campañas | Mensajes de validación administrativa | El sistema deberá mostrar mensajes visuales de confirmación y error durante las operaciones administrativas de campañas | Administrador | Media |

# MOD-05 — PSR / OSR

| Código | Módulo | Nombre | Descripción | Actor | Prioridad |
|---|---|---|---|---|---|
| RF-100 | PSR / OSR | Registro de PSR | El sistema deberá permitir registrar pedidos de servicio de requerimiento (PSR) dentro de la campaña activa | Administrador | Alta |
| RF-101 | PSR / OSR | Actualización de PSR | El sistema deberá permitir actualizar información asociada a registros PSR | Administrador | Alta |
| RF-102 | PSR / OSR | Eliminación de PSR | El sistema deberá permitir eliminar registros PSR que no tengan información operativa dependiente | Administrador | Media |
| RF-103 | PSR / OSR | Registro de OSR | El sistema deberá permitir registrar órdenes de servicio de requerimiento (OSR) asociadas a un PSR existente | Administrador | Alta |
| RF-104 | PSR / OSR | Actualización de OSR | El sistema deberá permitir actualizar información asociada a registros OSR | Administrador | Alta |
| RF-105 | PSR / OSR | Eliminación de OSR | El sistema deberá permitir eliminar registros OSR que no tengan operaciones dependientes | Administrador | Media |
| RF-106 | PSR / OSR | Visualización de registros documentales | El sistema deberá permitir visualizar el listado de registros PSR y OSR registrados | Usuario, Administrador | Alta |
| RF-107 | PSR / OSR | Consulta de detalle documental | El sistema deberá permitir visualizar el detalle completo de registros PSR y OSR asociados | Usuario, Administrador | Alta |
| RF-108 | PSR / OSR | Búsqueda documental | El sistema deberá permitir realizar búsquedas de registros PSR y OSR mediante filtros operativos y documentales | Usuario, Administrador | Alta |
| RF-109 | PSR / OSR | Ordenamiento documental | El sistema deberá permitir ordenar registros PSR y OSR por número, campaña o fecha de registro | Usuario, Administrador | Baja |
| RF-110 | PSR / OSR | Validación de número PSR único | El sistema deberá impedir el registro de números PSR duplicados dentro de la plataforma | Administrador | Alta |
| RF-111 | PSR / OSR | Validación de número OSR único | El sistema deberá impedir el registro de números OSR duplicados dentro de la plataforma | Administrador | Alta |
| RF-112 | PSR / OSR | Relación obligatoria OSR-PSR | El sistema deberá requerir que todo registro OSR esté asociado obligatoriamente a un PSR existente | Administrador | Alta |
| RF-113 | PSR / OSR | Asociación obligatoria a campaña activa | El sistema deberá asociar obligatoriamente los registros PSR y OSR a la campaña activa | Administrador | Alta |
| RF-114 | PSR / OSR | Restricción de equipo único | El sistema deberá permitir asociar únicamente un equipo por cada registro OSR | Administrador | Alta |
| RF-115 | PSR / OSR | Restricción de reutilización simultánea | El sistema deberá impedir asociar un equipo a múltiples registros OSR activos simultáneamente | Administrador | Alta |
| RF-116 | PSR / OSR | Asociación de equipo a OSR | El sistema deberá permitir asociar un equipo operativo a registros OSR | Administrador | Alta |
| RF-117 | PSR / OSR | Visualización de relación documental | El sistema deberá visualizar la relación existente entre registros PSR y OSR asociados | Usuario, Administrador | Media |
| RF-118 | PSR / OSR | Historial documental | El sistema deberá permitir visualizar el historial de registros PSR y OSR generados históricamente | Usuario, Administrador | Media |
| RF-119 | PSR / OSR | Registro de observaciones | El sistema deberá permitir registrar observaciones asociadas a registros PSR y OSR | Usuario, Administrador | Media |
| RF-120 | PSR / OSR | Generación de PDF documental | El sistema deberá permitir generar reportes PDF asociados a registros PSR y OSR | Usuario, Administrador | Media |
| RF-121 | PSR / OSR | Auditoría documental | El sistema deberá registrar eventos de creación, actualización y eliminación de registros PSR y OSR | Usuario, Administrador | Alta |
| RF-122 | PSR / OSR | Filtro global documental | El sistema deberá permitir filtrar registros PSR y OSR mediante criterios operativos y documentales | Usuario, Administrador | Alta |
| RF-123 | PSR / OSR | Inclusión de campaña en registros documentales | El sistema deberá mostrar la campaña operativa asociada en registros PSR y OSR | Usuario, Administrador | Media |
| RF-124 | PSR / OSR | Mensajes de validación documental | El sistema deberá mostrar mensajes visuales de confirmación y error durante las operaciones de PSR y OSR | Usuario, Administrador | Media |

# MOD-06 — EQUIPOS

| Código | Módulo | Nombre | Descripción | Actor | Prioridad |
|---|---|---|---|---|---|
| RF-125 | Equipos | Registro de equipos | El sistema deberá permitir registrar equipos operativos asociados a un registro OSR | Administrador | Alta |
| RF-126 | Equipos | Actualización de equipos | El sistema deberá permitir actualizar información asociada a equipos registrados | Administrador | Alta |
| RF-127 | Equipos | Desactivación de equipos | El sistema deberá permitir desactivar equipos manteniendo su historial operativo | Administrador | Media |
| RF-128 | Equipos | Visualización de equipos | El sistema deberá permitir visualizar el listado de equipos registrados | Usuario, Administrador | Alta |
| RF-129 | Equipos | Consulta de detalle de equipo | El sistema deberá permitir visualizar el detalle completo de un equipo operativo | Usuario, Administrador | Alta |
| RF-130 | Equipos | Búsqueda de equipos | El sistema deberá permitir realizar búsquedas de equipos mediante filtros operativos | Usuario, Administrador | Alta |
| RF-131 | Equipos | Filtrado de equipos | El sistema deberá permitir filtrar equipos por campaña, proveedor, tipo y estado operativo | Usuario, Administrador | Alta |
| RF-132 | Equipos | Ordenamiento de equipos | El sistema deberá permitir ordenar equipos mediante criterios operativos y documentales | Usuario, Administrador | Baja |
| RF-133 | Equipos | Registro de código interno | El sistema deberá permitir asignar un código interno único a cada equipo registrado | Administrador | Alta |
| RF-134 | Equipos | Registro de número de serie | El sistema deberá permitir registrar un número de serie único para cada equipo | Administrador | Alta |
| RF-135 | Equipos | Validación de serie única | El sistema deberá impedir el registro de equipos con números de serie duplicados | Administrador | Alta |
| RF-136 | Equipos | Relación obligatoria con OSR | El sistema deberá requerir que todo equipo registrado esté asociado obligatoriamente a un registro OSR | Administrador | Alta |
| RF-137 | Equipos | Asociación de proveedor | El sistema deberá permitir asociar obligatoriamente un proveedor a cada equipo registrado | Administrador | Alta |
| RF-138 | Equipos | Restricción de cambio de proveedor | El sistema no deberá permitir modificar el proveedor asociado a un equipo registrado | Administrador | Alta |
| RF-139 | Equipos | Asociación de tipo de equipo | El sistema deberá permitir asociar un tipo de equipo a cada equipo registrado | Administrador | Alta |
| RF-140 | Equipos | Registro de marca y modelo | El sistema deberá permitir registrar marca y modelo asociados al equipo | Administrador | Alta |
| RF-141 | Equipos | Registro de fechas operativas | El sistema deberá permitir registrar fechas de ingreso y devolución operativa de equipos | Administrador | Alta |
| RF-142 | Equipos | Estado operativo del equipo | El sistema deberá permitir administrar el estado operativo del equipo como operativo o averiado | Usuario, Administrador | Alta |
| RF-143 | Equipos | Visualización de estado operativo | El sistema deberá mostrar el estado operativo actual del equipo | Usuario, Administrador | Media |
| RF-144 | Equipos | Restricción de eliminación física | El sistema no deberá permitir la eliminación física de equipos registrados | Administrador | Alta |
| RF-145 | Equipos | Historial de campañas asociadas | El sistema deberá mantener el historial de campañas asociadas a cada equipo | Usuario, Administrador | Alta |
| RF-146 | Equipos | Historial operacional | El sistema deberá visualizar el historial operativo completo del equipo incluyendo PSR, OSR, averías y campañas asociadas | Usuario, Administrador | Alta |
| RF-147 | Equipos | Historial de averías | El sistema deberá visualizar el historial de averías registradas para cada equipo | Usuario, Administrador | Media |
| RF-148 | Equipos | Reutilización entre campañas | El sistema deberá permitir reutilizar equipos registrados en nuevas campañas operativas | Administrador | Media |
| RF-149 | Equipos | Restricción de asociación simultánea | El sistema deberá impedir asociar un equipo simultáneamente a múltiples registros OSR activos | Administrador | Alta |
| RF-150 | Equipos | Registro de observaciones | El sistema deberá permitir registrar observaciones asociadas a equipos operativos | Usuario, Administrador | Media |
| RF-151 | Equipos | Gestión de fotografías | El sistema deberá permitir registrar fotografías asociadas a equipos operativos | Usuario, Administrador | Media |
| RF-152 | Equipos | Gestión de múltiples fotografías | El sistema deberá permitir registrar múltiples fotografías asociadas a un equipo | Usuario, Administrador | Media |
| RF-153 | Equipos | Registro de devolución | El sistema deberá permitir registrar la devolución operativa del equipo al proveedor | Administrador | Media |
| RF-154 | Equipos | Desactivación lógica | El sistema deberá permitir desactivar equipos sin eliminar su información histórica | Administrador | Media |
| RF-155 | Equipos | Generación de PDF operativo | El sistema deberá permitir generar reportes PDF asociados a equipos registrados | Usuario, Administrador | Media |
| RF-156 | Equipos | Indicadores operativos de equipos | El sistema deberá permitir visualizar indicadores asociados a equipos operativos | Usuario, Administrador | Alta |
| RF-157 | Equipos | Dashboard filtrado por equipos | El sistema deberá permitir filtrar indicadores operativos mediante equipos registrados | Usuario, Administrador | Alta |
| RF-158 | Equipos | Auditoría de equipos | El sistema deberá registrar eventos de creación, actualización y desactivación de equipos | Usuario, Administrador | Alta |
| RF-159 | Equipos | Mensajes de validación operativa | El sistema deberá mostrar mensajes visuales de confirmación y error durante operaciones relacionadas a equipos | Usuario, Administrador | Media |

# 9. REGLAS DE NEGOCIO

[PENDIENTE]



# 10. REQUERIMIENTOS NO FUNCIONALES

[PENDIENTE]



# 11. ENTIDADES PRINCIPALES

[PENDIENTE]



# 12. FLUJOS OPERATIVOS

[PENDIENTE]



# 13. REPORTES

[PENDIENTE]



# 14. CONSIDERACIONES TÉCNICAS

[PENDIENTE]



# 15. PENDIENTES FUNCIONALES

[PENDIENTE]



# 16. ANEXOS

[PENDIENTE]
