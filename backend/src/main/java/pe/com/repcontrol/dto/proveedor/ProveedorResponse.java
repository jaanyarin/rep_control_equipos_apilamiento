package pe.com.repcontrol.dto.proveedor;

import java.time.LocalDateTime;

public record ProveedorResponse(
    Long id,
    String ruc,
    String razonSocial,
    String nombreComercial,
    String direccion,
    String telefono,
    String correo,
    String contactoNombre,
    String contactoTelefono,
    boolean estadoActivo,
    LocalDateTime fechaCreacion
) {}
