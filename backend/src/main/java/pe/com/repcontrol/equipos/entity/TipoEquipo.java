package pe.com.repcontrol.equipos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipos_equipos")
public class TipoEquipo extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    public String codigo;

    @Column(name = "nombre", nullable = false, length = 255)
    public String nombre;

    @Column(name = "categoria", nullable = false, length = 50)
    public String categoria;

    @Column(name = "tecnologia_bateria", length = 50)
    public String tecnologiaBateria;

    @Column(name = "requiere_horometro", nullable = false)
    public Boolean requiereHorometro = false;

    @Column(name = "requiere_bateria", nullable = false)
    public Boolean requiereBateria = false;

    @Column(name = "requiere_cargador", nullable = false)
    public Boolean requiereCargador = false;

    @Column(name = "requiere_transformador", nullable = false)
    public Boolean requiereTransformador = false;

    @Column(name = "descripcion", length = 500)
    public String descripcion;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

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
