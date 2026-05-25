import AsyncStorage from '@react-native-async-storage/async-storage';
import api from './api';
import { API_V1, AUTH_TOKEN_KEY, REFRESH_TOKEN_KEY } from '../constants';
import { ApiResponse, AuthTokenResponse, AuthLoginRequest } from '../types';

const AUTH_BASE = `${API_V1}/auth`;

export const authService = {
  async login(data: AuthLoginRequest): Promise<AuthTokenResponse> {
    const response = await api.post<ApiResponse<AuthTokenResponse>>(
      `${AUTH_BASE}/login`,
      data,
    );
    const result = response.data.data;
    await AsyncStorage.setItem(AUTH_TOKEN_KEY, result.token);
    await AsyncStorage.setItem(REFRESH_TOKEN_KEY, result.refreshToken);
    return result;
  },

  async logout(): Promise<void> {
    try {
      await api.post(`${AUTH_BASE}/logout`);
    } finally {
      await AsyncStorage.removeItem(AUTH_TOKEN_KEY);
      await AsyncStorage.removeItem(REFRESH_TOKEN_KEY);
    }
  },

  async refresh(refreshToken: string): Promise<AuthTokenResponse> {
    const response = await api.post<ApiResponse<AuthTokenResponse>>(
      `${AUTH_BASE}/refresh`,
      { refreshToken },
    );
    const result = response.data.data;
    await AsyncStorage.setItem(AUTH_TOKEN_KEY, result.token);
    await AsyncStorage.setItem(REFRESH_TOKEN_KEY, result.refreshToken);
    return result;
  },

  async getToken(): Promise<string | null> {
    return AsyncStorage.getItem(AUTH_TOKEN_KEY);
  },

  async isAuthenticated(): Promise<boolean> {
    const token = await AsyncStorage.getItem(AUTH_TOKEN_KEY);
    return token !== null;
  },
};
