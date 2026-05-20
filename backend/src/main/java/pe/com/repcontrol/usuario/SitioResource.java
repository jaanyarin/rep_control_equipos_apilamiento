package pe.com.repcontrol.usuario;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.sitio.SitioResponse;
import pe.com.repcontrol.entity.Sitio;

import java.util.List;

@Path("/api/v1/sitios")
@Produces(MediaType.APPLICATION_JSON)
public class SitioResource {

    @GET
    public Response list() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Sitio> sitios = (List<Sitio>) (List) Sitio.find("estadoActivo", true).list();
        var response = sitios.stream()
                .map(sitio -> new SitioResponse(sitio.id, sitio.codigo, sitio.nombre, sitio.direccion, sitio.estadoActivo, sitio.fechaCreacion))
                .toList();
        return Response.ok(ApiResponse.ok("Sitios recuperados", response)).build();
    }
}
