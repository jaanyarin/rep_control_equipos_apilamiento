package pe.com.repcontrol.equipos.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.equipos.entity.Proveedor;
import java.util.List;

@ApplicationScoped
public class ProveedorService {

    public List<Proveedor> listAll() {
        return Proveedor.listAll();
    }

    public Proveedor findById(Long id) {
        return Proveedor.<Proveedor>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proveedor", id));
    }
}
