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
