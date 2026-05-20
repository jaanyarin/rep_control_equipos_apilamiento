package pe.com.repcontrol.evidencias.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidencias")
public class Evidencia extends PanacheEntity {
    @Column(name = "equipo_id")
    public Long equipoId;

    @Column(name = "averia_id")
    public Long averiaId;

    @Column(name = "osr_id")
    public Long osrId;

    @Column(name = "tipo", nullable = false, length = 50)
    public String tipo;

    @Column(name = "nombre_original", nullable = false, length = 255)
    public String nombreOriginal;

    @Column(name = "nombre_archivo", nullable = false, length = 255)
    public String nombreArchivo;

    @Column(name = "ruta", nullable = false, length = 500)
    public String ruta;

    @Column(name = "tamano", nullable = false)
    public Integer tamano;

    @Column(name = "ancho")
    public Integer ancho;

    @Column(name = "alto")
    public Integer alto;

    @Column(name = "formato", nullable = false, length = 20)
    public String formato;

    @Column(name = "descripcion", length = 255)
    public String descripcion;

    @Column(name = "usuario_id", nullable = false)
    public Long usuarioId;

    @Column(name = "fecha_carga", nullable = false)
    public LocalDateTime fechaCarga;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @Column(name = "version", nullable = false)
    public Integer version = 0;

    @PrePersist
    protected void onCreate() {
        fechaCarga = LocalDateTime.now();
        if (estadoActivo == null) estadoActivo = true;
        if (version == null) version = 0;
    }
}
