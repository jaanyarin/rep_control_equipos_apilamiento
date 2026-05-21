package pe.com.repcontrol.equipos.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.equipos.service.EquipoService;

@Path("/api/v1/equipment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EquipoResource {

    private final EquipoService equipoService;

    public EquipoResource(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GET
    public Response listAll(
            @QueryParam("estado") String estado,
            @QueryParam("campana_id") Long campanaId,
            @QueryParam("proveedor_id") Long proveedorId,
            @QueryParam("filter") String filter,
            @QueryParam("tipoEquipoId") Long tipoEquipoId,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        var result = equipoService.listAll(estado, campanaId, proveedorId, filter, tipoEquipoId, page, pageSize);
        return Response.ok(ApiResponse.ok("Equipos obtenidos", result)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        var result = equipoService.findByIdWithHistory(id);
        return Response.ok(ApiResponse.ok("Equipo obtenido", result)).build();
    }

    @POST
    public Response create(pe.com.repcontrol.equipos.entity.Equipo equipo) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Equipo creado correctamente", equipoService.create(equipo)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.equipos.entity.Equipo equipo) {
        return Response.ok(ApiResponse.ok("Equipo actualizado correctamente", equipoService.update(id, equipo))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        equipoService.delete(id);
        return Response.ok(ApiResponse.ok("Equipo desactivado correctamente", null)).build();
    }
}
