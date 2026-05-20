package pe.com.repcontrol.equipos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "marcas")
public class Marca extends PanacheEntity {
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    public String nombre;

    @Column(name = "descripcion", length = 255)
    public String descripcion;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoActivo == null) estadoActivo = true;
    }
}
