package com.andoni.almacen.mappers;

import com.andoni.almacen.dto.ventas.DetalleVentaResponse;
import com.andoni.almacen.dto.ventas.VentaResponse;
import com.andoni.almacen.entities.DetalleVenta;
import com.andoni.almacen.entities.Venta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;

    public VentaResponse entidadAResponse (Venta venta){
        if (venta == null) return null;

        List<DetalleVentaResponse> detalles =
                venta.getDetalleVenta()
                        .stream()
                        .map(this::detalleAResponse)
                        .toList();

        BigDecimal total =
                venta.getDetalleVenta()
                        .stream()
                        .map(DetalleVenta::calcularSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                detalles,
                total
        );
    }

    public DetalleVentaResponse detalleAResponse (DetalleVenta detalle) {
        if (detalle == null) return null;

        return new DetalleVentaResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                detalle.calcularSubtotal()
        );
    }
}
