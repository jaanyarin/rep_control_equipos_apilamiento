import api, { extractData, extractPaged } from './api';
import { Evidencia, PagedContent } from '../types';

export const evidenciasService = {
  async upload(
    uri: string,
    tipo: string,
    equipoId?: number,
    averiaId?: number,
    osrId?: number,
    descripcion?: string,
  ): Promise<Evidencia> {
    const formData = new FormData();
    formData.append('file', {
      uri,
      type: 'image/jpeg',
      name: `evidencia_${Date.now()}.jpg`,
    } as any);
    formData.append('tipo', tipo);
    if (equipoId !== undefined) formData.append('equipoId', String(equipoId));
    if (averiaId !== undefined) formData.append('averiaId', String(averiaId));
    if (osrId !== undefined) formData.append('osrId', String(osrId));
    if (descripcion) formData.append('descripcion', descripcion);

    const response = await api.post('/api/v1/evidences/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
      timeout: 60000,
    });
    return extractData(response);
  },

  async listAll(params?: {
    equipoId?: number;
    averiaId?: number;
    osrId?: number;
    tipo?: string;
    page?: number;
    pageSize?: number;
  }): Promise<PagedContent<Evidencia>> {
    const response = await api.get('/api/v1/evidences', { params });
    return extractPaged(response);
  },

  async delete(id: number): Promise<void> {
    await api.delete(`/api/v1/evidences/${id}`);
  },
};
