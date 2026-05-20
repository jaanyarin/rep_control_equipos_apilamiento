package pe.com.repcontrol.averias.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipos_averias")
public class TipoAveria extends PanacheEntity {
    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    public String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    public String nombre;

    @Column(name = "descripcion", length = 255)
    public String descripcion;

    @Column(name = "es_critico", nullable = false)
    public Boolean esCritico = false;

    @Column(name = "requiere_evidencia", nullable = false)
    public Boolean requiereEvidencia = false;

    @Column(name = "estado_activo", nullable = false)
    public Boolean estadoActivo = true;

    @Column(name = "fecha_creacion", nullable = false)
    public LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoActivo == null) estadoActivo = true;
    }
}
