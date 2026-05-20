package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipos")
public class Equipo extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 50)
    public String codigo;

    @Column(name = "numero_serie", nullable = false, length = 100)
    public String numeroSerie;

    @Column(length = 100)
    public String marca;

    @Column(length = 100)
    public String modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id")
    public Campana campana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "osr_id")
    public Osr osr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    public Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_equipo_id", nullable = false)
    public TipoEquipo tipoEquipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    public Marca marcaRelacion;

    @Column(nullable = false, length = 50)
    public String estado = "DISPONIBLE";

    @Column(name = "bateria_tipo", length = 50)
    public String bateriaTipo;

    @Column(name = "bateria_horas", precision = 5, scale = 2)
    public BigDecimal bateriaHoras;

    @Column(name = "bateria_voltaje", length = 20)
    public String bateriaVoltaje;

    @Column(name = "cargador_info", length = 255)
    public String cargadorInfo;

    @Column(name = "transformador_info", length = 255)
    public String transformadorInfo;

    @Column(name = "fecha_ingreso")
    public LocalDate fechaIngreso;

    @Column(name = "fecha_devolucion")
    public LocalDate fechaDevolucion;

    @Column(name = "horometro_actual", precision = 10, scale = 2)
    public BigDecimal horometroActual;

    @Column(columnDefinition = "TEXT")
    public String observaciones;

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creacion", nullable = false)
    public Usuario usuarioCreacion;

    @Version
    public int version;
}
