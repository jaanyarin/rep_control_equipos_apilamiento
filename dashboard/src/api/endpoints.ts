import api from './client';
import type { ApiResponse, KPI, Metrics, Equipo, PagedContent, Campana, PSR, Averia } from '../types';

const extractData = <T>(response: { data: ApiResponse<T> }): T => response.data.data;

export const authApi = {
  login: (idToken: string) =>
    api.post('/auth/login', { idToken }),

  refresh: (refreshToken: string) =>
    api.post('/auth/refresh', { refreshToken }),
};

export const dashboardApi = {
  getKPIs: (params?: Record<string, string | number>) =>
    api.get('/dashboard/kpis', { params }).then(extractData<KPI>),

  getMetrics: () =>
    api.get('/dashboard/metrics').then(extractData<Metrics>),
};

export const equipmentApi = {
  list: (page = 0, pageSize = 20, estado?: string) =>
    api.get('/equipment', { params: { page, pageSize, estado } }).then(extractData<PagedContent<Equipo>>),

  getById: (id: number) =>
    api.get(`/equipment/${id}`).then(extractData<Equipo>),

  delete: (id: number) =>
    api.delete(`/equipment/${id}`),
};

export const campaignApi = {
  list: (page = 0, pageSize = 20) =>
    api.get('/campaigns', { params: { page, pageSize } }).then(extractData<PagedContent<Campana>>),

  activate: (id: number) => api.put(`/campaigns/${id}/activate`),
  close: (id: number) => api.put(`/campaigns/${id}/close`),
};

export const psrApi = {
  list: (page = 0, pageSize = 20, estado?: string) =>
    api.get('/psr', { params: { page, pageSize, estado } }).then(extractData<PagedContent<PSR>>),
};

export const damagesApi = {
  list: (page = 0, pageSize = 20) =>
    api.get('/damages', { params: { page, pageSize } }).then(extractData<PagedContent<Averia>>),

  close: (id: number, descripcionAtencion: string, accionCorrectiva: string) =>
    api.put(`/damages/${id}/close`, { descripcionAtencion, accionCorrectiva }),
};
