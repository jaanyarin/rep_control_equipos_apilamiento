package pe.com.repcontrol.equipos.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.equipos.entity.Proveedor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ProveedorService {

    public PagedResponse<Proveedor> listAll(String filtro, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (filtro != null) { clauses.add("(razonSocial LIKE :filtro OR ruc LIKE :filtro OR nombreComercial LIKE :filtro)"); params.put("filtro", "%" + filtro + "%"); }
        var queryStr = String.join(" AND ", clauses);
        var panacheQuery = Proveedor.find(queryStr, params);
        var total = panacheQuery.count();
        @SuppressWarnings("unchecked")
        List<Proveedor> items = (List<Proveedor>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
        return PagedResponse.of(items, total, page, pageSize);
    }

    public Proveedor findById(Long id) {
        return Proveedor.<Proveedor>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proveedor", id));
    }

    @Transactional
    public Proveedor create(Proveedor proveedor) {
        proveedor.persist();
        return proveedor;
    }

    @Transactional
    public Proveedor update(Long id, Proveedor updated) {
        Proveedor proveedor = findById(id);
        proveedor.ruc = updated.ruc;
        proveedor.razonSocial = updated.razonSocial;
        proveedor.nombreComercial = updated.nombreComercial;
        proveedor.direccion = updated.direccion;
        proveedor.telefono = updated.telefono;
        proveedor.correo = updated.correo;
        proveedor.contactoNombre = updated.contactoNombre;
        proveedor.contactoTelefono = updated.contactoTelefono;
        proveedor.persist();
        return proveedor;
    }
}
