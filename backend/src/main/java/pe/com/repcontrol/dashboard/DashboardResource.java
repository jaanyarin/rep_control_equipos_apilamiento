package pe.com.repcontrol.dashboard;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.dashboard.DashboardKpiResponse;
import pe.com.repcontrol.dto.dashboard.DashboardMetricsResponse;

import java.time.LocalDate;

@Path("/api/v1/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    DashboardService dashboardService;

    @GET
    @Path("/kpis")
    public Response getKpis(
            @QueryParam("campanaId") Long campanaId,
            @QueryParam("sitioId") Long sitioId,
            @QueryParam("proveedorId") Long proveedorId,
            @QueryParam("fechaDesde") LocalDate fechaDesde,
            @QueryParam("fechaHasta") LocalDate fechaHasta) {
        
        DashboardKpiResponse kpis = dashboardService.getKpis(
                campanaId,
                sitioId,
                proveedorId,
                fechaDesde,
                fechaHasta);
        
        return Response.ok(ApiResponse.ok("KPIs obtenidos", kpis)).build();
    }

    @GET
    @Path("/metrics")
    public Response getMetrics() {
        DashboardMetricsResponse metrics = dashboardService.getMetrics();
        return Response.ok(ApiResponse.ok("Métricas obtenidas", metrics)).build();
    }
}
