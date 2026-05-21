import { useState } from 'react';
import { Box, Card, CardContent, Typography, TextField, Button, Alert } from '@mui/material';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const { login, isLoading } = useAuth();
  const [code, setCode] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async () => {
    if (!code.trim()) { setError('Ingrese un código de autorización'); return; }
    setError('');
    try {
      await login(code.trim());
    } catch {
      setError('Error de autenticación. Verifique sus credenciales.');
    }
  };

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

          <TextField
            fullWidth label="Código de autorización" variant="outlined"
            value={code} onChange={(e) => setCode(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && handleLogin()}
            disabled={isLoading} sx={{ mb: 2 }}
          />
          <Button
            fullWidth variant="contained" size="large"
            onClick={handleLogin} disabled={isLoading}
          >
            {isLoading ? 'Ingresando...' : 'Ingresar'}
          </Button>
        </CardContent>
      </Card>
    </Box>
  );
}
