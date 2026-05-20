package pe.com.repcontrol.usuario;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.auth.dto.ApiResponse;
import pe.com.repcontrol.dto.rol.RolResponse;
import pe.com.repcontrol.entity.Rol;

import java.util.List;

@Path("/api/v1/roles")
@Produces(MediaType.APPLICATION_JSON)
public class RolResource {

    @GET
    public Response list() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Rol> roles = (List<Rol>) (List) Rol.find("estadoActivo", true).list();
        var response = roles.stream()
                .map(rol -> new RolResponse(rol.id, rol.nombre, rol.descripcion))
                .toList();
        return Response.ok(ApiResponse.ok("Roles recuperados", response)).build();
    }
}
