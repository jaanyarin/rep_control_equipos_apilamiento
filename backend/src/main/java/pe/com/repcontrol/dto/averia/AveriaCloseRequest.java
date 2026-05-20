package pe.com.repcontrol.dto.averia;

import java.time.LocalDateTime;

public record AveriaCloseRequest(
    String descripcionAtencion,
    String accionCorrectiva,
    LocalDateTime fechaCierre,
    String observaciones
) {}
