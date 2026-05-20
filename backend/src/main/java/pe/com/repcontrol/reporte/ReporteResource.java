package pe.com.repcontrol.reporte;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.reporte.ReporteService;

import java.time.LocalDate;

@Path("/api/v1/reports/pdf")
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public class ReporteResource {

    @Inject
    ReporteService reporteService;

    @GET
    @Path("/equipment/{id}")
    public Response generateEquipoPdf(@PathParam("id") Long id) {
        return reporteService.generateEquipoPdf(id);
    }

    @GET
    @Path("/psr/{id}")
    public Response generatePsrPdf(@PathParam("id") Long id) {
        return reporteService.generatePsrPdf(id);
    }

    @GET
    @Path("/damages")
    public Response generateDamagesPdf(
            @QueryParam("proveedorId") Long proveedorId,
            @QueryParam("estadoAveriaId") Long estadoAveriaId,
            @QueryParam("fechaDesde") LocalDate fechaDesde,
            @QueryParam("fechaHasta") LocalDate fechaHasta) {
        return reporteService.generateDamagesPdf(proveedorId, estadoAveriaId, fechaDesde, fechaHasta);
    }
}
