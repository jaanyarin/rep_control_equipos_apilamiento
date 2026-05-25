import React, {useState, useCallback, useRef} from 'react';
import {
  View, Text, TouchableOpacity, StyleSheet, ActivityIndicator, Image, Alert,
} from 'react-native';
import {useNavigation} from '@react-navigation/native';
import {useToast} from '../components/Toast';
import {evidenciasService} from '../services';

let Camera: any = null;
let useCameraDevice: any = null;
let useCameraPermission: any = null;

try {
  const VisionCamera = require('react-native-vision-camera');
  Camera = VisionCamera.Camera;
  useCameraDevice = VisionCamera.useCameraDevice;
  useCameraPermission = VisionCamera.useCameraPermission;
} catch {}

export default function CamaraScreen() {
  const navigation = useNavigation();
  const toast = useToast();
  const camera = useRef<any>(null);
  const [photo, setPhoto] = useState<string | null>(null);
  const [saving, setSaving] = useState(false);

  const hasVisionCamera = Camera !== null;

  const {hasPermission, requestPermission} = useCameraPermission
    ? useCameraPermission('photo')
    : {hasPermission: false, requestPermission: async () => false};
  const device = useCameraDevice ? useCameraDevice('back') : null;

  const handleCapture = useCallback(async () => {
    if (!hasVisionCamera) {
      Alert.alert(
        'Cámara no disponible',
        'Instale react-native-vision-camera:\n\nnpm install react-native-vision-camera',
      );
      return;
    }

    if (!hasPermission) {
      const granted = await requestPermission();
      if (!granted) {
        toast.show('Permiso de cámara denegado', 'error');
        return;
      }
    }

    if (!device) {
      toast.show('Cámara trasera no disponible', 'error');
      return;
    }

    try {
      if (camera.current) {
        const captured = await camera.current.takePhoto();
        setPhoto(captured.path);
        toast.show('Foto capturada', 'success');
      }
    } catch (err: any) {
      toast.show('Error al capturar foto: ' + (err.message || ''), 'error');
    }
  }, [hasVisionCamera, hasPermission, requestPermission, device, toast]);

  const handleSave = async () => {
    if (!photo) return;
    setSaving(true);
    try {
      await evidenciasService.upload(photo, 'FOTO');
      toast.show('Evidencia guardada', 'success');
      navigation.goBack();
    } catch {
      toast.show('Error al guardar evidencia', 'error');
    } finally {
      setSaving(false);
    }
  };

  const handleRetake = () => setPhoto(null);

  if (!hasVisionCamera) {
    return (
      <View style={styles.container}>
        <View style={styles.preview}>
          <Text style={styles.placeholder}>Cámara no disponible</Text>
          <Text style={styles.hint}>Instala react-native-vision-camera y configura los permisos nativos</Text>
        </View>
        <View style={styles.actions}>
          <TouchableOpacity style={styles.cancelBtn} onPress={() => navigation.goBack()}>
            <Text style={styles.cancelText}>Volver</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }

  if (!hasPermission) {
    return (
      <View style={styles.container}>
        <View style={styles.preview}>
          <Text style={styles.placeholder}>Permiso de cámara requerido</Text>
          <TouchableOpacity style={styles.captureBtn} onPress={requestPermission}>
            <Text style={styles.btnText}>Solicitar permiso</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }

  if (photo) {
    return (
      <View style={styles.container}>
        <Image source={{uri: 'file://' + photo}} style={styles.preview} />
        <View style={styles.actions}>
          <TouchableOpacity style={styles.captureBtn} onPress={handleRetake}>
            <Text style={styles.btnText}>Re-tomar</Text>
          </TouchableOpacity>
          <TouchableOpacity style={[styles.saveBtn, saving && styles.disabled]} onPress={handleSave} disabled={saving}>
            {saving ? <ActivityIndicator color="#fff" /> : <Text style={styles.btnText}>Guardar Evidencia</Text>}
          </TouchableOpacity>
        </View>
      </View>
    );
  }

  if (!device) {
    return (
      <View style={styles.container}>
        <View style={styles.preview}>
          <Text style={styles.placeholder}>Cámara trasera no disponible</Text>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Camera
        ref={camera}
        style={StyleSheet.absoluteFill}
        device={device}
        isActive={true}
        photo={true}
      />
      <View style={styles.overlay}>
        <TouchableOpacity style={styles.captureBtn} onPress={handleCapture}>
          <View style={styles.captureCircle} />
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {flex: 1, backgroundColor: '#000'},
  preview: {flex: 1, justifyContent: 'center', alignItems: 'center'},
  placeholder: {color: '#888', fontSize: 18, marginBottom: 10},
  hint: {color: '#666', fontSize: 13, textAlign: 'center', marginHorizontal: 30},
  overlay: {
    position: 'absolute', bottom: 40, left: 0, right: 0,
    alignItems: 'center',
  },
  captureBtn: {
    width: 70, height: 70, borderRadius: 35, borderWidth: 4,
    borderColor: '#fff', justifyContent: 'center', alignItems: 'center',
    backgroundColor: 'rgba(255,255,255,0.2)',
  },
  captureCircle: {
    width: 54, height: 54, borderRadius: 27, backgroundColor: '#fff',
  },
  actions: {
    flexDirection: 'row', padding: 20, gap: 12,
    backgroundColor: '#111', justifyContent: 'center',
  },
  saveBtn: {
    flex: 1, backgroundColor: '#4caf50', padding: 14,
    borderRadius: 10, alignItems: 'center',
  },
  cancelBtn: {padding: 14, alignItems: 'center'},
  disabled: {opacity: 0.6},
  btnText: {color: '#fff', fontSize: 16, fontWeight: '600'},
  cancelText: {color: '#ccc', fontSize: 14},
});
