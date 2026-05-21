package pe.com.repcontrol.campanas.service;

import org.junit.jupiter.api.Test;

class CampanaServiceTest {

    @Test
    void serviceShouldBeInstantiable() {
        assertNotNull(new CampanaService());
    }

    private void assertNotNull(CampanaService service) {
        java.util.Objects.requireNonNull(service);
    }
}
