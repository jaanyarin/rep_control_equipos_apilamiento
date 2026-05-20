package pe.com.repcontrol.psr.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.psr.service.OSRService;

@Path("/api/v1/osr")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OSRResource {

    private final OSRService osrService;

    public OSRResource(OSRService osrService) {
        this.osrService = osrService;
    }

    @GET
    public Response listAll(
            @QueryParam("psr_id") Long psrId,
            @QueryParam("equipo_id") Long equipoId) {
        if (psrId != null) {
            return Response.ok(ApiResponse.ok("OSR obtenidos", osrService.findByPSR(psrId))).build();
        }
        if (equipoId != null) {
            return Response.ok(ApiResponse.ok("OSR obtenidos", osrService.findByEquipo(equipoId))).build();
        }
        return Response.ok(ApiResponse.ok("OSR obtenidos", osrService.listAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("OSR obtenido", osrService.findById(id))).build();
    }

    @POST
    public Response create(pe.com.repcontrol.psr.entity.OSR osr) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("OSR creado correctamente", osrService.create(osr)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.psr.entity.OSR osr) {
        return Response.ok(ApiResponse.ok("OSR actualizado correctamente", osrService.update(id, osr))).build();
    }
}
