package pe.com.repcontrol.dto.tipoequipo;

public record TipoEquipoResponse(
    Long id,
    String codigo,
    String nombre,
    String categoria,
    String tecnologiaBateria,
    boolean requiereHorometro,
    boolean requiereBateria,
    boolean requiereCargador,
    boolean requiereTransformador,
    String descripcion,
    boolean estadoActivo
) {}
