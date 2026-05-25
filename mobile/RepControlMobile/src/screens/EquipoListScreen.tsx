import React, {useEffect, useCallback} from 'react';
import {
  View,
  Text,
  FlatList,
  TouchableOpacity,
  StyleSheet,
  ActivityIndicator,
  RefreshControl,
} from 'react-native';
import {useAppDispatch, useAppSelector} from '../hooks';
import {
  fetchEquiposStart,
  fetchEquiposSuccess,
  fetchEquiposFailure,
  selectEquipo,
  setPage,
} from '../store/slices/equipoSlice';
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

const EquipoCard: React.FC<{equipo: Equipo; onPress: () => void}> = ({
  equipo,
  onPress,
}) => (
  <TouchableOpacity style={styles.card} onPress={onPress}>
    <View style={styles.cardHeader}>
      <Text style={styles.cardTitle}>{equipo.codigo}</Text>
      <View
        style={[
          styles.statusBadge,
          {backgroundColor: estadoColors[equipo.estado] || '#999'},
        ]}>
        <Text style={styles.statusText}>{equipo.estado}</Text>
      </View>
    </View>
    <Text style={styles.cardSubtitle}>Serie: {equipo.numeroSerie}</Text>
    <Text style={styles.cardDetail}>
      {equipo.tipoEquipo?.nombre} - {equipo.proveedor?.razonSocial}
    </Text>
    {equipo.marca && (
      <Text style={styles.cardDetail}>
        {equipo.marca} {equipo.modelo ? `- ${equipo.modelo}` : ''}
      </Text>
    )}
  </TouchableOpacity>
);

const EquipoListScreen: React.FC<{navigation: any}> = ({navigation}) => {
  const dispatch = useAppDispatch();
  const {equipos, isLoading, error, total, page, pageSize} = useAppSelector(
    state => state.equipos,
  );

  const loadEquipos = useCallback(
    async (pageNum: number) => {
      dispatch(fetchEquiposStart());
      try {
        const paged = await equipoService.getAll(pageNum, pageSize);
        dispatch(
          fetchEquiposSuccess({
            equipos: paged.content,
            total: paged.totalElements,
          }),
        );
      } catch (err: any) {
        dispatch(
          fetchEquiposFailure(
            err.response?.data?.message || 'Error al cargar equipos',
          ),
        );
      }
    },
    [dispatch, pageSize],
  );

  useEffect(() => {
    loadEquipos(page);
  }, [page, loadEquipos]);

  useEffect(() => {
    navigation.setOptions({title: 'Equipos'});
  }, [navigation]);

  const handleEquipoPress = (equipo: Equipo) => {
    dispatch(selectEquipo(equipo));
    navigation.navigate('EquipoDetail', {equipoId: equipo.id});
  };

  const handleLoadMore = () => {
    if (equipos.length < total) {
      dispatch(setPage(page + 1));
    }
  };

  if (isLoading && equipos.length === 0) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#1a73e8" />
        <Text style={styles.loadingText}>Cargando equipos...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.center}>
        <Text style={styles.errorText}>{error}</Text>
        <TouchableOpacity
          style={styles.retryButton}
          onPress={() => loadEquipos(0)}>
          <Text style={styles.retryText}>Reintentar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={equipos}
        keyExtractor={item => item.id.toString()}
        renderItem={({item}) => (
          <EquipoCard
            equipo={item}
            onPress={() => handleEquipoPress(item)}
          />
        )}
        refreshControl={
          <RefreshControl
            refreshing={isLoading}
            onRefresh={() => loadEquipos(0)}
          />
        }
        onEndReached={handleLoadMore}
        onEndReachedThreshold={0.5}
        ListFooterComponent={
          isLoading && equipos.length > 0 ? (
            <ActivityIndicator style={styles.footer} color="#1a73e8" />
          ) : null
        }
        ListEmptyComponent={
          <View style={styles.center}>
            <Text style={styles.emptyText}>No hay equipos registrados</Text>
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
  cardHeader: {flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center'},
  cardTitle: {fontSize: 16, fontWeight: 'bold', color: '#333', flex: 1},
  cardSubtitle: {fontSize: 12, color: '#666', marginTop: 4},
  cardDetail: {fontSize: 12, color: '#999', marginTop: 2},
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

export default EquipoListScreen;
