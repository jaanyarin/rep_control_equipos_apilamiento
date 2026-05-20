package pe.com.repcontrol.reportes.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import java.io.ByteArrayOutputStream;

@ApplicationScoped
public class ReporteService {

  private final EntityManager em;

  public ReporteService(EntityManager em) {
    this.em = em;
  }

  public byte[] generateEquipoPdf(Long equipoId) {
    return generatePdf("Reporte de Equipo", "ID: " + equipoId);
  }

  public byte[] generatePsrPdf(Long psrId) {
    return generatePdf("Reporte de PSR", "ID: " + psrId);
  }

  public byte[] generateDamagesPdf(String fechaDesde, String fechaHasta, Long proveedorId, Long estadoAveriaId) {
    return generatePdf("Reporte de Averias",
        "Desde: " + fechaDesde + " Hasta: " + fechaHasta);
  }

  private byte[] generatePdf(String title, String subtitle) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Document document = new Document();
    try {
      PdfWriter.getInstance(document, baos);
      document.open();
      document.add(new Paragraph(title));
      document.add(new Paragraph(subtitle));
    } catch (DocumentException e) {
      throw new RuntimeException("Error generando PDF", e);
    } finally {
      document.close();
    }
    return baos.toByteArray();
  }
}
