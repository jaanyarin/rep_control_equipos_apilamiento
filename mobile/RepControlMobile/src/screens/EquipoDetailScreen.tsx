import React, {useEffect, useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ActivityIndicator,
  ScrollView,
  TouchableOpacity,
} from 'react-native';
import {equipoService} from '../services';
import {Equipo} from '../types';

const estadoColors: Record<string, string> = {
  DISPONIBLE: '#4caf50',
  OPERATIVO: '#2196f3',
  EN_REPARACION: '#ff9800',
  AVERIADO: '#f44336',
  MANTENIMIENTO: '#9c27b0',
  DEVUELTO: '#607d8b',
  BAJA: '#f44336',
};

interface DetailRowProps {
  label: string;
  value?: string | number | null;
}

const DetailRow: React.FC<DetailRowProps> = ({label, value}) => (
  <View style={styles.detailRow}>
    <Text style={styles.detailLabel}>{label}</Text>
    <Text style={styles.detailValue}>{value ?? '-'}</Text>
  </View>
);

const EquipoDetailScreen: React.FC<{route: any}> = ({route}) => {
  const {equipoId} = route.params;
  const [equipo, setEquipo] = useState<Equipo | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadEquipo = async () => {
      setIsLoading(true);
      try {
        const data = await equipoService.getById(equipoId);
        setEquipo(data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Error al cargar equipo');
      } finally {
        setIsLoading(false);
      }
    };
    loadEquipo();
  }, [equipoId]);

  if (isLoading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#1a73e8" />
      </View>
    );
  }

  if (error || !equipo) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error || 'Equipo no encontrado'}</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.card}>
        <DetailRow label="Código" value={equipo.codigo} />
        <DetailRow label="Número Serie" value={equipo.numeroSerie} />
        <DetailRow label="Marca" value={equipo.marca} />
        <DetailRow label="Modelo" value={equipo.modelo} />
        <DetailRow label="Tipo" value={equipo.tipoEquipo?.nombre} />
        <DetailRow label="Proveedor" value={equipo.proveedor?.razonSocial} />
        <DetailRow label="RUC" value={equipo.proveedor?.ruc} />
        <DetailRow label="Contacto" value={equipo.proveedor?.contactoNombre} />
        <DetailRow label="Teléfono" value={equipo.proveedor?.contactoTelefono} />
      </View>

      <View style={styles.card}>
        <Text style={styles.sectionTitle}>Estado</Text>
        <View style={styles.statusContainer}>
          <View
            style={[
              styles.statusBadge,
              {backgroundColor: estadoColors[equipo.estado] || '#999'},
            ]}>
            <Text style={styles.statusText}>{equipo.estado}</Text>
          </View>
        </View>
        <DetailRow label="Horómetro" value={equipo.horometroActual?.toString()} />
        <DetailRow label="FechaIngreso" value={equipo.fechaIngreso} />
        <DetailRow label="Fecha Devolución" value={equipo.fechaDevolucion} />
      </View>

      {equipo.observaciones && (
        <View style={styles.card}>
          <Text style={styles.sectionTitle}>Observaciones</Text>
          <Text style={styles.observaciones}>{equipo.observaciones}</Text>
        </View>
      )}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {flex: 1, backgroundColor: '#f5f5f5'},
  center: {flex: 1, justifyContent: 'center', alignItems: 'center'},
  card: {
    backgroundColor: '#fff',
    margin: 10,
    borderRadius: 10,
    overflow: 'hidden',
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#333',
    padding: 15,
    paddingBottom: 5,
  },
  detailRow: {
    flexDirection: 'row',
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#f0f0f0',
  },
  detailLabel: {flex: 1, fontSize: 14, color: '#666', fontWeight: '500'},
  detailValue: {flex: 1, fontSize: 14, color: '#333', fontWeight: '600', textAlign: 'right'},
  statusContainer: {paddingHorizontal: 15, paddingBottom: 10},
  statusBadge: {alignSelf: 'flex-start', paddingHorizontal: 12, paddingVertical: 4, borderRadius: 12},
  statusText: {color: '#fff', fontSize: 12, fontWeight: '600'},
  errorText: {color: '#d32f2f', fontSize: 16},
  observaciones: {padding: 15, paddingTop: 5, color: '#666', fontSize: 14, lineHeight: 20},
});

export default EquipoDetailScreen;
