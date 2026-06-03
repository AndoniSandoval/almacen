package com.andoni.almacen.mappers;

import com.andoni.almacen.dto.ventas.DetalleVentaResponse;
import com.andoni.almacen.dto.ventas.VentaResponse;
import com.andoni.almacen.entities.DetalleVenta;
import com.andoni.almacen.entities.Venta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;

    public VentaResponse entidadAResponse (Venta venta){
        return null;
    }

    public DetalleVentaResponse detalleAResponse (DetalleVenta detalle) {
        return null;
    }
}
