import api, {extractData, extractPaged} from './api';
import {Equipo, PagedContent} from '../types';

export const equipoService = {
  async getAll(
    page = 0,
    pageSize = 20,
    estado?: string,
  ): Promise<PagedContent<Equipo>> {
    const response = await api.get('/api/v1/equipment', {
      params: {page, pageSize, estado},
    });
    return extractPaged(response);
  },

  async getById(id: number): Promise<Equipo> {
    const response = await api.get(`/api/v1/equipment/${id}`);
    return extractData(response);
  },

  async getByEstado(estado: string, page = 0, pageSize = 20): Promise<PagedContent<Equipo>> {
    const response = await api.get('/api/v1/equipment', {
      params: {estado, page, pageSize},
    });
    return extractPaged(response);
  },

  async create(data: Partial<Equipo>): Promise<Equipo> {
    const response = await api.post('/api/v1/equipment', data);
    return extractData(response);
  },

  async update(id: number, data: Partial<Equipo>): Promise<Equipo> {
    const response = await api.put(`/api/v1/equipment/${id}`, data);
    return extractData(response);
  },

  async delete(id: number): Promise<void> {
    await api.delete(`/api/v1/equipment/${id}`);
  },
};
