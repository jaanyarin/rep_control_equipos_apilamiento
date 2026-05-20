package pe.com.repcontrol.equipos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "proveedores")
public class Proveedor extends PanacheEntity {
    @Column(name = "ruc", nullable = false, unique = true, length = 20)
    public String ruc;

    @Column(name = "razon_social", nullable = false, length = 255)
    public String razonSocial;

    @Column(name = "nombre_comercial", length = 255)
    public String nombreComercial;

    @Column(name = "direccion", length = 500)
    public String direccion;

    @Column(name = "telefono", length = 20)
    public String telefono;

    @Column(name = "correo", length = 255)
    public String correo;

    @Column(name = "contacto_nombre", length = 255)
    public String contactoNombre;

    @Column(name = "contacto_telefono", length = 20)
    public String contactoTelefono;

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
        if (version == null) version = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
