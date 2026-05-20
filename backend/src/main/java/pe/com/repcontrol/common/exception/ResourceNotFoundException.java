package pe.com.repcontrol.common.exception;

public class ResourceNotFoundException extends RuntimeException {
  private final String resourceName;
  private final String resourceKey;

  public ResourceNotFoundException(String resourceName, Long resourceId) {
    super(resourceName + " no encontrado con id: " + resourceId);
    this.resourceName = resourceName;
    this.resourceKey = String.valueOf(resourceId);
  }

  public ResourceNotFoundException(String resourceName, String resourceKey) {
    super(resourceName + " no encontrado con clave: " + resourceKey);
    this.resourceName = resourceName;
    this.resourceKey = resourceKey;
  }
}
