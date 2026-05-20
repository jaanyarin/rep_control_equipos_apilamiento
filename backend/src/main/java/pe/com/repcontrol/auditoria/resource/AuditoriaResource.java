package pe.com.repcontrol.auditoria.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auditoria.service.AuditoriaService;
import pe.com.repcontrol.common.dto.ApiResponse;

@Path("/api/v1/audit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuditoriaResource {

  private final AuditoriaService auditoriaService;

  public AuditoriaResource(AuditoriaService auditoriaService) {
    this.auditoriaService = auditoriaService;
  }

  @GET
  @Path("/logs")
  public Response listLogs(
      @QueryParam("usuarioId") Long usuarioId,
      @QueryParam("modulo") String modulo,
      @QueryParam("accion") String accion,
      @QueryParam("tipoEntidad") String tipoEntidad,
      @QueryParam("idEntidad") Long idEntidad,
      @QueryParam("fechaDesde") String fechaDesde,
      @QueryParam("fechaHasta") String fechaHasta) {
    var logs = auditoriaService.listAll(usuarioId, modulo, accion, tipoEntidad, idEntidad, fechaDesde, fechaHasta);
    return Response.ok(ApiResponse.ok("Registros de auditoria obtenidos", logs)).build();
  }
}
