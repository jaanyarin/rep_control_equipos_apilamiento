package pe.com.repcontrol.equipos.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.averias.entity.Averia;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.equipos.entity.Equipo;
import pe.com.repcontrol.equipos.entity.Proveedor;
import pe.com.repcontrol.equipos.entity.TipoEquipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EquipoService {

    public PagedResponse<Equipo> listAll(String estado, Long campanaId, Long proveedorId, String filter, Long tipoEquipoId, int page, int pageSize) {
        var clauses = new ArrayList<String>();
        var params = new HashMap<String, Object>();
        clauses.add("estadoActivo = true");
        if (estado != null) { clauses.add("estado = :estado"); params.put("estado", estado); }
        if (campanaId != null) { clauses.add("campana_id = :campanaId"); params.put("campanaId", campanaId); }
        if (proveedorId != null) { clauses.add("proveedor_id = :proveedorId"); params.put("proveedorId", proveedorId); }
        if (tipoEquipoId != null) { clauses.add("tipoEquipo_id = :tipoEquipoId"); params.put("tipoEquipoId", tipoEquipoId); }
        if (filter != null) { clauses.add("(codigo LIKE :filter OR numeroSerie LIKE :filter)"); params.put("filter", "%" + filter + "%"); }
        var queryStr = String.join(" AND ", clauses);
        var panacheQuery = Equipo.find(queryStr, params);
        var total = panacheQuery.count();
        @SuppressWarnings("unchecked")
        List<Equipo> items = (List<Equipo>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
        return PagedResponse.of(items, total, page, pageSize);
    }

    public Equipo findById(Long id) {
        return Equipo.<Equipo>findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Equipo", id));
    }

    public Map<String, Object> findByIdWithHistory(Long id) {
        Equipo equipo = findById(id);
        List<Averia> historico = Averia.find("equipo = ?1 AND estadoActivo = true ORDER BY fechaCreacion DESC", equipo)
            .page(Page.of(0, 10)).list();
        return Map.of("equipo", equipo, "historico", historico);
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
