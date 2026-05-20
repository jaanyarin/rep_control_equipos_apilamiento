package pe.com.repcontrol.evidencia;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.evidencia.EvidenciaRequest;
import pe.com.repcontrol.dto.evidencia.EvidenciaResponse;
import pe.com.repcontrol.entity.Averia;
import pe.com.repcontrol.entity.Equipo;
import pe.com.repcontrol.entity.Evidencia;
import pe.com.repcontrol.entity.Osr;
import pe.com.repcontrol.entity.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EvidenciaService {

    private static final int MAX_UPLOAD_SIZE_MB = 5;
    private static final int MAX_IMAGE_WIDTH = 1080;
    private static final int MAX_IMAGE_HEIGHT = 720;
    private static final List<String> ALLOWED_FORMATS = List.of("JPG", "JPEG", "PNG");

    @Transactional
    public List<EvidenciaResponse> listActive(
            Long equipoId,
            Long averiaId,
            Long osrId,
            String tipo) {
        StringBuilder query = new StringBuilder("estadoActivo = ?1");
        List<Object> params = new ArrayList<>();
        params.add(true);

        if (equipoId != null) {
            query.append(" and equipo.id = ?").append(params.size() + 1);
            params.add(equipoId);
        }
        if (averiaId != null) {
            query.append(" and averia.id = ?").append(params.size() + 1);
            params.add(averiaId);
        }
        if (osrId != null) {
            query.append(" and osr.id = ?").append(params.size() + 1);
            params.add(osrId);
        }
        if (tipo != null && !tipo.isBlank()) {
            query.append(" and tipo = ?").append(params.size() + 1);
            params.add(tipo.toUpperCase());
        }

        PanacheQuery<Evidencia> evidenciasQuery = Evidencia.find(query.toString(), params.toArray());
        return evidenciasQuery.list().stream().map(this::toResponse).toList();
    }

    @Transactional
    public EvidenciaResponse findById(Long id) {
        Evidencia evidencia = findActiveEvidencia(id);
        return toResponse(evidencia);
    }

    @Transactional
    public EvidenciaResponse create(EvidenciaRequest request) {
        validateRequest(request);

        Usuario usuario = findActiveUsuario(request.usuarioId());
        validateTipo(request.tipo());

        if (request.equipoId() != null) {
            findActiveEquipo(request.equipoId());
        }
        if (request.averiaId() != null) {
            findActiveAveria(request.averiaId());
        }
        if (request.osrId() != null) {
            findActiveOsr(request.osrId());
        }

        validateImageConstraints(request);

        Evidencia evidencia = new Evidencia();
        evidencia.equipo = request.equipoId() != null ? findActiveEquipo(request.equipoId()) : null;
        evidencia.averia = request.averiaId() != null ? findActiveAveria(request.averiaId()) : null;
        evidencia.osr = request.osrId() != null ? findActiveOsr(request.osrId()) : null;
        evidencia.tipo = request.tipo().toUpperCase();
        evidencia.nombreOriginal = request.nombreOriginal();
        evidencia.nombreArchivo = request.nombreArchivo();
        evidencia.ruta = request.ruta();
        evidencia.tamano = request.tamano();
        evidencia.ancho = request.ancho();
        evidencia.alto = request.alto();
        evidencia.formato = request.formato().toUpperCase();
        evidencia.descripcion = normalizeText(request.descripcion());
        evidencia.usuario = usuario;
        evidencia.fechaCarga = LocalDateTime.now();
        evidencia.estadoActivo = true;
        evidencia.persist();

        return toResponse(evidencia);
    }

    @Transactional
    public EvidenciaResponse delete(Long id) {
        Evidencia evidencia = findActiveEvidencia(id);
        
        evidencia.estadoActivo = false;
        evidencia.fechaBaja = LocalDateTime.now();
        
        return toResponse(evidencia);
    }

    private void validateRequest(EvidenciaRequest request) {
        if (request == null) {
            throw new BadRequestException("La solicitud es obligatoria");
        }
        if (request.tipo() == null || request.tipo().isBlank()) {
            throw new BadRequestException("El tipo de evidencia es obligatorio");
        }
        if (request.nombreOriginal() == null || request.nombreOriginal().isBlank()) {
            throw new BadRequestException("El nombre original es obligatorio");
        }
        if (request.nombreArchivo() == null || request.nombreArchivo().isBlank()) {
            throw new BadRequestException("El nombre de archivo es obligatorio");
        }
        if (request.ruta() == null || request.ruta().isBlank()) {
            throw new BadRequestException("La ruta es obligatoria");
        }
        if (request.tamano() == null || request.tamano() <= 0) {
            throw new BadRequestException("El tamaño es obligatorio y debe ser mayor a 0");
        }
        if (request.formato() == null || request.formato().isBlank()) {
            throw new BadRequestException("El formato es obligatorio");
        }
        if (request.usuarioId() == null) {
            throw new BadRequestException("El usuario es obligatorio");
        }
        
        boolean hasReference = request.equipoId() != null || request.averiaId() != null || request.osrId() != null;
        if (!hasReference) {
            throw new BadRequestException("Debe asociar la evidencia a un equipo, avería u OSR");
        }
    }

    private void validateTipo(String tipo) {
        String tipoUpper = tipo.toUpperCase();
        if (!List.of("EQUIPO", "AVERIA", "OSR", "DEVOLUCION").contains(tipoUpper)) {
            throw new BadRequestException("Tipo de evidencia invalido. Valores permitidos: EQUIPO, AVERIA, OSR, DEVOLUCION");
        }
    }

    private void validateImageConstraints(EvidenciaRequest request) {
        int maxSizeBytes = MAX_UPLOAD_SIZE_MB * 1024 * 1024;
        if (request.tamano() > maxSizeBytes) {
            throw new BadRequestException("El archivo excede el tamaño maximo permitido de " + MAX_UPLOAD_SIZE_MB + "MB");
        }

        String formatoUpper = request.formato().toUpperCase();
        if (!ALLOWED_FORMATS.contains(formatoUpper)) {
            throw new BadRequestException("Formato no permitido. Formatos permitidos: JPG, PNG");
        }

        if (request.ancho() != null && request.ancho() > MAX_IMAGE_WIDTH) {
            throw new BadRequestException("El ancho de la imagen excede el maximo permitido de " + MAX_IMAGE_WIDTH + "px");
        }

        if (request.alto() != null && request.alto() > MAX_IMAGE_HEIGHT) {
            throw new BadRequestException("El alto de la imagen excede el maximo permitido de " + MAX_IMAGE_HEIGHT + "px");
        }
    }

    private Evidencia findActiveEvidencia(Long id) {
        Evidencia evidencia = (Evidencia) Evidencia.findById(id);
        if (evidencia == null || !evidencia.estadoActivo) {
            throw new NotFoundException("Evidencia no encontrada: " + id);
        }
        return evidencia;
    }

    private Usuario findActiveUsuario(Long id) {
        Usuario usuario = (Usuario) Usuario.findById(id);
        if (usuario == null || !usuario.estadoActivo) {
            throw new BadRequestException("Usuario invalido o no encontrado");
        }
        return usuario;
    }

    private Equipo findActiveEquipo(Long id) {
        Equipo equipo = (Equipo) Equipo.findById(id);
        if (equipo == null || !equipo.estadoActivo) {
            throw new BadRequestException("Equipo invalido o no encontrado");
        }
        return equipo;
    }

    private Averia findActiveAveria(Long id) {
        Averia averia = (Averia) Averia.findById(id);
        if (averia == null || !averia.estadoActivo) {
            throw new BadRequestException("Averia invalida o no encontrada");
        }
        return averia;
    }

    private Osr findActiveOsr(Long id) {
        Osr osr = (Osr) Osr.findById(id);
        if (osr == null || !osr.estadoActivo) {
            throw new BadRequestException("OSR invalido o no encontrado");
        }
        return osr;
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    private EvidenciaResponse toResponse(Evidencia evidencia) {
        return new EvidenciaResponse(
                evidencia.id,
                evidencia.equipo != null ? evidencia.equipo.id : null,
                evidencia.averia != null ? evidencia.averia.id : null,
                evidencia.osr != null ? evidencia.osr.id : null,
                evidencia.tipo,
                evidencia.nombreOriginal,
                evidencia.nombreArchivo,
                evidencia.ruta,
                evidencia.tamano,
                evidencia.ancho,
                evidencia.alto,
                evidencia.formato,
                evidencia.descripcion,
                evidencia.usuario != null ? evidencia.usuario.id : null,
                evidencia.usuario != null ? evidencia.usuario.nombre : null,
                evidencia.fechaCarga,
                evidencia.estadoActivo,
                evidencia.fechaBaja
        );
    }
}
