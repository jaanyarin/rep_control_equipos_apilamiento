import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Equipo } from '../../types';

interface EquipoState {
  equipos: Equipo[];
  selectedEquipo: Equipo | null;
  isLoading: boolean;
  error: string | null;
  total: number;
  page: number;
  pageSize: number;
}

const initialState: EquipoState = {
  equipos: [],
  selectedEquipo: null,
  isLoading: false,
  error: null,
  total: 0,
  page: 0,
  pageSize: 20,
};

const equipoSlice = createSlice({
  name: 'equipos',
  initialState,
  reducers: {
    fetchEquiposStart(state) {
      state.isLoading = true;
      state.error = null;
    },
    fetchEquiposSuccess(state, action: PayloadAction<{ equipos: Equipo[]; total: number }>) {
      state.equipos = action.payload.equipos;
      state.total = action.payload.total;
      state.isLoading = false;
      state.error = null;
    },
    fetchEquiposFailure(state, action: PayloadAction<string>) {
      state.isLoading = false;
      state.error = action.payload;
    },
    selectEquipo(state, action: PayloadAction<Equipo | null>) {
      state.selectedEquipo = action.payload;
    },
    setPage(state, action: PayloadAction<number>) {
      state.page = action.payload;
    },
  },
});

export const {
  fetchEquiposStart,
  fetchEquiposSuccess,
  fetchEquiposFailure,
  selectEquipo,
  setPage,
} = equipoSlice.actions;
export default equipoSlice.reducer;
