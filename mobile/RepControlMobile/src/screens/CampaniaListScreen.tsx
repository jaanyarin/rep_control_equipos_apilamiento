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
import {Campana} from '../types';
import {campaniaService} from '../services';

const estadoColors: Record<string, string> = {
  ACTIVA: '#4caf50',
  CERRADA: '#f44336',
  PENDIENTE: '#ff9800',
};

const CampaniaCard: React.FC<{campania: Campana; onPress: () => void}> = ({
  campania,
  onPress,
}) => (
  <TouchableOpacity style={styles.card} onPress={onPress}>
    <View style={styles.cardHeader}>
      <Text style={styles.cardTitle}>{campania.nombre}</Text>
      <View
        style={[
          styles.statusBadge,
          {backgroundColor: estadoColors[campania.estado] || '#999'},
        ]}>
        <Text style={styles.statusText}>{campania.estado}</Text>
      </View>
    </View>
    <Text style={styles.cardDetail}>Código: {campania.codigo}</Text>
    <Text style={styles.cardDetail}>
      Sitio: {campania.sitio?.nombre || 'N/A'}
    </Text>
    <Text style={styles.cardDetail}>Tipo: {campania.tipo}</Text>
    <Text style={styles.cardDate}>
      {new Date(campania.fechaInicio).toLocaleDateString('es-PE')}
      {campania.fechaFin
        ? ` - ${new Date(campania.fechaFin).toLocaleDateString('es-PE')}`
        : ''}
    </Text>
  </TouchableOpacity>
);

const CampaniaListScreen: React.FC<{navigation: any}> = ({navigation}) => {
  const [campanias, setCampanias] = useState<Campana[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [total, setTotal] = useState(0);
  const pageSize = 20;

  const loadCampanias = async (pageNum = 0) => {
    if (pageNum === 0) setIsLoading(true);
    try {
      const paged = await campaniaService.listAll(
        undefined,
        pageNum,
        pageSize,
      );
      setCampanias(prev =>
        pageNum === 0 ? paged.content : [...prev, ...paged.content],
      );
      setTotal(paged.totalElements);
    } catch (err: any) {
      setError(err.message || 'Error al cargar campañas');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadCampanias(0);
    navigation.setOptions({title: 'Campañas'});
  }, [navigation]);

  const handleLoadMore = () => {
    if (campanias.length < total) {
      const nextPage = page + 1;
      setPage(nextPage);
      loadCampanias(nextPage);
    }
  };

  if (isLoading && campanias.length === 0) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#1a73e8" />
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error}</Text>
        <TouchableOpacity
          style={styles.retryButton}
          onPress={() => loadCampanias(0)}>
          <Text style={styles.retryText}>Reintentar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={campanias}
        keyExtractor={item => item.id.toString()}
        renderItem={({item}) => (
          <CampaniaCard campania={item} onPress={() => navigation.navigate('CampaniaDetail', {campaniaId: item.id})} />
        )}
        refreshControl={
          <RefreshControl
            refreshing={isLoading}
            onRefresh={() => loadCampanias(0)}
          />
        }
        onEndReached={handleLoadMore}
        onEndReachedThreshold={0.5}
        ListFooterComponent={
          isLoading && campanias.length > 0 ? (
            <ActivityIndicator style={styles.footer} color="#1a73e8" />
          ) : null
        }
        ListEmptyComponent={
          <View style={styles.center}>
            <Text style={styles.emptyText}>No hay campañas registradas</Text>
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

export default CampaniaListScreen;
