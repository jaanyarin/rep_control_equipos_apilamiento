import React, {useEffect, useState} from 'react';
import {View, Text, StyleSheet, ActivityIndicator, ScrollView, TouchableOpacity} from 'react-native';
import {PSR} from '../types';
import {psrService} from '../services';
import {useToast} from '../components/Toast';

const estadoColors: Record<string, string> = {
  ACTIVO: '#2196f3',
  PENDIENTE: '#ff9800',
  APROBADO: '#4caf50',
  RECHAZADO: '#f44336',
  CERRADO: '#607d8b',
};

const PSRDetailScreen: React.FC<{route: any; navigation: any}> = ({route, navigation}) => {
  const {psrId} = route.params;
  const [psr, setPsr] = useState<PSR | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [actionLoading, setActionLoading] = useState(false);
  const toast = useToast();

  const load = async () => {
    setLoading(true);
    try {
      const data = await psrService.getById(psrId);
      setPsr(data);
    } catch (err: any) {
      setError(err.message || 'Error al cargar PSR');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, [psrId]);

  const handleApprove = async () => {
    setActionLoading(true);
    try {
      await psrService.approve(psrId);
      toast.show('PSR aprobado', 'success');
      load();
    } catch { toast.show('Error al aprobar PSR', 'error'); }
    finally { setActionLoading(false); }
  };

  const handleReject = async () => {
    setActionLoading(true);
    try {
      await psrService.reject(psrId);
      toast.show('PSR rechazado', 'warning');
      load();
    } catch { toast.show('Error al rechazar PSR', 'error'); }
    finally { setActionLoading(false); }
  };

  const handleClose = async () => {
    setActionLoading(true);
    try {
      await psrService.close(psrId);
      toast.show('PSR cerrado', 'info');
      load();
    } catch { toast.show('Error al cerrar PSR', 'error'); }
    finally { setActionLoading(false); }
  };

  if (loading) {
    return (<View style={styles.center}><ActivityIndicator size="large" color="#1a73e8" /></View>);
  }

  if (error || !psr) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error || 'PSR no encontrado'}</Text>
        <TouchableOpacity style={styles.retryButton} onPress={load}>
          <Text style={styles.retryText}>Reintentar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  const canApprove = psr.estado === 'PENDIENTE';
  const canClose = psr.estado === 'APROBADO' || psr.estado === 'ACTIVO';

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.numero}>{psr.numero}</Text>
        <View style={[styles.statusBadge, {backgroundColor: estadoColors[psr.estado] || '#999'}]}>
          <Text style={styles.statusText}>{psr.estado}</Text>
        </View>
      </View>

      <View style={styles.section}>
        <InfoRow label="Campaña" value={psr.campana?.nombre || '-'} />
        <InfoRow label="Sitio" value={psr.sitio?.nombre || '-'} />
        <InfoRow label="Motivo" value={psr.motivo?.nombre || '-'} />
        <InfoRow label="Fecha Solicitud" value={new Date(psr.fechaSolicitud).toLocaleDateString('es-PE')} />
        <InfoRow label="Descripción" value={psr.descripcion || '-'} multiline />
        <InfoRow label="Observaciones" value={psr.observaciones || '-'} multiline />
      </View>

      {(canApprove || canClose) && (
        <View style={styles.actions}>
          {canApprove && (
            <>
              <TouchableOpacity style={styles.approveBtn} onPress={handleApprove} disabled={actionLoading}>
                <Text style={styles.btnText}>{actionLoading ? '...' : 'Aprobar'}</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.rejectBtn} onPress={handleReject} disabled={actionLoading}>
                <Text style={styles.btnText}>{actionLoading ? '...' : 'Rechazar'}</Text>
              </TouchableOpacity>
            </>
          )}
          {canClose && (
            <TouchableOpacity style={styles.closeBtn} onPress={handleClose} disabled={actionLoading}>
              <Text style={styles.btnText}>{actionLoading ? '...' : 'Cerrar'}</Text>
            </TouchableOpacity>
          )}
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
  numero: {fontSize: 20, fontWeight: 'bold', color: '#333'},
  statusBadge: {paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12},
  statusText: {color: '#fff', fontSize: 12, fontWeight: '600'},
  section: {backgroundColor: '#fff', marginTop: 8, paddingHorizontal: 16},
  infoRow: {paddingVertical: 12, borderBottomWidth: 1, borderBottomColor: '#f0f0f0'},
  label: {fontSize: 12, color: '#999', marginBottom: 2},
  value: {fontSize: 15, color: '#333'},
  multiline: {lineHeight: 20},
  actions: {
    flexDirection: 'row', padding: 16, gap: 10, backgroundColor: '#fff', marginTop: 8,
  },
  approveBtn: {flex: 1, backgroundColor: '#4caf50', padding: 14, borderRadius: 8, alignItems: 'center'},
  rejectBtn: {flex: 1, backgroundColor: '#f44336', padding: 14, borderRadius: 8, alignItems: 'center'},
  closeBtn: {flex: 1, backgroundColor: '#607d8b', padding: 14, borderRadius: 8, alignItems: 'center'},
  btnText: {color: '#fff', fontSize: 15, fontWeight: '600'},
  errorText: {color: '#d32f2f', textAlign: 'center', marginBottom: 10},
  retryButton: {backgroundColor: '#1a73e8', paddingHorizontal: 20, paddingVertical: 10, borderRadius: 8},
  retryText: {color: '#fff', fontWeight: '600'},
});

export default PSRDetailScreen;
