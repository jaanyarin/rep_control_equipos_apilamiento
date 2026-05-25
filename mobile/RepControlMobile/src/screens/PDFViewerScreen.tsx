import React, {useState, useEffect} from 'react';
import {
  View, Text, TouchableOpacity, StyleSheet, ActivityIndicator, Alert,
} from 'react-native';
import {useNavigation, useRoute} from '@react-navigation/native';

let PDF: any = null;
try { PDF = require('react-native-pdf').default; } catch {}

interface PDFViewerParams {
  url: string;
  title?: string;
}

export default function PDFViewerScreen() {
  const navigation = useNavigation();
  const route = useRoute();
  const {url, title} = route.params as PDFViewerParams;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [pages, setPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

  const hasPDF = PDF !== null;

  useEffect(() => {
    if (!hasPDF) {
      Alert.alert(
        'Visor PDF',
        'Instale las dependencias:\n\nnpm install react-native-pdf react-native-blob-util',
      );
      setLoading(false);
      setError('Librería PDF no instalada');
    }
  }, []);

  if (!hasPDF) {
    return (
      <View style={styles.container}>
        <View style={styles.header}>
          <Text style={styles.title}>{title || 'Documento'}</Text>
        </View>
        <View style={styles.center}>
          <Text style={styles.errorText}>Visor PDF no disponible</Text>
          <Text style={styles.hint}>npm install react-native-pdf react-native-blob-util</Text>
          <TouchableOpacity style={styles.closeBtn} onPress={() => navigation.goBack()}>
            <Text style={styles.closeText}>Cerrar</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Text style={styles.backText}>← Atrás</Text>
        </TouchableOpacity>
        <Text style={styles.title}>{title || 'Documento'}</Text>
        {pages > 0 && (
          <Text style={styles.pageInfo}>{currentPage} / {pages}</Text>
        )}
      </View>

      <View style={styles.content}>
        {loading && (
          <View style={styles.loadingOverlay}>
            <ActivityIndicator size="large" color="#1a73e8" />
            <Text style={styles.loadingText}>Cargando documento...</Text>
          </View>
        )}

        {error && (
          <View style={styles.center}>
            <Text style={styles.errorText}>{error}</Text>
          </View>
        )}

        <PDF
          source={{uri: url, cache: true}}
          onLoadComplete={(numberOfPages: number) => {
            setPages(numberOfPages);
            setLoading(false);
          }}
          onPageChanged={(page: number) => setCurrentPage(page)}
          onError={(err: Error) => {
            setError(err.message);
            setLoading(false);
          }}
          style={styles.pdf}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {flex: 1, backgroundColor: '#f5f5f5'},
  center: {flex: 1, justifyContent: 'center', alignItems: 'center', padding: 20},
  header: {
    flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between',
    paddingHorizontal: 16, paddingVertical: 12, backgroundColor: '#1a73e8',
  },
  backText: {color: '#fff', fontSize: 16},
  title: {color: '#fff', fontSize: 16, fontWeight: '600', flex: 1, textAlign: 'center'},
  pageInfo: {color: 'rgba(255,255,255,0.8)', fontSize: 13},
  content: {flex: 1, backgroundColor: '#e0e0e0'},
  pdf: {flex: 1},
  loadingOverlay: {
    ...StyleSheet.absoluteFill,
    justifyContent: 'center', alignItems: 'center', zIndex: 10,
    backgroundColor: 'rgba(255,255,255,0.8)',
  },
  loadingText: {marginTop: 10, color: '#666'},
  errorText: {color: '#d32f2f', textAlign: 'center', marginBottom: 10, fontSize: 16},
  hint: {color: '#666', textAlign: 'center', marginBottom: 20, fontSize: 13},
  closeBtn: {
    backgroundColor: '#1a73e8', paddingHorizontal: 24, paddingVertical: 10, borderRadius: 8,
  },
  closeText: {color: '#fff', fontWeight: '600'},
});
