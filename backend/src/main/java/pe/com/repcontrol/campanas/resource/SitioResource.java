package pe.com.repcontrol.campanas.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.campanas.entity.Sitio;
import pe.com.repcontrol.campanas.service.SitioService;
import pe.com.repcontrol.common.dto.ApiResponse;

@Path("/api/v1/sites")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SitioResource {

    private final SitioService sitioService;

    public SitioResource(SitioService sitioService) {
        this.sitioService = sitioService;
    }

    @GET
    public Response listAll(
            @QueryParam("filtro") String filtro,
            @QueryParam("estado") String estado,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        return Response.ok(ApiResponse.ok("Sitios obtenidos", sitioService.listAll(filtro, estado, page, pageSize))).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Sitio obtenido", sitioService.findById(id))).build();
    }

    @POST
    public Response create(Sitio sitio) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Sitio creado correctamente", sitioService.create(sitio)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Sitio sitio) {
        return Response.ok(ApiResponse.ok("Sitio actualizado correctamente", sitioService.update(id, sitio)))
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        sitioService.delete(id);
        return Response.ok(ApiResponse.ok("Sitio eliminado correctamente", null)).build();
    }
}
