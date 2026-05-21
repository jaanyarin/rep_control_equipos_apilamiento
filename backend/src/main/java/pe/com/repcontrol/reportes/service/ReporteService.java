package pe.com.repcontrol.reportes.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReporteService {

  private final EntityManager em;

  public ReporteService(EntityManager em) {
    this.em = em;
  }

  public byte[] generateEquipoPdf(Long equipoId) {
    Query query = em.createNativeQuery(
        "SELECT e.codigo, e.marca, e.modelo, e.numero_serie, " +
        "       te.nombre AS tipo_equipo, p.razon_social AS proveedor, " +
        "       c.nombre AS campana, s.nombre AS ubicacion, " +
        "       e.estado, e.horometro_actual, e.observaciones " +
        "FROM equipos e " +
        "LEFT JOIN tipos_equipos te ON e.tipo_equipo_id = te.id " +
        "LEFT JOIN proveedores p ON e.proveedor_id = p.id " +
        "LEFT JOIN campanas c ON e.campana_id = c.id " +
        "LEFT JOIN sitios s ON c.sitio_id = s.id " +
        "WHERE e.id = ?1");
    query.setParameter(1, equipoId);
    Object[] row = (Object[]) query.getSingleResult();

    return buildPdf("Reporte de Equipo", new String[][]{
        {"C\u00f3digo", val(row[0])},
        {"Marca", val(row[1])},
        {"Modelo", val(row[2])},
        {"Serie / Tag", val(row[3])},
        {"Tipo de Equipo", val(row[4])},
        {"Proveedor", val(row[5])},
        {"Campa\u00f1a", val(row[6])},
        {"Ubicaci\u00f3n", val(row[7])},
        {"Estado", val(row[8])},
        {"Hor\u00f3metro Actual", val(row[9])},
        {"Observaciones", val(row[10])},
    });
  }

  public byte[] generatePsrPdf(Long psrId) {
    Query query = em.createNativeQuery(
        "SELECT p.numero, c.nombre AS campana, s.nombre AS sitio, " +
        "       m.nombre AS motivo, p.descripcion, p.fecha_solicitud, " +
        "       p.estado, p.observaciones " +
        "FROM psr p " +
        "LEFT JOIN campanas c ON p.campana_id = c.id " +
        "LEFT JOIN sitios s ON p.sitio_id = s.id " +
        "LEFT JOIN motivos_psr m ON p.motivo_id = m.id " +
        "WHERE p.id = ?1");
    query.setParameter(1, psrId);
    Object[] row = (Object[]) query.getSingleResult();

    return buildPdf("Reporte de PSR", new String[][]{
        {"N\u00famero", val(row[0])},
        {"Campa\u00f1a", val(row[1])},
        {"Sitio", val(row[2])},
        {"Motivo", val(row[3])},
        {"Descripci\u00f3n", val(row[4])},
        {"Fecha de Solicitud", val(row[5])},
        {"Estado", val(row[6])},
        {"Observaciones", val(row[7])},
    });
  }

  @SuppressWarnings("unchecked")
  public byte[] generateDamagesPdf(String fechaDesde, String fechaHasta, Long proveedorId, Long estadoAveriaId) {
    StringBuilder sql = new StringBuilder(
        "SELECT a.codigo, e.codigo AS equipo_codigo, ta.nombre AS tipo_averia, " +
        "       ea.nombre AS estado_averia, p.razon_social AS proveedor, " +
        "       a.descripcion_falla, a.fecha_reporte, a.fecha_atencion, " +
        "       a.fecha_cierre, a.horas_inactivo, a.observaciones " +
        "FROM averias a " +
        "LEFT JOIN equipos e ON a.equipo_id = e.id " +
        "LEFT JOIN tipos_averias ta ON a.tipo_averia_id = ta.id " +
        "LEFT JOIN estados_averias ea ON a.estado_averia_id = ea.id " +
        "LEFT JOIN proveedores p ON a.proveedor_id = p.id " +
        "WHERE a.estado_activo = 1");
    var params = new ArrayList<Object>();

    if (fechaDesde != null && !fechaDesde.isBlank()) {
      sql.append(" AND a.fecha_reporte >= ?").append(params.size() + 1);
      params.add(java.sql.Date.valueOf(fechaDesde));
    }
    if (fechaHasta != null && !fechaHasta.isBlank()) {
      sql.append(" AND a.fecha_reporte <= ?").append(params.size() + 1);
      params.add(java.sql.Date.valueOf(fechaHasta));
    }
    if (proveedorId != null) {
      sql.append(" AND a.proveedor_id = ?").append(params.size() + 1);
      params.add(proveedorId);
    }
    if (estadoAveriaId != null) {
      sql.append(" AND a.estado_averia_id = ?").append(params.size() + 1);
      params.add(estadoAveriaId);
    }

    sql.append(" ORDER BY a.fecha_reporte DESC");

    Query query = em.createNativeQuery(sql.toString());
    for (int i = 0; i < params.size(); i++) {
      query.setParameter(i + 1, params.get(i));
    }
    List<Object[]> rows = query.getResultList();

    StringBuilder subtitle = new StringBuilder("Reporte de Aver\u00edas");
    if (fechaDesde != null) subtitle.append(" | Desde: ").append(fechaDesde);
    if (fechaHasta != null) subtitle.append(" | Hasta: ").append(fechaHasta);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Document document = new Document();
    try {
      PdfWriter.getInstance(document, baos);
      document.open();
      document.add(new Paragraph(subtitle.toString()));
      document.add(new Paragraph(" "));

      if (rows.isEmpty()) {
        document.add(new Paragraph("No se encontraron aver\u00edas con los filtros seleccionados."));
      } else {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        String[] headers = {"C\u00f3digo", "Equipo", "Tipo Aver\u00eda", "Estado", "Proveedor", "Fecha Reporte"};
        for (String h : headers) {
          table.addCell(new PdfPCell(new Paragraph(h)));
        }
        for (Object[] r : rows) {
          table.addCell(val(r[0]));
          table.addCell(val(r[1]));
          table.addCell(val(r[2]));
          table.addCell(val(r[3]));
          table.addCell(val(r[4]));
          table.addCell(r[6] != null ? r[6].toString() : "");
        }
        document.add(table);
      }
    } catch (DocumentException e) {
      throw new RuntimeException("Error generando PDF", e);
    } finally {
      document.close();
    }
    return baos.toByteArray();
  }

  private byte[] buildPdf(String title, String[][] data) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Document document = new Document();
    try {
      PdfWriter.getInstance(document, baos);
      document.open();
      document.add(new Paragraph(title));
      document.add(new Paragraph(" "));
      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(100);
      for (String[] row : data) {
        table.addCell(new PdfPCell(new Paragraph(row[0])));
        table.addCell(new PdfPCell(new Paragraph(row[1] != null ? row[1] : "")));
      }
      document.add(table);
    } catch (DocumentException e) {
      throw new RuntimeException("Error generando PDF", e);
    } finally {
      document.close();
    }
    return baos.toByteArray();
  }

  private String val(Object o) {
    return o != null ? o.toString() : "";
  }
}
