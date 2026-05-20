package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipos_equipos")
public class TipoEquipo extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 50)
    public String codigo;

    @Column(nullable = false, length = 255)
    public String nombre;

    @Column(nullable = false, length = 50)
    public String categoria;

    @Column(name = "tecnologia_bateria", length = 50)
    public String tecnologiaBateria;

    @Column(name = "requiere_horometro", nullable = false)
    public boolean requiereHorometro = false;

    @Column(name = "requiere_bateria", nullable = false)
    public boolean requiereBateria = false;

    @Column(name = "requiere_cargador", nullable = false)
    public boolean requiereCargador = false;

    @Column(name = "requiere_transformador", nullable = false)
    public boolean requiereTransformador = false;

    @Column(length = 500)
    public String descripcion;

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    public LocalDateTime fechaActualizacion;

    @Version
    public int version;
}
