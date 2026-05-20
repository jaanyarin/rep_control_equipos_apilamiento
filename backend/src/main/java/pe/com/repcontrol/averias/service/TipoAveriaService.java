package pe.com.repcontrol.averias.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.averias.entity.TipoAveria;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import java.util.List;

@ApplicationScoped
public class TipoAveriaService {

    public List<TipoAveria> listAll() {
        return TipoAveria.listAll();
    }

    public TipoAveria findById(Long id) {
        return TipoAveria.<TipoAveria>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("TipoAveria", id));
    }
}
