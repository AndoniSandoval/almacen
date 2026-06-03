package com.andoni.almacen.service;

import com.andoni.almacen.dto.productos.ProductoRequest;
import com.andoni.almacen.dto.productos.ProductoResponse;
import com.andoni.almacen.entities.Producto;
import com.andoni.almacen.enums.Categoria;
import com.andoni.almacen.exceptions.RecursoNoEncontradoException;
import com.andoni.almacen.mappers.ProductoMapper;
import com.andoni.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRespository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(
            String nombre, String categoria,
            BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando todos los productos");
        return productoRespository
                .findAll()
                .stream()
                .map(productoMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request)
    {
        log.info("Registrado nuevo producto...");

        Categoria categoria = Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim());

        Producto producto = productoMapper.requestAEntidad(request, categoria);

        productoRespository.save(producto);

        log.info("Nuevo producto {} registrado ", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerProductoException(id);
        log.info("Actualizando producto con id: {}", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()),
                request.precio(),
                request.cantidad()
        );

        log.info("Producto {} actualizado", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProductoException(id);

        log.info("Eliminando producto con id: {} ", id);

        productoRespository.delete(producto);

        log.info("Eliminando producto con id: {}", id);

    }

    private Producto obtenerProductoException(Long id) {
        log.info("Buscando producto por id: {} ", id);
        return productoRespository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Producto no encontrado con id:" + id));
    }
}
