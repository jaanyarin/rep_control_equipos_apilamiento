package pe.com.repcontrol.configuracion.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuraciones")
public class Configuracion extends PanacheEntity {
    @Column(name = "clave", nullable = false, unique = true, length = 100)
    public String clave;

    @Column(name = "valor", nullable = false, columnDefinition = "TEXT")
    public String valor;

    @Column(name = "descripcion", length = 255)
    public String descripcion;

    @Column(name = "tipo", nullable = false, length = 50)
    public String tipo;

    @Column(name = "categoria", nullable = false, length = 50)
    public String categoria;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
