package pe.com.repcontrol.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRefreshRequest(
    @NotBlank(message = "El refresh token es requerido") String refreshToken) {}
