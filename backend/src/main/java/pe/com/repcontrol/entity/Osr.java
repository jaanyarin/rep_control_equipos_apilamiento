package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "osr")
public class Osr extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 50)
    public String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "psr_id", nullable = false)
    public Psr psr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id")
    public Equipo equipo;

    @Column(name = "fecha_asignacion", nullable = false)
    public LocalDate fechaAsignacion;

    @Column(name = "hora_inicio")
    public LocalTime horaInicio;

    @Column(name = "hora_fin")
    public LocalTime horaFin;

    @Column(name = "horometro_inicio", precision = 10, scale = 2)
    public BigDecimal horometroInicio;

    @Column(name = "horometro_fin", precision = 10, scale = 2)
    public BigDecimal horometroFin;

    @Column(nullable = false, length = 50)
    public String estado = "PENDIENTE";

    @Column(columnDefinition = "TEXT")
    public String observaciones;

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creacion", nullable = false)
    public Usuario usuarioCreacion;

    @Version
    public int version;
}
