package pe.com.repcontrol.evidencias.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.evidencias.entity.Evidencia;
import pe.com.repcontrol.evidencias.service.EvidenciaService;

@Path("/api/v1/evidences")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvidenciaResource {

  private final EvidenciaService evidenciaService;

  public EvidenciaResource(EvidenciaService evidenciaService) {
    this.evidenciaService = evidenciaService;
  }

  @GET
  public Response listAll(
      @QueryParam("equipoId") Long equipoId,
      @QueryParam("averiaId") Long averiaId,
      @QueryParam("osrId") Long osrId,
      @QueryParam("tipo") String tipo) {
    var evidencias = evidenciaService.listAll(equipoId, averiaId, osrId, tipo);
    return Response.ok(ApiResponse.ok("Evidencias obtenidas", evidencias)).build();
  }

  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Long id) {
    var evidencia = evidenciaService.findById(id);
    return Response.ok(ApiResponse.ok("Evidencia obtenida", evidencia)).build();
  }

  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response upload(
      @FormParam("tipo") String tipo,
      @FormParam("equipoId") Long equipoId,
      @FormParam("averiaId") Long averiaId,
      @FormParam("osrId") Long osrId,
      @FormParam("descripcion") String descripcion) {
    Evidencia evidencia = new Evidencia();
    evidencia.tipo = tipo;
    evidencia.equipoId = equipoId;
    evidencia.averiaId = averiaId;
    evidencia.osrId = osrId;
    evidencia.descripcion = descripcion;
    evidencia.nombreOriginal = "upload.bin";
    evidencia.nombreArchivo = "upload.bin";
    evidencia.ruta = "/uploads";
    evidencia.tamano = 0;
    evidencia.formato = "BIN";
    evidencia.usuarioId = 1L;
    var created = evidenciaService.create(evidencia);
    return Response.status(Response.Status.CREATED)
        .entity(ApiResponse.ok("Evidencia cargada correctamente", created))
        .build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    evidenciaService.delete(id);
    return Response.ok(ApiResponse.ok("Evidencia desactivada correctamente", null)).build();
  }
}
