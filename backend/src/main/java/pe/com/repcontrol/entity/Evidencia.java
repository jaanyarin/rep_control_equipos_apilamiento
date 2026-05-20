package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidencias")
public class Evidencia extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id")
    public Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "averia_id")
    public Averia averia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "osr_id")
    public Osr osr;

    @Column(nullable = false, length = 50)
    public String tipo;

    @Column(name = "nombre_original", nullable = false, length = 255)
    public String nombreOriginal;

    @Column(name = "nombre_archivo", nullable = false, length = 255)
    public String nombreArchivo;

    @Column(nullable = false, length = 500)
    public String ruta;

    @Column(nullable = false)
    public int tamano;

    public Integer ancho;
    public Integer alto;

    @Column(nullable = false, length = 20)
    public String formato;

    @Column(length = 255)
    public String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    public Usuario usuario;

    @Column(name = "fecha_carga", nullable = false, updatable = false)
    public LocalDateTime fechaCarga = LocalDateTime.now();

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @Version
    public int version;
}
