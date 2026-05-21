import { useEffect, useState } from 'react';
import {
  Box, Typography, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Chip, TablePagination, CircularProgress,
} from '@mui/material';
import { psrApi } from '../api/endpoints';
import type { PSR } from '../types';

const estadoColor: Record<string, 'warning' | 'success' | 'error' | 'info' | 'default'> = {
  PENDIENTE: 'warning',
  APROBADO: 'success',
  RECHAZADO: 'error',
  ACTIVO: 'info',
  CERRADO: 'default',
};

export default function PSRPage() {
  const [data, setData] = useState<PSR[]>([]);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const pageSize = 20;

  useEffect(() => {
    setLoading(true);
    psrApi.list(page, pageSize)
      .then((paged) => { setData(paged.content); setTotal(paged.totalElements); })
      .finally(() => setLoading(false));
  }, [page]);

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" fontWeight={700} mb={3}>PSR / OSR</Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Número</TableCell>
              <TableCell>Campaña</TableCell>
              <TableCell>Sitio</TableCell>
              <TableCell>Descripción</TableCell>
              <TableCell>Fecha Solicitud</TableCell>
              <TableCell>Estado</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={6} align="center"><CircularProgress /></TableCell>
              </TableRow>
            ) : data.map((p) => (
              <TableRow key={p.id} hover>
                <TableCell>{p.numero}</TableCell>
                <TableCell>{p.campana?.nombre || '-'}</TableCell>
                <TableCell>{p.sitio?.nombre || '-'}</TableCell>
                <TableCell>{p.descripcion || '-'}</TableCell>
                <TableCell>{new Date(p.fechaSolicitud).toLocaleDateString('es-PE')}</TableCell>
                <TableCell>
                  <Chip label={p.estado} color={estadoColor[p.estado] || 'default'} size="small" />
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
