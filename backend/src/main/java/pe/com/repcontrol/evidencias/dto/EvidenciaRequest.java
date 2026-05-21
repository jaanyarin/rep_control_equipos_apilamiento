package pe.com.repcontrol.evidencias.dto;

import jakarta.ws.rs.FormParam;

public class EvidenciaRequest {
    @FormParam("tipo")
    public String tipo;

    @FormParam("equipoId")
    public Long equipoId;

    @FormParam("averiaId")
    public Long averiaId;

    @FormParam("osrId")
    public Long osrId;

    @FormParam("descripcion")
    public String descripcion;
}
