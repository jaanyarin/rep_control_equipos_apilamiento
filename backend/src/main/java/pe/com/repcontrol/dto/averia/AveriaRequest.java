package pe.com.repcontrol.dto.averia;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AveriaRequest(
    String codigo,
    Long equipoId,
    Long tipoAveriaId,
    Long estadoAveriaId,
    Long proveedorId,
    String descripcionFalla,
    String descripcionAtencion,
    String accionCorrectiva,
    LocalDateTime fechaReporte,
    LocalDateTime fechaAtencion,
    LocalDateTime fechaCierre,
    BigDecimal horasInactivo,
    Long usuarioReportaId,
    Long usuarioAtiendeId,
    String observaciones,
    Long usuarioCreacionId
) {}
