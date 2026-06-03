package com.andoni.almacen.service;

import com.andoni.almacen.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class VentaServiceImpl {

    private VentaRepository ventaRepository;
}
