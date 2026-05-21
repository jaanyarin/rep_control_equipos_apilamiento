package pe.com.repcontrol;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class TestSecurityConfig implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.ofEntries(
            Map.entry("quarkus.http.port", "19999"),
            Map.entry("quarkus.http.test-port", "19999"),
            Map.entry("mp.jwt.verify.secretkey", "${JWT_SECRET:test-secret-key-for-testing-only-minimum-32-chars!}"),
            Map.entry("quarkus.http.cors.origins", "${CORS_ORIGINS:http://localhost:3000,http://localhost:8081}"),
            Map.entry("quarkus.datasource.db-kind", "h2"),
            Map.entry("quarkus.datasource.jdbc.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
            Map.entry("quarkus.datasource.username", "sa"),
            Map.entry("quarkus.datasource.password", "sa"),
            Map.entry("quarkus.hibernate-orm.database.generation", "drop-and-create"),
            Map.entry("quarkus.flyway.migrate-at-start", "false"),
            Map.entry("quarkus.hibernate-orm.log.sql", "false")
        );
    }
}
