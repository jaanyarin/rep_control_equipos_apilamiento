package pe.com.repcontrol.averias.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.averias.entity.Averia;
import pe.com.repcontrol.averias.entity.EstadoAveria;
import pe.com.repcontrol.common.exception.BusinessException;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import java.util.List;

@ApplicationScoped
public class AveriaService {

    public List<Averia> listAll() {
        return Averia.listAll();
    }

    public Averia findById(Long id) {
        return Averia.<Averia>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Averia", id));
    }

    public List<Averia> findByEquipo(Long equipoId) {
        return Averia.list("equipo_id = ?1 AND estado_activo = 1", equipoId);
    }

    public List<Averia> findByEstado(Long estadoAveriaId) {
        return Averia.list("estado_averia_id = ?1 AND estado_activo = 1", estadoAveriaId);
    }

    public List<Averia> findByProveedor(Long proveedorId) {
        return Averia.list("proveedor_id = ?1 AND estado_activo = 1", proveedorId);
    }

    @Transactional
    public Averia create(Averia averia) {
        averia.persist();
        return averia;
    }

    @Transactional
    public Averia update(Long id, Averia updated) {
        Averia averia = findById(id);
        averia.codigo = updated.codigo;
        averia.equipo = updated.equipo;
        averia.tipoAveria = updated.tipoAveria;
        averia.estadoAveria = updated.estadoAveria;
        averia.proveedor = updated.proveedor;
        averia.descripcionFalla = updated.descripcionFalla;
        averia.descripcionAtencion = updated.descripcionAtencion;
        averia.accionCorrectiva = updated.accionCorrectiva;
        averia.fechaReporte = updated.fechaReporte;
        averia.fechaAtencion = updated.fechaAtencion;
        averia.usuarioReportaId = updated.usuarioReportaId;
        averia.usuarioAtiendeId = updated.usuarioAtiendeId;
        averia.horasInactivo = updated.horasInactivo;
        averia.observaciones = updated.observaciones;
        averia.persist();
        return averia;
    }

    @Transactional
    public Averia close(Long id, String descripcionAtencion, String accionCorrectiva) {
        Averia averia = findById(id);
        averia.descripcionAtencion = descripcionAtencion;
        averia.accionCorrectiva = accionCorrectiva;
        averia.fechaCierre = java.time.LocalDateTime.now();
        EstadoAveria cerrada = EstadoAveria.find("codigo", "CERRADA").firstResult();
        if (cerrada != null) {
            averia.estadoAveria = cerrada;
        }
        averia.persist();
        return averia;
    }
}
