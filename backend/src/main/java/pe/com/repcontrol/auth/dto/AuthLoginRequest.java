package pe.com.repcontrol.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
    @NotBlank(message = "El idToken de Microsoft es requerido") String idToken) {}
