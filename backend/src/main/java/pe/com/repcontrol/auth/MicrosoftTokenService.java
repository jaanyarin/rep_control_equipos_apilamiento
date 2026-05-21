package pe.com.repcontrol.auth;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import pe.com.repcontrol.common.exception.BusinessException;

@ApplicationScoped
public class MicrosoftTokenService {

    @ConfigProperty(name = "microsoft.tenant.id", defaultValue = "04e05ea3-3981-4a7b-9be2-17535cf34b8f")
    String tenantId;

    @ConfigProperty(name = "microsoft.client.id", defaultValue = "2953b82b-0891-41d0-99ec-6d2951f46851")
    String clientId;

    HttpsJwksVerificationKeyResolver keyResolver;

    @PostConstruct
    void init() {
        var jwksUri = "https://login.microsoftonline.com/" + tenantId + "/discovery/v2.0/keys";
        var httpsJwks = new HttpsJwks(jwksUri);
        httpsJwks.setDefaultCacheDuration(86_400_000L);
        keyResolver = new HttpsJwksVerificationKeyResolver(httpsJwks);
    }

    public MicrosoftUser validateIdToken(String idToken) {
        try {
            var consumer = new JwtConsumerBuilder()
                .setVerificationKeyResolver(keyResolver)
                .setExpectedIssuer("https://login.microsoftonline.com/" + tenantId + "/v2.0")
                .setExpectedAudience(clientId)
                .setRequireExpirationTime()
                .setRequireSubject()
                .build();

            JwtClaims claims = consumer.processToClaims(idToken);
            String email = claims.getClaimValue("email", String.class);
            String preferredUsername = claims.getClaimValue("preferred_username", String.class);
            String name = claims.getClaimValue("name", String.class);
            String oid = claims.getClaimValue("oid", String.class);

            return new MicrosoftUser(
                oid != null ? oid : claims.getSubject(),
                email != null ? email : preferredUsername,
                name != null ? name : "Usuario"
            );
        } catch (Exception e) {
            throw new BusinessException("MICROSOFT_TOKEN_INVALID",
                "Token de Microsoft inválido: " + e.getMessage());
        }
    }

    public record MicrosoftUser(String microsoftId, String email, String nombre) {}
}
