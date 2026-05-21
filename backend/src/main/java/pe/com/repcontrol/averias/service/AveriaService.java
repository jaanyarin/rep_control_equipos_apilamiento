package pe.com.repcontrol.averias.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.averias.entity.Averia;
import pe.com.repcontrol.averias.entity.EstadoAveria;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.BusinessException;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class AveriaService {

    public PagedResponse<Averia> listAll(Long equipoId, Long estadoAveriaId, Long proveedorId, Long tipoAveriaId, String fechaDesde, String fechaHasta, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (equipoId != null) { clauses.add("equipo.id = :equipoId"); params.put("equipoId", equipoId); }
        if (estadoAveriaId != null) { clauses.add("estadoAveria.id = :estadoAveriaId"); params.put("estadoAveriaId", estadoAveriaId); }
        if (proveedorId != null) { clauses.add("proveedor.id = :proveedorId"); params.put("proveedorId", proveedorId); }
        if (tipoAveriaId != null) { clauses.add("tipoAveria.id = :tipoAveriaId"); params.put("tipoAveriaId", tipoAveriaId); }
        if (fechaDesde != null) { clauses.add("fechaCreacion >= :fechaDesde"); params.put("fechaDesde", LocalDateTime.parse(fechaDesde)); }
        if (fechaHasta != null) { clauses.add("fechaCreacion <= :fechaHasta"); params.put("fechaHasta", LocalDateTime.parse(fechaHasta)); }
        var queryStr = String.join(" AND ", clauses);
    var panacheQuery = Averia.find(queryStr, params);
    var total = panacheQuery.count();
    @SuppressWarnings("unchecked")
    List<Averia> items = (List<Averia>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
    return PagedResponse.of(items, total, page, pageSize);
    }

    public Averia findById(Long id) {
        return Averia.<Averia>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Averia", id));
    }

    public List<Averia> findByEquipo(Long equipoId) {
        return Averia.list("equipo_id = ?1 AND estado_activo = 1", equipoId);
    }

    public List<Averia> findByEstado(Long estadoAveriaId) {
        return Averia.list("estado_averia_id = ?1 AND estado_activo = 1", estadoAveriaId);
    }

    public List<Averia> findByProveedor(Long proveedorId) {
        return Averia.list("proveedor_id = ?1 AND estado_activo = 1", proveedorId);
    }

    @Transactional
    public Averia create(Averia averia) {
        averia.persist();
        return averia;
    }

    @Transactional
    public Averia update(Long id, Averia updated) {
        Averia averia = findById(id);
        averia.codigo = updated.codigo;
        averia.equipo = updated.equipo;
        averia.tipoAveria = updated.tipoAveria;
        averia.estadoAveria = updated.estadoAveria;
        averia.proveedor = updated.proveedor;
        averia.descripcionFalla = updated.descripcionFalla;
        averia.descripcionAtencion = updated.descripcionAtencion;
        averia.accionCorrectiva = updated.accionCorrectiva;
        averia.fechaReporte = updated.fechaReporte;
        averia.fechaAtencion = updated.fechaAtencion;
        averia.usuarioReportaId = updated.usuarioReportaId;
        averia.usuarioAtiendeId = updated.usuarioAtiendeId;
        averia.horasInactivo = updated.horasInactivo;
        averia.observaciones = updated.observaciones;
        averia.persist();
        return averia;
    }

    @Transactional
    public Averia close(Long id, String descripcionAtencion, String accionCorrectiva, String fechaCierreStr) {
        Averia averia = findById(id);
        averia.descripcionAtencion = descripcionAtencion;
        averia.accionCorrectiva = accionCorrectiva;
        if (fechaCierreStr != null && !fechaCierreStr.isBlank()) {
            averia.fechaCierre = LocalDateTime.parse(fechaCierreStr);
        } else {
            averia.fechaCierre = LocalDateTime.now();
        }
        EstadoAveria cerrada = EstadoAveria.find("codigo", "CERRADA").firstResult();
        if (cerrada != null) {
            averia.estadoAveria = cerrada;
        }
        averia.persist();
        return averia;
    }
}
