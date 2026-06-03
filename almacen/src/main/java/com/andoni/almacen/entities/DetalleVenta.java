package com.andoni.almacen.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "DETALLE_VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA", nullable = false)
    private Venta venta;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto producto;

    @JoinColumn(name = "CANTIDAD_PRODUCTO", nullable = false)
    private Integer cantidadProducto;

    @JoinColumn(name = "PRECIO_PRODUCTO", nullable = false)
    private BigDecimal precioProducto;

    public BigDecimal calcularSubtotal() {
        return this.precioProducto.multiply(
                BigDecimal.valueOf(this.cantidadProducto)
        );
    }
}
