package pe.com.repcontrol.tipoequipo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.tipoequipo.TipoEquipoRequest;
import pe.com.repcontrol.dto.tipoequipo.TipoEquipoResponse;
import pe.com.repcontrol.entity.TipoEquipo;

import java.util.List;

@ApplicationScoped
public class TipoEquipoService {

    @Transactional
    public List<TipoEquipoResponse> listActive() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<TipoEquipo> tipos = (List<TipoEquipo>) (List) TipoEquipo.find("estadoActivo", true).list();
        return tipos.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TipoEquipoResponse findById(Long id) {
        var tipo = (TipoEquipo) TipoEquipo.findById(id);
        if (tipo == null || !tipo.estadoActivo) {
            throw new NotFoundException("Tipo de equipo no encontrado: " + id);
        }
        return toResponse(tipo);
    }

    @Transactional
    public TipoEquipoResponse create(TipoEquipoRequest request) {
        validateRequest(request, false);

        var existing = TipoEquipo.find("codigo", request.codigo()).firstResultOptional();
        if (existing.isPresent()) {
            throw new BadRequestException("Ya existe un tipo de equipo con el código indicado");
        }

        var tipo = new TipoEquipo();
        tipo.codigo = request.codigo().trim();
        tipo.nombre = request.nombre().trim();
        tipo.categoria = request.categoria().trim();
        tipo.tecnologiaBateria = request.tecnologiaBateria();
        tipo.requiereHorometro = request.requiereHorometro();
        tipo.requiereBateria = request.requiereBateria();
        tipo.requiereCargador = request.requiereCargador();
        tipo.requiereTransformador = request.requiereTransformador();
        tipo.descripcion = request.descripcion();
        tipo.estadoActivo = true;

        tipo.persist();
        return toResponse(tipo);
    }

    @Transactional
    public TipoEquipoResponse update(Long id, TipoEquipoRequest request) {
        var tipo = (TipoEquipo) TipoEquipo.findById(id);
        if (tipo == null || !tipo.estadoActivo) {
            throw new NotFoundException("Tipo de equipo no encontrado: " + id);
        }

        validateRequest(request, true);

        if (request.codigo() != null && !request.codigo().isBlank()) {
            var existing = TipoEquipo.find("codigo", request.codigo().trim()).firstResultOptional();
            if (existing.isPresent() && !((TipoEquipo) existing.get()).id.equals(id)) {
                throw new BadRequestException("El código de tipo de equipo ya está en uso");
            }
            tipo.codigo = request.codigo().trim();
        }
        if (request.nombre() != null && !request.nombre().isBlank()) {
            tipo.nombre = request.nombre().trim();
        }
        if (request.categoria() != null && !request.categoria().isBlank()) {
            tipo.categoria = request.categoria().trim();
        }
        tipo.tecnologiaBateria = request.tecnologiaBateria();
        tipo.requiereHorometro = request.requiereHorometro();
        tipo.requiereBateria = request.requiereBateria();
        tipo.requiereCargador = request.requiereCargador();
        tipo.requiereTransformador = request.requiereTransformador();
        tipo.descripcion = request.descripcion();
        tipo.fechaActualizacion = java.time.LocalDateTime.now();

        return toResponse(tipo);
    }

    @Transactional
    public void softDelete(Long id) {
        var tipo = (TipoEquipo) TipoEquipo.findById(id);
        if (tipo == null || !tipo.estadoActivo) {
            throw new NotFoundException("Tipo de equipo no encontrado: " + id);
        }
        tipo.estadoActivo = false;
        tipo.fechaActualizacion = java.time.LocalDateTime.now();
    }

    private void validateRequest(TipoEquipoRequest request, boolean allowPartial) {
        if (!allowPartial) {
            if (request.codigo() == null || request.codigo().isBlank()) {
                throw new BadRequestException("El código es obligatorio");
            }
            if (request.nombre() == null || request.nombre().isBlank()) {
                throw new BadRequestException("El nombre es obligatorio");
            }
            if (request.categoria() == null || request.categoria().isBlank()) {
                throw new BadRequestException("La categoría es obligatoria");
            }
        }
    }

    private TipoEquipoResponse toResponse(TipoEquipo tipo) {
        return new TipoEquipoResponse(
                tipo.id,
                tipo.codigo,
                tipo.nombre,
                tipo.categoria,
                tipo.tecnologiaBateria,
                tipo.requiereHorometro,
                tipo.requiereBateria,
                tipo.requiereCargador,
                tipo.requiereTransformador,
                tipo.descripcion,
                tipo.estadoActivo
        );
    }
}
