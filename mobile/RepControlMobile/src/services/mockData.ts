import {Equipo, PSR, Averia, Campana, AuthUserResponse} from '../types';

const mockUser: AuthUserResponse = {
  id: 1,
  nombre: 'Juan Perez',
  correo: 'juan@dominioempresa.com',
  rol: 'ADMIN',
  sitio: 'Packing Uva',
};

const mockEquipos: Equipo[] = [
  {
    id: 1,
    codigo: 'EQ-00001',
    numeroSerie: 'SN-001',
    tipoEquipo: {id: 1, codigo: 'TRANS-001', nombre: 'Transpaleta Eléctrica'},
    proveedor: {id: 1, ruc: '20123456789', razonSocial: 'Equipos Agrícolas S.A.C.', contactoNombre: 'Carlos Mendoza', estadoActivo: true},
    estado: 'OPERATIVO',
    estadoActivo: true,
  },
  {
    id: 2,
    codigo: 'EQ-00002',
    numeroSerie: 'SN-002',
    tipoEquipo: {id: 2, codigo: 'MONT-001', nombre: 'Montacargas'},
    proveedor: {id: 2, ruc: '20987654321', razonSocial: 'Maquinarias del Norte S.A.', contactoNombre: 'María López', estadoActivo: true},
    estado: 'DISPONIBLE',
    estadoActivo: true,
  },
  {
    id: 3,
    codigo: 'EQ-00003',
    numeroSerie: 'SN-003',
    tipoEquipo: {id: 3, codigo: 'TM-001', nombre: 'Transpaleta Manual'},
    proveedor: {id: 1, ruc: '20123456789', razonSocial: 'Equipos Agrícolas S.A.C.', contactoNombre: 'Carlos Mendoza', estadoActivo: true},
    estado: 'EN_REPARACION',
    estadoActivo: true,
  },
  {
    id: 4,
    codigo: 'EQ-00004',
    numeroSerie: 'SN-004',
    tipoEquipo: {id: 2, codigo: 'MONT-001', nombre: 'Montacargas'},
    proveedor: {id: 2, ruc: '20987654321', razonSocial: 'Maquinarias del Norte S.A.', contactoNombre: 'María López', estadoActivo: true},
    estado: 'BAJA',
    estadoActivo: false,
  },
  {
    id: 5,
    codigo: 'EQ-00005',
    numeroSerie: 'SN-005',
    tipoEquipo: {id: 1, codigo: 'TRANS-001', nombre: 'Transpaleta Eléctrica'},
    proveedor: {id: 1, ruc: '20123456789', razonSocial: 'Equipos Agrícolas S.A.C.', contactoNombre: 'Carlos Mendoza', estadoActivo: true},
    estado: 'OPERATIVO',
    estadoActivo: true,
  },
];

const mockCampanias: Campana[] = [
  {
    id: 1,
    codigo: 'UVA-2026',
    nombre: 'Campaña Uva 2026',
    tipo: 'UVA',
    fechaInicio: '2026-01-01',
    fechaFin: '2026-04-30',
    estado: 'ACTIVA',
    esActiva: true,
    estadoActivo: true,
  },
  {
    id: 2,
    codigo: 'PALTA-2026',
    nombre: 'Campaña Palta 2026',
    tipo: 'PALTA',
    fechaInicio: '2026-03-01',
    fechaFin: '2026-08-31',
    estado: 'ACTIVA',
    esActiva: true,
    estadoActivo: true,
  },
];

const mockPSRs: PSR[] = [
  {
    id: 1,
    numero: 'PSR-2026-0001',
    campana: mockCampanias[0],
    descripcion: 'Se requiere equipo para carga de uva',
    fechaSolicitud: '2026-01-10',
    estado: 'APROBADO',
    estadoActivo: true,
  },
  {
    id: 2,
    numero: 'PSR-2026-0002',
    campana: mockCampanias[0],
    descripcion: 'Montacargas para apilamiento de pallets',
    fechaSolicitud: '2026-01-15',
    estado: 'PENDIENTE',
    estadoActivo: true,
  },
];

const mockAverias: Averia[] = [
  {
    id: 1,
    codigo: 'AV-00001',
    descripcionFalla: 'Motor no responde',
    equipo: mockEquipos[2],
    fechaReporte: '2026-03-15T10:00:00',
    proveedor: {id: 1, ruc: '20123456789', razonSocial: 'Equipos Agrícolas S.A.C.', contactoNombre: 'Carlos Mendoza', estadoActivo: true},
    estadoActivo: true,
    observaciones: 'Fallo en el motor eléctrico',
  },
  {
    id: 2,
    codigo: 'AV-00002',
    descripcionFalla: 'Fuga de aceite hidráulico',
    equipo: mockEquipos[0],
    fechaReporte: '2026-04-01T08:00:00',
    proveedor: {id: 1, ruc: '20123456789', razonSocial: 'Equipos Agrícolas S.A.C.', contactoNombre: 'Carlos Mendoza', estadoActivo: true},
    estadoActivo: true,
    observaciones: 'Se observa fuga en el sistema hidráulico',
  },
];

const delay = (ms: number) => new Promise<void>(resolve => setTimeout(resolve, ms));

export const mockService = {
  async login(_authorizationCode: string, _redirectUri: string) {
    await delay(1000);
    return {
      success: true,
      message: 'Login exitoso',
      data: {
        token: 'mock-jwt-token-' + Date.now(),
        refreshToken: 'mock-refresh-token-' + Date.now(),
        expiresIn: 900,
        user: mockUser,
      },
      timestamp: new Date().toISOString(),
    };
  },

  async getEquipos(): Promise<Equipo[]> {
    await delay(500);
    return mockEquipos;
  },

  async getEquipoById(id: number): Promise<Equipo | undefined> {
    await delay(300);
    return mockEquipos.find(e => e.id === id);
  },

  async getCampanias(): Promise<Campana[]> {
    await delay(500);
    return mockCampanias;
  },

  async getPSRs(): Promise<PSR[]> {
    await delay(500);
    return mockPSRs;
  },

  async getAverias(): Promise<Averia[]> {
    await delay(500);
    return mockAverias;
  },

  async getCurrentUser(): Promise<AuthUserResponse> {
    await delay(200);
    return mockUser;
  },
};
