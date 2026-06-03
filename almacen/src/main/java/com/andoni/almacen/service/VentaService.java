package com.andoni.almacen.service;

import com.andoni.almacen.dto.ventas.VentaRequest;
import com.andoni.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {

    List<VentaResponse> listar();

    VentaResponse obtenerPorId(Long id);

    VentaResponse registrar(VentaRequest request);

    void cancelar(Long id);
}
