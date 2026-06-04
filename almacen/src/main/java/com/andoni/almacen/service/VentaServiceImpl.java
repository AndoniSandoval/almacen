package com.andoni.almacen.service;

import com.andoni.almacen.dto.ventas.VentaRequest;
import com.andoni.almacen.dto.ventas.VentaResponse;
import com.andoni.almacen.enums.EstadoVenta;
import com.andoni.almacen.mappers.VentaMapper;
import com.andoni.almacen.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class VentaServiceImpl implements VentaService{

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    @Override
    public List<VentaResponse> listar() {
        log.info("Listando ventas registradas");

        return ventaRepository
                .findByEstadoVenta(EstadoVenta.REGISTRADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    public VentaResponse obtenerPorId(Long id) {
        throw new UnsupportedOperationException("Pendiente implementar");
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        throw new UnsupportedOperationException("Pendiente implementar REGISTRO");
    }

    @Override
    public void cancelar(Long id) {
        throw new UnsupportedOperationException("Pendiente implementar");
    }
}
