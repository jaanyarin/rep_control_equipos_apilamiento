package pe.com.repcontrol.auth;

import java.time.Duration;
import java.util.Set;
import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenService {

  public String generateAccessToken(Long userId, String correo, String nombre, String rol, String sitio) {
    return Jwt.issuer("https://repcontrol.pe")
        .upn(correo)
        .preferredUserName(nombre)
        .subject(userId.toString())
        .groups(Set.of(rol))
        .claim("sitio", sitio)
        .expiresIn(900)
        .sign();
  }

  public String generateRefreshToken(Long userId, String correo) {
    return Jwt.issuer("https://repcontrol.pe")
        .upn(correo)
        .subject(userId.toString())
        .claim("token_type", "refresh")
        .expiresIn(Duration.ofHours(24))
        .sign();
  }
}
