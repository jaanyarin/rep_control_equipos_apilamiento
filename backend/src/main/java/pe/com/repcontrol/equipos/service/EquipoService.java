package pe.com.repcontrol.equipos.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.equipos.entity.Equipo;
import pe.com.repcontrol.equipos.entity.Proveedor;
import pe.com.repcontrol.equipos.entity.TipoEquipo;
import java.util.List;

@ApplicationScoped
public class EquipoService {

    public List<Equipo> listAll() {
        return Equipo.listAll();
    }

    public Equipo findById(Long id) {
        return Equipo.<Equipo>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Equipo", id));
    }

    public List<Equipo> findByEstado(String estado) {
        return Equipo.list("estado = ?1 AND estado_activo = 1", estado);
    }

    public List<Equipo> findByCampana(Long campanaId) {
        return Equipo.list("campana_id = ?1 AND estado_activo = 1", campanaId);
    }

    public List<Equipo> findByProveedor(Long proveedorId) {
        return Equipo.list("proveedor_id = ?1 AND estado_activo = 1", proveedorId);
    }

    @Transactional
    public Equipo create(Equipo equipo) {
        equipo.persist();
        return equipo;
    }

    @Transactional
    public Equipo update(Long id, Equipo updated) {
        Equipo equipo = findById(id);
        equipo.codigo = updated.codigo;
        equipo.numeroSerie = updated.numeroSerie;
        equipo.marca = updated.marca;
        equipo.modelo = updated.modelo;
        equipo.estado = updated.estado;
        equipo.horometroActual = updated.horometroActual;
        equipo.observaciones = updated.observaciones;
        equipo.campana = updated.campana;
        equipo.proveedor = updated.proveedor;
        equipo.tipoEquipo = updated.tipoEquipo;
        equipo.marcaObj = updated.marcaObj;
        equipo.bateriaTipo = updated.bateriaTipo;
        equipo.bateriaHoras = updated.bateriaHoras;
        equipo.bateriaVoltaje = updated.bateriaVoltaje;
        equipo.cargadorInfo = updated.cargadorInfo;
        equipo.transformadorInfo = updated.transformadorInfo;
        equipo.fechaIngreso = updated.fechaIngreso;
        equipo.fechaDevolucion = updated.fechaDevolucion;
        equipo.osrId = updated.osrId;
        equipo.persist();
        return equipo;
    }

    @Transactional
    public void delete(Long id) {
        Equipo equipo = findById(id);
        equipo.estadoActivo = false;
        equipo.persist();
    }
}
