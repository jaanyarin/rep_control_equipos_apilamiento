package pe.com.repcontrol.psr.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.psr.entity.PSR;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class PSRService {

    public PagedResponse<PSR> listAll(Long campanaId, String estado, Long sitioId, String filtro, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (campanaId != null) { clauses.add("campana.id = :campanaId"); params.put("campanaId", campanaId); }
        if (estado != null) { clauses.add("estado = :estado"); params.put("estado", estado); }
        if (sitioId != null) { clauses.add("sitio.id = :sitioId"); params.put("sitioId", sitioId); }
        if (filtro != null) { clauses.add("(motivo LIKE :filtro OR descripcion LIKE :filtro)"); params.put("filtro", "%" + filtro + "%"); }
        var queryStr = String.join(" AND ", clauses);
        var panacheQuery = PSR.find(queryStr, params);
        var total = panacheQuery.count();
        @SuppressWarnings("unchecked")
        List<PSR> items = (List<PSR>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
        return PagedResponse.of(items, total, page, pageSize);
    }

    public PSR findById(Long id) {
        return PSR.<PSR>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("PSR", id));
    }

    public List<PSR> findByCampana(Long campanaId) {
        return PSR.list("campana_id = ?1 AND estado_activo = 1", campanaId);
    }

    public List<PSR> findByEstado(String estado) {
        return PSR.list("estado = ?1 AND estado_activo = 1", estado);
    }

    @Transactional
    public PSR create(PSR psr) {
        psr.persist();
        return psr;
    }

    @Transactional
    public PSR update(Long id, PSR updated) {
        PSR psr = findById(id);
        psr.numero = updated.numero;
        psr.campana = updated.campana;
        psr.sitio = updated.sitio;
        psr.motivo = updated.motivo;
        psr.descripcion = updated.descripcion;
        psr.fechaSolicitud = updated.fechaSolicitud;
        psr.estado = updated.estado;
        psr.observaciones = updated.observaciones;
        psr.persist();
        return psr;
    }

    @Transactional
    public PSR approve(Long id) {
        PSR psr = findById(id);
        psr.estado = "APROBADO";
        psr.persist();
        return psr;
    }

    @Transactional
    public PSR reject(Long id) {
        PSR psr = findById(id);
        psr.estado = "RECHAZADO";
        psr.persist();
        return psr;
    }

    @Transactional
    public PSR close(Long id) {
        PSR psr = findById(id);
        psr.estado = "CERRADO";
        psr.persist();
        return psr;
    }

    @Transactional
    public void delete(Long id) {
        PSR psr = findById(id);
        psr.estadoActivo = false;
        psr.persist();
    }
}
