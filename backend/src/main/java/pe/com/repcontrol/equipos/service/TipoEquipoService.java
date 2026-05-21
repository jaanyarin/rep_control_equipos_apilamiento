package pe.com.repcontrol.equipos.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.equipos.entity.TipoEquipo;
import java.util.List;

@ApplicationScoped
public class TipoEquipoService {

    public List<TipoEquipo> listAll() {
        return TipoEquipo.listAll();
    }

    public TipoEquipo findById(Long id) {
        return TipoEquipo.<TipoEquipo>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("TipoEquipo", id));
    }

    @Transactional
    public TipoEquipo update(Long id, TipoEquipo updated) {
        TipoEquipo tipoEquipo = findById(id);
        tipoEquipo.codigo = updated.codigo;
        tipoEquipo.nombre = updated.nombre;
        tipoEquipo.categoria = updated.categoria;
        tipoEquipo.tecnologiaBateria = updated.tecnologiaBateria;
        tipoEquipo.requiereHorometro = updated.requiereHorometro;
        tipoEquipo.requiereBateria = updated.requiereBateria;
        tipoEquipo.requiereCargador = updated.requiereCargador;
        tipoEquipo.requiereTransformador = updated.requiereTransformador;
        tipoEquipo.descripcion = updated.descripcion;
        tipoEquipo.persist();
        return tipoEquipo;
    }
}
