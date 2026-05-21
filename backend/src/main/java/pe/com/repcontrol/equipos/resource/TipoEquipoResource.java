package pe.com.repcontrol.equipos.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.equipos.service.TipoEquipoService;

@Path("/api/v1/equipment-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoEquipoResource {

    private final TipoEquipoService tipoEquipoService;

    public TipoEquipoResource(TipoEquipoService tipoEquipoService) {
        this.tipoEquipoService = tipoEquipoService;
    }

    @GET
    public Response listAll() {
        return Response.ok(ApiResponse.ok("Tipos de equipo obtenidos", tipoEquipoService.listAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Tipo de equipo obtenido", tipoEquipoService.findById(id))).build();
    }

    @POST
    public Response create(pe.com.repcontrol.equipos.entity.TipoEquipo tipoEquipo) {
        tipoEquipo.persist();
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Tipo de equipo creado correctamente", tipoEquipo))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.equipos.entity.TipoEquipo tipoEquipo) {
        return Response.ok(ApiResponse.ok("Tipo de equipo actualizado correctamente", tipoEquipoService.update(id, tipoEquipo)))
            .build();
    }
}
