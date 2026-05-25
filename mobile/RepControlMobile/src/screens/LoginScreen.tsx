import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ActivityIndicator,
} from 'react-native';
import { authorize } from 'react-native-app-auth';
import { useAppDispatch, useAppSelector } from '../hooks';
import { loginStart, loginSuccess, loginFailure, clearError } from '../store/slices/authSlice';
import { authService } from '../services';
import { MICROSOFT_AUTH_CONFIG } from '../constants';
import { useToast } from '../components/Toast';

const LoginScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const dispatch = useAppDispatch();
  const { isLoading, error } = useAppSelector((state) => state.auth);
  const toast = useToast();

  const handleMicrosoftLogin = async () => {
    dispatch(loginStart());
    try {
      const authState = await authorize(MICROSOFT_AUTH_CONFIG);
      const response = await authService.login({
        idToken: authState.idToken,
      });
      dispatch(loginSuccess(response.user));
      navigation.replace('Home');
    } catch (err: any) {
      let message = 'Error de autenticación';
      if (err.response?.data?.message) {
        message = err.response.data.message;
      } else if (err.message) {
        message = err.message;
      }
      dispatch(loginFailure(message));
      toast.show(message, 'error');
    }
  };

  if (isLoading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#1a73e8" />
        <Text style={styles.loadingText}>Iniciando sesión...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.logoContainer}>
        <Text style={styles.title}>REP Control</Text>
        <Text style={styles.subtitle}>Equipos Apilamiento</Text>
      </View>

      <View style={styles.form}>
        {error && (
          <View style={styles.errorBanner}>
            <Text style={styles.errorText}>{error}</Text>
            <TouchableOpacity onPress={() => dispatch(clearError())}>
              <Text style={styles.dismissText}>X</Text>
            </TouchableOpacity>
          </View>
        )}

        <TouchableOpacity
          style={styles.microsoftButton}
          onPress={handleMicrosoftLogin}>
          <Text style={styles.microsoftButtonText}>
            Iniciar sesión con Microsoft
          </Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  logoContainer: {
    alignItems: 'center',
    marginBottom: 50,
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#1a73e8',
  },
  subtitle: {
    fontSize: 16,
    color: '#666',
    marginTop: 5,
  },
  form: {
    backgroundColor: '#fff',
    padding: 20,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  errorBanner: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: '#fef2f2',
    padding: 12,
    borderRadius: 8,
    marginBottom: 15,
  },
  errorText: {
    color: '#d32f2f',
    fontSize: 13,
    flex: 1,
  },
  dismissText: {
    color: '#d32f2f',
    fontWeight: 'bold',
    marginLeft: 10,
  },
  microsoftButton: {
    backgroundColor: '#2f2f2f',
    padding: 15,
    borderRadius: 8,
    alignItems: 'center',
  },
  microsoftButtonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '600',
  },
  loadingText: {
    marginTop: 15,
    textAlign: 'center',
    color: '#666',
    fontSize: 16,
  },
});

export default LoginScreen;
