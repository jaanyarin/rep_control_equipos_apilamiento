package pe.com.repcontrol.dto.equipo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EquipoResponse(
    Long id,
    String codigo,
    String numeroSerie,
    String marca,
    String modelo,
    Long campanaId,
    Long osrId,
    Long proveedorId,
    Long tipoEquipoId,
    Long marcaRelacionId,
    String estado,
    String bateriaTipo,
    BigDecimal bateriaHoras,
    String bateriaVoltaje,
    String cargadorInfo,
    String transformadorInfo,
    LocalDate fechaIngreso,
    LocalDate fechaDevolucion,
    BigDecimal horometroActual,
    String observaciones,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
