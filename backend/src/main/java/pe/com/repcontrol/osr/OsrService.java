package pe.com.repcontrol.osr;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.osr.OsrRequest;
import pe.com.repcontrol.dto.osr.OsrResponse;
import pe.com.repcontrol.entity.Equipo;
import pe.com.repcontrol.entity.Osr;
import pe.com.repcontrol.entity.Psr;
import pe.com.repcontrol.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OsrService {

    @Transactional
    public List<OsrResponse> listActive() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Osr> osrs = (List<Osr>) (List) Osr.find("estadoActivo", true).list();
        return osrs.stream().map(this::toResponse).toList();
    }

    @Transactional
    public OsrResponse findById(Long id) {
        var osr = (Osr) Osr.findById(id);
        if (osr == null || !osr.estadoActivo) {
            throw new NotFoundException("OSR no encontrado: " + id);
        }
        return toResponse(osr);
    }

    @Transactional
    public OsrResponse create(OsrRequest request) {
        validateRequest(request, false);

        var existing = Osr.find("numero", request.numero().trim()).firstResultOptional();
        if (existing.isPresent()) {
            throw new BadRequestException("Ya existe una OSR con el número indicado");
        }

        var psr = (Psr) Psr.findById(request.psrId());
        if (psr == null || !psr.estadoActivo) {
            throw new BadRequestException("PSR inválido o no encontrado");
        }

        Equipo equipo = null;
        if (request.equipoId() != null) {
            equipo = (Equipo) Equipo.findById(request.equipoId());
            if (equipo == null || !equipo.estadoActivo) {
                throw new BadRequestException("Equipo inválido o no encontrado");
            }
        }

        var usuarioCreacion = (Usuario) Usuario.findById(request.usuarioCreacionId());
        if (usuarioCreacion == null || !usuarioCreacion.estadoActivo) {
            throw new BadRequestException("Usuario de creación inválido o no encontrado");
        }

        var osr = new Osr();
        osr.numero = request.numero().trim();
        osr.psr = psr;
        osr.equipo = equipo;
        osr.fechaAsignacion = request.fechaAsignacion();
        osr.horaInicio = request.horaInicio();
        osr.horaFin = request.horaFin();
        osr.horometroInicio = request.horometroInicio();
        osr.horometroFin = request.horometroFin();
        osr.estado = request.estado() != null && !request.estado().isBlank() ? request.estado().trim() : "PENDIENTE";
        osr.observaciones = request.observaciones();
        osr.estadoActivo = true;
        osr.usuarioCreacion = usuarioCreacion;
        osr.persist();
        return toResponse(osr);
    }

    @Transactional
    public OsrResponse update(Long id, OsrRequest request) {
        var osr = (Osr) Osr.findById(id);
        if (osr == null || !osr.estadoActivo) {
            throw new NotFoundException("OSR no encontrado: " + id);
        }

        validateRequest(request, true);

        if (request.numero() != null && !request.numero().isBlank()) {
            var existing = Osr.find("numero", request.numero().trim()).firstResultOptional();
            if (existing.isPresent() && !((Osr) existing.get()).id.equals(id)) {
                throw new BadRequestException("El número de OSR ya está en uso");
            }
            osr.numero = request.numero().trim();
        }
        if (request.psrId() != null) {
            var psr = (Psr) Psr.findById(request.psrId());
            if (psr == null || !psr.estadoActivo) {
                throw new BadRequestException("PSR inválido o no encontrado");
            }
            osr.psr = psr;
        }
        if (request.equipoId() != null) {
            var equipo = (Equipo) Equipo.findById(request.equipoId());
            if (equipo == null || !equipo.estadoActivo) {
                throw new BadRequestException("Equipo inválido o no encontrado");
            }
            osr.equipo = equipo;
        }
        if (request.fechaAsignacion() != null) {
            osr.fechaAsignacion = request.fechaAsignacion();
        }
        if (request.horaInicio() != null) {
            osr.horaInicio = request.horaInicio();
        }
        if (request.horaFin() != null) {
            osr.horaFin = request.horaFin();
        }
        if (request.horometroInicio() != null) {
            osr.horometroInicio = request.horometroInicio();
        }
        if (request.horometroFin() != null) {
            osr.horometroFin = request.horometroFin();
        }
        if (request.estado() != null && !request.estado().isBlank()) {
            osr.estado = request.estado().trim();
        }
        if (request.observaciones() != null) {
            osr.observaciones = request.observaciones();
        }
        osr.fechaActualizacion = LocalDateTime.now();
        return toResponse(osr);
    }

    @Transactional
    public void softDelete(Long id) {
        var osr = (Osr) Osr.findById(id);
        if (osr == null || !osr.estadoActivo) {
            throw new NotFoundException("OSR no encontrado: " + id);
        }
        osr.estadoActivo = false;
        osr.fechaActualizacion = LocalDateTime.now();
    }

    private void validateRequest(OsrRequest request, boolean allowPartial) {
        if (!allowPartial) {
            if (request.numero() == null || request.numero().isBlank()) {
                throw new BadRequestException("El número de OSR es obligatorio");
            }
            if (request.psrId() == null) {
                throw new BadRequestException("El id de PSR es obligatorio");
            }
            if (request.fechaAsignacion() == null) {
                throw new BadRequestException("La fecha de asignación es obligatoria");
            }
            if (request.usuarioCreacionId() == null) {
                throw new BadRequestException("El id del usuario creador es obligatorio");
            }
        }
        if (request.numero() != null && request.numero().length() > 50) {
            throw new BadRequestException("El número de OSR no debe superar 50 caracteres");
        }
        if (request.estado() != null && request.estado().length() > 50) {
            throw new BadRequestException("El estado no debe superar 50 caracteres");
        }
    }

    private OsrResponse toResponse(Osr osr) {
        return new OsrResponse(
                osr.id,
                osr.numero,
                osr.psr != null ? osr.psr.id : null,
                osr.equipo != null ? osr.equipo.id : null,
                osr.fechaAsignacion,
                osr.horaInicio,
                osr.horaFin,
                osr.horometroInicio,
                osr.horometroFin,
                osr.estado,
                osr.observaciones,
                osr.estadoActivo,
                osr.fechaCreacion
        );
    }
}
