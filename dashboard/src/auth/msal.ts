import { PublicClientApplication } from '@azure/msal-browser';

const tenantId = '04e05ea3-3981-4a7b-9be2-17535cf34b8f';
const clientId = '2953b82b-0891-41d0-99ec-6d2951f46851';

export const msalConfig = {
  auth: {
    clientId,
    authority: `https://login.microsoftonline.com/${tenantId}/v2.0`,
    redirectUri: window.location.origin,
  },
  cache: {
    cacheLocation: 'localStorage',
    storeAuthStateInCookie: false,
  },
};

export const loginRequest = {
  scopes: ['openid', 'profile', 'email', 'User.Read'],
};

export const msalInstance = new PublicClientApplication(msalConfig);
