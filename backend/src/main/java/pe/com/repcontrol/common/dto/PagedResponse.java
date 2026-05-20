package pe.com.repcontrol.common.dto;

import java.util.List;

public record PagedResponse<T>(
    List<T> content,
    long totalElements,
    int totalPages,
    int currentPage,
    int pageSize) {

  public static <T> PagedResponse<T> of(List<T> content, long totalElements, int currentPage, int pageSize) {
    int totalPages = (int) Math.ceil((double) totalElements / pageSize);
    return new PagedResponse<>(content, totalElements, totalPages, currentPage, pageSize);
  }
}
