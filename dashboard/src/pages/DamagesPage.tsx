import { useEffect, useState } from 'react';
import {
  Box, Typography, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Chip, IconButton, TablePagination,
  CircularProgress, Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button,
} from '@mui/material';
import { CheckCircleIcon } from '../components/Icons';
import { damagesApi } from '../api/endpoints';
import type { Averia } from '../types';

export default function DamagesPage() {
  const [data, setData] = useState<Averia[]>([]);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [closeId, setCloseId] = useState<number | null>(null);
  const [descripcion, setDescripcion] = useState('');
  const [accion, setAccion] = useState('');
  const pageSize = 20;

  const load = () => {
    setLoading(true);
    damagesApi.list(page, pageSize)
      .then((paged) => { setData(paged.content); setTotal(paged.totalElements); })
      .finally(() => setLoading(false));
  };

  useEffect(load, [page]);

  const handleClose = async () => {
    if (closeId === null) return;
    await damagesApi.close(closeId, descripcion, accion);
    setCloseId(null);
    setDescripcion('');
    setAccion('');
    load();
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" fontWeight={700} mb={3}>Averías</Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Código</TableCell>
              <TableCell>Equipo</TableCell>
              <TableCell>Falla</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Estado</TableCell>
              <TableCell>Fecha Reporte</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={7} align="center"><CircularProgress /></TableCell>
              </TableRow>
            ) : data.map((a) => (
              <TableRow key={a.id} hover>
                <TableCell>{a.codigo}</TableCell>
                <TableCell>{a.equipo?.codigo || '-'}</TableCell>
                <TableCell>{a.descripcionFalla}</TableCell>
                <TableCell>{a.tipoAveria?.nombre || '-'}</TableCell>
                <TableCell>
                  <Chip
                    label={a.estadoAveria?.nombre || 'SIN ESTADO'}
                    color={a.estadoAveria?.nombre === 'CERRADA' ? 'success' : 'warning'}
                    size="small"
                  />
                </TableCell>
                <TableCell>{new Date(a.fechaReporte).toLocaleDateString('es-PE')}</TableCell>
                <TableCell>
                  {a.estadoAveria?.nombre !== 'CERRADA' && (
                    <IconButton color="success" onClick={() => setCloseId(a.id)} title="Cerrar Avería">
                      <CheckCircleIcon />
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

      <Dialog open={closeId !== null} onClose={() => setCloseId(null)} maxWidth="sm" fullWidth>
        <DialogTitle>Cerrar Avería</DialogTitle>
        <DialogContent>
          <TextField
            fullWidth label="Descripción de atención" multiline rows={3}
            value={descripcion} onChange={(e) => setDescripcion(e.target.value)}
            sx={{ mt: 2 }}
          />
          <TextField
            fullWidth label="Acción correctiva" multiline rows={3}
            value={accion} onChange={(e) => setAccion(e.target.value)}
            sx={{ mt: 2 }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setCloseId(null)}>Cancelar</Button>
          <Button variant="contained" onClick={handleClose}>Cerrar</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
