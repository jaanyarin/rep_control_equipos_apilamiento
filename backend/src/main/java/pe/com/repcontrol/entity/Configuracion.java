package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuraciones")
public class Configuracion extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 100)
    public String clave;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String valor;

    @Column(length = 255)
    public String descripcion;

    @Column(nullable = false, length = 50)
    public String tipo;

    @Column(nullable = false, length = 50)
    public String categoria;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;
}
