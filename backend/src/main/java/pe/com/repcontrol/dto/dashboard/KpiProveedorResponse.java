package pe.com.repcontrol.dto.dashboard;

public record KpiProveedorResponse(
    String proveedor,
    int totalEquipos,
    int averias
) {}
