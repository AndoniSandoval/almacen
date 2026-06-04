package com.andoni.almacen.controllers;

import com.andoni.almacen.dto.ventas.VentaResponse;
import com.andoni.almacen.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listar() {
        return ResponseEntity.ok(
                ventaService.listar()
        );
    }

    //obtener por id
    //cancelar
    //registrar
    //metodos de delete, post y update
}
