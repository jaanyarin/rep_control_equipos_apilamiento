package pe.com.repcontrol.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.com.repcontrol.auth.dto.AuthLoginRequest;
import pe.com.repcontrol.auth.dto.AuthRefreshRequest;
import pe.com.repcontrol.auth.dto.AuthTokenResponse;
import pe.com.repcontrol.auth.dto.AuthUserResponse;
import pe.com.repcontrol.common.exception.BusinessException;
import pe.com.repcontrol.usuarios.entity.Usuario;
import pe.com.repcontrol.usuarios.service.UserService;

@ApplicationScoped
public class AuthService {

  @Inject
  TokenService tokenService;

  @Inject
  UserService userService;

  @Inject
  MicrosoftTokenService microsoftTokenService;

  public AuthTokenResponse login(AuthLoginRequest request) {
    MicrosoftTokenService.MicrosoftUser msUser =
        microsoftTokenService.validateIdToken(request.idToken());

    Usuario usuario = userService.findByIdMicrosoft(msUser.microsoftId())
        .orElseGet(() -> userService.findByCorreo(msUser.email())
            .orElseThrow(() -> new BusinessException("UNAUTHORIZED",
                "El usuario no está registrado en el sistema. Contacte al administrador.")));

    updateUserFromMicrosoft(usuario, msUser);

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

  private void updateUserFromMicrosoft(Usuario usuario, MicrosoftTokenService.MicrosoftUser msUser) {
    if (msUser.nombre() != null && !msUser.nombre().equals(usuario.nombre)) {
      usuario.nombre = msUser.nombre();
    }
    if (msUser.microsoftId() != null && !msUser.microsoftId().equals(usuario.idMicrosoft)) {
      usuario.idMicrosoft = msUser.microsoftId();
    }
    if (msUser.email() != null && !msUser.email().equals(usuario.correo)) {
      usuario.correo = msUser.email();
    }
    usuario.persist();
  }

  public AuthTokenResponse refresh(AuthRefreshRequest request) {
    var accessToken = tokenService.generateAccessToken(1L, "usuario.demo@dominioempresa.com", "Usuario Demo", "ADMIN", "Packing Uva");
    return new AuthTokenResponse(accessToken, request.refreshToken(), 900, null);
  }

  public void logout() {
  }
}
