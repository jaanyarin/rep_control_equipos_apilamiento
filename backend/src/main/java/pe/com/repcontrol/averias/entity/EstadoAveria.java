package pe.com.repcontrol.averias.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "estados_averias")
public class EstadoAveria extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    public String codigo;

    @Column(name = "nombre", nullable = false, length = 50)
    public String nombre;

    @Column(name = "descripcion", length = 255)
    public String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
