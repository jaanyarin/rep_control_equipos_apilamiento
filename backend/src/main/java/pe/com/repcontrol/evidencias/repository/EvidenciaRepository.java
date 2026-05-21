package pe.com.repcontrol.evidencias.repository;

import pe.com.repcontrol.evidencias.entity.Evidencia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EvidenciaRepository implements PanacheRepository<Evidencia> {
}
