package pe.com.repcontrol.auditoria.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria")
public class LogAuditoria extends PanacheEntity {
    @Column(name = "usuario_id")
    public Long usuarioId;

    @Column(name = "nombre_usuario", length = 255)
    public String nombreUsuario;

    @Column(name = "modulo", nullable = false, length = 100)
    public String modulo;

    @Column(name = "accion", nullable = false, length = 50)
    public String accion;

    @Column(name = "tipo_entidad", length = 100)
    public String tipoEntidad;

    @Column(name = "id_entidad")
    public Long idEntidad;

    @Column(name = "valor_anterior", columnDefinition = "JSON")
    public String valorAnterior;

    @Column(name = "valor_nuevo", columnDefinition = "JSON")
    public String valorNuevo;

    @Column(name = "ip_cliente", length = 45)
    public String ipCliente;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    public String userAgent;

    @Column(name = "fecha_evento", nullable = false)
    public LocalDateTime fechaEvento;

    @Column(name = "detalles", columnDefinition = "TEXT")
    public String detalles;

    @PrePersist
    protected void onCreate() {
        fechaEvento = LocalDateTime.now();
    }
}
