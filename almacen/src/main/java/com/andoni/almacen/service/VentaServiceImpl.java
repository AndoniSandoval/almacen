package com.andoni.almacen.service;

import com.andoni.almacen.dto.ventas.DetalleVentaRequest;
import com.andoni.almacen.dto.ventas.VentaRequest;
import com.andoni.almacen.dto.ventas.VentaResponse;
import com.andoni.almacen.entities.DetalleVenta;
import com.andoni.almacen.entities.Producto;
import com.andoni.almacen.entities.Sucursal;
import com.andoni.almacen.entities.Venta;
import com.andoni.almacen.enums.EstadoVenta;
import com.andoni.almacen.exceptions.RecursoNoEncontradoException;
import com.andoni.almacen.mappers.VentaMapper;
import com.andoni.almacen.repositories.ProductoRepository;
import com.andoni.almacen.repositories.SucursalRepository;
import com.andoni.almacen.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class VentaServiceImpl implements VentaService{

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    @Override
    public List<VentaResponse> listar() {
        log.info("Listando ventas registradas");

        return ventaRepository
                .findByEstadoVenta(EstadoVenta.REGISTRADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    public List<VentaResponse> listarCanceladas() {
        log.info("Listando ventas canceladas...");

        return ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    public VentaResponse obtenerPorId(Long id) {
        log.info("Obteniendo por id: {}", id);

        Venta venta = ventaRepository.findByIdAndEstadoVenta(
                id,
                EstadoVenta.REGISTRADA
        ).orElseThrow(() ->
                new RecursoNoEncontradoException("Venta no encontrado con id: " + id)
        );

        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando nueva venta...");

        Sucursal sucursal = sucursalRepository
                .findById(request.idSucursal())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Sucursal no encontrada con id:" + request.idSucursal()));

        Venta venta = Venta.builder()
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .sucursal(sucursal)
                .build();

        for (DetalleVentaRequest detalleRequest : request.productos()) {

            Producto producto = productoRepository.findById(
                            detalleRequest.idProducto())
                    .orElseThrow(() ->
                            new RecursoNoEncontradoException(
                                    "Producto no encontrado con id: "
                                            + detalleRequest.idProducto()));

            producto.descontarCantidad(detalleRequest.cantidadProducto());

            DetalleVenta detalleVenta = DetalleVenta.builder()
                    .producto(producto)
                    .cantidadProducto(detalleRequest.cantidadProducto())
                    .precioProducto(producto.getPrecio())
                    .build();

            venta.agregarDetalle(detalleVenta);
        }

        Venta ventaGuardada = ventaRepository.save(venta);

        log.info("Venta registrada correctamente con id: {}",
                ventaGuardada.getId());

        return ventaMapper.entidadAResponse(ventaGuardada);
    }

    @Override
    public void cancelar(Long id) {
        log.info("Cancelando venta con id: {}" + id);

        Venta venta = ventaRepository.findByIdAndEstadoVenta(
                id, EstadoVenta.REGISTRADA)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Venta no encontrada con id: " + id));

        for (DetalleVenta detalle : venta.getDetalleVenta()) {

            Producto producto = detalle.getProducto();

            producto.aumentarCantidad(detalle.getCantidadProducto());
        }

        venta.cancelada();

        ventaRepository.save(venta);

        log.info("Venta cancelada correctamente");
    }
}
