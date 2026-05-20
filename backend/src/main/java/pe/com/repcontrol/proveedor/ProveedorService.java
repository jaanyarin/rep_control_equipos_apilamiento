package pe.com.repcontrol.proveedor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.proveedor.ProveedorRequest;
import pe.com.repcontrol.dto.proveedor.ProveedorResponse;
import pe.com.repcontrol.entity.Proveedor;
import pe.com.repcontrol.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProveedorService {

    @Transactional
    public List<ProveedorResponse> listActive() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Proveedor> proveedores = (List<Proveedor>) (List) Proveedor.find("estadoActivo", true).list();
        return proveedores.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProveedorResponse findById(Long id) {
        var proveedor = (Proveedor) Proveedor.findById(id);
        if (proveedor == null || !proveedor.estadoActivo) {
            throw new NotFoundException("Proveedor no encontrado: " + id);
        }
        return toResponse(proveedor);
    }

    @Transactional
    public ProveedorResponse create(ProveedorRequest request) {
        validateRequest(request, false);

        if (Proveedor.find("ruc", request.ruc().trim()).firstResultOptional().isPresent()) {
            throw new BadRequestException("Ya existe un proveedor con el RUC indicado");
        }

        var usuarioCreacion = (Usuario) Usuario.findById(request.usuarioCreacionId());
        if (usuarioCreacion == null || !usuarioCreacion.estadoActivo) {
            throw new BadRequestException("Usuario de creación inválido o no encontrado");
        }

        var proveedor = new Proveedor();
        proveedor.ruc = request.ruc().trim();
        proveedor.razonSocial = request.razonSocial().trim();
        proveedor.nombreComercial = request.nombreComercial() != null ? request.nombreComercial().trim() : null;
        proveedor.direccion = request.direccion();
        proveedor.telefono = request.telefono();
        proveedor.correo = request.correo();
        proveedor.contactoNombre = request.contactoNombre();
        proveedor.contactoTelefono = request.contactoTelefono();
        proveedor.estadoActivo = true;
        proveedor.usuarioCreacion = usuarioCreacion;
        proveedor.persist();
        return toResponse(proveedor);
    }

    @Transactional
    public ProveedorResponse update(Long id, ProveedorRequest request) {
        var proveedor = (Proveedor) Proveedor.findById(id);
        if (proveedor == null || !proveedor.estadoActivo) {
            throw new NotFoundException("Proveedor no encontrado: " + id);
        }

        validateRequest(request, true);

        if (request.ruc() != null && !request.ruc().isBlank()) {
            var existing = Proveedor.find("ruc", request.ruc().trim()).firstResultOptional();
            if (existing.isPresent() && !((Proveedor) existing.get()).id.equals(id)) {
                throw new BadRequestException("El RUC ya está en uso por otro proveedor");
            }
            proveedor.ruc = request.ruc().trim();
        }
        if (request.razonSocial() != null && !request.razonSocial().isBlank()) {
            proveedor.razonSocial = request.razonSocial().trim();
        }
        if (request.nombreComercial() != null) {
            proveedor.nombreComercial = request.nombreComercial().trim();
        }
        proveedor.direccion = request.direccion();
        proveedor.telefono = request.telefono();
        proveedor.correo = request.correo();
        proveedor.contactoNombre = request.contactoNombre();
        proveedor.contactoTelefono = request.contactoTelefono();
        proveedor.fechaActualizacion = LocalDateTime.now();
        return toResponse(proveedor);
    }

    @Transactional
    public void softDelete(Long id) {
        var proveedor = (Proveedor) Proveedor.findById(id);
        if (proveedor == null || !proveedor.estadoActivo) {
            throw new NotFoundException("Proveedor no encontrado: " + id);
        }
        proveedor.estadoActivo = false;
        proveedor.fechaActualizacion = LocalDateTime.now();
    }

    private void validateRequest(ProveedorRequest request, boolean allowPartial) {
        if (!allowPartial) {
            if (request.ruc() == null || request.ruc().isBlank()) {
                throw new BadRequestException("El RUC es obligatorio");
            }
            if (request.razonSocial() == null || request.razonSocial().isBlank()) {
                throw new BadRequestException("La razón social es obligatoria");
            }
            if (request.usuarioCreacionId() == null) {
                throw new BadRequestException("El id del usuario creador es obligatorio");
            }
        }
        if (request.ruc() != null && request.ruc().length() > 20) {
            throw new BadRequestException("El RUC no debe superar 20 caracteres");
        }
    }

    private ProveedorResponse toResponse(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.id,
                proveedor.ruc,
                proveedor.razonSocial,
                proveedor.nombreComercial,
                proveedor.direccion,
                proveedor.telefono,
                proveedor.correo,
                proveedor.contactoNombre,
                proveedor.contactoTelefono,
                proveedor.estadoActivo,
                proveedor.fechaCreacion
        );
    }
}
