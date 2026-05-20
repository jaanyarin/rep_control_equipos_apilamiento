package pe.com.repcontrol.equipo;

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
import pe.com.repcontrol.dto.equipo.EquipoRequest;
import pe.com.repcontrol.dto.equipo.EquipoResponse;

import java.util.List;

@Path("/api/v1/equipos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EquipoResource {

    private final EquipoService equipoService;

    public EquipoResource(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GET
    public Response list() {
        List<EquipoResponse> equipos = equipoService.listActive();
        return Response.ok(ApiResponse.ok("Equipos recuperados", equipos)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        EquipoResponse equipo = equipoService.findById(id);
        return Response.ok(ApiResponse.ok("Equipo encontrado", equipo)).build();
    }

    @POST
    public Response create(EquipoRequest request) {
        EquipoResponse equipo = equipoService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Equipo creado", equipo))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EquipoRequest request) {
        EquipoResponse equipo = equipoService.update(id, request);
        return Response.ok(ApiResponse.ok("Equipo actualizado", equipo)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        equipoService.softDelete(id);
        return Response.ok(ApiResponse.ok("Equipo desactivado", null)).build();
    }
}
