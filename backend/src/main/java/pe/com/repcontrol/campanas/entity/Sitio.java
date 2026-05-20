package pe.com.repcontrol.campanas.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sitios")
public class Sitio extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    public String codigo;

    @Column(name = "nombre", nullable = false, length = 255)
    public String nombre;

    @Column(name = "descripcion", length = 500)
    public String descripcion;

    @Column(name = "direccion", length = 500)
    public String direccion;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @Column(name = "usuario_creacion")
    public Long usuarioCreacion;

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
}
