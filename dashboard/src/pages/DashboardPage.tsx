import { useEffect, useState } from 'react';
import {
  Box, Grid, Card, CardContent, Typography, CircularProgress,
} from '@mui/material';
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
  PieChart, Pie, Cell, LineChart, Line, Legend,
} from 'recharts';
import { dashboardApi } from '../api/endpoints';
import type { KPI, Metrics } from '../types';

const COLORS = ['#4caf50', '#2196f3', '#ff9800', '#f44336', '#9c27b0', '#607d8b'];

function StatCard({ title, value, subtitle, color }: { title: string; value: string | number; subtitle?: string; color?: string }) {
  return (
    <Card sx={{ height: '100%' }}>
      <CardContent>
        <Typography variant="body2" color="text.secondary" gutterBottom>{title}</Typography>
        <Typography variant="h4" fontWeight={700} color={color || 'text.primary'}>{value}</Typography>
        {subtitle && <Typography variant="caption" color="text.secondary">{subtitle}</Typography>}
      </CardContent>
    </Card>
  );
}

export default function DashboardPage() {
  const [kpis, setKpis] = useState<KPI | null>(null);
  const [metrics, setMetrics] = useState<Metrics | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([dashboardApi.getKPIs(), dashboardApi.getMetrics()])
      .then(([kpiData, metricsData]) => {
        setKpis(kpiData);
        setMetrics(metricsData);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
        <CircularProgress />
      </Box>
    );
  }

  if (!kpis || !metrics) {
    return (
      <Box sx={{ p: 3 }}><Typography color="error">Error al cargar indicadores</Typography></Box>
    );
  }

  const estadoPie = Object.entries(metrics.equiposPorEstado).map(([name, value]) => ({ name, value }));
  const evolucion = metrics.evolucionMensual;

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" fontWeight={700} mb={3}>Dashboard</Typography>

      <Grid container spacing={2} mb={3}>
        <Grid item xs={12} sm={6} md={3}><StatCard title="Equipos Activos" value={kpis.equiposActivos} color="primary.main" /></Grid>
        <Grid item xs={12} sm={6} md={3}><StatCard title="Disponibilidad" value={`${kpis.disponibilidad}%`} color="success.main" /></Grid>
        <Grid item xs={12} sm={6} md={3}><StatCard title="Averías Abiertas" value={kpis.averiasAbiertas} color="error.main" /></Grid>
        <Grid item xs={12} sm={6} md={3}><StatCard title="Tiempo Prom. Atención" value={`${kpis.tiempoPromedioAtencion}h`} color="warning.main" /></Grid>
      </Grid>

      <Grid container spacing={2} mb={3}>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard title="Total Equipos" value={metrics.totalEquipos} />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard title="Campañas" value={metrics.totalCampañas} />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard title="Proveedores" value={metrics.totalProveedores} />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard title="Usuarios" value={metrics.totalUsuarios} />
        </Grid>
      </Grid>

      <Grid container spacing={2}>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" fontWeight={600} mb={2}>Equipos por Tipo</Typography>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={kpis.porTipo}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="tipo" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="total" name="Total" fill="#1976d2" />
                  <Bar dataKey="disponibles" name="Disponibles" fill="#4caf50" />
                </BarChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" fontWeight={600} mb={2}>Equipos por Estado</Typography>
              <ResponsiveContainer width="100%" height={300}>
                <PieChart>
                  <Pie data={estadoPie} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={100} label>
                    {estadoPie.map((_, i) => <Cell key={i} fill={COLORS[i % COLORS.length]} />)}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card>
            <CardContent>
              <Typography variant="h6" fontWeight={600} mb={2}>Evolución Mensual</Typography>
              <ResponsiveContainer width="100%" height={300}>
                <LineChart data={evolucion}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="mes" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="equipos" name="Equipos" stroke="#1976d2" strokeWidth={2} />
                  <Line type="monotone" dataKey="averias" name="Averías" stroke="#f44336" strokeWidth={2} />
                </LineChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
}
