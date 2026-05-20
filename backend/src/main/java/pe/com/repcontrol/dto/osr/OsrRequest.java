package pe.com.repcontrol.dto.osr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record OsrRequest(
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
    Long usuarioCreacionId
) {}
