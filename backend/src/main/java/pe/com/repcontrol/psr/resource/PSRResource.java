package pe.com.repcontrol.psr.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.psr.service.PSRService;

@Path("/api/v1/psr")
@Produces(MediaType.APPLICATION_JSON)
public class PSRResource {

    private final PSRService psrService;

    public PSRResource(PSRService psrService) {
        this.psrService = psrService;
    }

    @GET
    public Response listAll(
            @QueryParam("campana_id") Long campanaId,
            @QueryParam("estado") String estado,
            @QueryParam("sitioId") Long sitioId,
            @QueryParam("filtro") String filtro,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        var result = psrService.listAll(campanaId, estado, sitioId, filtro, page, pageSize);
        return Response.ok(ApiResponse.ok("PSR obtenidos", result)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("PSR obtenido", psrService.findById(id))).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(pe.com.repcontrol.psr.entity.PSR psr) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("PSR creado correctamente", psrService.create(psr)))
            .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.psr.entity.PSR psr) {
        return Response.ok(ApiResponse.ok("PSR actualizado correctamente", psrService.update(id, psr))).build();
    }

    @PUT
    @Path("/{id}/approve")
    public Response approve(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("PSR aprobado correctamente", psrService.approve(id))).build();
    }

    @PUT
    @Path("/{id}/reject")
    public Response reject(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("PSR rechazado correctamente", psrService.reject(id))).build();
    }

    @PUT
    @Path("/{id}/close")
    public Response close(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("PSR cerrado correctamente", psrService.close(id))).build();
    }
}
