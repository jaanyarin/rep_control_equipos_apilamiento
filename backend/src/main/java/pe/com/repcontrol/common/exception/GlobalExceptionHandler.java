package pe.com.repcontrol.common.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pe.com.repcontrol.common.dto.ApiResponse;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {

  @Override
  public Response toResponse(RuntimeException exception) {
    if (exception instanceof ResourceNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.error(e.getMessage(), "ERR002"))
          .build();
    }
    if (exception instanceof BusinessException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.error(e.getMessage(), e.getErrorCode()))
          .build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(ApiResponse.error("Error interno del servidor", "ERR006"))
        .build();
  }
}
