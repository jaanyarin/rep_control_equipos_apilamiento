import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import equipoReducer from './slices/equipoSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    equipos: equipoReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
