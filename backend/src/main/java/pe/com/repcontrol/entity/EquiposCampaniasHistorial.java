package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipos_campanias_historial")
public class EquiposCampaniasHistorial extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    public Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id", nullable = false)
    public Campana campana;

    @Column(name = "fecha_inicio", nullable = false, updatable = false)
    public LocalDateTime fechaInicio = LocalDateTime.now();

    @Column(name = "fecha_fin")
    public LocalDateTime fechaFin;

    @Column(length = 255)
    public String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creacion", nullable = false)
    public Usuario usuarioCreacion;

    @Version
    public int version;
}
