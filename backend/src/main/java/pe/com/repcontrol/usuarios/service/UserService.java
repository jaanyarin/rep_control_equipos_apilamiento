package pe.com.repcontrol.usuarios.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.usuarios.entity.Usuario;
import java.time.LocalDateTime;
import java.util.List;
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

  public List<Usuario> listAll() {
    return Usuario.list("estadoActivo = 1 ORDER BY nombre");
  }

  @Transactional
  public Usuario create(Usuario usuario) {
    usuario.persist();
    return usuario;
  }

  @Transactional
  public Usuario update(Long id, Usuario updated) {
    Usuario usuario = findById(id);
    usuario.idMicrosoft = updated.idMicrosoft;
    usuario.correo = updated.correo;
    usuario.nombre = updated.nombre;
    usuario.puesto = updated.puesto;
    usuario.area = updated.area;
    usuario.empresa = updated.empresa;
    usuario.departamento = updated.departamento;
    usuario.ubicacion = updated.ubicacion;
    usuario.rol = updated.rol;
    usuario.sitio = updated.sitio;
    usuario.usuarioActualizacion = updated.usuarioActualizacion;
    usuario.persist();
    return usuario;
  }

  @Transactional
  public void delete(Long id) {
    Usuario usuario = findById(id);
    usuario.estadoActivo = false;
    usuario.fechaBaja = LocalDateTime.now();
    usuario.persist();
  }
}
