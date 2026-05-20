package pe.com.repcontrol.auth;

import java.util.UUID;
import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.auth.dto.AuthLoginRequest;
import pe.com.repcontrol.auth.dto.AuthRefreshRequest;
import pe.com.repcontrol.auth.dto.AuthTokenResponse;
import pe.com.repcontrol.auth.dto.AuthUserResponse;

@ApplicationScoped
public class AuthService {

  public AuthTokenResponse login(AuthLoginRequest request) {
    var user = new AuthUserResponse(
        1L,
        "Usuario Demo",
        "usuario.demo@dominioempresa.com",
        "ADMIN",
        "Packing Uva");

    return new AuthTokenResponse(
        generateToken("access", request.authorizationCode()),
        generateToken("refresh", request.redirectUri()),
        900,
        user);
  }

  public AuthTokenResponse refresh(AuthRefreshRequest request) {
    var user = new AuthUserResponse(
        1L,
        "Usuario Demo",
        "usuario.demo@dominioempresa.com",
        "ADMIN",
        "Packing Uva");

    return new AuthTokenResponse(
        generateToken("access", request.refreshToken()),
        request.refreshToken(),
        900,
        user);
  }

  public void logout() {
    // La invalidacion real se conectara cuando exista persistencia de sesiones.
  }

  private String generateToken(String type, String seed) {
    return type + "." + UUID.nameUUIDFromBytes((seed + ":" + type).getBytes());
  }
}
