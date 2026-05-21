package pe.com.repcontrol.common.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void shouldFormatMessageWithLongId() {
        var ex = new ResourceNotFoundException("Equipo", 42L);
        assertTrue(ex.getMessage().contains("Equipo"));
        assertTrue(ex.getMessage().contains("42"));
    }

    @Test
    void shouldFormatMessageWithStringKey() {
        var ex = new ResourceNotFoundException("Usuario", "test@test.com");
        assertTrue(ex.getMessage().contains("Usuario"));
        assertTrue(ex.getMessage().contains("test@test.com"));
    }
}
