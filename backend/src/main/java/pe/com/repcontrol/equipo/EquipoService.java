package pe.com.repcontrol.equipo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.equipo.EquipoRequest;
import pe.com.repcontrol.dto.equipo.EquipoResponse;
import pe.com.repcontrol.entity.Campana;
import pe.com.repcontrol.entity.Equipo;
import pe.com.repcontrol.entity.Marca;
import pe.com.repcontrol.entity.Osr;
import pe.com.repcontrol.entity.Proveedor;
import pe.com.repcontrol.entity.TipoEquipo;
import pe.com.repcontrol.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EquipoService {

    @Transactional
    public List<EquipoResponse> listActive() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Equipo> equipos = (List<Equipo>) (List) Equipo.find("estadoActivo", true).list();
        return equipos.stream().map(this::toResponse).toList();
    }

    @Transactional
    public EquipoResponse findById(Long id) {
        var equipo = (Equipo) Equipo.findById(id);
        if (equipo == null || !equipo.estadoActivo) {
            throw new NotFoundException("Equipo no encontrado: " + id);
        }
        return toResponse(equipo);
    }

    @Transactional
    public EquipoResponse create(EquipoRequest request) {
        validateRequest(request, false);

        if (Equipo.find("codigo", request.codigo().trim()).firstResultOptional().isPresent()) {
            throw new BadRequestException("Ya existe un equipo con el código indicado");
        }

        var proveedor = (Proveedor) Proveedor.findById(request.proveedorId());
        if (proveedor == null || !proveedor.estadoActivo) {
            throw new BadRequestException("Proveedor inválido o no encontrado");
        }

        var tipoEquipo = (TipoEquipo) TipoEquipo.findById(request.tipoEquipoId());
        if (tipoEquipo == null || !tipoEquipo.estadoActivo) {
            throw new BadRequestException("Tipo de equipo inválido o no encontrado");
        }

        Campana campana = null;
        if (request.campanaId() != null) {
            campana = (Campana) Campana.findById(request.campanaId());
            if (campana == null || !campana.estadoActivo) {
                throw new BadRequestException("Campaña inválida o no encontrada");
            }
        }

        Osr osr = null;
        if (request.osrId() != null) {
            osr = (Osr) Osr.findById(request.osrId());
            if (osr == null || !osr.estadoActivo) {
                throw new BadRequestException("OSR inválida o no encontrada");
            }
        }

        Marca marcaRelacion = null;
        if (request.marcaRelacionId() != null) {
            marcaRelacion = (Marca) Marca.findById(request.marcaRelacionId());
            if (marcaRelacion == null) {
                throw new BadRequestException("Marca inválida o no encontrada");
            }
        }

        var usuarioCreacion = (Usuario) Usuario.findById(request.usuarioCreacionId());
        if (usuarioCreacion == null || !usuarioCreacion.estadoActivo) {
            throw new BadRequestException("Usuario de creación inválido o no encontrado");
        }

        var equipo = new Equipo();
        equipo.codigo = request.codigo().trim();
        equipo.numeroSerie = request.numeroSerie().trim();
        equipo.marca = request.marca();
        equipo.modelo = request.modelo();
        equipo.campana = campana;
        equipo.osr = osr;
        equipo.proveedor = proveedor;
        equipo.tipoEquipo = tipoEquipo;
        equipo.marcaRelacion = marcaRelacion;
        equipo.estado = request.estado() != null && !request.estado().isBlank() ? request.estado().trim() : "DISPONIBLE";
        equipo.bateriaTipo = request.bateriaTipo();
        equipo.bateriaHoras = request.bateriaHoras();
        equipo.bateriaVoltaje = request.bateriaVoltaje();
        equipo.cargadorInfo = request.cargadorInfo();
        equipo.transformadorInfo = request.transformadorInfo();
        equipo.fechaIngreso = request.fechaIngreso();
        equipo.fechaDevolucion = request.fechaDevolucion();
        equipo.horometroActual = request.horometroActual();
        equipo.observaciones = request.observaciones();
        equipo.estadoActivo = true;
        equipo.usuarioCreacion = usuarioCreacion;
        equipo.persist();
        return toResponse(equipo);
    }

    @Transactional
    public EquipoResponse update(Long id, EquipoRequest request) {
        var equipo = (Equipo) Equipo.findById(id);
        if (equipo == null || !equipo.estadoActivo) {
            throw new NotFoundException("Equipo no encontrado: " + id);
        }

        validateRequest(request, true);

        if (request.codigo() != null && !request.codigo().isBlank()) {
            var existing = Equipo.find("codigo", request.codigo().trim()).firstResultOptional();
            if (existing.isPresent() && !((Equipo) existing.get()).id.equals(id)) {
                throw new BadRequestException("El código ya está en uso por otro equipo");
            }
            equipo.codigo = request.codigo().trim();
        }
        if (request.numeroSerie() != null && !request.numeroSerie().isBlank()) {
            equipo.numeroSerie = request.numeroSerie().trim();
        }
        if (request.marca() != null) {
            equipo.marca = request.marca();
        }
        if (request.modelo() != null) {
            equipo.modelo = request.modelo();
        }
        if (request.campanaId() != null) {
            var campana = (Campana) Campana.findById(request.campanaId());
            if (campana == null || !campana.estadoActivo) {
                throw new BadRequestException("Campaña inválida o no encontrada");
            }
            equipo.campana = campana;
        }
        if (request.osrId() != null) {
            var osr = (Osr) Osr.findById(request.osrId());
            if (osr == null || !osr.estadoActivo) {
                throw new BadRequestException("OSR inválida o no encontrada");
            }
            equipo.osr = osr;
        }
        if (request.proveedorId() != null) {
            var proveedor = (Proveedor) Proveedor.findById(request.proveedorId());
            if (proveedor == null || !proveedor.estadoActivo) {
                throw new BadRequestException("Proveedor inválido o no encontrado");
            }
            equipo.proveedor = proveedor;
        }
        if (request.tipoEquipoId() != null) {
            var tipoEquipo = (TipoEquipo) TipoEquipo.findById(request.tipoEquipoId());
            if (tipoEquipo == null || !tipoEquipo.estadoActivo) {
                throw new BadRequestException("Tipo de equipo inválido o no encontrado");
            }
            equipo.tipoEquipo = tipoEquipo;
        }
        if (request.marcaRelacionId() != null) {
            var marcaRelacion = (Marca) Marca.findById(request.marcaRelacionId());
            if (marcaRelacion == null) {
                throw new BadRequestException("Marca inválida o no encontrada");
            }
            equipo.marcaRelacion = marcaRelacion;
        }
        if (request.estado() != null && !request.estado().isBlank()) {
            equipo.estado = request.estado().trim();
        }
        if (request.bateriaTipo() != null) {
            equipo.bateriaTipo = request.bateriaTipo();
        }
        if (request.bateriaHoras() != null) {
            equipo.bateriaHoras = request.bateriaHoras();
        }
        if (request.bateriaVoltaje() != null) {
            equipo.bateriaVoltaje = request.bateriaVoltaje();
        }
        if (request.cargadorInfo() != null) {
            equipo.cargadorInfo = request.cargadorInfo();
        }
        if (request.transformadorInfo() != null) {
            equipo.transformadorInfo = request.transformadorInfo();
        }
        if (request.fechaIngreso() != null) {
            equipo.fechaIngreso = request.fechaIngreso();
        }
        if (request.fechaDevolucion() != null) {
            equipo.fechaDevolucion = request.fechaDevolucion();
        }
        if (request.horometroActual() != null) {
            equipo.horometroActual = request.horometroActual();
        }
        if (request.observaciones() != null) {
            equipo.observaciones = request.observaciones();
        }
        equipo.fechaActualizacion = LocalDateTime.now();
        return toResponse(equipo);
    }

    @Transactional
    public void softDelete(Long id) {
        var equipo = (Equipo) Equipo.findById(id);
        if (equipo == null || !equipo.estadoActivo) {
            throw new NotFoundException("Equipo no encontrado: " + id);
        }
        equipo.estadoActivo = false;
        equipo.fechaActualizacion = LocalDateTime.now();
    }

    private void validateRequest(EquipoRequest request, boolean allowPartial) {
        if (!allowPartial) {
            if (request.codigo() == null || request.codigo().isBlank()) {
                throw new BadRequestException("El código es obligatorio");
            }
            if (request.numeroSerie() == null || request.numeroSerie().isBlank()) {
                throw new BadRequestException("El número de serie es obligatorio");
            }
            if (request.proveedorId() == null) {
                throw new BadRequestException("El id del proveedor es obligatorio");
            }
            if (request.tipoEquipoId() == null) {
                throw new BadRequestException("El id del tipo de equipo es obligatorio");
            }
            if (request.usuarioCreacionId() == null) {
                throw new BadRequestException("El id del usuario creador es obligatorio");
            }
        }
    }

    private EquipoResponse toResponse(Equipo equipo) {
        return new EquipoResponse(
                equipo.id,
                equipo.codigo,
                equipo.numeroSerie,
                equipo.marca,
                equipo.modelo,
                equipo.campana != null ? equipo.campana.id : null,
                equipo.osr != null ? equipo.osr.id : null,
                equipo.proveedor != null ? equipo.proveedor.id : null,
                equipo.tipoEquipo != null ? equipo.tipoEquipo.id : null,
                equipo.marcaRelacion != null ? equipo.marcaRelacion.id : null,
                equipo.estado,
                equipo.bateriaTipo,
                equipo.bateriaHoras,
                equipo.bateriaVoltaje,
                equipo.cargadorInfo,
                equipo.transformadorInfo,
                equipo.fechaIngreso,
                equipo.fechaDevolucion,
                equipo.horometroActual,
                equipo.observaciones,
                equipo.estadoActivo,
                equipo.fechaCreacion
        );
    }
}
