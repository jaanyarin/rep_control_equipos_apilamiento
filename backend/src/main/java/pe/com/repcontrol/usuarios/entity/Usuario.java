package pe.com.repcontrol.usuarios.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntity {
    @Column(name = "id_microsoft", nullable = false, unique = true, length = 255)
    public String idMicrosoft;

    @Column(name = "correo", nullable = false, unique = true, length = 255)
    public String correo;

    @Column(name = "nombre", nullable = false, length = 255)
    public String nombre;

    @Column(name = "puesto", length = 255)
    public String puesto;

    @Column(name = "area", length = 255)
    public String area;

    @Column(name = "empresa", length = 255)
    public String empresa;

    @Column(name = "departamento", length = 255)
    public String departamento;

    @Column(name = "ubicacion", length = 255)
    public String ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    public Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitio_id")
    public pe.com.repcontrol.campanas.entity.Sitio sitio;

    @Column(name = "ultimo_acceso")
    public LocalDateTime ultimoAcceso;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @Column(name = "usuario_creacion", nullable = false)
    public Long usuarioCreacion;

    @Column(name = "usuario_actualizacion")
    public Long usuarioActualizacion;

    @Column(name = "version", nullable = false)
    public Integer version = 0;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoActivo == null) estadoActivo = true;
        if (version == null) version = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public String getRolNombre() {
        return rol != null ? rol.nombre : null;
    }
}
