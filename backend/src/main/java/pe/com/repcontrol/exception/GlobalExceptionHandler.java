package pe.com.repcontrol.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import pe.com.repcontrol.auth.dto.ApiResponse;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof jakarta.ws.rs.WebApplicationException wae) {
            return Response.status(wae.getResponse().getStatus())
                .entity(ApiResponse.error(wae.getMessage(), "ERR007"))
                .build();
        }
        exception.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ApiResponse.error(exception.getMessage() != null ? exception.getMessage() : "Error interno del servidor", "ERR006"))
            .build();
    }
}
