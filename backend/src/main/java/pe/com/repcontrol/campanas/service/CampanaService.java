package pe.com.repcontrol.campanas.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.campanas.entity.Campana;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class CampanaService {

    public PagedResponse<Campana> listAll(Long sitioId, String estado, Boolean esActiva, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (sitioId != null) { clauses.add("sitio_id = :sitioId"); params.put("sitioId", sitioId); }
        if (estado != null) { clauses.add("estado = :estado"); params.put("estado", estado); }
        if (esActiva != null) { clauses.add("esActiva = :esActiva"); params.put("esActiva", esActiva); }
        var queryStr = String.join(" AND ", clauses);
    var panacheQuery = Campana.find(queryStr, params);
    var total = panacheQuery.count();
    @SuppressWarnings("unchecked")
    List<Campana> items = (List<Campana>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
    return PagedResponse.of(items, total, page, pageSize);
    }

    public Campana findById(Long id) {
        return Campana.<Campana>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campana", id));
    }

    @Transactional
    public Campana create(Campana campana) {
        campana.persist();
        return campana;
    }

    @Transactional
    public Campana update(Long id, Campana updated) {
        Campana campana = findById(id);
        campana.codigo = updated.codigo;
        campana.nombre = updated.nombre;
        campana.tipo = updated.tipo;
        campana.sitio = updated.sitio;
        campana.fechaInicio = updated.fechaInicio;
        campana.fechaFin = updated.fechaFin;
        campana.descripcion = updated.descripcion;
        campana.estado = updated.estado;
        campana.esActiva = updated.esActiva;
        campana.persist();
        return campana;
    }

    @Transactional
    public void delete(Long id) {
        Campana campana = findById(id);
        campana.estadoActivo = false;
        campana.persist();
    }

    public List<Campana> findByActiva(boolean activa) {
        return Campana.list("es_activa = ?1 AND estado_activo = 1", activa);
    }

    @Transactional
    public Campana activate(Long id) {
        Campana campana = findById(id);
        campana.esActiva = true;
        campana.estado = "ACTIVA";
        campana.persist();
        return campana;
    }

    @Transactional
    public Campana close(Long id) {
        Campana campana = findById(id);
        campana.esActiva = false;
        campana.estado = "CERRADA";
        campana.persist();
        return campana;
    }
}
