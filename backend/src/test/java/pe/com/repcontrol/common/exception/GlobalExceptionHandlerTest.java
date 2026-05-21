package pe.com.repcontrol.common.exception;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldReturn404ForResourceNotFound() {
        var ex = new ResourceNotFoundException("Equipo", 1L);
        var response = handler.toResponse(ex);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldReturn400ForBusinessException() {
        var ex = new BusinessException("bad request", "ERR001");
        var response = handler.toResponse(ex);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldReturn500ForGenericException() {
        var ex = new RuntimeException("unexpected");
        var response = handler.toResponse(ex);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
