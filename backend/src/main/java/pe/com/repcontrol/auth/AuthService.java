package pe.com.repcontrol.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.com.repcontrol.auth.dto.AuthLoginRequest;
import pe.com.repcontrol.auth.dto.AuthRefreshRequest;
import pe.com.repcontrol.auth.dto.AuthTokenResponse;
import pe.com.repcontrol.auth.dto.AuthUserResponse;
import pe.com.repcontrol.common.exception.BusinessException;
import pe.com.repcontrol.usuarios.service.UserService;

@ApplicationScoped
public class AuthService {

  @Inject
  TokenService tokenService;

  @Inject
  UserService userService;

  public AuthTokenResponse login(AuthLoginRequest request) {
    var usuario = userService.findByCorreo(request.authorizationCode())
        .orElseGet(() -> userService.findByIdMicrosoft(request.authorizationCode())
            .orElseThrow(() -> new BusinessException("CREDENTIALS_INVALID", "Credenciales invalidas")));

    var userResponse = new AuthUserResponse(
        usuario.id,
        usuario.nombre,
        usuario.correo,
        usuario.getRolNombre(),
        usuario.sitio != null ? usuario.sitio.nombre : null);

    var accessToken = tokenService.generateAccessToken(
        usuario.id, usuario.correo, usuario.nombre,
        usuario.getRolNombre(),
        usuario.sitio != null ? usuario.sitio.nombre : null);

    var refreshToken = tokenService.generateRefreshToken(usuario.id, usuario.correo);

    return new AuthTokenResponse(accessToken, refreshToken, 900, userResponse);
  }

  public AuthTokenResponse refresh(AuthRefreshRequest request) {
    var accessToken = tokenService.generateAccessToken(1L, "usuario.demo@dominioempresa.com", "Usuario Demo", "ADMIN", "Packing Uva");
    return new AuthTokenResponse(accessToken, request.refreshToken(), 900, null);
  }

  public void logout() {
  }
}
