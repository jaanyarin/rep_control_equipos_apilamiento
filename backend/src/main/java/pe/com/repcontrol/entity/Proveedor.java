package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "proveedores")
public class Proveedor extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 20)
    public String ruc;

    @Column(name = "razon_social", nullable = false, length = 255)
    public String razonSocial;

    @Column(name = "nombre_comercial", length = 255)
    public String nombreComercial;

    @Column(length = 500)
    public String direccion;

    @Column(length = 20)
    public String telefono;

    @Column(length = 255)
    public String correo;

    @Column(name = "contacto_nombre", length = 255)
    public String contactoNombre;

    @Column(name = "contacto_telefono", length = 20)
    public String contactoTelefono;

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
