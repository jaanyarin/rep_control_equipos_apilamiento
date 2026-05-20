package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "motivos_psr")
public class MotivoPsr extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 20)
    public String codigo;

    @Column(nullable = false, length = 100)
    public String nombre;

    @Column(length = 255)
    public String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_equipo_id")
    public TipoEquipo tipoEquipo;

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();
}
