package pe.com.repcontrol.psr.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.psr.entity.OSR;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class OSRService {

    public PagedResponse<OSR> listAll(Long psrId, Long equipoId, String estado, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (psrId != null) { clauses.add("psr.id = :psrId"); params.put("psrId", psrId); }
        if (equipoId != null) { clauses.add("equipoId = :equipoId"); params.put("equipoId", equipoId); }
        if (estado != null) { clauses.add("estado = :estado"); params.put("estado", estado); }
        var queryStr = String.join(" AND ", clauses);
        var panacheQuery = OSR.find(queryStr, params);
        var total = panacheQuery.count();
        @SuppressWarnings("unchecked")
        List<OSR> items = (List<OSR>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
        return PagedResponse.of(items, total, page, pageSize);
    }

    public OSR findById(Long id) {
        return OSR.<OSR>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("OSR", id));
    }

    public List<OSR> findByPSR(Long psrId) {
        return OSR.list("psr_id = ?1 AND estado_activo = 1", psrId);
    }

    public List<OSR> findByEquipo(Long equipoId) {
        return OSR.list("equipo_id = ?1 AND estado_activo = 1", equipoId);
    }

    @Transactional
    public OSR create(OSR osr) {
        osr.persist();
        return osr;
    }

    @Transactional
    public OSR update(Long id, OSR updated) {
        OSR osr = findById(id);
        osr.numero = updated.numero;
        osr.psr = updated.psr;
        osr.equipoId = updated.equipoId;
        osr.fechaAsignacion = updated.fechaAsignacion;
        osr.horaInicio = updated.horaInicio;
        osr.horaFin = updated.horaFin;
        osr.horometroInicio = updated.horometroInicio;
        osr.horometroFin = updated.horometroFin;
        osr.estado = updated.estado;
        osr.observaciones = updated.observaciones;
        osr.persist();
        return osr;
    }
}
