package pe.com.repcontrol.dto.dashboard;

import java.util.Map;

public record EquiposPorEstadoResponse(
    Map<String, Integer> equiposPorEstado
) {}
