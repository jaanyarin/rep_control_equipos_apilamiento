package pe.com.repcontrol.equipos.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.equipos.service.ProveedorService;

@Path("/api/v1/providers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProveedorResource {

    private final ProveedorService proveedorService;

    public ProveedorResource(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GET
    public Response listAll() {
        return Response.ok(ApiResponse.ok("Proveedores obtenidos", proveedorService.listAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Proveedor obtenido", proveedorService.findById(id))).build();
    }
}
