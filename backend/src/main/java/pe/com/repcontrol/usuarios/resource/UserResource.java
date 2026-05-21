package pe.com.repcontrol.usuarios.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.usuarios.entity.Usuario;
import pe.com.repcontrol.usuarios.service.UserService;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response listAll() {
        return Response.ok(ApiResponse.ok("Usuarios obtenidos", userService.listAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ApiResponse.ok("Usuario obtenido", userService.findById(id))).build();
    }

    @POST
    public Response create(Usuario usuario) {
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.ok("Usuario creado correctamente", userService.create(usuario)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Usuario usuario) {
        return Response.ok(ApiResponse.ok("Usuario actualizado correctamente", userService.update(id, usuario)))
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.ok(ApiResponse.ok("Usuario eliminado correctamente", null)).build();
    }
}
