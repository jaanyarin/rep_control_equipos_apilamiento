export const API_BASE_URL = __DEV__
  ? 'http://10.0.2.2:8080'
  : 'https://api.repcontrol.com';

export const API_V1 = `${API_BASE_URL}/api/v1`;

export const AUTH_TOKEN_KEY = '@auth_token';
export const REFRESH_TOKEN_KEY = '@refresh_token';
export const SESSION_TIMEOUT = 15 * 60 * 1000;

export const MICROSOFT_AUTH_CONFIG = {
  issuer: 'https://login.microsoftonline.com/04e05ea3-3981-4a7b-9be2-17535cf34b8f/v2.0',
  clientId: '2953b82b-0891-41d0-99ec-6d2951f46851',
  redirectUrl: 'msal2953b82b-0891-41d0-99ec-6d2951f46851://auth',
  scopes: ['openid', 'profile', 'email', 'User.Read'],
};
