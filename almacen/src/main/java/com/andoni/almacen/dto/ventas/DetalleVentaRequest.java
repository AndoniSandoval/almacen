package com.andoni.almacen.dto.ventas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleVentaRequest(
        @NotNull(message = "El ID del producto es requerido")
        @Positive(message = "El ID del producto debe ser positivo")
        Long idProducto,

        @NotNull(message = "El ID del producto es requerido")
        @Positive(message = "El ID del producto debe ser positivo")
        Integer cantidadProducto
) {
}
