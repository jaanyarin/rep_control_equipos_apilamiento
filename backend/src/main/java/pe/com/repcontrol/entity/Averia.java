package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "averias")
public class Averia extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 50)
    public String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    public Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_averia_id", nullable = false)
    public TipoAveria tipoAveria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_averia_id", nullable = false)
    public EstadoAveria estadoAveria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    public Proveedor proveedor;

    @Column(name = "descripcion_falla", nullable = false, columnDefinition = "TEXT")
    public String descripcionFalla;

    @Column(name = "descripcion_atencion", columnDefinition = "TEXT")
    public String descripcionAtencion;

    @Column(name = "accion_correctiva", columnDefinition = "TEXT")
    public String accionCorrectiva;

    @Column(name = "fecha_reporte", nullable = false)
    public LocalDateTime fechaReporte;

    @Column(name = "fecha_atencion")
    public LocalDateTime fechaAtencion;

    @Column(name = "fecha_cierre")
    public LocalDateTime fechaCierre;

    @Column(name = "horas_inactivo", precision = 10, scale = 2)
    public BigDecimal horasInactivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_reporta_id", nullable = false)
    public Usuario usuarioReporta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_atiende_id")
    public Usuario usuarioAtiende;

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
