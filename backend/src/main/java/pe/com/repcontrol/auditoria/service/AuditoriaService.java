package pe.com.repcontrol.auditoria.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import pe.com.repcontrol.auditoria.entity.LogAuditoria;
import pe.com.repcontrol.common.dto.PagedResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class AuditoriaService {

  public PagedResponse<LogAuditoria> listAll(Long usuarioId, String modulo, String accion,
      String tipoEntidad, Long idEntidad, String fechaDesde, String fechaHasta, int page, int pageSize) {
    var clauses = new ArrayList<String>();
    var params = new HashMap<String, Object>();
    clauses.add("1=1");
    if (usuarioId != null) { clauses.add("usuarioId = :usuarioId"); params.put("usuarioId", usuarioId); }
    if (modulo != null) { clauses.add("modulo = :modulo"); params.put("modulo", modulo); }
    if (accion != null) { clauses.add("accion = :accion"); params.put("accion", accion); }
    if (tipoEntidad != null) { clauses.add("tipoEntidad = :tipoEntidad"); params.put("tipoEntidad", tipoEntidad); }
    if (idEntidad != null) { clauses.add("idEntidad = :idEntidad"); params.put("idEntidad", idEntidad); }
    var queryStr = String.join(" AND ", clauses) + " ORDER BY fechaEvento DESC";
    var panacheQuery = LogAuditoria.find(queryStr, params);
    var total = panacheQuery.count();
    @SuppressWarnings("unchecked")
    List<LogAuditoria> items = (List<LogAuditoria>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
    return PagedResponse.of(items, total, page, pageSize);
  }
}
