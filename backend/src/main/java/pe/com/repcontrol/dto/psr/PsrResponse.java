package pe.com.repcontrol.dto.psr;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PsrResponse(
    Long id,
    String numero,
    Long campanaId,
    Long sitioId,
    Long motivoId,
    String descripcion,
    LocalDate fechaSolicitud,
    String estado,
    String observaciones,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}