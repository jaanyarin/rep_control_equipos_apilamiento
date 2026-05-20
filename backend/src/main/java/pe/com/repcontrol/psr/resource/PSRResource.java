package pe.com.repcontrol.psr.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.psr.service.PSRService;

@Path("/api/v1/psr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PSRResource {

    private final PSRService psrService;

    public PSRResource(PSRService psrService) {
        this.psrService = psrService;
    }

    @GET
    public Response listAll(
            @QueryParam("campana_id") Long campanaId,
            @QueryParam("estado") String estado) {
        if (campanaId != null) {
            return Response.ok(ApiResponse.ok("PSR obtenidos", psrService.findByCampana(campanaId))).build();
        }
        if (estado != null) {
            return Response.ok(ApiResponse.ok("PSR obtenidos", psrService.findByEstado(estado))).build();
        }
        return Response.ok(ApiResponse.ok("PSR obtenidos", psrService.listAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("PSR obtenido", psrService.findById(id))).build();
    }

    @POST
    public Response create(pe.com.repcontrol.psr.entity.PSR psr) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("PSR creado correctamente", psrService.create(psr)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.psr.entity.PSR psr) {
        return Response.ok(ApiResponse.ok("PSR actualizado correctamente", psrService.update(id, psr))).build();
    }
}
