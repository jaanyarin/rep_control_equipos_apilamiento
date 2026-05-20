package pe.com.repcontrol.usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import pe.com.repcontrol.dto.usuario.UsuarioRequest;
import pe.com.repcontrol.dto.usuario.UsuarioResponse;
import pe.com.repcontrol.entity.Rol;
import pe.com.repcontrol.entity.Sitio;
import pe.com.repcontrol.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public List<UsuarioResponse> listActive() {
        return Usuario.find("estadoActivo", true)
                .list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponse findById(Long id) {
        var usuario = (Usuario) Usuario.findById(id);
        if (usuario == null || !usuario.estadoActivo) {
            throw new NotFoundException("Usuario no encontrado: " + id);
        }
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        if (request.correo() == null || request.correo().isBlank()) {
            throw new BadRequestException("El correo es obligatorio");
        }
        if (request.nombre() == null || request.nombre().isBlank()) {
            throw new BadRequestException("El nombre es obligatorio");
        }

        var correoLower = request.correo().trim().toLowerCase();
        if (Usuario.find("correo", correoLower).firstResultOptional().isPresent()) {
            throw new BadRequestException("Ya existe un usuario con el correo indicado");
        }

        var rol = (Rol) Rol.findById(request.rolId());
        if (rol == null || !rol.estadoActivo) {
            throw new BadRequestException("Rol inválido o no encontrado");
        }

        Sitio sitio = null;
        if (request.sitioId() != null) {
            sitio = (Sitio) Sitio.findById(request.sitioId());
            if (sitio == null || !sitio.estadoActivo) {
                throw new BadRequestException("Sitio inválido o no encontrado");
            }
        }

        var usuario = new Usuario();
        usuario.idMicrosoft = correoLower;
        usuario.correo = correoLower;
        usuario.nombre = request.nombre().trim();
        usuario.rol = rol;
        usuario.sitio = sitio;
        usuario.puesto = null;
        usuario.area = null;
        usuario.empresa = null;
        usuario.departamento = null;
        usuario.ubicacion = null;
        usuario.estadoActivo = true;
        usuario.fechaCreacion = LocalDateTime.now();
        usuario.fechaActualizacion = null;
        usuario.fechaBaja = null;

        usuario.persist();
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        var usuario = (Usuario) Usuario.findById(id);
        if (usuario == null || !usuario.estadoActivo) {
            throw new NotFoundException("Usuario no encontrado: " + id);
        }

        if (request.correo() != null && !request.correo().isBlank()) {
            var correoLower = request.correo().trim().toLowerCase();
            var existing = Usuario.find("correo", correoLower).firstResultOptional();
            if (existing.isPresent() && !((Usuario) existing.get()).id.equals(id)) {
                throw new BadRequestException("El correo ya está en uso por otro usuario");
            }
            usuario.correo = correoLower;
            usuario.idMicrosoft = correoLower;
        }

        if (request.nombre() != null && !request.nombre().isBlank()) {
            usuario.nombre = request.nombre().trim();
        }

        if (request.rolId() != null) {
            var rol = (Rol) Rol.findById(request.rolId());
            if (rol == null || !rol.estadoActivo) {
                throw new BadRequestException("Rol inválido o no encontrado");
            }
            usuario.rol = rol;
        }

        if (request.sitioId() != null) {
            var sitio = (Sitio) Sitio.findById(request.sitioId());
            if (sitio == null || !sitio.estadoActivo) {
                throw new BadRequestException("Sitio inválido o no encontrado");
            }
            usuario.sitio = sitio;
        }

        usuario.fechaActualizacion = LocalDateTime.now();
        return toResponse(usuario);
    }

    @Transactional
    public void softDelete(Long id) {
        var usuario = (Usuario) Usuario.findById(id);
        if (usuario == null || !usuario.estadoActivo) {
            throw new NotFoundException("Usuario no encontrado: " + id);
        }
        usuario.estadoActivo = false;
        usuario.fechaBaja = LocalDateTime.now();
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        var rolNombre = usuario.rol != null ? usuario.rol.nombre : null;
        var sitioNombre = usuario.sitio != null ? usuario.sitio.nombre : null;
        var sitioId = usuario.sitio != null ? usuario.sitio.id : null;
        return new UsuarioResponse(
                usuario.id,
                usuario.nombre,
                usuario.correo,
                usuario.puesto,
                usuario.area,
                usuario.empresa,
                usuario.departamento,
                usuario.ubicacion,
                rolNombre,
                usuario.rol != null ? usuario.rol.id : null,
                sitioNombre,
                sitioId,
                usuario.estadoActivo,
                usuario.fechaCreacion
        );
    }
}
