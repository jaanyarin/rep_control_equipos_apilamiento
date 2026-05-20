package pe.com.repcontrol.dto.campana;

import java.time.LocalDate;

public record CampanaRequest(
    String nombre,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    Long usuarioCreacionId
) {}
