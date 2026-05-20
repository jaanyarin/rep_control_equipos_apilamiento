package pe.com.repcontrol.dto.evidencia;

import java.time.LocalDateTime;

public record EvidenciaResponse(
    Long id,
    Long equipoId,
    Long averiaId,
    Long osrId,
    String tipo,
    String nombreOriginal,
    String nombreArchivo,
    String ruta,
    Integer tamano,
    Integer ancho,
    Integer alto,
    String formato,
    String descripcion,
    Long usuarioId,
    String usuarioNombre,
    LocalDateTime fechaCarga,
    boolean estadoActivo,
    LocalDateTime fechaBaja
) {}
