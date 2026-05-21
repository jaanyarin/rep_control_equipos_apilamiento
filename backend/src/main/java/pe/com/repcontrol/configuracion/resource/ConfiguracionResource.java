package pe.com.repcontrol.configuracion.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.configuracion.service.ConfiguracionService;
import java.util.Map;

@Path("/api/v1/configurations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfiguracionResource {

  private final ConfiguracionService configuracionService;

  public ConfiguracionResource(ConfiguracionService configuracionService) {
    this.configuracionService = configuracionService;
  }

  @GET
  public Response listAll(
      @QueryParam("categoria") String categoria,
      @DefaultValue("0") @QueryParam("page") int page,
      @DefaultValue("20") @QueryParam("pageSize") int pageSize) {
    var configs = configuracionService.listAll(categoria, page, pageSize);
    return Response.ok(ApiResponse.ok("Configuraciones obtenidas", configs)).build();
  }

  @GET
  @Path("/{clave}")
  public Response getByClave(@PathParam("clave") String clave) {
    var config = configuracionService.findByClave(clave);
    return Response.ok(ApiResponse.ok("Configuracion obtenida", config)).build();
  }

  @PUT
  @Path("/{clave}")
  public Response update(@PathParam("clave") String clave, Map<String, String> body) {
    var config = configuracionService.update(clave, body.get("valor"), body.get("descripcion"));
    return Response.ok(ApiResponse.ok("Configuracion actualizada correctamente", config)).build();
  }
}
