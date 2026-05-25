import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {useAppSelector} from '../hooks';
import {ToastProvider} from '../components/Toast';
import ErrorBoundary from '../components/ErrorBoundary';
import LoginScreen from '../screens/LoginScreen';
import HomeScreen from '../screens/HomeScreen';
import EquipoListScreen from '../screens/EquipoListScreen';
import EquipoDetailScreen from '../screens/EquipoDetailScreen';
import PSRListScreen from '../screens/PSRListScreen';
import PSRDetailScreen from '../screens/PSRDetailScreen';
import AveriaListScreen from '../screens/AveriaListScreen';
import AveriaDetailScreen from '../screens/AveriaDetailScreen';
import CampaniaListScreen from '../screens/CampaniaListScreen';
import CampaniaDetailScreen from '../screens/CampaniaDetailScreen';
import CamaraScreen from '../screens/CamaraScreen';
import PDFViewerScreen from '../screens/PDFViewerScreen';

export type RootStackParamList = {
  Login: undefined;
  Home: undefined;
  Equipos: undefined;
  EquipoDetail: {equipoId: number};
  PSR: undefined;
  PSRDetail: {psrId: number};
  Averias: undefined;
  AveriaDetail: {averiaId: number};
  Campanias: undefined;
  CampaniaDetail: {campaniaId: number};
  Camara: undefined;
  PDFViewer: {url: string; title?: string};
};

const Stack = createNativeStackNavigator<RootStackParamList>();

const AppNavigator: React.FC = () => {
  const {isAuthenticated} = useAppSelector(state => state.auth);

  return (
    <ErrorBoundary>
      <ToastProvider>
        <NavigationContainer>
          <Stack.Navigator
            initialRouteName={isAuthenticated ? 'Home' : 'Login'}
            screenOptions={{
              headerStyle: {backgroundColor: '#1a73e8'},
              headerTintColor: '#fff',
              headerTitleStyle: {fontWeight: '600'},
            }}>
            {!isAuthenticated ? (
              <Stack.Screen
                name="Login"
                component={LoginScreen}
                options={{headerShown: false}}
              />
            ) : (
              <>
                <Stack.Screen
                  name="Home"
                  component={HomeScreen}
                  options={{headerShown: false}}
                />
                <Stack.Screen
                  name="Equipos"
                  component={EquipoListScreen}
                  options={{title: 'Equipos'}}
                />
                <Stack.Screen
                  name="EquipoDetail"
                  component={EquipoDetailScreen}
                  options={{title: 'Detalle Equipo'}}
                />
                <Stack.Screen
                  name="PSR"
                  component={PSRListScreen}
                  options={{title: 'PSR / OSR'}}
                />
                <Stack.Screen
                  name="PSRDetail"
                  component={PSRDetailScreen}
                  options={{title: 'Detalle PSR'}}
                />
                <Stack.Screen
                  name="Averias"
                  component={AveriaListScreen}
                  options={{title: 'Averías'}}
                />
                <Stack.Screen
                  name="AveriaDetail"
                  component={AveriaDetailScreen}
                  options={{title: 'Detalle Avería'}}
                />
                <Stack.Screen
                  name="Campanias"
                  component={CampaniaListScreen}
                  options={{title: 'Campañas'}}
                />
                <Stack.Screen
                  name="CampaniaDetail"
                  component={CampaniaDetailScreen}
                  options={{title: 'Detalle Campaña'}}
                />
                <Stack.Screen
                  name="Camara"
                  component={CamaraScreen}
                  options={{title: 'Cámara', headerStyle: {backgroundColor: '#111'}}}
                />
                <Stack.Screen
                  name="PDFViewer"
                  component={PDFViewerScreen}
                  options={{headerShown: false}}
                />
              </>
            )}
          </Stack.Navigator>
        </NavigationContainer>
      </ToastProvider>
    </ErrorBoundary>
  );
};

export default AppNavigator;
