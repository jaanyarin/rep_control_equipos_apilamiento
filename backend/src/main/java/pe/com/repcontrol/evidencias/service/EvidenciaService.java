package pe.com.repcontrol.evidencias.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.evidencias.entity.Evidencia;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class EvidenciaService {

  public PagedResponse<Evidencia> listAll(Long equipoId, Long averiaId, Long osrId, String tipo, int page, int pageSize) {
    var clauses = new ArrayList<String>();
    var params = new HashMap<String, Object>();
    clauses.add("estadoActivo = true");
    if (equipoId != null) { clauses.add("equipoId = :equipoId"); params.put("equipoId", equipoId); }
    if (averiaId != null) { clauses.add("averiaId = :averiaId"); params.put("averiaId", averiaId); }
    if (osrId != null) { clauses.add("osrId = :osrId"); params.put("osrId", osrId); }
    if (tipo != null) { clauses.add("tipo = :tipo"); params.put("tipo", tipo); }
    var queryStr = String.join(" AND ", clauses) + " ORDER BY fechaCarga DESC";
    var panacheQuery = Evidencia.find(queryStr, params);
    var total = panacheQuery.count();
    @SuppressWarnings("unchecked")
    List<Evidencia> items = (List<Evidencia>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
    return PagedResponse.of(items, total, page, pageSize);
  }

  public Evidencia findById(Long id) {
    return Evidencia.<Evidencia>findByIdOptional(id)
        .orElseThrow(() -> new ResourceNotFoundException("Evidencia", id));
  }

  @Transactional
  public Evidencia create(Evidencia evidencia) {
    evidencia.persist();
    return evidencia;
  }

  @Transactional
  public void delete(Long id) {
    Evidencia evidencia = findById(id);
    evidencia.estadoActivo = false;
    evidencia.fechaBaja = LocalDateTime.now();
    evidencia.persist();
  }
}
