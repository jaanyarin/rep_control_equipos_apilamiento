package pe.com.repcontrol.psr.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "osr")
public class OSR extends PanacheEntity {
    @Column(name = "numero", nullable = false, unique = true, length = 50)
    public String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "psr_id", nullable = false)
    public PSR psr;

    @Column(name = "equipo_id")
    public Long equipoId;

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

    @Column(name = "estado", nullable = false, length = 50)
    public String estado = "PENDIENTE";

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
        if (estado == null) estado = "PENDIENTE";
        if (version == null) version = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
