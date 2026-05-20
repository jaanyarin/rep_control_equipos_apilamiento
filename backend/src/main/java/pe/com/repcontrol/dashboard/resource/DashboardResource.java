package pe.com.repcontrol.dashboard.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.dashboard.service.DashboardService;

@Path("/api/v1/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DashboardResource {

  private final DashboardService dashboardService;

  public DashboardResource(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GET
  @Path("/kpis")
  public Response getKPIs(
      @QueryParam("campanaId") Long campanaId,
      @QueryParam("sitioId") Long sitioId,
      @QueryParam("proveedorId") Long proveedorId,
      @QueryParam("fechaDesde") String fechaDesde,
      @QueryParam("fechaHasta") String fechaHasta) {
    var kpis = dashboardService.getKPIs(campanaId, sitioId, proveedorId, fechaDesde, fechaHasta);
    return Response.ok(ApiResponse.ok("KPIs obtenidos", kpis)).build();
  }

  @GET
  @Path("/metrics")
  public Response getMetrics() {
    var metrics = dashboardService.getMetrics();
    return Response.ok(ApiResponse.ok("Metricas obtenidas", metrics)).build();
  }
}
