package pe.com.repcontrol.common.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void ok_shouldCreateSuccessResponse() {
        var response = ApiResponse.ok("test message", "data");
        assertTrue(response.success());
        assertEquals("test message", response.message());
        assertEquals("data", response.data());
        assertNull(response.errorCode());
        assertNotNull(response.timestamp());
    }

    @Test
    void ok_shouldAcceptNullData() {
        var response = ApiResponse.ok("only message", null);
        assertTrue(response.success());
        assertNull(response.data());
    }

    @Test
    void error_shouldCreateErrorResponse() {
        var response = ApiResponse.error("error message", "ERR001");
        assertFalse(response.success());
        assertEquals("error message", response.message());
        assertNull(response.data());
        assertEquals("ERR001", response.errorCode());
        assertNotNull(response.timestamp());
    }

    @Test
    void error_shouldAcceptNullErrorCode() {
        var response = ApiResponse.error("error message", null);
        assertFalse(response.success());
        assertNull(response.errorCode());
    }
}
