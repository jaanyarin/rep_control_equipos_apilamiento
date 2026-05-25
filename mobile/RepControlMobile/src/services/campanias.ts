import api, {extractData, extractPaged} from './api';
import {Campana, PagedContent} from '../types';

export const campaniaService = {
  async listAll(
    esActiva?: boolean,
    page = 0,
    pageSize = 20,
  ): Promise<PagedContent<Campana>> {
    const params: Record<string, string | number | boolean> = {page, pageSize};
    if (esActiva !== undefined) params.es_activa = esActiva;
    const response = await api.get('/api/v1/campaigns', {params});
    return extractPaged(response);
  },

  async getById(id: number): Promise<Campana> {
    const response = await api.get(`/api/v1/campaigns/${id}`);
    return extractData(response);
  },

  async create(data: Partial<Campana>): Promise<Campana> {
    const response = await api.post('/api/v1/campaigns', data);
    return extractData(response);
  },

  async update(id: number, data: Partial<Campana>): Promise<Campana> {
    const response = await api.put(`/api/v1/campaigns/${id}`, data);
    return extractData(response);
  },

  async activate(id: number): Promise<Campana> {
    const response = await api.put(`/api/v1/campaigns/${id}/activate`);
    return extractData(response);
  },

  async close(id: number): Promise<Campana> {
    const response = await api.put(`/api/v1/campaigns/${id}/close`);
    return extractData(response);
  },
};
