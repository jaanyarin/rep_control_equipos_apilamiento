import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import App from './App';
import { msalInstance } from './auth/msal';
import { authApi } from './api/endpoints';

// Handle MSAL redirect response (when using loginRedirect)
msalInstance.handleRedirectPromise().then(async (resp) => {
  try {
    const idToken = (resp as any)?.idToken;
    if (idToken) {
      // Exchange idToken with backend to obtain app JWT
      const r = await authApi.login(idToken);
      const data = r.data.data;
      localStorage.setItem('auth_token', data.token);
      localStorage.setItem('refresh_token', data.refreshToken);
      localStorage.setItem('auth_user', JSON.stringify(data.user));
      // redirect to app root after successful login
      window.location.href = '/';
    }
  } catch (e) {
    // ignore and let app render login
    // console.error('MSAL redirect processing failed', e);
  }
}).catch(() => {
  // ignore
});

const theme = createTheme({
  palette: {
    primary: { main: '#1976d2' },
    secondary: { main: '#dc004e' },
  },
  typography: {
    fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
  },
  shape: { borderRadius: 8 },
});

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <App />
    </ThemeProvider>
  </StrictMode>,
);
