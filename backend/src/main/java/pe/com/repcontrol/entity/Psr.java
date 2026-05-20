package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "psr")
public class Psr extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 50)
    public String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id", nullable = false)
    public Campana campana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitio_id", nullable = false)
    public Sitio sitio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motivo_id", nullable = false)
    public MotivoPsr motivo;

    @Column(columnDefinition = "TEXT")
    public String descripcion;

    @Column(name = "fecha_solicitud", nullable = false)
    public LocalDate fechaSolicitud;

    @Column(nullable = false, length = 50)
    public String estado = "ACTIVO";

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
