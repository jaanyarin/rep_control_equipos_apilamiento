package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipos_averias")
public class TipoAveria extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true, length = 20)
    public String codigo;

    @Column(nullable = false, length = 100)
    public String nombre;

    @Column(length = 255)
    public String descripcion;

    @Column(name = "es_critico", nullable = false)
    public boolean esCritico = false;

    @Column(name = "requiere_evidencia", nullable = false)
    public boolean requiereEvidencia = false;

    @Column(name = "estado_activo", nullable = false)
    public boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    public LocalDateTime fechaCreacion = LocalDateTime.now();
}
