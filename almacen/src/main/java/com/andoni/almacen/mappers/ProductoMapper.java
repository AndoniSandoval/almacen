package com.andoni.almacen.mappers;

import com.andoni.almacen.dto.productos.ProductoRequest;
import com.andoni.almacen.dto.productos.ProductoResponse;
import com.andoni.almacen.entities.Producto;
import com.andoni.almacen.enums.Categoria;


public class ProductoMapper {
    public Producto requestAEntidad(ProductoRequest request, Categoria categoria){
        if (request == null)return null;

        return Producto.builder()
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .precio(request.precio())
                .cantidad(request.cantidad())
                .build();
    }

    public ProductoResponse entidadAResponse(Producto entidad){
        if (entidad == null) return null;

        return new ProductoResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getCategoria().getDescripcion(),
                entidad.getPrecio(),
                entidad.getCantidad()
        );
    }
}

