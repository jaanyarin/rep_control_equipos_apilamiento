package pe.com.repcontrol.equipos.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.equipos.entity.TipoEquipo;
import java.util.List;

@ApplicationScoped
public class TipoEquipoService {

    public List<TipoEquipo> listAll() {
        return TipoEquipo.listAll();
    }

    public TipoEquipo findById(Long id) {
        return TipoEquipo.<TipoEquipo>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("TipoEquipo", id));
    }
}
