package com.andoni.almacen.entities;

import com.andoni.almacen.enums.Categoria;
import com.andoni.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "PRODUCTOS")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "CATEGORIA", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void aumentarCantidad(int cantidad){
        if (cantidad < 0)
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad){
        if (this.cantidad < cantidad)
            throw new IllegalArgumentException("Cantidad insuficiente para descontar");
        this.cantidad -= cantidad;
    }

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    private void validarDatos(String nombre, String categoria, BigDecimal precio, Integer cantidad){
        StringCustomUtils.validarTamanio(nombre, 5,55, "El nombre es requerido y debe tener entre 5 y 55 caracteres");

        if (categoria == null)
            throw new IllegalArgumentException("La Cateoria es requerida");

        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("La Cateoria es requerida");

        if (cantidad == null || cantidad < 0)
            throw new IllegalArgumentException("La Cateoria es requerida");    }
}
