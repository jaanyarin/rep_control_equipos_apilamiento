import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from 'react-native';
import { useAppDispatch, useAppSelector } from '../hooks';
import { logout } from '../store/slices/authSlice';
import { authService } from '../services';

const HomeScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const dispatch = useAppDispatch();
  const { user } = useAppSelector((state) => state.auth);

  const handleLogout = async () => {
    Alert.alert('Cerrar Sesión', '¿Está seguro de cerrar sesión?', [
      { text: 'Cancelar', style: 'cancel' },
      {
        text: 'Salir',
        style: 'destructive',
        onPress: async () => {
          await authService.logout();
          dispatch(logout());
          navigation.replace('Login');
        },
      },
    ]);
  };

  const menuItems = [
    { title: 'Equipos', screen: 'Equipos' },
    { title: 'PSR / OSR', screen: 'PSR' },
    { title: 'Averías', screen: 'Averias' },
    { title: 'Campañas', screen: 'Campanias' },
    { title: 'Cámara', screen: 'Camara' },
    { title: 'Documentos', screen: 'PDFViewer', params: { url: 'https://', title: 'Documento' } },
  ];

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <View>
          <Text style={styles.welcome}>Bienvenido,</Text>
          <Text style={styles.userName}>{user?.nombre || 'Usuario'}</Text>
          <Text style={styles.userRole}>{user?.rol || ''}</Text>
        </View>
        <TouchableOpacity onPress={handleLogout} style={styles.logoutButton}>
          <Text style={styles.logoutText}>Salir</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.menuGrid}>
        {menuItems.map((item, index) => (
          <TouchableOpacity
            key={index}
            style={styles.menuItem}
            onPress={() => navigation.navigate(item.screen, item.params || {})}>
            <Text style={styles.menuTitle}>{item.title}</Text>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#1a73e8',
  },
  welcome: {
    color: '#fff',
    fontSize: 14,
    opacity: 0.9,
  },
  userName: {
    color: '#fff',
    fontSize: 20,
    fontWeight: 'bold',
  },
  userRole: {
    color: '#fff',
    fontSize: 12,
    opacity: 0.8,
    marginTop: 2,
  },
  logoutButton: {
    backgroundColor: 'rgba(255,255,255,0.2)',
    paddingHorizontal: 15,
    paddingVertical: 8,
    borderRadius: 20,
  },
  logoutText: {
    color: '#fff',
    fontWeight: '600',
  },
  menuGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    padding: 10,
  },
  menuItem: {
    width: '46%',
    backgroundColor: '#fff',
    margin: '2%',
    padding: 20,
    borderRadius: 12,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 3,
    elevation: 2,
  },
  menuTitle: {
    fontSize: 14,
    fontWeight: '600',
    color: '#333',
  },
});

export default HomeScreen;
