package pe.com.repcontrol.dto.dashboard;

import java.util.List;
import java.util.Map;

public record DashboardMetricsResponse(
    int totalEquipos,
    int totalCampañas,
    int totalProveedores,
    int totalUsuarios,
    Map<String, Integer> equiposPorEstado,
    List<AveriasPorTipoResponse> averiasPorTipo,
    List<EvolucionMensualResponse> evolucionMensual
) {}
