package pe.com.repcontrol.averias.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.averias.service.TipoAveriaService;
import pe.com.repcontrol.common.dto.ApiResponse;

@Path("/api/v1/damage-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoAveriaResource {

    private final TipoAveriaService tipoAveriaService;

    public TipoAveriaResource(TipoAveriaService tipoAveriaService) {
        this.tipoAveriaService = tipoAveriaService;
    }

    @GET
    public Response listAll() {
        return Response.ok(ApiResponse.ok("Tipos de avería obtenidos", tipoAveriaService.listAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Tipo de avería obtenido", tipoAveriaService.findById(id))).build();
    }
}
