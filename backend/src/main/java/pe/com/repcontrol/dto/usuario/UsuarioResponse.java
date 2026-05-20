package pe.com.repcontrol.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
    Long id,
    String nombre,
    String correo,
    String puesto,
    String area,
    String empresa,
    String departamento,
    String ubicacion,
    String rol,
    Long rolId,
    String sitio,
    Long sitioId,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
