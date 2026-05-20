package pe.com.repcontrol.averia;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.averia.AveriaCloseRequest;
import pe.com.repcontrol.dto.averia.AveriaRequest;
import pe.com.repcontrol.dto.averia.AveriaResponse;
import pe.com.repcontrol.entity.Averia;
import pe.com.repcontrol.entity.Equipo;
import pe.com.repcontrol.entity.EstadoAveria;
import pe.com.repcontrol.entity.Proveedor;
import pe.com.repcontrol.entity.TipoAveria;
import pe.com.repcontrol.entity.Usuario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AveriaService {

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String ESTADO_EN_ATENCION = "EN_ATENCION";
    private static final String ESTADO_CERRADA = "CERRADA";
    private static final String EQUIPO_AVERIADO = "AVERIADO";
    private static final String EQUIPO_DISPONIBLE = "DISPONIBLE";

    @Transactional
    public List<AveriaResponse> listActive(
            Long equipoId,
            Long tipoAveriaId,
            Long estadoAveriaId,
            Long proveedorId,
            LocalDate fechaDesde,
            LocalDate fechaHasta) {
        StringBuilder query = new StringBuilder("estadoActivo = ?1");
        List<Object> params = new ArrayList<>();
        params.add(true);

        if (equipoId != null) {
            query.append(" and equipo.id = ?").append(params.size() + 1);
            params.add(equipoId);
        }
        if (tipoAveriaId != null) {
            query.append(" and tipoAveria.id = ?").append(params.size() + 1);
            params.add(tipoAveriaId);
        }
        if (estadoAveriaId != null) {
            query.append(" and estadoAveria.id = ?").append(params.size() + 1);
            params.add(estadoAveriaId);
        }
        if (proveedorId != null) {
            query.append(" and proveedor.id = ?").append(params.size() + 1);
            params.add(proveedorId);
        }
        if (fechaDesde != null) {
            query.append(" and fechaReporte >= ?").append(params.size() + 1);
            params.add(fechaDesde.atStartOfDay());
        }
        if (fechaHasta != null) {
            query.append(" and fechaReporte <= ?").append(params.size() + 1);
            params.add(fechaHasta.atTime(23, 59, 59));
        }

        PanacheQuery<Averia> averiasQuery = Averia.find(query.toString(), params.toArray());
        return averiasQuery.list().stream().map(this::toResponse).toList();
    }

    @Transactional
    public AveriaResponse findById(Long id) {
        Averia averia = findActiveAveria(id);
        return toResponse(averia);
    }

    @Transactional
    public AveriaResponse create(AveriaRequest request) {
        validateRequest(request, false);

        String codigo = request.codigo().trim();
        if (Averia.find("codigo", codigo).firstResultOptional().isPresent()) {
            throw new BadRequestException("Ya existe una averia con el codigo indicado");
        }

        Equipo equipo = findActiveEquipo(request.equipoId());
        TipoAveria tipoAveria = findActiveTipoAveria(request.tipoAveriaId());
        Proveedor proveedor = findActiveProveedor(request.proveedorId());
        Usuario usuarioReporta = findActiveUsuario(request.usuarioReportaId(), "Usuario reporta invalido o no encontrado");
        Usuario usuarioCreacion = findActiveUsuario(request.usuarioCreacionId(), "Usuario de creacion invalido o no encontrado");
        EstadoAveria estadoPendiente = findEstadoByCodigo(ESTADO_PENDIENTE);

        Averia averia = new Averia();
        averia.codigo = codigo;
        averia.equipo = equipo;
        averia.tipoAveria = tipoAveria;
        averia.estadoAveria = estadoPendiente;
        averia.proveedor = proveedor;
        averia.descripcionFalla = request.descripcionFalla().trim();
        averia.descripcionAtencion = normalizeText(request.descripcionAtencion());
        averia.accionCorrectiva = normalizeText(request.accionCorrectiva());
        averia.fechaReporte = request.fechaReporte();
        averia.fechaAtencion = request.fechaAtencion();
        averia.fechaCierre = request.fechaCierre();
        averia.horasInactivo = request.horasInactivo() != null ? request.horasInactivo() : BigDecimal.ZERO;
        averia.usuarioReporta = usuarioReporta;
        averia.usuarioAtiende = request.usuarioAtiendeId() != null
                ? findActiveUsuario(request.usuarioAtiendeId(), "Usuario atiende invalido o no encontrado")
                : null;
        averia.observaciones = normalizeText(request.observaciones());
        averia.estadoActivo = true;
        averia.usuarioCreacion = usuarioCreacion;
        averia.persist();

        markEquipoAsAveriado(equipo);
        return toResponse(averia);
    }

    @Transactional
    public AveriaResponse update(Long id, AveriaRequest request) {
        Averia averia = findActiveAveria(id);
        validateRequest(request, true);

        if (request.codigo() != null && !request.codigo().isBlank()) {
            String codigo = request.codigo().trim();
            var existing = Averia.find("codigo", codigo).firstResultOptional();
            if (existing.isPresent() && !((Averia) existing.get()).id.equals(id)) {
                throw new BadRequestException("El codigo ya esta en uso por otra averia");
            }
            averia.codigo = codigo;
        }
        if (request.equipoId() != null) {
            averia.equipo = findActiveEquipo(request.equipoId());
        }
        if (request.tipoAveriaId() != null) {
            averia.tipoAveria = findActiveTipoAveria(request.tipoAveriaId());
        }
        if (request.proveedorId() != null) {
            averia.proveedor = findActiveProveedor(request.proveedorId());
        }
        if (request.descripcionFalla() != null && !request.descripcionFalla().isBlank()) {
            averia.descripcionFalla = request.descripcionFalla().trim();
        }
        if (request.descripcionAtencion() != null) {
            averia.descripcionAtencion = normalizeText(request.descripcionAtencion());
        }
        if (request.accionCorrectiva() != null) {
            averia.accionCorrectiva = normalizeText(request.accionCorrectiva());
        }
        if (request.fechaReporte() != null) {
            averia.fechaReporte = request.fechaReporte();
        }
        if (request.fechaAtencion() != null) {
            averia.fechaAtencion = request.fechaAtencion();
        }
        if (request.fechaCierre() != null) {
            averia.fechaCierre = request.fechaCierre();
        }
        if (request.horasInactivo() != null) {
            averia.horasInactivo = request.horasInactivo();
        }
        if (request.usuarioReportaId() != null) {
            averia.usuarioReporta = findActiveUsuario(request.usuarioReportaId(), "Usuario reporta invalido o no encontrado");
        }
        if (request.usuarioAtiendeId() != null) {
            averia.usuarioAtiende = findActiveUsuario(request.usuarioAtiendeId(), "Usuario atiende invalido o no encontrado");
        }
        if (request.observaciones() != null) {
            averia.observaciones = normalizeText(request.observaciones());
        }
        if (request.estadoAveriaId() != null) {
            averia.estadoAveria = findEstadoById(request.estadoAveriaId());
        }

        applyEquipmentStateByDamageStatus(averia);
        averia.fechaActualizacion = LocalDateTime.now();
        return toResponse(averia);
    }

    @Transactional
    public AveriaResponse close(Long id, AveriaCloseRequest request) {
        Averia averia = findActiveAveria(id);
        if (request == null) {
            throw new BadRequestException("La solicitud de cierre es obligatoria");
        }

        if (request.descripcionAtencion() == null || request.descripcionAtencion().isBlank()) {
            throw new BadRequestException("La descripcion de atencion es obligatoria para cerrar la averia");
        }
        if (request.accionCorrectiva() == null || request.accionCorrectiva().isBlank()) {
            throw new BadRequestException("La accion correctiva es obligatoria para cerrar la averia");
        }

        LocalDateTime fechaCierre = request.fechaCierre() != null ? request.fechaCierre() : LocalDateTime.now();
        if (fechaCierre.isBefore(averia.fechaReporte)) {
            throw new BadRequestException("La fecha de cierre no puede ser anterior a la fecha de reporte");
        }

        averia.estadoAveria = findEstadoByCodigo(ESTADO_CERRADA);
        averia.descripcionAtencion = request.descripcionAtencion().trim();
        averia.accionCorrectiva = request.accionCorrectiva().trim();
        averia.fechaAtencion = averia.fechaAtencion != null ? averia.fechaAtencion : LocalDateTime.now();
        averia.fechaCierre = fechaCierre;
        averia.observaciones = request.observaciones() != null ? normalizeText(request.observaciones()) : averia.observaciones;
        averia.horasInactivo = calculateHorasInactivo(averia.fechaReporte, fechaCierre);
        averia.fechaActualizacion = LocalDateTime.now();

        averia.equipo.estado = EQUIPO_DISPONIBLE;
        averia.equipo.fechaActualizacion = LocalDateTime.now();
        return toResponse(averia);
    }

    private void validateRequest(AveriaRequest request, boolean allowPartial) {
        if (request == null) {
            throw new BadRequestException("La solicitud es obligatoria");
        }

        if (!allowPartial) {
            if (request.codigo() == null || request.codigo().isBlank()) {
                throw new BadRequestException("El codigo es obligatorio");
            }
            if (request.equipoId() == null) {
                throw new BadRequestException("El id del equipo es obligatorio");
            }
            if (request.tipoAveriaId() == null) {
                throw new BadRequestException("El id del tipo de averia es obligatorio");
            }
            if (request.proveedorId() == null) {
                throw new BadRequestException("El id del proveedor es obligatorio");
            }
            if (request.descripcionFalla() == null || request.descripcionFalla().isBlank()) {
                throw new BadRequestException("La descripcion de la falla es obligatoria");
            }
            if (request.fechaReporte() == null) {
                throw new BadRequestException("La fecha de reporte es obligatoria");
            }
            if (request.usuarioReportaId() == null) {
                throw new BadRequestException("El usuario que reporta es obligatorio");
            }
            if (request.usuarioCreacionId() == null) {
                throw new BadRequestException("El usuario creador es obligatorio");
            }
        }
    }

    private Averia findActiveAveria(Long id) {
        Averia averia = (Averia) Averia.findById(id);
        if (averia == null || !averia.estadoActivo) {
            throw new NotFoundException("Averia no encontrada: " + id);
        }
        return averia;
    }

    private Equipo findActiveEquipo(Long id) {
        Equipo equipo = (Equipo) Equipo.findById(id);
        if (equipo == null || !equipo.estadoActivo) {
            throw new BadRequestException("Equipo invalido o no encontrado");
        }
        return equipo;
    }

    private TipoAveria findActiveTipoAveria(Long id) {
        TipoAveria tipoAveria = (TipoAveria) TipoAveria.findById(id);
        if (tipoAveria == null || !tipoAveria.estadoActivo) {
            throw new BadRequestException("Tipo de averia invalido o no encontrado");
        }
        return tipoAveria;
    }

    private Proveedor findActiveProveedor(Long id) {
        Proveedor proveedor = (Proveedor) Proveedor.findById(id);
        if (proveedor == null || !proveedor.estadoActivo) {
            throw new BadRequestException("Proveedor invalido o no encontrado");
        }
        return proveedor;
    }

    private Usuario findActiveUsuario(Long id, String message) {
        Usuario usuario = (Usuario) Usuario.findById(id);
        if (usuario == null || !usuario.estadoActivo) {
            throw new BadRequestException(message);
        }
        return usuario;
    }

    private EstadoAveria findEstadoByCodigo(String codigo) {
        EstadoAveria estado = EstadoAveria.find("codigo", codigo).firstResult();
        if (estado == null) {
            throw new BadRequestException("Estado de averia no configurado: " + codigo);
        }
        return estado;
    }

    private EstadoAveria findEstadoById(Long id) {
        EstadoAveria estado = (EstadoAveria) EstadoAveria.findById(id);
        if (estado == null) {
            throw new BadRequestException("Estado de averia invalido o no encontrado");
        }
        return estado;
    }

    private void applyEquipmentStateByDamageStatus(Averia averia) {
        String codigoEstado = averia.estadoAveria.codigo;
        if (ESTADO_CERRADA.equalsIgnoreCase(codigoEstado)) {
            LocalDateTime fechaCierre = averia.fechaCierre != null ? averia.fechaCierre : LocalDateTime.now();
            if (fechaCierre.isBefore(averia.fechaReporte)) {
                throw new BadRequestException("La fecha de cierre no puede ser anterior a la fecha de reporte");
            }
            averia.fechaCierre = fechaCierre;
            averia.fechaAtencion = averia.fechaAtencion != null ? averia.fechaAtencion : LocalDateTime.now();
            averia.horasInactivo = calculateHorasInactivo(averia.fechaReporte, fechaCierre);
            averia.equipo.estado = EQUIPO_DISPONIBLE;
            averia.equipo.fechaActualizacion = LocalDateTime.now();
            return;
        }

        if (ESTADO_EN_ATENCION.equalsIgnoreCase(codigoEstado) && averia.fechaAtencion == null) {
            averia.fechaAtencion = LocalDateTime.now();
        }

        markEquipoAsAveriado(averia.equipo);
    }

    private void markEquipoAsAveriado(Equipo equipo) {
        equipo.estado = EQUIPO_AVERIADO;
        equipo.fechaActualizacion = LocalDateTime.now();
    }

    private BigDecimal calculateHorasInactivo(LocalDateTime fechaReporte, LocalDateTime fechaCierre) {
        long minutos = Duration.between(fechaReporte, fechaCierre).toMinutes();
        return BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    private AveriaResponse toResponse(Averia averia) {
        return new AveriaResponse(
                averia.id,
                averia.codigo,
                averia.equipo != null ? averia.equipo.id : null,
                averia.equipo != null ? averia.equipo.codigo : null,
                averia.tipoAveria != null ? averia.tipoAveria.id : null,
                averia.tipoAveria != null ? averia.tipoAveria.nombre : null,
                averia.estadoAveria != null ? averia.estadoAveria.id : null,
                averia.estadoAveria != null ? averia.estadoAveria.codigo : null,
                averia.proveedor != null ? averia.proveedor.id : null,
                averia.proveedor != null ? averia.proveedor.razonSocial : null,
                averia.descripcionFalla,
                averia.descripcionAtencion,
                averia.accionCorrectiva,
                averia.fechaReporte,
                averia.fechaAtencion,
                averia.fechaCierre,
                averia.horasInactivo,
                averia.usuarioReporta != null ? averia.usuarioReporta.id : null,
                averia.usuarioAtiende != null ? averia.usuarioAtiende.id : null,
                averia.observaciones,
                averia.equipo != null ? averia.equipo.estado : null,
                averia.estadoActivo,
                averia.fechaCreacion
        );
    }
}
