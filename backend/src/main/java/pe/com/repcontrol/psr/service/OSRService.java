package pe.com.repcontrol.psr.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.psr.entity.OSR;
import java.util.List;

@ApplicationScoped
public class OSRService {

    public List<OSR> listAll() {
        return OSR.listAll();
    }

    public OSR findById(Long id) {
        return OSR.<OSR>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("OSR", id));
    }

    public List<OSR> findByPSR(Long psrId) {
        return OSR.list("psr_id = ?1 AND estado_activo = 1", psrId);
    }

    public List<OSR> findByEquipo(Long equipoId) {
        return OSR.list("equipo_id = ?1 AND estado_activo = 1", equipoId);
    }

    @Transactional
    public OSR create(OSR osr) {
        osr.persist();
        return osr;
    }

    @Transactional
    public OSR update(Long id, OSR updated) {
        OSR osr = findById(id);
        osr.numero = updated.numero;
        osr.psr = updated.psr;
        osr.equipoId = updated.equipoId;
        osr.fechaAsignacion = updated.fechaAsignacion;
        osr.horaInicio = updated.horaInicio;
        osr.horaFin = updated.horaFin;
        osr.horometroInicio = updated.horometroInicio;
        osr.horometroFin = updated.horometroFin;
        osr.estado = updated.estado;
        osr.observaciones = updated.observaciones;
        osr.persist();
        return osr;
    }
}
