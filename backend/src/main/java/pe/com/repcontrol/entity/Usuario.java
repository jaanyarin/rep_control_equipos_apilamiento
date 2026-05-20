package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "id_microsoft", unique = true)
    public String idMicrosoft;

    @Column(nullable = false, length = 255)
    public String nombre;

    @Column(nullable = false, unique = true, length = 255)
    public String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    public Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitio_id")
    public Sitio sitio;

    @Column(length = 255)
    public String puesto;

    @Column(length = 255)
    public String area;

    @Column(length = 255)
    public String empresa;

    @Column(length = 255)
    public String departamento;

    @Column(length = 255)
    public String ubicacion;

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Column(name = "fecha_baja")
    public LocalDateTime fechaBaja;

    @Version
    public int version;
}
