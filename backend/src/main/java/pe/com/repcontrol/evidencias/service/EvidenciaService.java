package pe.com.repcontrol.evidencias.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.evidencias.entity.Evidencia;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EvidenciaService {

  public List<Evidencia> listAll(Long equipoId, Long averiaId, Long osrId, String tipo) {
    var query = new StringBuilder("1=1");
    if (equipoId != null) query.append(" AND equipoId = ").append(equipoId);
    if (averiaId != null) query.append(" AND averiaId = ").append(averiaId);
    if (osrId != null) query.append(" AND osrId = ").append(osrId);
    if (tipo != null) query.append(" AND tipo = '").append(tipo).append("'");
    query.append(" ORDER BY fechaCarga DESC");
    return Evidencia.list(query.toString());
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
