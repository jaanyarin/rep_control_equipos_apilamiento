import React from 'react';
import { Provider } from 'react-redux';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { store } from './src/store';
import AppNavigator from './src/navigation/AppNavigator';
import ErrorBoundary from './src/components/ErrorBoundary';

function App() {
  return (
    <Provider store={store}>
      <SafeAreaProvider>
        <ErrorBoundary>
          <AppNavigator />
        </ErrorBoundary>
      </SafeAreaProvider>
    </Provider>
  );
}

export default App;
