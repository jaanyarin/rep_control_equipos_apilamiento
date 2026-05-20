package pe.com.repcontrol.equipos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipos")
public class Equipo extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    public String codigo;

    @Column(name = "numero_serie", nullable = false, length = 100)
    public String numeroSerie;

    @Column(name = "marca", length = 100)
    public String marca;

    @Column(name = "modelo", length = 100)
    public String modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id")
    public pe.com.repcontrol.campanas.entity.Campana campana;

    @Column(name = "osr_id")
    public Long osrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    public Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_equipo_id", nullable = false)
    public TipoEquipo tipoEquipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    public Marca marcaObj;

    @Column(name = "estado", nullable = false, length = 50)
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

    @Column(name = "observaciones", columnDefinition = "TEXT")
    public String observaciones;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @Column(name = "usuario_creacion", nullable = false)
    public Long usuarioCreacion;

    @Column(name = "version", nullable = false)
    public Integer version = 0;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoActivo == null) estadoActivo = true;
        if (estado == null) estado = "DISPONIBLE";
        if (version == null) version = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
