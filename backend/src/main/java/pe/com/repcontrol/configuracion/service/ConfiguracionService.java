package pe.com.repcontrol.configuracion.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.common.exception.ResourceNotFoundException;
import pe.com.repcontrol.configuracion.entity.Configuracion;
import java.util.List;

@ApplicationScoped
public class ConfiguracionService {

  public List<Configuracion> listAll(String categoria) {
    if (categoria != null) {
      return Configuracion.list("categoria = ?1", categoria);
    }
    return Configuracion.listAll();
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
