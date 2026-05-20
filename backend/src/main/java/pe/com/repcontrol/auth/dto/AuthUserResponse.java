package pe.com.repcontrol.auth.dto;

public record AuthUserResponse(
    Long id,
    String nombre,
    String correo,
    String rol,
    String sitio) {}
