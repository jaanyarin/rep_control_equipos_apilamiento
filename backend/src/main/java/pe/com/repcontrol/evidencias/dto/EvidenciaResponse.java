package pe.com.repcontrol.evidencias.dto;

import pe.com.repcontrol.evidencias.entity.Evidencia;
import java.time.LocalDateTime;

public record EvidenciaResponse(
    Long id,
    String tipo,
    Long equipoId,
    Long averiaId,
    Long osrId,
    String descripcion,
    String nombreArchivo,
    String formato,
    Integer tamano,
    Long usuarioId,
    LocalDateTime fechaCarga) {

    public static EvidenciaResponse from(Evidencia e) {
        return new EvidenciaResponse(
            e.id, e.tipo, e.equipoId, e.averiaId, e.osrId,
            e.descripcion, e.nombreArchivo, e.formato, e.tamano,
            e.usuarioId, e.fechaCarga);
    }
}
