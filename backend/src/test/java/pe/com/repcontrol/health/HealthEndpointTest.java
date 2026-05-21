package pe.com.repcontrol.health;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthEndpointTest {

    @Test
    void healthEndpointShouldReturnUp() {
        var resource = new HealthResource();
        var response = resource.health();
        assertEquals("UP", response.status());
    }
}
