package pe.com.repcontrol.averia;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.averia.AveriaCloseRequest;
import pe.com.repcontrol.dto.averia.AveriaRequest;
import pe.com.repcontrol.dto.averia.AveriaResponse;

import java.time.LocalDate;
import java.util.List;

@Path("/api/v1/damages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AveriaResource {

    private final AveriaService averiaService;

    public AveriaResource(AveriaService averiaService) {
        this.averiaService = averiaService;
    }

    @GET
    public Response list(
            @QueryParam("equipo_id") Long equipoId,
            @QueryParam("tipo_averia_id") Long tipoAveriaId,
            @QueryParam("estado_averia_id") Long estadoAveriaId,
            @QueryParam("proveedor_id") Long proveedorId,
            @QueryParam("fechaDesde") LocalDate fechaDesde,
            @QueryParam("fechaHasta") LocalDate fechaHasta) {
        List<AveriaResponse> averias = averiaService.listActive(
                equipoId,
                tipoAveriaId,
                estadoAveriaId,
                proveedorId,
                fechaDesde,
                fechaHasta);
        return Response.ok(ApiResponse.ok("Averias recuperadas", averias)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        AveriaResponse averia = averiaService.findById(id);
        return Response.ok(ApiResponse.ok("Averia encontrada", averia)).build();
    }

    @POST
    public Response create(AveriaRequest request) {
        AveriaResponse averia = averiaService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Averia creada y equipo marcado como averiado", averia))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AveriaRequest request) {
        AveriaResponse averia = averiaService.update(id, request);
        return Response.ok(ApiResponse.ok("Averia actualizada", averia)).build();
    }

    @PUT
    @Path("/{id}/close")
    public Response close(@PathParam("id") Long id, AveriaCloseRequest request) {
        AveriaResponse averia = averiaService.close(id, request);
        return Response.ok(ApiResponse.ok("Averia cerrada correctamente", averia)).build();
    }
}
