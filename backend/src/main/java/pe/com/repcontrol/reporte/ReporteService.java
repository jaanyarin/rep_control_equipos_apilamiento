package pe.com.repcontrol.reporte;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.entity.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReporteService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Transactional
    public Response generateEquipoPdf(Long id) {
        Equipo equipo = findActiveEquipo(id);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Título
            document.add(new Paragraph("REPORTE DE EQUIPO")
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph(" "));
            
            // Información del equipo
            Table table = new Table(2);
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));
            
            addTableRow(table, "Código:", equipo.codigo, font);
            addTableRow(table, "Número de Serie:", equipo.numeroSerie, font);
            addTableRow(table, "Marca:", equipo.marca != null ? equipo.marca : "N/A", font);
            addTableRow(table, "Modelo:", equipo.modelo != null ? equipo.modelo : "N/A", font);
            addTableRow(table, "Estado:", equipo.estado, font);
            addTableRow(table, "Proveedor:", equipo.proveedor != null ? equipo.proveedor.razonSocial : "N/A", font);
            addTableRow(table, "Tipo de Equipo:", equipo.tipoEquipo != null ? equipo.tipoEquipo.nombre : "N/A", font);
            addTableRow(table, "Campaña:", equipo.campana != null ? equipo.campana.nombre : "N/A", font);
            addTableRow(table, "Fecha de Ingreso:", equipo.fechaIngreso != null ? equipo.fechaIngreso.toString() : "N/A", font);
            addTableRow(table, "Horómetro Actual:", equipo.horometroActual != null ? equipo.horometroActual.toString() : "N/A", font);
            
            document.add(table);
            
            // Observaciones
            if (equipo.observaciones != null && !equipo.observaciones.isBlank()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Observaciones:")
                        .setFont(font)
                        .setFontSize(12)
                        .setBold());
                document.add(new Paragraph(equipo.observaciones)
                        .setFont(font)
                        .setFontSize(10));
            }
            
            // Fecha de generación
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Generado: " + LocalDateTime.now().format(DATE_FORMATTER))
                    .setFont(font)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.RIGHT));
            
            document.close();
            
            return Response.ok(outputStream.toByteArray())
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=equipo_" + equipo.codigo + ".pdf")
                    .build();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    @Transactional
    public Response generatePsrPdf(Long id) {
        Psr psr = findActivePsr(id);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Título
            document.add(new Paragraph("REPORTE DE PSR")
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph(" "));
            
            // Información del PSR
            Table table = new Table(2);
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));
            
            addTableRow(table, "Número:", psr.numero, font);
            addTableRow(table, "Campaña:", psr.campana != null ? psr.campana.nombre : "N/A", font);
            addTableRow(table, "Sitio:", psr.sitio != null ? psr.sitio.nombre : "N/A", font);
            addTableRow(table, "Motivo:", psr.motivo != null ? psr.motivo.nombre : "N/A", font);
            addTableRow(table, "Fecha Solicitud:", psr.fechaSolicitud != null ? psr.fechaSolicitud.toString() : "N/A", font);
            addTableRow(table, "Estado:", psr.estado, font);
            
            document.add(table);
            
            // Descripción
            if (psr.descripcion != null && !psr.descripcion.isBlank()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Descripción:")
                        .setFont(font)
                        .setFontSize(12)
                        .setBold());
                document.add(new Paragraph(psr.descripcion)
                        .setFont(font)
                        .setFontSize(10));
            }
            
            // Observaciones
            if (psr.observaciones != null && !psr.observaciones.isBlank()) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Observaciones:")
                        .setFont(font)
                        .setFontSize(12)
                        .setBold());
                document.add(new Paragraph(psr.observaciones)
                        .setFont(font)
                        .setFontSize(10));
            }
            
            // Fecha de generación
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Generado: " + LocalDateTime.now().format(DATE_FORMATTER))
                    .setFont(font)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.RIGHT));
            
            document.close();
            
            return Response.ok(outputStream.toByteArray())
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=psr_" + psr.numero + ".pdf")
                    .build();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    @Transactional
    public Response generateDamagesPdf(Long proveedorId, Long estadoAveriaId, java.time.LocalDate fechaDesde, java.time.LocalDate fechaHasta) {
        StringBuilder query = new StringBuilder("estadoActivo = ?1");
        List<Object> params = new ArrayList<>();
        params.add(true);

        if (proveedorId != null) {
            query.append(" and proveedor.id = ?").append(params.size() + 1);
            params.add(proveedorId);
        }
        if (estadoAveriaId != null) {
            query.append(" and estadoAveria.id = ?").append(params.size() + 1);
            params.add(estadoAveriaId);
        }
        if (fechaDesde != null) {
            query.append(" and fechaReporte >= ?").append(params.size() + 1);
            params.add(fechaDesde.atStartOfDay());
        }
        if (fechaHasta != null) {
            query.append(" and fechaReporte <= ?").append(params.size() + 1);
            params.add(fechaHasta.atTime(23, 59, 59));
        }

        List<Averia> averias = Averia.find(query.toString(), params.toArray()).list();
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Título
            document.add(new Paragraph("REPORTE DE AVERÍAS")
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph(" "));
            
            // Filtros aplicados
            document.add(new Paragraph("Filtros aplicados:")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold());
            
            if (proveedorId != null) {
                Proveedor prov = (Proveedor) Proveedor.findById(proveedorId);
                document.add(new Paragraph("Proveedor: " + (prov != null ? prov.razonSocial : "N/A"))
                        .setFont(font)
                        .setFontSize(10));
            }
            if (estadoAveriaId != null) {
                EstadoAveria estado = (EstadoAveria) EstadoAveria.findById(estadoAveriaId);
                document.add(new Paragraph("Estado: " + (estado != null ? estado.nombre : "N/A"))
                        .setFont(font)
                        .setFontSize(10));
            }
            if (fechaDesde != null || fechaHasta != null) {
                String rango = (fechaDesde != null ? fechaDesde.toString() : "") + 
                              " - " + 
                              (fechaHasta != null ? fechaHasta.toString() : "");
                document.add(new Paragraph("Rango de fechas: " + rango)
                        .setFont(font)
                        .setFontSize(10));
            }
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total de averías: " + averias.size())
                    .setFont(font)
                    .setFontSize(12)
                    .setBold());
            
            // Tabla de averías
            if (!averias.isEmpty()) {
                Table table = new Table(new float[]{1, 2, 2, 2, 2});
                table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));
                
                table.addHeaderCell(createCell("Código", font, true));
                table.addHeaderCell(createCell("Equipo", font, true));
                table.addHeaderCell(createCell("Tipo", font, true));
                table.addHeaderCell(createCell("Fecha Reporte", font, true));
                table.addHeaderCell(createCell("Estado", font, true));
                
                for (Averia averia : averias) {
                    table.addCell(createCell(averia.codigo, font, false));
                    table.addCell(createCell(averia.equipo != null ? averia.equipo.codigo : "N/A", font, false));
                    table.addCell(createCell(averia.tipoAveria != null ? averia.tipoAveria.nombre : "N/A", font, false));
                    table.addCell(createCell(averia.fechaReporte != null ? averia.fechaReporte.format(DATE_FORMATTER) : "N/A", font, false));
                    table.addCell(createCell(averia.estadoAveria != null ? averia.estadoAveria.nombre : "N/A", font, false));
                }
                
                document.add(table);
            }
            
            // Fecha de generación
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Generado: " + LocalDateTime.now().format(DATE_FORMATTER))
                    .setFont(font)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.RIGHT));
            
            document.close();
            
            return Response.ok(outputStream.toByteArray())
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=reporte_averias.pdf")
                    .build();
            
        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    private void addTableRow(Table table, String label, String value, PdfFont font) {
        table.addCell(new Cell().add(new Paragraph(label).setFont(font).setBold()));
        table.addCell(new Cell().add(new Paragraph(value != null ? value : "N/A").setFont(font)));
    }

    private Cell createCell(String text, PdfFont font, boolean isHeader) {
        Cell cell = new Cell().add(new Paragraph(text).setFont(font));
        if (isHeader) {
            cell.setBold();
        }
        return cell;
    }

    private Equipo findActiveEquipo(Long id) {
        Equipo equipo = (Equipo) Equipo.findById(id);
        if (equipo == null || !equipo.estadoActivo) {
            throw new NotFoundException("Equipo no encontrado: " + id);
        }
        return equipo;
    }

    private Psr findActivePsr(Long id) {
        Psr psr = (Psr) Psr.findById(id);
        if (psr == null || !psr.estadoActivo) {
            throw new NotFoundException("PSR no encontrado: " + id);
        }
        return psr;
    }
}
