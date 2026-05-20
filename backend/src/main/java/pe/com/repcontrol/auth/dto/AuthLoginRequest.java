package pe.com.repcontrol.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
    @NotBlank(message = "El código de autorización es requerido") String authorizationCode,
    @NotBlank(message = "La URI de redirección es requerida") String redirectUri) {}
