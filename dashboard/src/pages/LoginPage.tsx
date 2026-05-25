import { useState, useEffect } from 'react';
import { Box, Card, CardContent, Typography, TextField, Button, Alert } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { msalInstance, loginRequest } from '../auth/msal';

export default function LoginPage() {
  const { login, isLoading } = useAuth();
  const [code, setCode] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async () => {
    if (!code.trim()) { setError('Ingrese un token de Microsoft'); return; }
    setError('');
    try {
      await login(code.trim());
    } catch {
      setError('Error de autenticación. Verifique sus credenciales.');
    }
  };

  const handleMicrosoftLogin = async () => {
    setError('');
    try {
      // Use redirect so the user sees the Microsoft hosted login page.
      // The redirect flow will return to the app and `handleRedirectPromise`
      // in `main.tsx` will complete the exchange with the backend.
      await msalInstance.loginRedirect(loginRequest);
    } catch (err) {
      setError('Error de autenticación. Verifique sus credenciales.');
    }
  };

  useEffect(() => {
    // If user already has app token, don't redirect
    const token = localStorage.getItem('auth_token');
    if (!token) {
      // Automatically redirect to Microsoft login when visiting /login
      // but give user a brief chance to interact or cancel
      const t = setTimeout(() => {
        msalInstance.loginRedirect(loginRequest).catch(() => {});
      }, 300);
      return () => clearTimeout(t);
    }
  }, []);

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', bgcolor: '#f5f5f5' }}>
      <Card sx={{ maxWidth: 420, width: '100%', mx: 2 }}>
        <CardContent sx={{ p: 4 }}>
          <Typography variant="h4" fontWeight={700} color="primary" textAlign="center" gutterBottom>
            REP Control
          </Typography>
          <Typography variant="body2" color="text.secondary" textAlign="center" mb={3}>
            Equipos de Apilamiento - Dashboard
          </Typography>

          {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

          <Button
            fullWidth variant="contained" size="large"
            onClick={handleMicrosoftLogin} disabled={isLoading}
            sx={{ mb: 2 }}
          >
            {isLoading ? 'Ingresando...' : 'Ingresar con Microsoft'}
          </Button>
          <Typography variant="body2" color="text.secondary" sx={{ mb: 1, textAlign: 'center' }}>
            Si ya tienes un token Microsoft válido, puedes ingresarlo aquí:
          </Typography>
          <TextField
            fullWidth label="Token de Microsoft (fallback)" variant="outlined"
            value={code} onChange={(e) => setCode(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && handleLogin()}
            disabled={isLoading} sx={{ mb: 2 }}
          />
          <Button
            fullWidth variant="outlined" size="large"
            onClick={handleLogin} disabled={isLoading}
          >
            {isLoading ? 'Ingresando...' : 'Ingresar con token'}
          </Button>
        </CardContent>
      </Card>
    </Box>
  );
}
