package pe.com.repcontrol.campana;

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
import pe.com.repcontrol.dto.campana.CampanaRequest;
import pe.com.repcontrol.dto.campana.CampanaResponse;

import java.util.List;

@Path("/api/v1/campanas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CampanaResource {

    private final CampanaService campanaService;

    public CampanaResource(CampanaService campanaService) {
        this.campanaService = campanaService;
    }

    @GET
    public Response list() {
        List<CampanaResponse> campanas = campanaService.listActive();
        return Response.ok(ApiResponse.ok("Campañas recuperadas", campanas)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        CampanaResponse campana = campanaService.findById(id);
        return Response.ok(ApiResponse.ok("Campaña encontrada", campana)).build();
    }

    @POST
    public Response create(CampanaRequest request) {
        CampanaResponse campana = campanaService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Campaña creada", campana))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CampanaRequest request) {
        CampanaResponse campana = campanaService.update(id, request);
        return Response.ok(ApiResponse.ok("Campaña actualizada", campana)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        campanaService.softDelete(id);
        return Response.ok(ApiResponse.ok("Campaña desactivada", null)).build();
    }
}
