package pe.com.repcontrol.auditoria.service;

import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.auditoria.entity.LogAuditoria;
import java.util.List;

@ApplicationScoped
public class AuditoriaService {

  public List<LogAuditoria> listAll(Long usuarioId, String modulo, String accion,
      String tipoEntidad, Long idEntidad, String fechaDesde, String fechaHasta) {
    var query = new StringBuilder("1=1");
    if (usuarioId != null) query.append(" AND usuarioId = ").append(usuarioId);
    if (modulo != null) query.append(" AND modulo = '").append(modulo).append("'");
    if (accion != null) query.append(" AND accion = '").append(accion).append("'");
    if (tipoEntidad != null) query.append(" AND tipoEntidad = '").append(tipoEntidad).append("'");
    if (idEntidad != null) query.append(" AND idEntidad = ").append(idEntidad);
    query.append(" ORDER BY fechaEvento DESC");
    return LogAuditoria.list(query.toString());
  }
}
