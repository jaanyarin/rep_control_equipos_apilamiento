package pe.com.repcontrol.auth.dto;

public record AuthLoginRequest(String authorizationCode, String redirectUri) {}
