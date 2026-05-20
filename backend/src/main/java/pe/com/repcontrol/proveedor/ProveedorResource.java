package pe.com.repcontrol.proveedor;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.proveedor.ProveedorRequest;
import pe.com.repcontrol.dto.proveedor.ProveedorResponse;

import java.util.List;

@Path("/api/v1/proveedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProveedorResource {

    private final ProveedorService proveedorService;

    public ProveedorResource(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GET
    public Response list() {
        List<ProveedorResponse> proveedores = proveedorService.listActive();
        return Response.ok(ApiResponse.ok("Proveedores recuperados", proveedores)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        ProveedorResponse proveedor = proveedorService.findById(id);
        return Response.ok(ApiResponse.ok("Proveedor encontrado", proveedor)).build();
    }

    @POST
    public Response create(ProveedorRequest request) {
        ProveedorResponse proveedor = proveedorService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Proveedor creado", proveedor))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ProveedorRequest request) {
        ProveedorResponse proveedor = proveedorService.update(id, request);
        return Response.ok(ApiResponse.ok("Proveedor actualizado", proveedor)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        proveedorService.softDelete(id);
        return Response.ok(ApiResponse.ok("Proveedor desactivado", null)).build();
    }
}
