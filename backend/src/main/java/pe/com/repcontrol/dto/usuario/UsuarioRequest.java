package pe.com.repcontrol.dto.usuario;

public record UsuarioRequest(
    String correo,
    String nombre,
    Long rolId,
    Long sitioId
) {}
