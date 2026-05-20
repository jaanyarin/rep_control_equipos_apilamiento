package pe.com.repcontrol.dto.dashboard;

public record KpiTipoResponse(
    String tipo,
    int total,
    int disponibles
) {}
