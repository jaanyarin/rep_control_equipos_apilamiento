package pe.com.repcontrol.reportes.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.reportes.service.ReporteService;

@Path("/api/v1/reports/pdf")
@Produces("application/pdf")
public class ReporteResource {

  private final ReporteService reporteService;

  public ReporteResource(ReporteService reporteService) {
    this.reporteService = reporteService;
  }

  @GET
  @Path("/equipment/{id}")
  public Response generateEquipoPdf(@PathParam("id") Long id) {
    byte[] pdf = reporteService.generateEquipoPdf(id);
    return Response.ok(pdf)
        .header("Content-Disposition", "attachment; filename=equipo_" + id + ".pdf")
        .build();
  }

  @GET
  @Path("/psr/{id}")
  public Response generatePsrPdf(@PathParam("id") Long id) {
    byte[] pdf = reporteService.generatePsrPdf(id);
    return Response.ok(pdf)
        .header("Content-Disposition", "attachment; filename=psr_" + id + ".pdf")
        .build();
  }

  @GET
  @Path("/damages")
  public Response generateDamagesPdf(
      @QueryParam("fechaDesde") String fechaDesde,
      @QueryParam("fechaHasta") String fechaHasta,
      @QueryParam("proveedorId") Long proveedorId,
      @QueryParam("estadoAveriaId") Long estadoAveriaId) {
    byte[] pdf = reporteService.generateDamagesPdf(fechaDesde, fechaHasta, proveedorId, estadoAveriaId);
    return Response.ok(pdf)
        .header("Content-Disposition", "attachment; filename=averias.pdf")
        .build();
  }
}
