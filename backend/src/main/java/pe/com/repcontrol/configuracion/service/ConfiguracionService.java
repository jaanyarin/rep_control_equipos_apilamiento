package pe.com.repcontrol.configuracion.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.dto.PagedResponse;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.configuracion.entity.Configuracion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ConfiguracionService {

  public PagedResponse<Configuracion> listAll(String categoria, int page, int pageSize) {
    var clauses = new ArrayList<String>();
    var params = new HashMap<String, Object>();
    clauses.add("1=1");
    if (categoria != null) { clauses.add("categoria = :categoria"); params.put("categoria", categoria); }
    var queryStr = String.join(" AND ", clauses);
    var panacheQuery = Configuracion.find(queryStr, params);
    var total = panacheQuery.count();
    @SuppressWarnings("unchecked")
    List<Configuracion> items = (List<Configuracion>) (List<?>) panacheQuery.page(Page.of(page, pageSize)).list();
    return PagedResponse.of(items, total, page, pageSize);
  }

  public Configuracion findByClave(String clave) {
    Configuracion config = Configuracion.find("clave", clave).firstResult();
    if (config == null) throw new ResourceNotFoundException("Configuracion", clave);
    return config;
  }

  @Transactional
  public Configuracion update(String clave, String valor, String descripcion) {
    Configuracion config = findByClave(clave);
    if (valor != null) config.valor = valor;
    if (descripcion != null) config.descripcion = descripcion;
    config.persist();
    return config;
  }
}
