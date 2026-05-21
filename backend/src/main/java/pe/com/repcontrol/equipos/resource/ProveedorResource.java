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
    public Response listAll(
            @QueryParam("filtro") String filtro,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
        return Response.ok(ApiResponse.ok("Proveedores obtenidos", proveedorService.listAll(filtro, page, pageSize))).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Proveedor obtenido", proveedorService.findById(id))).build();
    }

    @POST
    public Response create(pe.com.repcontrol.equipos.entity.Proveedor proveedor) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Proveedor creado correctamente", proveedorService.create(proveedor)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, pe.com.repcontrol.equipos.entity.Proveedor proveedor) {
        return Response.ok(ApiResponse.ok("Proveedor actualizado correctamente", proveedorService.update(id, proveedor)))
            .build();
    }
}
