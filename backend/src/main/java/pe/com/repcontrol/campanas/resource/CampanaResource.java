package pe.com.repcontrol.campanas.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.campanas.entity.Campana;
import pe.com.repcontrol.campanas.service.CampanaService;
import pe.com.repcontrol.common.dto.ApiResponse;

@Path("/api/v1/campaigns")
@Produces(MediaType.APPLICATION_JSON)
public class CampanaResource {

    private final CampanaService campanaService;

    public CampanaResource(CampanaService campanaService) {
        this.campanaService = campanaService;
    }

    @GET
    public Response listAll(
            @QueryParam("es_activa") Boolean esActiva,
            @QueryParam("sitioId") Long sitioId,
            @QueryParam("estado") String estado,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        var result = campanaService.listAll(sitioId, estado, esActiva, page, pageSize);
        return Response.ok(ApiResponse.ok("Campañas obtenidas", result)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Campaña obtenida", campanaService.findById(id))).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Campana campana) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Campaña creada correctamente", campanaService.create(campana)))
            .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Campana campana) {
        return Response.ok(ApiResponse.ok("Campaña actualizada correctamente", campanaService.update(id, campana))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        campanaService.delete(id);
        return Response.ok(ApiResponse.ok("Campaña desactivada correctamente", null)).build();
    }

    @PUT
    @Path("/{id}/activate")
    public Response activate(@PathParam("id") Long id) {
        Campana campana = campanaService.activate(id);
        return Response.ok(ApiResponse.ok("Campaña activada correctamente", campana)).build();
    }

    @PUT
    @Path("/{id}/close")
    public Response close(@PathParam("id") Long id) {
        Campana campana = campanaService.close(id);
        return Response.ok(ApiResponse.ok("Campaña cerrada correctamente", campana)).build();
    }
}
