package pe.com.repcontrol.evidencias.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.evidencias.dto.EvidenciaResponse;
import pe.com.repcontrol.evidencias.entity.Evidencia;
import pe.com.repcontrol.evidencias.service.EvidenciaService;
import pe.com.repcontrol.evidencias.service.FileStorageService;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/api/v1/evidences")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvidenciaResource {

    private final EvidenciaService evidenciaService;
    private final FileStorageService fileStorageService;

    @Inject
    JsonWebToken jwt;

    public EvidenciaResource(EvidenciaService evidenciaService, FileStorageService fileStorageService) {
        this.evidenciaService = evidenciaService;
        this.fileStorageService = fileStorageService;
    }

    @GET
    public Response listAll(
            @QueryParam("equipoId") Long equipoId,
            @QueryParam("averiaId") Long averiaId,
            @QueryParam("osrId") Long osrId,
            @QueryParam("tipo") String tipo,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        var evidencias = evidenciaService.listAll(equipoId, averiaId, osrId, tipo, page, pageSize);
        return Response.ok(ApiResponse.ok("Evidencias obtenidas", evidencias)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        var evidencia = evidenciaService.findById(id);
        return Response.ok(ApiResponse.ok("Evidencia obtenida", EvidenciaResponse.from(evidencia))).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({"ADMIN", "USUARIO"})
    public Response upload(
            @FormParam("file") InputStream fileInputStream,
            @FormParam("tipo") String tipo,
            @FormParam("equipoId") Long equipoId,
            @FormParam("averiaId") Long averiaId,
            @FormParam("osrId") Long osrId,
            @FormParam("descripcion") String descripcion) {
        if (fileInputStream == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("El archivo es requerido", "ERR_VALIDATION"))
                    .build();
        }

        byte[] fileBytes;
        try {
            fileBytes = fileInputStream.readAllBytes();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("Error al leer el archivo", "ERR_FILE_READ"))
                    .build();
        }

        if (fileBytes.length > 5 * 1024 * 1024) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("El archivo excede el tamaño máximo de 5MB", "ERR_FILE_SIZE"))
                    .build();
        }

        String formato = detectFormat(fileBytes);
        if (formato == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("Formato de archivo no permitido. Solo: JPG, PNG, GIF, WEBP", "ERR_FORMAT"))
                    .build();
        }

        byte[] processedBytes = compressImage(fileBytes, formato);
        String extension = formato.toLowerCase();
        String ruta;
        try (var is = new ByteArrayInputStream(processedBytes)) {
            ruta = fileStorageService.store(is, extension);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error("Error al guardar el archivo", "ERR_STORAGE"))
                    .build();
        }

        Long usuarioId;
        try {
            usuarioId = Long.parseLong(jwt.getSubject());
        } catch (Exception e) {
            usuarioId = 1L;
        }

        Evidencia evidencia = new Evidencia();
        evidencia.tipo = tipo;
        evidencia.equipoId = equipoId;
        evidencia.averiaId = averiaId;
        evidencia.osrId = osrId;
        evidencia.descripcion = descripcion;
        String fileName = ruta.substring(ruta.lastIndexOf(java.io.File.separator) + 1);
        evidencia.nombreOriginal = fileName;
        evidencia.nombreArchivo = fileName;
        evidencia.ruta = ruta;
        evidencia.tamano = fileBytes.length;
        evidencia.formato = formato;
        evidencia.usuarioId = usuarioId;

        var created = evidenciaService.create(evidencia);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Evidencia cargada correctamente", EvidenciaResponse.from(created)))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response delete(@PathParam("id") Long id) {
        var evidencia = evidenciaService.findById(id);
        fileStorageService.delete(evidencia.ruta);
        evidenciaService.delete(id);
        return Response.ok(ApiResponse.ok("Evidencia desactivada correctamente", null)).build();
    }

    private byte[] compressImage(byte[] imageBytes, String format) {
        if (!"JPEG".equals(format) && !"PNG".equals(format)) {
            return imageBytes;
        }
        try {
            BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (original == null) return imageBytes;

            int maxWidth = 1080;
            int maxHeight = 720;
            int newWidth = original.getWidth();
            int newHeight = original.getHeight();

            if (newWidth > maxWidth || newHeight > maxHeight) {
                double ratio = Math.min((double) maxWidth / newWidth, (double) maxHeight / newHeight);
                newWidth = (int) (newWidth * ratio);
                newHeight = (int) (newHeight * ratio);
            }

            BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resized.createGraphics();
            g.drawImage(original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if ("PNG".equals(format)) {
                ImageIO.write(resized, "PNG", baos);
            } else {
                ImageIO.write(resized, "JPEG", baos);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            return imageBytes;
        }
    }

    private String detectFormat(byte[] bytes) {
        if (bytes.length < 4) return null;
        if (bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF) return "JPEG";
        if (bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x4E && bytes[3] == (byte) 0x47) return "PNG";
        if (bytes[0] == (byte) 0x47 && bytes[1] == (byte) 0x49 && bytes[2] == (byte) 0x46 && bytes[3] == (byte) 0x38) return "GIF";
        if (bytes.length > 12 && bytes[0] == (byte) 0x52 && bytes[1] == (byte) 0x49
                && bytes[2] == (byte) 0x46 && bytes[3] == (byte) 0x46
                && bytes[8] == (byte) 0x57 && bytes[9] == (byte) 0x45
                && bytes[10] == (byte) 0x42 && bytes[11] == (byte) 0x50) return "WEBP";
        return null;
    }
}
