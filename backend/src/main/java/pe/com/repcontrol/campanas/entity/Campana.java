package pe.com.repcontrol.campanas.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "campanas")
public class Campana extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    public String codigo;

    @Column(name = "nombre", nullable = false, length = 255)
    public String nombre;

    @Column(name = "tipo", nullable = false, length = 50)
    public String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitio_id", nullable = false)
    public Sitio sitio;

    @Column(name = "fecha_inicio", nullable = false)
    public LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    public LocalDate fechaFin;

    @Column(name = "estado", nullable = false, length = 50)
    public String estado = "ACTIVA";

    @Column(name = "es_activa", nullable = false)
    public Boolean esActiva = false;

    @Column(name = "descripcion", length = 500)
    public String descripcion;

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
