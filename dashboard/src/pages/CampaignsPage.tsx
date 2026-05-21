import { useEffect, useState } from 'react';
import {
  Box, Typography, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Chip, IconButton, TablePagination, CircularProgress,
} from '@mui/material';
import { CheckCircleIcon, CancelIcon } from '../components/Icons';
import { campaignApi } from '../api/endpoints';
import type { Campana } from '../types';

export default function CampaignsPage() {
  const [data, setData] = useState<Campana[]>([]);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const pageSize = 20;

  const load = () => {
    setLoading(true);
    campaignApi.list(page, pageSize)
      .then((paged) => { setData(paged.content); setTotal(paged.totalElements); })
      .finally(() => setLoading(false));
  };

  useEffect(load, [page]);

  const handleActivate = async (id: number) => {
    await campaignApi.activate(id);
    load();
  };

  const handleClose = async (id: number) => {
    await campaignApi.close(id);
    load();
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" fontWeight={700} mb={3}>Campañas</Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Código</TableCell>
              <TableCell>Nombre</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Sitio</TableCell>
              <TableCell>Inicio</TableCell>
              <TableCell>Fin</TableCell>
              <TableCell>Estado</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={8} align="center"><CircularProgress /></TableCell>
              </TableRow>
            ) : data.map((c) => (
              <TableRow key={c.id} hover>
                <TableCell>{c.codigo}</TableCell>
                <TableCell>{c.nombre}</TableCell>
                <TableCell>{c.tipo}</TableCell>
                <TableCell>{c.sitio?.nombre || '-'}</TableCell>
                <TableCell>{new Date(c.fechaInicio).toLocaleDateString('es-PE')}</TableCell>
                <TableCell>{c.fechaFin ? new Date(c.fechaFin).toLocaleDateString('es-PE') : '-'}</TableCell>
                <TableCell>
                  <Chip label={c.estado} color={c.esActiva ? 'success' : 'default'} size="small" />
                </TableCell>
                <TableCell>
                  {!c.esActiva && (
                    <IconButton color="success" onClick={() => handleActivate(c.id)} title="Activar">
                      <CheckCircleIcon />
                    </IconButton>
                  )}
                  {c.esActiva && (
                    <IconButton color="error" onClick={() => handleClose(c.id)} title="Cerrar">
                      <CancelIcon />
                    </IconButton>
                  )}
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
