package pe.com.repcontrol.common.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PagedResponseTest {

    @Test
    void of_shouldCalculateTotalPagesCorrectly() {
        var items = List.of("a", "b", "c", "d", "e");
        var response = PagedResponse.of(items, 25, 1, 10);
        assertEquals(items, response.content());
        assertEquals(25, response.totalElements());
        assertEquals(3, response.totalPages());
        assertEquals(1, response.currentPage());
        assertEquals(10, response.pageSize());
    }

    @Test
    void of_shouldHandleEmptyList() {
        var items = List.of();
        var response = PagedResponse.of(items, 0, 0, 20);
        assertEquals(0, response.totalElements());
        assertEquals(0, response.totalPages());
        assertTrue(response.content().isEmpty());
    }

    @Test
    void of_shouldHandleExactPageSize() {
        var items = List.of(1, 2, 3, 4, 5);
        var response = PagedResponse.of(items, 10, 0, 5);
        assertEquals(2, response.totalPages());
        assertEquals(5, response.pageSize());
    }
}
