package pe.com.repcontrol.auth;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.repcontrol.common.dto.ApiResponse;
import pe.com.repcontrol.auth.dto.AuthLoginRequest;
import pe.com.repcontrol.auth.dto.AuthRefreshRequest;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  private final AuthService authService;

  public AuthResource(AuthService authService) {
    this.authService = authService;
  }

  @POST
  @Path("/login")
  public Response login(@Valid AuthLoginRequest request) {
    return Response.ok(ApiResponse.ok("Login exitoso", authService.login(request))).build();
  }

  @POST
  @Path("/refresh")
  public Response refresh(@Valid AuthRefreshRequest request) {
    return Response.ok(ApiResponse.ok("Token renovado", authService.refresh(request))).build();
  }

  @POST
  @Path("/logout")
  public Response logout(@HeaderParam("Authorization") String authorization) {
    authService.logout();
    return Response.ok(ApiResponse.ok("Sesion cerrada correctamente", null)).build();
  }
}
