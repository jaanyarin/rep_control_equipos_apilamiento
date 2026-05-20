package pe.com.repcontrol.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria")
public class LogAuditoria extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;

    @Column(name = "nombre_usuario", length = 255)
    public String nombreUsuario;

    @Column(nullable = false, length = 100)
    public String modulo;

    @Column(nullable = false, length = 50)
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

    @Column(name = "fecha_evento", nullable = false, updatable = false)
    public LocalDateTime fechaEvento = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    public String detalles;
}
