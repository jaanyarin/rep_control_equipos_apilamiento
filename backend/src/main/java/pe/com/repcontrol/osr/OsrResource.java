package pe.com.repcontrol.osr;

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
import pe.com.repcontrol.dto.osr.OsrRequest;
import pe.com.repcontrol.dto.osr.OsrResponse;

import java.util.List;

@Path("/api/v1/osr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OsrResource {

    private final OsrService osrService;

    public OsrResource(OsrService osrService) {
        this.osrService = osrService;
    }

    @GET
    public Response list() {
        List<OsrResponse> osrs = osrService.listActive();
        return Response.ok(ApiResponse.ok("OSR recuperadas", osrs)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        OsrResponse osr = osrService.findById(id);
        return Response.ok(ApiResponse.ok("OSR encontrada", osr)).build();
    }

    @POST
    public Response create(OsrRequest request) {
        OsrResponse osr = osrService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("OSR creada", osr))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, OsrRequest request) {
        OsrResponse osr = osrService.update(id, request);
        return Response.ok(ApiResponse.ok("OSR actualizada", osr)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        osrService.softDelete(id);
        return Response.ok(ApiResponse.ok("OSR desactivada", null)).build();
    }
}
