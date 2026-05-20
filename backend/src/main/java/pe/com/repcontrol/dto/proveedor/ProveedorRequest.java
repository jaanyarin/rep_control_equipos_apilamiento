package pe.com.repcontrol.dto.proveedor;

public record ProveedorRequest(
    String ruc,
    String razonSocial,
    String nombreComercial,
    String direccion,
    String telefono,
    String correo,
    String contactoNombre,
    String contactoTelefono,
    Long usuarioCreacionId
) {}
