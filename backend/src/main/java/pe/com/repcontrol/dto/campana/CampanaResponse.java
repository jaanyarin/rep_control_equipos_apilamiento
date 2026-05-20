package pe.com.repcontrol.dto.campana;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CampanaResponse(
    Long id,
    String nombre,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    String estado,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
