package pe.com.repcontrol.dto.osr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record OsrResponse(
    Long id,
    String numero,
    Long psrId,
    Long equipoId,
    LocalDate fechaAsignacion,
    LocalTime horaInicio,
    LocalTime horaFin,
    BigDecimal horometroInicio,
    BigDecimal horometroFin,
    String estado,
    String observaciones,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
