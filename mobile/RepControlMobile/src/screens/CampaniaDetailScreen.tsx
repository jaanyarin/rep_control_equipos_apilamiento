import React, {useEffect, useState} from 'react';
import {View, Text, StyleSheet, ActivityIndicator, ScrollView, TouchableOpacity} from 'react-native';
import {Campana} from '../types';
import {campaniaService} from '../services';
import {useToast} from '../components/Toast';

const CampaniaDetailScreen: React.FC<{route: any; navigation: any}> = ({route, navigation}) => {
  const {campaniaId} = route.params;
  const [campania, setCampania] = useState<Campana | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [actionLoading, setActionLoading] = useState(false);
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try {
      const data = await campaniaService.getById(campaniaId);
      setCampania(data);
    } catch (err: any) {
      setError(err.message || 'Error al cargar campaña');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, [campaniaId]);

  const handleActivate = async () => {
    setActionLoading(true);
    try {
      await campaniaService.activate(campaniaId);
      toast.show('Campaña activada', 'success');
      load();
    } catch { toast.show('Error al activar campaña', 'error'); }
    finally { setActionLoading(false); }
  };

  const handleClose = async () => {
    setActionLoading(true);
    try {
      await campaniaService.close(campaniaId);
      toast.show('Campaña cerrada', 'info');
      load();
    } catch { toast.show('Error al cerrar campaña', 'error'); }
    finally { setActionLoading(false); }
  };

  if (loading) {
    return (<View style={styles.center}><ActivityIndicator size="large" color="#1a73e8" /></View>);
  }

  if (error || !campania) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error || 'Campaña no encontrada'}</Text>
        <TouchableOpacity style={styles.retryButton} onPress={load}>
          <Text style={styles.retryText}>Reintentar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.nombre}>{campania.nombre}</Text>
        <View style={[styles.statusBadge, {backgroundColor: campania.esActiva ? '#4caf50' : '#607d8b'}]}>
          <Text style={styles.statusText}>{campania.estado}</Text>
        </View>
      </View>

      <View style={styles.section}>
        <InfoRow label="Código" value={campania.codigo} />
        <InfoRow label="Tipo" value={campania.tipo} />
        <InfoRow label="Sitio" value={campania.sitio?.nombre || '-'} />
        <InfoRow label="Inicio" value={new Date(campania.fechaInicio).toLocaleDateString('es-PE')} />
        <InfoRow label="Fin" value={campania.fechaFin ? new Date(campania.fechaFin).toLocaleDateString('es-PE') : '-'} />
        <InfoRow label="Descripción" value={campania.descripcion || '-'} multiline />
      </View>

      <View style={styles.actions}>
        {!campania.esActiva && (
          <TouchableOpacity style={styles.activateBtn} onPress={handleActivate} disabled={actionLoading}>
            <Text style={styles.btnText}>{actionLoading ? '...' : 'Activar'}</Text>
          </TouchableOpacity>
        )}
        {campania.esActiva && (
          <TouchableOpacity style={styles.closeBtn} onPress={handleClose} disabled={actionLoading}>
            <Text style={styles.btnText}>{actionLoading ? '...' : 'Cerrar Campaña'}</Text>
          </TouchableOpacity>
        )}
      </View>
    </ScrollView>
  );
};

const InfoRow: React.FC<{label: string; value: string; multiline?: boolean}> = ({label, value, multiline}) => (
  <View style={styles.infoRow}>
    <Text style={styles.label}>{label}</Text>
    <Text style={[styles.value, multiline && styles.multiline]}>{value}</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {flex: 1, backgroundColor: '#f5f5f5'},
  center: {flex: 1, justifyContent: 'center', alignItems: 'center', padding: 20},
  header: {
    flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center',
    padding: 16, backgroundColor: '#fff', borderBottomWidth: 1, borderBottomColor: '#e0e0e0',
  },
  nombre: {fontSize: 20, fontWeight: 'bold', color: '#333', flex: 1, marginRight: 10},
  statusBadge: {paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12},
  statusText: {color: '#fff', fontSize: 12, fontWeight: '600'},
  section: {backgroundColor: '#fff', marginTop: 8, paddingHorizontal: 16},
  infoRow: {paddingVertical: 12, borderBottomWidth: 1, borderBottomColor: '#f0f0f0'},
  label: {fontSize: 12, color: '#999', marginBottom: 2},
  value: {fontSize: 15, color: '#333'},
  multiline: {lineHeight: 20},
  actions: {flexDirection: 'row', padding: 16, gap: 10, backgroundColor: '#fff', marginTop: 8},
  activateBtn: {flex: 1, backgroundColor: '#4caf50', padding: 14, borderRadius: 8, alignItems: 'center'},
  closeBtn: {flex: 1, backgroundColor: '#f44336', padding: 14, borderRadius: 8, alignItems: 'center'},
  btnText: {color: '#fff', fontSize: 15, fontWeight: '600'},
  errorText: {color: '#d32f2f', textAlign: 'center', marginBottom: 10},
  retryButton: {backgroundColor: '#1a73e8', paddingHorizontal: 20, paddingVertical: 10, borderRadius: 8},
  retryText: {color: '#fff', fontWeight: '600'},
});

export default CampaniaDetailScreen;
