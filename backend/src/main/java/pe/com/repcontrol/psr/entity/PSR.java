package pe.com.repcontrol.psr.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "psr")
public class PSR extends PanacheEntity {
    @Column(name = "numero", nullable = false, unique = true, length = 50)
    public String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id", nullable = false)
    public pe.com.repcontrol.campanas.entity.Campana campana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitio_id", nullable = false)
    public pe.com.repcontrol.campanas.entity.Sitio sitio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motivo_id", nullable = false)
    public MotivoPSR motivo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    public String descripcion;

    @Column(name = "fecha_solicitud", nullable = false)
    public LocalDate fechaSolicitud;

    @Column(name = "estado", nullable = false, length = 50)
    public String estado = "ACTIVO";

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
        if (estado == null) estado = "ACTIVO";
        if (version == null) version = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
