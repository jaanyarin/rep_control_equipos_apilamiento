package pe.com.repcontrol.dto.psr;

import java.time.LocalDate;

public record PsrRequest(
    String numero,
    Long campanaId,
    Long sitioId,
    Long motivoId,
    String descripcion,
    LocalDate fechaSolicitud,
    String estado,
    String observaciones,
    Long usuarioCreacionId
) {}