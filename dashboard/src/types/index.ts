export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  errorCode?: string;
  timestamp: string;
}

export interface PagedContent<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export interface AuthUser {
  id: number;
  nombre: string;
  correo: string;
  rol: string;
  sitio: string | null;
}

export interface AuthTokenResponse {
  token: string;
  refreshToken: string;
  expiresIn: number;
  user: AuthUser;
}

export interface LoginRequest {
  authorizationCode: string;
  redirectUri: string;
}

export interface KPI {
  equiposActivos: number;
  equiposDisponibles: number;
  equiposAveriados: number;
  equiposEnMantenimiento: number;
  equiposDevueltos: number;
  averiasAbiertas: number;
  averiasCerradas: number;
  tiempoPromedioAtencion: number;
  disponibilidad: number;
  utilizacion: number;
  porProveedor: ProveedorKPI[];
  porTipo: TipoKPI[];
}

export interface ProveedorKPI {
  proveedor: string;
  totalEquipos: number;
  averias: number;
}

export interface TipoKPI {
  tipo: string;
  total: number;
  disponibles: number;
}

export interface Metrics {
  totalEquipos: number;
  totalCampañas: number;
  totalProveedores: number;
  totalUsuarios: number;
  equiposPorEstado: Record<string, number>;
  averiasPorTipo: { tipo: string; cantidad: number }[];
  evolucionMensual: { mes: string; equipos: number; averias: number }[];
}

export interface Equipo {
  id: number;
  codigo: string;
  numeroSerie: string;
  marca?: string;
  modelo?: string;
  tipoEquipo?: { id: number; nombre: string };
  proveedor?: { id: number; razonSocial: string };
  estado: string;
  estadoActivo: boolean;
  horometroActual?: number;
  fechaIngreso?: string;
  observaciones?: string;
}

export interface Campana {
  id: number;
  codigo: string;
  nombre: string;
  tipo: string;
  sitio?: { id: number; nombre: string };
  fechaInicio: string;
  fechaFin?: string;
  estado: string;
  esActiva: boolean;
}

export interface PSR {
  id: number;
  numero: string;
  campana?: Campana;
  sitio?: { id: number; nombre: string };
  descripcion?: string;
  fechaSolicitud: string;
  estado: string;
}

export interface Averia {
  id: number;
  codigo: string;
  equipo?: Equipo;
  tipoAveria?: { id: number; nombre: string };
  estadoAveria?: { id: number; nombre: string };
  proveedor?: { id: number; razonSocial: string };
  descripcionFalla: string;
  fechaReporte: string;
  fechaAtencion?: string;
  estadoActivo: boolean;
}
