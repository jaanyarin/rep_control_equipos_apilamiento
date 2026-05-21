import { useEffect, useState } from 'react';
import {
  Box, Typography, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Chip, TablePagination, CircularProgress,
} from '@mui/material';
import { equipmentApi } from '../api/endpoints';
import type { Equipo } from '../types';

const estadoColor: Record<string, 'success' | 'info' | 'warning' | 'error' | 'default'> = {
  DISPONIBLE: 'success',
  OPERATIVO: 'info',
  EN_REPARACION: 'warning',
  AVERIADO: 'error',
  MANTENIMIENTO: 'warning',
  DEVUELTO: 'default',
  BAJA: 'error',
};

export default function EquipmentPage() {
  const [data, setData] = useState<Equipo[]>([]);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const pageSize = 20;

  useEffect(() => {
    setLoading(true);
    equipmentApi.list(page, pageSize)
      .then((paged) => { setData(paged.content); setTotal(paged.totalElements); })
      .finally(() => setLoading(false));
  }, [page]);

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" fontWeight={700} mb={3}>Equipos</Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Código</TableCell>
              <TableCell>Serie</TableCell>
              <TableCell>Marca/Modelo</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Proveedor</TableCell>
              <TableCell>Estado</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={6} align="center"><CircularProgress /></TableCell>
              </TableRow>
            ) : data.map((eq) => (
              <TableRow key={eq.id} hover>
                <TableCell>{eq.codigo}</TableCell>
                <TableCell>{eq.numeroSerie}</TableCell>
                <TableCell>{[eq.marca, eq.modelo].filter(Boolean).join(' - ') || '-'}</TableCell>
                <TableCell>{eq.tipoEquipo?.nombre || '-'}</TableCell>
                <TableCell>{eq.proveedor?.razonSocial || '-'}</TableCell>
                <TableCell>
                  <Chip
                    label={eq.estado}
                    color={estadoColor[eq.estado] || 'default'}
                    size="small"
                  />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <TablePagination
          component="div" count={total} page={page}
          onPageChange={(_, p) => setPage(p)}
          rowsPerPage={pageSize} rowsPerPageOptions={[pageSize]}
        />
      </TableContainer>
    </Box>
  );
}
