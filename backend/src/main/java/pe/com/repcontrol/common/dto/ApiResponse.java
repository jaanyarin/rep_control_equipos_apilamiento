package pe.com.repcontrol.common.dto;

import java.time.OffsetDateTime;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    String errorCode,
    OffsetDateTime timestamp) {

  public static <T> ApiResponse<T> ok(String message, T data) {
    return new ApiResponse<>(true, message, data, null, OffsetDateTime.now());
  }

  public static <T> ApiResponse<T> error(String message, String errorCode) {
    return new ApiResponse<>(false, message, null, errorCode, OffsetDateTime.now());
  }
}
