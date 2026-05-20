package pe.com.repcontrol.psr.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.psr.entity.PSR;
import java.util.List;

@ApplicationScoped
public class PSRService {

    public List<PSR> listAll() {
        return PSR.listAll();
    }

    public PSR findById(Long id) {
        return PSR.<PSR>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("PSR", id));
    }

    public List<PSR> findByCampana(Long campanaId) {
        return PSR.list("campana_id = ?1 AND estado_activo = 1", campanaId);
    }

    public List<PSR> findByEstado(String estado) {
        return PSR.list("estado = ?1 AND estado_activo = 1", estado);
    }

    @Transactional
    public PSR create(PSR psr) {
        psr.persist();
        return psr;
    }

    @Transactional
    public PSR update(Long id, PSR updated) {
        PSR psr = findById(id);
        psr.numero = updated.numero;
        psr.campana = updated.campana;
        psr.sitio = updated.sitio;
        psr.motivo = updated.motivo;
        psr.descripcion = updated.descripcion;
        psr.fechaSolicitud = updated.fechaSolicitud;
        psr.estado = updated.estado;
        psr.observaciones = updated.observaciones;
        psr.persist();
        return psr;
    }

    @Transactional
    public void delete(Long id) {
        PSR psr = findById(id);
        psr.estadoActivo = false;
        psr.persist();
    }
}
