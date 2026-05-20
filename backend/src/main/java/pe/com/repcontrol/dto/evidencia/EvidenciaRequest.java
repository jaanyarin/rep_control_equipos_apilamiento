package pe.com.repcontrol.dto.evidencia;

public record EvidenciaRequest(
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
    Long usuarioId
) {}
