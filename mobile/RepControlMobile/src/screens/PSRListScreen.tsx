import React, {useEffect, useState} from 'react';
import {
  View,
  Text,
  FlatList,
  TouchableOpacity,
  StyleSheet,
  ActivityIndicator,
  RefreshControl,
} from 'react-native';
import {PSR} from '../types';
import {psrService} from '../services';

const estadoColors: Record<string, string> = {
  ACTIVO: '#2196f3',
  PENDIENTE: '#ff9800',
  APROBADO: '#4caf50',
  RECHAZADO: '#f44336',
  CERRADO: '#607d8b',
};

const PSRCard: React.FC<{psr: PSR; onPress: () => void}> = ({psr, onPress}) => (
  <TouchableOpacity style={styles.card} onPress={onPress}>
    <View style={styles.cardHeader}>
      <Text style={styles.cardTitle}>{psr.numero}</Text>
      <View
        style={[
          styles.statusBadge,
          {backgroundColor: estadoColors[psr.estado] || '#999'},
        ]}>
        <Text style={styles.statusText}>{psr.estado}</Text>
      </View>
    </View>
    <Text style={styles.cardDetail}>
      Campaña: {psr.campana?.nombre || 'N/A'}
    </Text>
    <Text style={styles.cardDetail}>
      Sitio: {psr.sitio?.nombre || 'N/A'}
    </Text>
    <Text style={styles.cardDetail}>{psr.descripcion}</Text>
    {psr.motivo && (
      <Text style={styles.cardDetail}>Motivo: {psr.motivo.nombre}</Text>
    )}
    <Text style={styles.cardDate}>
      {new Date(psr.fechaSolicitud).toLocaleDateString('es-PE')}
    </Text>
  </TouchableOpacity>
);

const PSRListScreen: React.FC<{navigation: any}> = ({navigation}) => {
  const [psrs, setPsrs] = useState<PSR[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [total, setTotal] = useState(0);
  const pageSize = 20;

  const loadPSRs = async (pageNum = 0) => {
    if (pageNum === 0) setIsLoading(true);
    try {
      const paged = await psrService.listAll(
        undefined,
        undefined,
        pageNum,
        pageSize,
      );
      setPsrs(prev =>
        pageNum === 0 ? paged.content : [...prev, ...paged.content],
      );
      setTotal(paged.totalElements);
    } catch (err: any) {
      setError(err.message || 'Error al cargar PSR');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadPSRs(0);
    navigation.setOptions({title: 'PSR / OSR'});
  }, [navigation]);

  const handleLoadMore = () => {
    if (psrs.length < total) {
      const nextPage = page + 1;
      setPage(nextPage);
      loadPSRs(nextPage);
    }
  };

  if (isLoading && psrs.length === 0) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#1a73e8" />
        <Text style={styles.loadingText}>Cargando PSR...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error}</Text>
        <TouchableOpacity
          style={styles.retryButton}
          onPress={() => loadPSRs(0)}>
          <Text style={styles.retryText}>Reintentar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={psrs}
        keyExtractor={item => item.id.toString()}
        renderItem={({item}) => <PSRCard psr={item} onPress={() => navigation.navigate('PSRDetail', {psrId: item.id})} />}
        refreshControl={
          <RefreshControl
            refreshing={isLoading}
            onRefresh={() => loadPSRs(0)}
          />
        }
        onEndReached={handleLoadMore}
        onEndReachedThreshold={0.5}
        ListFooterComponent={
          isLoading && psrs.length > 0 ? (
            <ActivityIndicator style={styles.footer} color="#1a73e8" />
          ) : null
        }
        ListEmptyComponent={
          <View style={styles.center}>
            <Text style={styles.emptyText}>No hay PSR registrados</Text>
          </View>
        }
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {flex: 1, backgroundColor: '#f5f5f5'},
  center: {flex: 1, justifyContent: 'center', alignItems: 'center', padding: 20},
  card: {
    backgroundColor: '#fff',
    marginHorizontal: 10,
    marginVertical: 5,
    padding: 15,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 1},
    shadowOpacity: 0.1,
    shadowRadius: 3,
    elevation: 2,
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8,
  },
  cardTitle: {fontSize: 16, fontWeight: 'bold', color: '#333', flex: 1},
  cardDetail: {fontSize: 13, color: '#666', marginTop: 2},
  cardDate: {fontSize: 11, color: '#999', marginTop: 5},
  statusBadge: {paddingHorizontal: 8, paddingVertical: 3, borderRadius: 12},
  statusText: {color: '#fff', fontSize: 11, fontWeight: '600'},
  loadingText: {marginTop: 10, color: '#666'},
  errorText: {color: '#d32f2f', textAlign: 'center', marginBottom: 10},
  retryButton: {
    backgroundColor: '#1a73e8',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 8,
  },
  retryText: {color: '#fff', fontWeight: '600'},
  emptyText: {color: '#999', fontSize: 16},
  footer: {padding: 20},
});

export default PSRListScreen;
