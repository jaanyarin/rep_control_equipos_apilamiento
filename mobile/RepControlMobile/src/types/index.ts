export interface AuthUserResponse {
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
  user: AuthUserResponse;
}

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

export interface AuthLoginRequest {
  idToken: string;
}

export interface AuthRefreshRequest {
  refreshToken: string;
}

export interface Sitio {
  id: number;
  codigo: string;
  nombre: string;
  descripcion?: string;
  direccion?: string;
  estadoActivo: boolean;
}

export interface Campana {
  id: number;
  codigo: string;
  nombre: string;
  tipo: string;
  sitio?: Sitio;
  fechaInicio: string;
  fechaFin?: string;
  estado: string;
  esActiva: boolean;
  descripcion?: string;
  estadoActivo: boolean;
}

export interface TipoEquipo {
  id: number;
  codigo: string;
  nombre: string;
  descripcion?: string;
}

export interface Proveedor {
  id: number;
  ruc: string;
  razonSocial: string;
  nombreComercial?: string;
  direccion?: string;
  telefono?: string;
  correo?: string;
  contactoNombre?: string;
  contactoTelefono?: string;
  estadoActivo: boolean;
}

export interface Equipo {
  id: number;
  codigo: string;
  numeroSerie: string;
  marca?: string;
  modelo?: string;
  tipoEquipo?: TipoEquipo;
  proveedor?: Proveedor;
  estado: string;
  estadoActivo: boolean;
  observaciones?: string;
  fechaIngreso?: string;
  fechaDevolucion?: string;
  horometroActual?: number;
}

export interface MotivoPSR {
  id: number;
  codigo: string;
  nombre: string;
}

export interface PSR {
  id: number;
  numero: string;
  campana?: Campana;
  sitio?: Sitio;
  motivo?: MotivoPSR;
  descripcion?: string;
  fechaSolicitud: string;
  estado: string;
  observaciones?: string;
  estadoActivo: boolean;
}

export interface EstadoAveria {
  id: number;
  codigo: string;
  nombre: string;
}

export interface TipoAveria {
  id: number;
  codigo: string;
  nombre: string;
}

export interface Averia {
  id: number;
  codigo: string;
  equipo?: Equipo;
  tipoAveria?: TipoAveria;
  estadoAveria?: EstadoAveria;
  proveedor?: Proveedor;
  descripcionFalla: string;
  descripcionAtencion?: string;
  accionCorrectiva?: string;
  fechaReporte: string;
  fechaAtencion?: string;
  fechaCierre?: string;
  estadoActivo: boolean;
  observaciones?: string;
}

export interface Evidencia {
  id: number;
  tipo: string;
  equipoId?: number;
  averiaId?: number;
  osrId?: number;
  descripcion?: string;
  nombreArchivo: string;
  formato: string;
  tamano: number;
  usuarioId: number;
  fechaCarga: string;
}
