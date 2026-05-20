package pe.com.repcontrol.usuarios.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.usuarios.entity.Usuario;
import java.util.Optional;

@ApplicationScoped
public class UserService {

  public Optional<Usuario> findByCorreo(String correo) {
    return Usuario.find("correo = ?1 AND estado_activo = 1", correo).firstResultOptional();
  }

  public Optional<Usuario> findByIdMicrosoft(String idMicrosoft) {
    return Usuario.find("idMicrosoft = ?1 AND estado_activo = 1", idMicrosoft).firstResultOptional();
  }

  public Usuario findById(Long id) {
    return Usuario.<Usuario>findByIdOptional(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
  }
}
