package pe.com.repcontrol.campanas.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.campanas.entity.Sitio;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class SitioService {

    public PagedResponse<Sitio> listAll(String filtro, String estado, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (filtro != null) { clauses.add("(nombre LIKE :filtro OR codigo LIKE :filtro)"); params.put("filtro", "%" + filtro + "%"); }
        var queryStr = String.join(" AND ", clauses);
        var panacheQuery = Sitio.find(queryStr, params);
        var total = panacheQuery.count();
        @SuppressWarnings("unchecked")
        List<Sitio> items = (List<Sitio>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
        return PagedResponse.of(items, total, page, pageSize);
    }

    public Sitio findById(Long id) {
        return Sitio.<Sitio>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sitio", id));
    }

    @Transactional
    public Sitio create(Sitio sitio) {
        sitio.persist();
        return sitio;
    }

    @Transactional
    public Sitio update(Long id, Sitio updated) {
        Sitio sitio = findById(id);
        sitio.codigo = updated.codigo;
        sitio.nombre = updated.nombre;
        sitio.descripcion = updated.descripcion;
        sitio.direccion = updated.direccion;
        sitio.persist();
        return sitio;
    }

    @Transactional
    public void delete(Long id) {
        Sitio sitio = findById(id);
        sitio.estadoActivo = false;
        sitio.fechaBaja = LocalDateTime.now();
        sitio.persist();
    }
}
