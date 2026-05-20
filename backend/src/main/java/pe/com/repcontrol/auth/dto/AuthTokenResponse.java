package pe.com.repcontrol.auth.dto;

public record AuthTokenResponse(
    String token,
    String refreshToken,
    int expiresIn,
    AuthUserResponse user) {}
