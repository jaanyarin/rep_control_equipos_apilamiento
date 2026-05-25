import React, {useEffect, useState} from 'react';
import {View, Text, StyleSheet, ActivityIndicator, ScrollView, TouchableOpacity} from 'react-native';
import {Averia} from '../types';
import {averiaService} from '../services';
import {useToast} from '../components/Toast';

const AveriaDetailScreen: React.FC<{route: any; navigation: any}> = ({route, navigation}) => {
  const {averiaId} = route.params;
  const [averia, setAveria] = useState<Averia | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showCloseForm, setShowCloseForm] = useState(false);
  const [actionLoading, setActionLoading] = useState(false);
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try {
      const data = await averiaService.getById(averiaId);
      setAveria(data);
    } catch (err: any) {
      setError(err.message || 'Error al cargar avería');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, [averiaId]);

  const handleClose = async () => {
    setActionLoading(true);
    try {
      await averiaService.close(averiaId, 'Atención realizada', 'Equipo reparado');
      toast.show('Avería cerrada', 'success');
      load();
    } catch { toast.show('Error al cerrar avería', 'error'); }
    finally { setActionLoading(false); }
  };

  if (loading) {
    return (<View style={styles.center}><ActivityIndicator size="large" color="#1a73e8" /></View>);
  }

  if (error || !averia) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error || 'Avería no encontrada'}</Text>
        <TouchableOpacity style={styles.retryButton} onPress={load}>
          <Text style={styles.retryText}>Reintentar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  const esCerrada = averia.estadoAveria?.nombre === 'CERRADA';

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.codigo}>{averia.codigo}</Text>
        <View style={[styles.statusBadge, {backgroundColor: esCerrada ? '#4caf50' : '#ff9800'}]}>
          <Text style={styles.statusText}>{averia.estadoAveria?.nombre || 'SIN ESTADO'}</Text>
        </View>
      </View>

      <View style={styles.section}>
        <InfoRow label="Equipo" value={averia.equipo?.codigo || '-'} />
        <InfoRow label="Tipo Avería" value={averia.tipoAveria?.nombre || '-'} />
        <InfoRow label="Proveedor" value={averia.proveedor?.razonSocial || '-'} />
        <InfoRow label="Fecha Reporte" value={new Date(averia.fechaReporte).toLocaleDateString('es-PE')} />
        <InfoRow label="Descripción Falla" value={averia.descripcionFalla} multiline />
        <InfoRow label="Descripción Atención" value={averia.descripcionAtencion || '-'} multiline />
        <InfoRow label="Acción Correctiva" value={averia.accionCorrectiva || '-'} multiline />
        {averia.fechaAtencion && (
          <InfoRow label="Fecha Atención" value={new Date(averia.fechaAtencion).toLocaleDateString('es-PE')} />
        )}
        {averia.fechaCierre && (
          <InfoRow label="Fecha Cierre" value={new Date(averia.fechaCierre).toLocaleDateString('es-PE')} />
        )}
        <InfoRow label="Observaciones" value={averia.observaciones || '-'} multiline />
      </View>

      {!esCerrada && (
        <View style={styles.actions}>
          <TouchableOpacity style={styles.closeBtn} onPress={handleClose} disabled={actionLoading}>
            <Text style={styles.btnText}>{actionLoading ? 'Cerrando...' : 'Cerrar Avería'}</Text>
          </TouchableOpacity>
        </View>
      )}
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
  codigo: {fontSize: 20, fontWeight: 'bold', color: '#333'},
  statusBadge: {paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12},
  statusText: {color: '#fff', fontSize: 12, fontWeight: '600'},
  section: {backgroundColor: '#fff', marginTop: 8, paddingHorizontal: 16},
  infoRow: {paddingVertical: 12, borderBottomWidth: 1, borderBottomColor: '#f0f0f0'},
  label: {fontSize: 12, color: '#999', marginBottom: 2},
  value: {fontSize: 15, color: '#333'},
  multiline: {lineHeight: 20},
  actions: {padding: 16, backgroundColor: '#fff', marginTop: 8},
  closeBtn: {backgroundColor: '#4caf50', padding: 14, borderRadius: 8, alignItems: 'center'},
  btnText: {color: '#fff', fontSize: 15, fontWeight: '600'},
  errorText: {color: '#d32f2f', textAlign: 'center', marginBottom: 10},
  retryButton: {backgroundColor: '#1a73e8', paddingHorizontal: 20, paddingVertical: 10, borderRadius: 8},
  retryText: {color: '#fff', fontWeight: '600'},
});

export default AveriaDetailScreen;
