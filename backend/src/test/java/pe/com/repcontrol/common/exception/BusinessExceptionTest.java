package pe.com.repcontrol.common.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    void shouldStoreErrorCode() {
        var ex = new BusinessException("test error", "ERR001");
        assertEquals("test error", ex.getMessage());
        assertEquals("ERR001", ex.getErrorCode());
    }
}
