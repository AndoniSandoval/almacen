package com.andoni.almacen.enums;

import com.andoni.almacen.exceptions.RecursoNoEncontradoException;
import com.andoni.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA("Registrada", 1L),
    CANCELADA("Cancelada", 0L);

    private final String descripcion;

    private final Long codigo;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion) {

        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarTildes(descripcion.trim());

        for (EstadoVenta EstadoVenta: values()) {
            if (StringCustomUtils.quitarTildes(EstadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada)) {
                return EstadoVenta;
            }
        }

        throw new RecursoNoEncontradoException("No existe una estado venta con la descripcion: " + descripcion);
    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo) {

        for (EstadoVenta estadoVenta : values()) {
            if (Objects.equals(estadoVenta.codigo, codigo))
                return estadoVenta;
        }

        throw new RecursoNoEncontradoException("No existe una estado venta con la codigo: " + codigo);
    }
}
