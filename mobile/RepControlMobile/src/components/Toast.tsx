import React, { createContext, useContext, useState, useCallback, useRef } from 'react';
import { Animated, Text, StyleSheet, View } from 'react-native';

type ToastType = 'success' | 'error' | 'info' | 'warning';

interface ToastMessage {
  message: string;
  type: ToastType;
  duration?: number;
}

interface ToastContextValue {
  show: (msg: string, type?: ToastType, duration?: number) => void;
}

const ToastContext = createContext<ToastContextValue>({ show: () => {} });

export const useToast = () => useContext(ToastContext);

const bgColors: Record<ToastType, string> = {
  success: '#4caf50',
  error: '#f44336',
  info: '#2196f3',
  warning: '#ff9800',
};

export const ToastProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [visible, setVisible] = useState(false);
  const [toast, setToast] = useState<ToastMessage>({ message: '', type: 'info' });
  const opacity = useRef(new Animated.Value(0)).current;
  const timer = useRef<ReturnType<typeof setTimeout> | null>(null);

  const show = useCallback((message: string, type: ToastType = 'info', duration = 3000) => {
    if (timer.current) clearTimeout(timer.current);
    setToast({ message, type, duration });
    setVisible(true);
    Animated.timing(opacity, { toValue: 1, duration: 300, useNativeDriver: true }).start();
    timer.current = setTimeout(() => {
      Animated.timing(opacity, { toValue: 0, duration: 300, useNativeDriver: true }).start(() => {
        setVisible(false);
      });
    }, duration);
  }, [opacity]);

  return (
    <ToastContext.Provider value={{ show }}>
      {children}
      {visible && (
        <Animated.View style={[styles.container, { opacity, backgroundColor: bgColors[toast.type] }]}>
          <Text style={styles.text}>{toast.message}</Text>
        </Animated.View>
      )}
    </ToastContext.Provider>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    bottom: 40,
    left: 20,
    right: 20,
    padding: 14,
    borderRadius: 10,
    zIndex: 9999,
    elevation: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 4,
  },
  text: {
    color: '#fff',
    fontSize: 14,
    fontWeight: '600',
    textAlign: 'center',
  },
});
