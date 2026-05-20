package pe.com.repcontrol.averias.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "averias")
public class Averia extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    public String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    public pe.com.repcontrol.equipos.entity.Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_averia_id", nullable = false)
    public TipoAveria tipoAveria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_averia_id", nullable = false)
    public EstadoAveria estadoAveria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    public pe.com.repcontrol.equipos.entity.Proveedor proveedor;

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

    @Column(name = "usuario_reporta_id", nullable = false)
    public Long usuarioReportaId;

    @Column(name = "usuario_atiende_id")
    public Long usuarioAtiendeId;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    public String observaciones;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Column(name = "usuario_creacion", nullable = false)
    public Long usuarioCreacion;

    @Column(name = "version", nullable = false)
    public Integer version = 0;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoActivo == null) estadoActivo = true;
        if (version == null) version = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
