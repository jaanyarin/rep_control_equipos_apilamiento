package pe.com.repcontrol.dto.dashboard;

public record EvolucionMensualResponse(
    String mes,
    int equipos,
    int averias
) {}
