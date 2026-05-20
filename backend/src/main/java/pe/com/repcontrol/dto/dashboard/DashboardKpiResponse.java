package pe.com.repcontrol.dto.dashboard;

import java.util.List;

public record DashboardKpiResponse(
    int equiposActivos,
    int equiposDisponibles,
    int equiposAveriados,
    int equiposEnMantenimiento,
    int equiposDevueltos,
    int averiasAbiertas,
    int averiasCerradas,
    double tiempoPromedioAtencion,
    double disponibilidad,
    double utilizacion,
    List<KpiProveedorResponse> porProveedor,
    List<KpiTipoResponse> porTipo
) {}
