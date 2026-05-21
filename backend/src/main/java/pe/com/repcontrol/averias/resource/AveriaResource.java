package pe.com.repcontrol.averias.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.averias.service.AveriaService;
import pe.com.repcontrol.common.dto.ApiResponse;
import java.util.Map;

@Path("/api/v1/damages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AveriaResource {

    private final AveriaService averiaService;

    public AveriaResource(AveriaService averiaService) {
        this.averiaService = averiaService;
    }

    @GET
    public Response listAll(
            @QueryParam("equipo_id") Long equipoId,
            @QueryParam("estado_averia_id") Long estadoAveriaId,
            @QueryParam("proveedor_id") Long proveedorId,
            @QueryParam("tipoAveriaId") Long tipoAveriaId,
            @QueryParam("fechaDesde") String fechaDesde,
            @QueryParam("fechaHasta") String fechaHasta,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        var result = averiaService.listAll(equipoId, estadoAveriaId, proveedorId, tipoAveriaId, fechaDesde, fechaHasta, page, pageSize);
        return Response.ok(ApiResponse.ok("Averías obtenidas", result)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Avería obtenida", averiaService.findById(id))).build();
    }

    @POST
    public Response create(pe.com.repcontrol.averias.entity.Averia averia) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Avería creada correctamente", averiaService.create(averia)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.averias.entity.Averia averia) {
        return Response.ok(ApiResponse.ok("Avería actualizada correctamente", averiaService.update(id, averia))).build();
    }

    @PUT
    @Path("/{id}/close")
    public Response close(@PathParam("id") Long id, Map<String, String> body) {
        var result = averiaService.close(id,
            body.getOrDefault("descripcionAtencion", ""),
            body.getOrDefault("accionCorrectiva", ""),
            body.get("fechaCierre"));
        return Response.ok(ApiResponse.ok("Avería cerrada correctamente", result)).build();
    }
}
