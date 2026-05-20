package pe.com.repcontrol.usuario;

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
import pe.com.repcontrol.dto.usuario.UsuarioRequest;
import pe.com.repcontrol.dto.usuario.UsuarioResponse;

import java.util.List;

@Path("/api/v1/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GET
    public Response list() {
        List<UsuarioResponse> usuarios = usuarioService.listActive();
        return Response.ok(ApiResponse.ok("Usuarios recuperados", usuarios)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        UsuarioResponse usuario = usuarioService.findById(id);
        return Response.ok(ApiResponse.ok("Usuario encontrado", usuario)).build();
    }

    @POST
    public Response create(UsuarioRequest request) {
        UsuarioResponse usuario = usuarioService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Usuario creado", usuario))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UsuarioRequest request) {
        UsuarioResponse usuario = usuarioService.update(id, request);
        return Response.ok(ApiResponse.ok("Usuario actualizado", usuario)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        usuarioService.softDelete(id);
        return Response.ok(ApiResponse.ok("Usuario dado de baja", null)).build();
    }
}
