package pe.com.repcontrol.dto.sitio;

public record SitioRequest(
    String codigo,
    String nombre,
    String descripcion,
    String direccion
) {}
