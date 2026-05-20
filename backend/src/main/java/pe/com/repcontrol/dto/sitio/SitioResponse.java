package pe.com.repcontrol.dto.sitio;

import java.time.LocalDateTime;

public record SitioResponse(
    Long id,
    String codigo,
    String nombre,
    String direccion,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
