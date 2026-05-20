package pe.com.repcontrol.dto.averia;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AveriaResponse(
    Long id,
    String codigo,
    Long equipoId,
    String equipoCodigo,
    Long tipoAveriaId,
    String tipoAveria,
    Long estadoAveriaId,
    String estadoAveria,
    Long proveedorId,
    String proveedor,
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
    String estadoEquipo,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
