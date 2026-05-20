package pe.com.repcontrol.psr;

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
import pe.com.repcontrol.dto.psr.PsrRequest;
import pe.com.repcontrol.dto.psr.PsrResponse;

import java.util.List;

@Path("/api/v1/psr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PsrResource {

    private final PsrService psrService;

    public PsrResource(PsrService psrService) {
        this.psrService = psrService;
    }

    @GET
    public Response list() {
        List<PsrResponse> psrs = psrService.listActive();
        return Response.ok(ApiResponse.ok("PSR recuperados", psrs)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        PsrResponse psr = psrService.findById(id);
        return Response.ok(ApiResponse.ok("PSR encontrado", psr)).build();
    }

    @POST
    public Response create(PsrRequest request) {
        PsrResponse psr = psrService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("PSR creado", psr))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PsrRequest request) {
        PsrResponse psr = psrService.update(id, request);
        return Response.ok(ApiResponse.ok("PSR actualizado", psr)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        psrService.softDelete(id);
        return Response.ok(ApiResponse.ok("PSR desactivado", null)).build();
    }
}
