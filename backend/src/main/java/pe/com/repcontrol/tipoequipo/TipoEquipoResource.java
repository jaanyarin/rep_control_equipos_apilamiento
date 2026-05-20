package pe.com.repcontrol.tipoequipo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.tipoequipo.TipoEquipoRequest;
import pe.com.repcontrol.dto.tipoequipo.TipoEquipoResponse;

import java.util.List;

@Path("/api/v1/tipos-equipos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoEquipoResource {

    private final TipoEquipoService tipoEquipoService;

    public TipoEquipoResource(TipoEquipoService tipoEquipoService) {
        this.tipoEquipoService = tipoEquipoService;
    }

    @GET
    public Response list() {
        List<TipoEquipoResponse> tipos = tipoEquipoService.listActive();
        return Response.ok(ApiResponse.ok("Tipos de equipos recuperados", tipos)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        TipoEquipoResponse tipo = tipoEquipoService.findById(id);
        return Response.ok(ApiResponse.ok("Tipo de equipo encontrado", tipo)).build();
    }

    @POST
    public Response create(TipoEquipoRequest request) {
        TipoEquipoResponse tipo = tipoEquipoService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Tipo de equipo creado", tipo))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TipoEquipoRequest request) {
        TipoEquipoResponse tipo = tipoEquipoService.update(id, request);
        return Response.ok(ApiResponse.ok("Tipo de equipo actualizado", tipo)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        tipoEquipoService.softDelete(id);
        return Response.ok(ApiResponse.ok("Tipo de equipo desactivado", null)).build();
    }
}
