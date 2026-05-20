package pe.com.repcontrol.campanas.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.campanas.entity.Sitio;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import java.util.List;

@ApplicationScoped
public class SitioService {

    public List<Sitio> listAll() {
        return Sitio.listAll();
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
        sitio.persist();
    }
}
