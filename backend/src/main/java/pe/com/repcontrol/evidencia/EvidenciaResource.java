package pe.com.repcontrol.evidencia;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.evidencia.EvidenciaRequest;
import pe.com.repcontrol.dto.evidencia.EvidenciaResponse;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/evidences")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvidenciaResource {

    @Inject
    EvidenciaService evidenciaService;

    @GET
    public Response list(
            @QueryParam("equipoId") Long equipoId,
            @QueryParam("averiaId") Long averiaId,
            @QueryParam("osrId") Long osrId,
            @QueryParam("tipo") String tipo) {
        List<EvidenciaResponse> evidencias = evidenciaService.listActive(
                equipoId,
                averiaId,
                osrId,
                tipo);
        return Response.ok(ApiResponse.ok("Evidencias recuperadas", evidencias)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        EvidenciaResponse evidencia = evidenciaService.findById(id);
        return Response.ok(ApiResponse.ok("Evidencia encontrada", evidencia)).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @RestForm("file") @PartType(MediaType.APPLICATION_OCTET_STREAM) java.io.InputStream file,
            @RestForm("fileName") @PartType(MediaType.TEXT_PLAIN) String fileName,
            @RestForm("fileSize") @PartType(MediaType.TEXT_PLAIN) Long fileSize,
            @RestForm("tipo") @PartType(MediaType.TEXT_PLAIN) String tipo,
            @RestForm("equipoId") @PartType(MediaType.TEXT_PLAIN) Long equipoId,
            @RestForm("averiaId") @PartType(MediaType.TEXT_PLAIN) Long averiaId,
            @RestForm("osrId") @PartType(MediaType.TEXT_PLAIN) Long osrId,
            @RestForm("descripcion") @PartType(MediaType.TEXT_PLAIN) String descripcion,
            @RestForm("usuarioId") @PartType(MediaType.TEXT_PLAIN) Long usuarioId,
            @RestForm("ancho") @PartType(MediaType.TEXT_PLAIN) Integer ancho,
            @RestForm("alto") @PartType(MediaType.TEXT_PLAIN) Integer alto) {
        
        if (file == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("El archivo es obligatorio", "ERR001"))
                    .build();
        }
        if (tipo == null || tipo.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("El tipo de evidencia es obligatorio", "ERR002"))
                    .build();
        }
        if (usuarioId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("El usuario es obligatorio", "ERR003"))
                    .build();
        }

        try {
            String originalFilename = fileName != null ? fileName : "unknown.jpg";
            String extension = originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toUpperCase()
                    : "JPG";
            String uuid = UUID.randomUUID().toString();
            String newFilename = uuid + "." + extension.toLowerCase();
            
            String year = String.valueOf(java.time.LocalDate.now().getYear());
            String month = String.format("%02d", java.time.LocalDate.now().getMonthValue());
            String ruta = "/uploads/" + year + "/" + month + "/" + newFilename;

            EvidenciaRequest request = new EvidenciaRequest(
                    equipoId,
                    averiaId,
                    osrId,
                    tipo,
                    originalFilename,
                    newFilename,
                    ruta,
                    fileSize != null ? fileSize.intValue() : 0,
                    ancho,
                    alto,
                    extension,
                    descripcion,
                    usuarioId
            );

            EvidenciaResponse evidencia = evidenciaService.create(request);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.ok("Evidencia cargada correctamente", evidencia))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error("Error al procesar la evidencia: " + e.getMessage(), "ERR500"))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        EvidenciaResponse evidencia = evidenciaService.delete(id);
        return Response.ok(ApiResponse.ok("Evidencia eliminada correctamente", evidencia)).build();
    }
}
