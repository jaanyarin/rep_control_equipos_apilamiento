package pe.com.repcontrol.dashboard;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import pe.com.repcontrol.dto.dashboard.*;
import pe.com.repcontrol.entity.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DashboardService {

    @Transactional
    public DashboardKpiResponse getKpis(
            Long campanaId,
            Long sitioId,
            Long proveedorId,
            LocalDate fechaDesde,
            LocalDate fechaHasta) {
        
        // Contar equipos por estado
        long equiposDisponibles = Equipo.count("estadoActivo = ?1 and estado = ?2", true, "DISPONIBLE");
        long equiposOperativos = Equipo.count("estadoActivo = ?1 and estado = ?2", true, "OPERATIVO");
        long equiposAveriados = Equipo.count("estadoActivo = ?1 and estado = ?2", true, "AVERIADO");
        long equiposMantenimiento = Equipo.count("estadoActivo = ?1 and estado = ?2", true, "MANTENIMIENTO");
        long equiposDevueltos = Equipo.count("estadoActivo = ?1 and estado = ?2", true, "DEVUELTO");
        
        int equiposActivos = (int) (equiposDisponibles + equiposOperativos);
        
        // Contar averías por estado
        long averiasAbiertas = Averia.count("estadoActivo = ?1 and estadoAveria.codigo != ?2", true, "CERRADA");
        long averiasCerradas = Averia.count("estadoActivo = ?1 and estadoAveria.codigo = ?2", true, "CERRADA");
        
        // Calcular tiempo promedio de atención (en horas)
        double tiempoPromedioAtencion = calculateTiempoPromedioAtencion();
        
        // Calcular disponibilidad y utilización
        double disponibilidad = calculateDisponibilidad(equiposActivos, equiposAveriados);
        double utilizacion = calculateUtilizacion(equiposActivos, equiposDisponibles);
        
        // KPIs por proveedor
        List<KpiProveedorResponse> porProveedor = getKpisPorProveedor();
        
        // KPIs por tipo
        List<KpiTipoResponse> porTipo = getKpisPorTipo();
        
        return new DashboardKpiResponse(
                equiposActivos,
                (int) equiposDisponibles,
                (int) equiposAveriados,
                (int) equiposMantenimiento,
                (int) equiposDevueltos,
                (int) averiasAbiertas,
                (int) averiasCerradas,
                tiempoPromedioAtencion,
                disponibilidad,
                utilizacion,
                porProveedor,
                porTipo
        );
    }

    @Transactional
    public DashboardMetricsResponse getMetrics() {
        // Contadores generales
        long totalEquipos = Equipo.count("estadoActivo", true);
        long totalCampañas = Campana.count("estadoActivo", true);
        long totalProveedores = Proveedor.count("estadoActivo", true);
        long totalUsuarios = Usuario.count("estadoActivo", true);
        
        // Equipos por estado
        Map<String, Integer> equiposPorEstado = getEquiposPorEstado();
        
        // Averías por tipo
        List<AveriasPorTipoResponse> averiasPorTipo = getAveriasPorTipo();
        
        // Evolución mensual (últimos 6 meses)
        List<EvolucionMensualResponse> evolucionMensual = getEvolucionMensual();
        
        return new DashboardMetricsResponse(
                (int) totalEquipos,
                (int) totalCampañas,
                (int) totalProveedores,
                (int) totalUsuarios,
                equiposPorEstado,
                averiasPorTipo,
                evolucionMensual
        );
    }

    private double calculateTiempoPromedioAtencion() {
        List<Averia> averiasCerradas = Averia.list(
                "estadoActivo = ?1 and estadoAveria.codigo = ?2 and horasInactivo is not null",
                true, "CERRADA");
        
        if (averiasCerradas.isEmpty()) {
            return 0.0;
        }
        
        double sumaHoras = averiasCerradas.stream()
                .mapToDouble(a -> a.horasInactivo != null ? a.horasInactivo.doubleValue() : 0.0)
                .sum();
        
        return sumaHoras / averiasCerradas.size();
    }

    private double calculateDisponibilidad(long equiposActivos, long equiposAveriados) {
        long total = equiposActivos + equiposAveriados;
        if (total == 0) {
            return 0.0;
        }
        return (equiposActivos * 100.0) / total;
    }

    private double calculateUtilizacion(long equiposActivos, long equiposDisponibles) {
        if (equiposActivos == 0) {
            return 0.0;
        }
        return ((equiposActivos - equiposDisponibles) * 100.0) / equiposActivos;
    }

    private List<KpiProveedorResponse> getKpisPorProveedor() {
        List<Proveedor> proveedores = Proveedor.list("estadoActivo", true);
        
        return proveedores.stream().map(proveedor -> {
            long totalEquipos = Equipo.count("estadoActivo = ?1 and proveedor.id = ?2", true, proveedor.id);
            long averias = Averia.count("estadoActivo = ?1 and proveedor.id = ?2", true, proveedor.id);
            
            return new KpiProveedorResponse(
                    proveedor.razonSocial,
                    (int) totalEquipos,
                    (int) averias
            );
        }).collect(Collectors.toList());
    }

    private List<KpiTipoResponse> getKpisPorTipo() {
        List<TipoEquipo> tipos = TipoEquipo.list("estadoActivo", true);
        
        return tipos.stream().map(tipo -> {
            long total = Equipo.count("estadoActivo = ?1 and tipoEquipo.id = ?2", true, tipo.id);
            long disponibles = Equipo.count(
                    "estadoActivo = ?1 and tipoEquipo.id = ?2 and estado = ?3",
                    true, tipo.id, "DISPONIBLE");
            
            return new KpiTipoResponse(
                    tipo.nombre,
                    (int) total,
                    (int) disponibles
            );
        }).collect(Collectors.toList());
    }

    private Map<String, Integer> getEquiposPorEstado() {
        Map<String, Integer> result = new HashMap<>();
        
        String[] estados = {"DISPONIBLE", "OPERATIVO", "AVERIADO", "MANTENIMIENTO", "DEVUELTO"};
        
        for (String estado : estados) {
            long count = Equipo.count("estadoActivo = ?1 and estado = ?2", true, estado);
            result.put(estado, (int) count);
        }
        
        return result;
    }

    private List<AveriasPorTipoResponse> getAveriasPorTipo() {
        List<TipoAveria> tipos = TipoAveria.list("estadoActivo", true);
        
        return tipos.stream().map(tipo -> {
            long count = Averia.count("estadoActivo = ?1 and tipoAveria.id = ?2", true, tipo.id);
            
            return new AveriasPorTipoResponse(
                    tipo.nombre,
                    (int) count
            );
        }).collect(Collectors.toList());
    }

    private List<EvolucionMensualResponse> getEvolucionMensual() {
        List<EvolucionMensualResponse> result = new ArrayList<>();
        
        // Últimos 6 meses
        for (int i = 5; i >= 0; i--) {
            YearMonth yearMonth = YearMonth.now().minusMonths(i);
            String mes = yearMonth.toString(); // Formato: "2026-01"
            
            LocalDate inicioMes = yearMonth.atDay(1);
            LocalDate finMes = yearMonth.atEndOfMonth();
            
            long equipos = Equipo.count(
                    "estadoActivo = ?1 and fechaCreacion >= ?2 and fechaCreacion <= ?3",
                    true, inicioMes.atStartOfDay(), finMes.atTime(23, 59, 59));
            
            long averias = Averia.count(
                    "estadoActivo = ?1 and fechaCreacion >= ?2 and fechaCreacion <= ?3",
                    true, inicioMes.atStartOfDay(), finMes.atTime(23, 59, 59));
            
            result.add(new EvolucionMensualResponse(mes, (int) equipos, (int) averias));
        }
        
        return result;
    }
}
