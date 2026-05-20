package pe.com.repcontrol.campana;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.campana.CampanaRequest;
import pe.com.repcontrol.dto.campana.CampanaResponse;
import pe.com.repcontrol.entity.Campana;
import pe.com.repcontrol.entity.Usuario;

import java.util.List;

@ApplicationScoped
public class CampanaService {

    @Transactional
    public List<CampanaResponse> listActive() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Campana> campanas = (List<Campana>) (List) Campana.find("estadoActivo", true).list();
        return campanas.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CampanaResponse findById(Long id) {
        var campana = (Campana) Campana.findById(id);
        if (campana == null || !campana.estadoActivo) {
            throw new NotFoundException("Campaña no encontrada: " + id);
        }
        return toResponse(campana);
    }

    @Transactional
    public CampanaResponse create(CampanaRequest request) {
        validateRequest(request, false);

        var usuarioCreacion = (Usuario) Usuario.findById(request.usuarioCreacionId());
        if (usuarioCreacion == null || !usuarioCreacion.estadoActivo) {
            throw new BadRequestException("Usuario de creación inválido o no encontrado");
        }

        var campana = new Campana();
        campana.nombre = request.nombre().trim();
        campana.fechaInicio = request.fechaInicio();
        campana.fechaFin = request.fechaFin();
        campana.estado = "ACTIVA";
        campana.estadoActivo = true;
        campana.usuarioCreacion = usuarioCreacion;

        campana.persist();
        return toResponse(campana);
    }

    @Transactional
    public CampanaResponse update(Long id, CampanaRequest request) {
        var campana = (Campana) Campana.findById(id);
        if (campana == null || !campana.estadoActivo) {
            throw new NotFoundException("Campaña no encontrada: " + id);
        }

        validateRequest(request, true);

        if (request.nombre() != null && !request.nombre().isBlank()) {
            campana.nombre = request.nombre().trim();
        }
        if (request.fechaInicio() != null) {
            campana.fechaInicio = request.fechaInicio();
        }
        if (request.fechaFin() != null) {
            campana.fechaFin = request.fechaFin();
        }
        campana.fechaActualizacion = java.time.LocalDateTime.now();
        return toResponse(campana);
    }

    @Transactional
    public void softDelete(Long id) {
        var campana = (Campana) Campana.findById(id);
        if (campana == null || !campana.estadoActivo) {
            throw new NotFoundException("Campaña no encontrada: " + id);
        }
        campana.estadoActivo = false;
        campana.fechaActualizacion = java.time.LocalDateTime.now();
    }

    private void validateRequest(CampanaRequest request, boolean allowPartial) {
        if (!allowPartial) {
            if (request.nombre() == null || request.nombre().isBlank()) {
                throw new BadRequestException("El nombre de campaña es obligatorio");
            }
            if (request.fechaInicio() == null) {
                throw new BadRequestException("La fecha de inicio es obligatoria");
            }
            if (request.usuarioCreacionId() == null) {
                throw new BadRequestException("El id del usuario creador es obligatorio");
            }
        }
        if (request.fechaInicio() != null && request.fechaFin() != null && request.fechaFin().isBefore(request.fechaInicio())) {
            throw new BadRequestException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }

    private CampanaResponse toResponse(Campana campana) {
        return new CampanaResponse(
                campana.id,
                campana.nombre,
                campana.fechaInicio,
                campana.fechaFin,
                campana.estado,
                campana.estadoActivo,
                campana.fechaCreacion
        );
    }
}
