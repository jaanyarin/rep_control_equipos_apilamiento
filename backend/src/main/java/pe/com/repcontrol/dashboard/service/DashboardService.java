package pe.com.repcontrol.dashboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DashboardService {

    private final EntityManager em;

    public DashboardService(EntityManager em) {
        this.em = em;
    }

    public Map<String, Object> getKPIs(Long campanaId, Long sitioId, Long proveedorId,
            String fechaDesde, String fechaHasta) {
        Map<String, Object> kpis = new LinkedHashMap<>();

        Map<String, Object> params = new HashMap<>();
        List<String> equipoClauses = new ArrayList<>();
        List<String> averiaClauses = new ArrayList<>();
        buildFilters(campanaId, sitioId, proveedorId, fechaDesde, fechaHasta, params, equipoClauses, averiaClauses);

        String equipoWhere = equipoClauses.isEmpty() ? "" : " AND " + String.join(" AND ", equipoClauses);
        String averiaWhere = averiaClauses.isEmpty() ? "" : " AND " + String.join(" AND ", averiaClauses);

        Long activos = count("SELECT COUNT(*) FROM equipos WHERE estado_activo = 1" + equipoWhere, params);
        Long disponibles = count("SELECT COUNT(*) FROM equipos WHERE estado = 'DISPONIBLE' AND estado_activo = 1" + equipoWhere, params);
        Long averiados = count("SELECT COUNT(*) FROM equipos e WHERE e.estado IN ('AVERIADO','EN_REPARACION') AND e.estado_activo = 1" + equipoWhere, params);
        Long mantenimiento = count("SELECT COUNT(*) FROM equipos WHERE estado = 'MANTENIMIENTO' AND estado_activo = 1" + equipoWhere, params);
        Long devueltos = count("SELECT COUNT(*) FROM equipos WHERE estado = 'DEVUELTO' AND estado_activo = 1" + equipoWhere, params);

        kpis.put("equiposActivos", activos);
        kpis.put("equiposDisponibles", disponibles);
        kpis.put("equiposAveriados", averiados);
        kpis.put("equiposEnMantenimiento", mantenimiento);
        kpis.put("equiposDevueltos", devueltos);

        Long averiasAbiertas = count(
            "SELECT COUNT(*) FROM averias a JOIN estados_averia ea ON a.estado_averia_id = ea.id WHERE ea.nombre <> 'CERRADA' AND a.estado_activo = 1" + averiaWhere,
            params);
        Long averiasCerradas = count(
            "SELECT COUNT(*) FROM averias a JOIN estados_averia ea ON a.estado_averia_id = ea.id WHERE ea.nombre = 'CERRADA' AND a.estado_activo = 1" + averiaWhere,
            params);

        kpis.put("averiasAbiertas", averiasAbiertas);
        kpis.put("averiasCerradas", averiasCerradas);

        BigDecimal tiempoPromedio = (BigDecimal) createQuery(
            "SELECT COALESCE(AVG(TIMESTAMPDIFF(HOUR, fecha_reporte, fecha_atencion)), 0) FROM averias WHERE fecha_atencion IS NOT NULL AND estado_activo = 1" + averiaWhere,
            params).getSingleResult();
        kpis.put("tiempoPromedioAtencion", tiempoPromedio.doubleValue());

        if (activos > 0) {
            kpis.put("disponibilidad", Math.round((disponibles.doubleValue() / activos) * 1000.0) / 10.0);
            kpis.put("utilizacion", Math.round(((double)(activos - disponibles - mantenimiento) / activos) * 1000.0) / 10.0);
        } else {
            kpis.put("disponibilidad", 0.0);
            kpis.put("utilizacion", 0.0);
        }

        List<Map<String, Object>> porProveedor = new ArrayList<>();
        String provSql = "SELECT p.id, p.nombre, COUNT(e.id) as total, " +
            "(SELECT COUNT(*) FROM averias a JOIN equipos eq ON a.equipo_id = eq.id WHERE eq.proveedor_id = p.id AND a.estado_activo = 1" + averiaWhere + ") as averias " +
            "FROM proveedores p JOIN equipos e ON e.proveedor_id = p.id WHERE e.estado_activo = 1" + equipoWhere + " GROUP BY p.id, p.nombre";
        var proveedoresRows = createQuery(provSql, params).getResultList();
        for (Object row : proveedoresRows) {
            Object[] cols = (Object[]) row;
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("proveedor", cols[1]);
            item.put("totalEquipos", ((Number) cols[2]).longValue());
            item.put("averias", ((Number) cols[3]).longValue());
            porProveedor.add(item);
        }
        kpis.put("porProveedor", porProveedor);

        List<Map<String, Object>> porTipo = new ArrayList<>();
        String tipoSql = "SELECT te.id, te.nombre, COUNT(e.id) as total, " +
            "SUM(CASE WHEN e.estado = 'DISPONIBLE' THEN 1 ELSE 0 END) as disponibles " +
            "FROM tipos_equipo te JOIN equipos e ON e.tipo_equipo_id = te.id WHERE e.estado_activo = 1" + equipoWhere + " GROUP BY te.id, te.nombre";
        var tiposRows = createQuery(tipoSql, params).getResultList();
        for (Object row : tiposRows) {
            Object[] cols = (Object[]) row;
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("tipo", cols[1]);
            item.put("total", ((Number) cols[2]).longValue());
            item.put("disponibles", ((Number) cols[3]).longValue());
            porTipo.add(item);
        }
        kpis.put("porTipo", porTipo);

        return kpis;
    }

    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new LinkedHashMap<>();

        Long totalEquipos = ((Number) em.createNativeQuery(
            "SELECT COUNT(*) FROM equipos WHERE estado_activo = 1").getSingleResult()).longValue();
        Long totalCampañas = ((Number) em.createNativeQuery(
            "SELECT COUNT(*) FROM campanas WHERE estado_activo = 1").getSingleResult()).longValue();
        Long totalProveedores = ((Number) em.createNativeQuery(
            "SELECT COUNT(*) FROM proveedores WHERE estado_activo = 1").getSingleResult()).longValue();
        Long totalUsuarios = ((Number) em.createNativeQuery(
            "SELECT COUNT(*) FROM usuarios WHERE estado_activo = 1").getSingleResult()).longValue();

        metrics.put("totalEquipos", totalEquipos);
        metrics.put("totalCampañas", totalCampañas);
        metrics.put("totalProveedores", totalProveedores);
        metrics.put("totalUsuarios", totalUsuarios);

        Map<String, Long> equiposPorEstado = new LinkedHashMap<>();
        var estadoRows = em.createNativeQuery(
            "SELECT estado, COUNT(*) as total FROM equipos WHERE estado_activo = 1 GROUP BY estado").getResultList();
        for (Object row : estadoRows) {
            Object[] cols = (Object[]) row;
            equiposPorEstado.put((String) cols[0], ((Number) cols[1]).longValue());
        }
        metrics.put("equiposPorEstado", equiposPorEstado);

        List<Map<String, Object>> averiasPorTipo = new ArrayList<>();
        var tipoAveriaRows = em.createNativeQuery(
            "SELECT ta.nombre, COUNT(a.id) as cantidad FROM averias a JOIN tipos_averia ta ON a.tipo_averia_id = ta.id WHERE a.estado_activo = 1 GROUP BY ta.id, ta.nombre")
            .getResultList();
        for (Object row : tipoAveriaRows) {
            Object[] cols = (Object[]) row;
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("tipo", cols[0]);
            item.put("cantidad", ((Number) cols[1]).longValue());
            averiasPorTipo.add(item);
        }
        metrics.put("averiasPorTipo", averiasPorTipo);

        List<Map<String, Object>> evolucionMensual = new ArrayList<>();
        var evolRows = em.createNativeQuery(
            "SELECT DATE_FORMAT(e.fecha_creacion, '%Y-%m') as mes, COUNT(DISTINCT e.id) as equipos, "
            + "COUNT(DISTINCT a.id) as averias "
            + "FROM equipos e LEFT JOIN averias a ON a.equipo_id = e.id AND DATE_FORMAT(a.fecha_creacion, '%Y-%m') = DATE_FORMAT(e.fecha_creacion, '%Y-%m') "
            + "WHERE e.fecha_creacion >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) AND e.estado_activo = 1 "
            + "GROUP BY DATE_FORMAT(e.fecha_creacion, '%Y-%m') ORDER BY mes")
            .getResultList();
        for (Object row : evolRows) {
            Object[] cols = (Object[]) row;
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("mes", cols[0]);
            item.put("equipos", ((Number) cols[1]).longValue());
            item.put("averias", cols[2] != null ? ((Number) cols[2]).longValue() : 0);
            evolucionMensual.add(item);
        }
        metrics.put("evolucionMensual", evolucionMensual);

        return metrics;
    }

    private void buildFilters(Long campanaId, Long sitioId, Long proveedorId,
            String fechaDesde, String fechaHasta,
            Map<String, Object> params, List<String> equipoClauses, List<String> averiaClauses) {
        if (campanaId != null) {
            String eqKey = "eqCampanaId";
            equipoClauses.add("campana_id = :" + eqKey);
            params.put(eqKey, campanaId);
            String avKey = "avCampanaId";
            averiaClauses.add("a.equipo_id IN (SELECT id FROM equipos WHERE campana_id = :" + avKey + ")");
            params.put(avKey, campanaId);
        }
        if (sitioId != null) {
            String key = "sitioId";
            equipoClauses.add("sitio_id = :" + key);
            params.put(key, sitioId);
        }
        if (proveedorId != null) {
            String eqKey = "eqProvId";
            equipoClauses.add("proveedor_id = :" + eqKey);
            params.put(eqKey, proveedorId);
            String avKey = "avProvId";
            averiaClauses.add("a.proveedor_id = :" + avKey);
            params.put(avKey, proveedorId);
        }
        if (fechaDesde != null) {
            String key = "fechaDesde";
            averiaClauses.add("a.fecha_reporte >= :" + key);
            params.put(key, java.sql.Date.valueOf(fechaDesde));
        }
        if (fechaHasta != null) {
            String key = "fechaHasta";
            averiaClauses.add("a.fecha_reporte <= :" + key);
            params.put(key, java.sql.Date.valueOf(fechaHasta));
        }
    }

    private Long count(String sql, Map<String, Object> params) {
        return ((Number) createQuery(sql, params).getSingleResult()).longValue();
    }

    private Query createQuery(String sql, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }
}
