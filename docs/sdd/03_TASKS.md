# SOFTWARE DEVELOPMENT DOCUMENT (SDD)
# 03_TASKS.md

---

# 1. Objetivo del Documento

El presente documento tiene como finalidad definir la estructura de tareas técnicas, operativas y organizacionales necesarias para la construcción del sistema de control operativo de equipos de apilamiento.

Este documento permitirá:
- organizar el desarrollo,
- controlar avances,
- gestionar prioridades,
- establecer dependencias técnicas,
- mantener trazabilidad del proyecto,
- facilitar mantenibilidad y escalabilidad futura.

La estructura de tareas estará alineada a la arquitectura modular definida en los documentos de especificación y planificación.

---

# 2. Estrategia General de Desarrollo

El desarrollo del proyecto será ejecutado mediante una estrategia incremental y modular, priorizando inicialmente los componentes críticos para garantizar la construcción de un MVP funcional y estable.

La implementación estará orientada a:
- desacoplamiento de componentes,
- mantenibilidad,
- escalabilidad,
- reutilización,
- estabilidad operacional.

El desarrollo se dividirá en:
- infraestructura,
- backend,
- frontend móvil,
- frontend web,
- seguridad,
- auditoría,
- analítica,
- despliegue,
- pruebas.

Cada módulo deberá completarse de manera progresiva y validarse antes de continuar con dependencias posteriores.

---

# 3. Estructura de Gestión de Tareas

Las tareas estarán organizadas mediante:
- módulos funcionales,
- componentes técnicos,
- prioridades,
- dependencias,
- estados operativos.

Cada tarea deberá contener:
- identificador único,
- descripción,
- prioridad,
- dependencia,
- responsable,
- estado,
- observaciones técnicas.

---

# 4. Estados de Tareas

| Estado | Descripción |
|---|---|
| Pendiente | Tarea no iniciada |
| En Progreso | Desarrollo activo |
| En Validación | En proceso QA |
| Completado | Finalizado y validado |
| Bloqueado | Dependencia pendiente |
| Cancelado | Tarea descartada |

---

# 5. Prioridades de Desarrollo

| Prioridad | Descripción |
|---|---|
| Crítica | Bloquea arquitectura o desarrollo |
| Alta | Necesaria para MVP |
| Media | Complementaria |
| Baja | Mejora futura |

---

# 6. Roadmap General de Desarrollo

| Orden | Componente |
|---|---|
| 1 | Infraestructura Base |
| 2 | Backend Base |
| 3 | Autenticación |
| 4 | Usuarios |
| 5 | Sedes |
| 6 | Campañas |
| 7 | Tipos de Equipos |
| 8 | Proveedores |
| 9 | PSR / OSR |
| 10 | Equipos |
| 11 | Averías |
| 12 | Evidencias Fotográficas |
| 13 | Dashboard KPI |
| 14 | Reportes PDF |
| 15 | Auditoría |
| 16 | Configuración |
| 17 | QA Integral |
| 18 | Despliegue Producción |

---

# 7. Tareas de Infraestructura

## Objetivo

Implementar la base técnica necesaria para soportar el desarrollo, despliegue y operación del sistema.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| INF-001 | Crear estructura inicial del repositorio | Crítica |
| INF-002 | Configurar ramas GitHub | Alta |
| INF-003 | Configurar Docker Backend | Crítica |
| INF-004 | Configurar Docker Frontend Web | Crítica |
| INF-005 | Configurar Docker Base de Datos | Crítica |
| INF-006 | Configurar Docker Compose | Crítica |
| INF-007 | Configurar variables de entorno | Alta |
| INF-008 | Configurar Nginx Reverse Proxy | Alta |
| INF-009 | Configurar ambientes Desarrollo / QA / Producción | Alta |
| INF-010 | Configurar GitHub Actions CI/CD | Alta |
| INF-011 | Configurar políticas de despliegue | Media |
| INF-012 | Configurar backups iniciales | Media |
| INF-013 | Configurar healthchecks | Alta |
| INF-014 | Configurar monitoreo básico | Media |
| INF-015 | Definir política backups | Alta |
| INF-016 | Configurar restauración backups | Alta |
| INF-017 | Configurar gestión centralizada variables entorno | Alta |
| INF-018 | Definir estándares desarrollo | Media |
| INF-019 | Configurar lint frontend | Media |
| INF-020 | Configurar formateo código | Media |

---

# 8. Tareas Base de Datos

## Objetivo

Diseñar e implementar la estructura relacional del sistema.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| DB-001 | Diseñar modelo relacional inicial | Crítica |
| DB-002 | Diseñar tablas de usuarios | Crítica |
| DB-003 | Diseñar tablas de campañas | Alta |
| DB-004 | Diseñar tablas de sedes | Alta |
| DB-005 | Diseñar tablas PSR / OSR | Crítica |
| DB-006 | Diseñar tablas de equipos | Crítica |
| DB-007 | Diseñar tablas de averías | Alta |
| DB-008 | Diseñar tablas de fotografías | Alta |
| DB-009 | Diseñar tablas auditoría | Alta |
| DB-010 | Configurar índices y relaciones | Alta |
| DB-011 | Configurar restricciones de integridad | Alta |
| DB-012 | Configurar migraciones iniciales | Alta |

---

# 9. Tareas Backend

## Objetivo

Desarrollar las APIs REST y lógica de negocio del sistema.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| BE-001 | Inicializar proyecto Quarkus | Crítica |
| BE-002 | Configurar conexión MySQL | Crítica |
| BE-003 | Configurar arquitectura modular | Crítica |
| BE-004 | Configurar autenticación Microsoft | Crítica |
| BE-005 | Configurar JWT | Crítica |
| BE-006 | Implementar módulo usuarios | Alta |
| BE-007 | Implementar módulo campañas | Alta |
| BE-008 | Implementar módulo sedes | Alta |
| BE-009 | Implementar módulo tipos equipos | Alta |
| BE-010 | Implementar módulo proveedores | Alta |
| BE-011 | Implementar módulo PSR / OSR | Crítica |
| BE-012 | Implementar módulo equipos | Crítica |
| BE-013 | Implementar módulo averías | Alta |
| BE-014 | Implementar módulo evidencias | Alta |
| BE-015 | Implementar auditoría | Alta |
| BE-016 | Implementar generación PDF | Media |
| BE-017 | Implementar APIs dashboard KPI | Media |
| BE-018 | Configurar manejo errores global | Alta |
| BE-019 | Configurar validaciones backend | Alta |
| BE-020 | Configurar logs backend | Alta |
| BE-021 | Configurar versionamiento APIs | Alta |
| BE-022 | Definir estándares REST | Alta |
| BE-023 | Configurar OpenAPI | Media |
| BE-024 | Configurar Swagger UI | Media |
| BE-025 | Configurar estrategia logging | Alta |

---

# 10. Tareas Frontend Android

## Objetivo

Desarrollar la aplicación móvil operativa.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| AND-001 | Inicializar proyecto React Native | Crítica |
| AND-002 | Configurar navegación principal | Alta |
| AND-003 | Implementar login Microsoft | Crítica |
| AND-004 | Implementar control sesión | Alta |
| AND-005 | Implementar gestión usuarios | Media |
| AND-006 | Implementar gestión campañas | Alta |
| AND-007 | Implementar gestión PSR / OSR | Crítica |
| AND-008 | Implementar gestión equipos | Crítica |
| AND-009 | Implementar gestión averías | Alta |
| AND-010 | Implementar captura fotografías | Alta |
| AND-011 | Implementar consumo APIs REST | Crítica |
| AND-012 | Implementar manejo errores UI | Alta |
| AND-013 | Implementar validaciones formularios | Alta |
| AND-014 | Implementar visualización PDFs | Media |
| AND-015 | Implementar notificaciones visuales | Alta |
| AND-016 | Configurar manejo global errores | Alta |

---

# 11. Tareas Frontend Web

## Objetivo

Desarrollar la plataforma web analítica y administrativa.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| WEB-001 | Inicializar proyecto React | Crítica |
| WEB-002 | Configurar estructura modular | Alta |
| WEB-003 | Implementar login Microsoft | Alta |
| WEB-004 | Implementar dashboard KPI | Crítica |
| WEB-005 | Implementar filtros globales | Alta |
| WEB-006 | Implementar tablas operativas | Alta |
| WEB-007 | Implementar gestión usuarios | Media |
| WEB-008 | Implementar visualización equipos | Alta |
| WEB-009 | Implementar visualización averías | Alta |
| WEB-010 | Implementar exportación PDF | Media |
| WEB-011 | Implementar consumo APIs REST | Crítica |
| WEB-012 | Implementar control permisos | Alta |
| WEB-013 | Implementar manejo errores UI | Alta |
| WEB-014 | Configurar manejo global errores | Alta |

---

# 12. Tareas Seguridad

## Objetivo

Garantizar protección de acceso y control operacional.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| SEC-001 | Configurar autenticación Microsoft | Crítica |
| SEC-002 | Configurar JWT | Crítica |
| SEC-003 | Configurar expiración sesiones | Alta |
| SEC-004 | Configurar control roles | Alta |
| SEC-005 | Configurar validación APIs | Alta |
| SEC-006 | Configurar protección endpoints | Alta |
| SEC-007 | Configurar HTTPS | Alta |
| SEC-008 | Configurar logs seguridad | Media |

---

# 13. Tareas Auditoría

## Objetivo

Mantener trazabilidad operacional completa.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| AUD-001 | Registrar login/logout | Alta |
| AUD-002 | Registrar creación registros | Alta |
| AUD-003 | Registrar actualizaciones | Alta |
| AUD-004 | Registrar eliminaciones lógicas | Alta |
| AUD-005 | Registrar errores críticos | Media |
| AUD-006 | Registrar eventos seguridad | Media |

---

# 14. Tareas Evidencias Fotográficas

## Objetivo

Gestionar almacenamiento y visualización de fotografías operativas.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| IMG-001 | Configurar almacenamiento fotografías | Alta |
| IMG-002 | Configurar estructura directorios | Alta |
| IMG-003 | Configurar upload imágenes | Alta |
| IMG-004 | Configurar asociación registros | Alta |
| IMG-005 | Configurar visualización imágenes | Media |
| IMG-006 | Configurar optimización tamaño imágenes | Media |

---

# 15. Tareas Dashboard KPI

## Objetivo

Desarrollar visualización analítica operacional.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| KPI-001 | Diseñar estructura KPIs | Alta |
| KPI-002 | Diseñar consultas analíticas | Alta |
| KPI-003 | Implementar filtros dashboard | Alta |
| KPI-004 | Implementar métricas campañas | Alta |
| KPI-005 | Implementar métricas equipos | Alta |
| KPI-006 | Implementar métricas averías | Alta |
| KPI-007 | Implementar gráficos operativos | Media |

---

# 16. Tareas Reportes PDF

## Objetivo

Implementar generación de reportes operativos.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| PDF-001 | Configurar iText PDF | Alta |
| PDF-002 | Diseñar plantillas PDF | Alta |
| PDF-003 | Generar PDF equipos | Alta |
| PDF-004 | Generar PDF PSR / OSR | Media |
| PDF-005 | Generar PDF averías | Media |
| PDF-006 | Configurar descarga PDFs | Media |

---

# 17. Tareas QA y Testing

## Objetivo

Garantizar estabilidad y correcto funcionamiento del sistema.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| QA-001 | Ejecutar pruebas backend | Alta |
| QA-002 | Ejecutar pruebas frontend web | Alta |
| QA-003 | Ejecutar pruebas Android | Alta |
| QA-004 | Validar APIs REST | Alta |
| QA-005 | Validar seguridad | Alta |
| QA-006 | Validar flujos operativos | Crítica |
| QA-007 | Validar dashboards | Media |
| QA-008 | Validar generación PDF | Media |

---

# 18. Tareas Despliegue

## Objetivo

Preparar publicación y operación del sistema.

---

## Tareas

| ID | Tarea | Prioridad |
|---|---|---|
| DEP-001 | Configurar VPS Linux | Alta |
| DEP-002 | Configurar Docker producción | Alta |
| DEP-003 | Configurar Nginx producción | Alta |
| DEP-004 | Configurar dominios | Media |
| DEP-005 | Configurar HTTPS SSL | Alta |
| DEP-006 | Configurar pipelines despliegue | Alta |
| DEP-007 | Ejecutar despliegue QA | Alta |
| DEP-008 | Ejecutar despliegue producción | Crítica |
| DEP-009 | Validar estabilidad producción | Crítica |

---

# 19. Dependencias Técnicas

| Componente | Dependencia |
|---|---|
| Usuarios | Autenticación |
| Campañas | Usuarios |
| PSR / OSR | Campañas |
| Equipos | PSR / OSR |
| Averías | Equipos |
| Evidencias | Equipos / Averías |
| Dashboard | APIs operativas |
| PDFs | Datos operativos |
| Auditoría | Todos los módulos |

---

# 20. Riesgos Técnicos

| Riesgo | Impacto |
|---|---|
| Cambios funcionales tardíos | Alto |
| Problemas integración Microsoft | Alto |
| Crecimiento no controlado módulos | Medio |
| Manejo incorrecto fotografías | Medio |
| Problemas despliegue Docker | Medio |
| Dependencias externas | Medio |

---

# 21. Consideraciones Finales

La estructura de tareas definida permitirá mantener control operativo y técnico del proyecto, asegurando una implementación ordenada, escalable y mantenible.

La estrategia modular facilitará futuras ampliaciones y permitirá mantener separación clara entre componentes funcionales y técnicos del sistema.