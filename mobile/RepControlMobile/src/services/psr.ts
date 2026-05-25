import api, {extractData, extractPaged} from './api';
import {PSR, PagedContent} from '../types';

export const psrService = {
  async listAll(
    campanaId?: number,
    estado?: string,
    page = 0,
    pageSize = 20,
  ): Promise<PagedContent<PSR>> {
    const params: Record<string, string | number> = {page, pageSize};
    if (campanaId !== undefined) params.campana_id = campanaId;
    if (estado !== undefined) params.estado = estado;
    const response = await api.get('/api/v1/psr', {params});
    return extractPaged(response);
  },

  async getById(id: number): Promise<PSR> {
    const response = await api.get(`/api/v1/psr/${id}`);
    return extractData(response);
  },

  async create(data: Partial<PSR>): Promise<PSR> {
    const response = await api.post('/api/v1/psr', data);
    return extractData(response);
  },

  async update(id: number, data: Partial<PSR>): Promise<PSR> {
    const response = await api.put(`/api/v1/psr/${id}`, data);
    return extractData(response);
  },

  async approve(id: number): Promise<PSR> {
    const response = await api.put(`/api/v1/psr/${id}/approve`);
    return extractData(response);
  },

  async reject(id: number): Promise<PSR> {
    const response = await api.put(`/api/v1/psr/${id}/reject`);
    return extractData(response);
  },

  async close(id: number): Promise<PSR> {
    const response = await api.put(`/api/v1/psr/${id}/close`);
    return extractData(response);
  },
};
