package pe.com.repcontrol.psr;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.psr.PsrRequest;
import pe.com.repcontrol.dto.psr.PsrResponse;
import pe.com.repcontrol.entity.Campana;
import pe.com.repcontrol.entity.MotivoPsr;
import pe.com.repcontrol.entity.Psr;
import pe.com.repcontrol.entity.Sitio;
import pe.com.repcontrol.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PsrService {

    @Transactional
    public List<PsrResponse> listActive() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Psr> psrs = (List<Psr>) (List) Psr.find("estadoActivo", true).list();
        return psrs.stream().map(this::toResponse).toList();
    }

    @Transactional
    public PsrResponse findById(Long id) {
        var psr = (Psr) Psr.findById(id);
        if (psr == null || !psr.estadoActivo) {
            throw new NotFoundException("PSR no encontrado: " + id);
        }
        return toResponse(psr);
    }

    @Transactional
    public PsrResponse create(PsrRequest request) {
        validateRequest(request, false);

        var existing = Psr.find("numero", request.numero().trim()).firstResultOptional();
        if (existing.isPresent()) {
            throw new BadRequestException("Ya existe un PSR con el número indicado");
        }

        var campana = (Campana) Campana.findById(request.campanaId());
        var sitio = (Sitio) Sitio.findById(request.sitioId());
        var motivo = (MotivoPsr) MotivoPsr.findById(request.motivoId());
        var usuarioCreacion = (Usuario) Usuario.findById(request.usuarioCreacionId());

        if (campana == null) {
            throw new BadRequestException("Campaña no encontrada");
        }
        if (sitio == null) {
            throw new BadRequestException("Sitio no encontrado");
        }
        if (motivo == null) {
            throw new BadRequestException("Motivo PSR no encontrado");
        }
        if (usuarioCreacion == null || !usuarioCreacion.estadoActivo) {
            throw new BadRequestException("Usuario de creación inválido o no encontrado");
        }

        var psr = new Psr();
        psr.numero = request.numero().trim();
        psr.campana = campana;
        psr.sitio = sitio;
        psr.motivo = motivo;
        psr.descripcion = request.descripcion();
        psr.fechaSolicitud = request.fechaSolicitud();
        psr.estado = request.estado() != null && !request.estado().isBlank() ? request.estado().trim() : "ACTIVO";
        psr.observaciones = request.observaciones();
        psr.estadoActivo = true;
        psr.usuarioCreacion = usuarioCreacion;
        psr.persist();
        return toResponse(psr);
    }

    @Transactional
    public PsrResponse update(Long id, PsrRequest request) {
        var psr = (Psr) Psr.findById(id);
        if (psr == null || !psr.estadoActivo) {
            throw new NotFoundException("PSR no encontrado: " + id);
        }

        validateRequest(request, true);

        if (request.numero() != null && !request.numero().isBlank()) {
            var existing = Psr.find("numero", request.numero().trim()).firstResultOptional();
            if (existing.isPresent() && !((Psr) existing.get()).id.equals(id)) {
                throw new BadRequestException("El número de PSR ya está en uso");
            }
            psr.numero = request.numero().trim();
        }
        if (request.campanaId() != null) {
            var campana = (Campana) Campana.findById(request.campanaId());
            if (campana == null) {
                throw new BadRequestException("Campaña no encontrada");
            }
            psr.campana = campana;
        }
        if (request.sitioId() != null) {
            var sitio = (Sitio) Sitio.findById(request.sitioId());
            if (sitio == null) {
                throw new BadRequestException("Sitio no encontrado");
            }
            psr.sitio = sitio;
        }
        if (request.motivoId() != null) {
            var motivo = (MotivoPsr) MotivoPsr.findById(request.motivoId());
            if (motivo == null) {
                throw new BadRequestException("Motivo PSR no encontrado");
            }
            psr.motivo = motivo;
        }
        if (request.descripcion() != null) {
            psr.descripcion = request.descripcion();
        }
        if (request.fechaSolicitud() != null) {
            psr.fechaSolicitud = request.fechaSolicitud();
        }
        if (request.estado() != null && !request.estado().isBlank()) {
            psr.estado = request.estado().trim();
        }
        if (request.observaciones() != null) {
            psr.observaciones = request.observaciones();
        }
        psr.fechaActualizacion = LocalDateTime.now();
        return toResponse(psr);
    }

    @Transactional
    public void softDelete(Long id) {
        var psr = (Psr) Psr.findById(id);
        if (psr == null || !psr.estadoActivo) {
            throw new NotFoundException("PSR no encontrado: " + id);
        }
        psr.estadoActivo = false;
        psr.fechaActualizacion = LocalDateTime.now();
    }

    private void validateRequest(PsrRequest request, boolean allowPartial) {
        if (!allowPartial) {
            if (request.numero() == null || request.numero().isBlank()) {
                throw new BadRequestException("El número de PSR es obligatorio");
            }
            if (request.campanaId() == null) {
                throw new BadRequestException("El id de campaña es obligatorio");
            }
            if (request.sitioId() == null) {
                throw new BadRequestException("El id de sitio es obligatorio");
            }
            if (request.motivoId() == null) {
                throw new BadRequestException("El id de motivo PSR es obligatorio");
            }
            if (request.fechaSolicitud() == null) {
                throw new BadRequestException("La fecha de solicitud es obligatoria");
            }
            if (request.usuarioCreacionId() == null) {
                throw new BadRequestException("El id del usuario creador es obligatorio");
            }
        }
        if (request.numero() != null && request.numero().length() > 50) {
            throw new BadRequestException("El número de PSR no debe superar 50 caracteres");
        }
        if (request.estado() != null && request.estado().length() > 50) {
            throw new BadRequestException("El estado no debe superar 50 caracteres");
        }
    }

    private PsrResponse toResponse(Psr psr) {
        return new PsrResponse(
                psr.id,
                psr.numero,
                psr.campana != null ? psr.campana.id : null,
                psr.sitio != null ? psr.sitio.id : null,
                psr.motivo != null ? psr.motivo.id : null,
                psr.descripcion,
                psr.fechaSolicitud,
                psr.estado,
                psr.observaciones,
                psr.estadoActivo,
                psr.fechaCreacion
        );
    }
}
