import api, {extractData, extractPaged} from './api';
import {Averia, PagedContent} from '../types';

export const averiaService = {
  async listAll(
    equipoId?: number,
    estadoAveriaId?: number,
    proveedorId?: number,
    page = 0,
    pageSize = 20,
  ): Promise<PagedContent<Averia>> {
    const params: Record<string, string | number> = {page, pageSize};
    if (equipoId !== undefined) params.equipo_id = equipoId;
    if (estadoAveriaId !== undefined) params.estado_averia_id = estadoAveriaId;
    if (proveedorId !== undefined) params.proveedor_id = proveedorId;
    const response = await api.get('/api/v1/damages', {params});
    return extractPaged(response);
  },

  async getById(id: number): Promise<Averia> {
    const response = await api.get(`/api/v1/damages/${id}`);
    return extractData(response);
  },

  async create(data: Partial<Averia>): Promise<Averia> {
    const response = await api.post('/api/v1/damages', data);
    return extractData(response);
  },

  async update(id: number, data: Partial<Averia>): Promise<Averia> {
    const response = await api.put(`/api/v1/damages/${id}`, data);
    return extractData(response);
  },

  async close(
    id: number,
    descripcionAtencion: string,
    accionCorrectiva: string,
  ): Promise<Averia> {
    const response = await api.put(`/api/v1/damages/${id}/close`, {
      descripcionAtencion,
      accionCorrectiva,
    });
    return extractData(response);
  },
};
