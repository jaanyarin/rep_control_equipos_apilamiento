package pe.com.repcontrol;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestProfile(TestSecurityConfig.class)
class AuthIntegrationTest {

    @Test
    void healthEndpointShouldReturnUp() {
        given()
            .when().get("/q/health")
            .then()
            .statusCode(200)
            .body("status", is("UP"));
    }

    @Test
    void loginWithInvalidCodeShouldReturnError() {
        given()
            .contentType("application/json")
            .body("{\"idToken\": \"invalid-token\"}")
            .when().post("/api/v1/auth/login")
            .then()
            .statusCode(400)
            .body("success", is(false));
    }

    @Test
    void loginWithEmptyBodyShouldReturn400() {
        given()
            .contentType("application/json")
            .body("{}")
            .when().post("/api/v1/auth/login")
            .then()
            .statusCode(400);
    }
}
