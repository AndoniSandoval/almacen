package com.andoni.almacen.service;


import com.andoni.almacen.dto.sucursales.SucursalRequest;
import com.andoni.almacen.dto.sucursales.SucursalResponse;
import com.andoni.almacen.entities.Sucursal;
import com.andoni.almacen.exceptions.RecursoNoEncontradoException;
import com.andoni.almacen.mappers.SucursalMapper;
import com.andoni.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImplemt implements SucursalService{

    private final SucursalRepository sucursalRepository;

    private final SucursalMapper sucursalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll().stream().map(sucursalMapper::entidadAResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadAResponse(obtenerSucursalException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando Nueva Sucursal");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

        log.info("Sucursal registrada con id {}", sucursalGuardada.getId());

        return sucursalMapper.entidadAResponse(sucursalGuardada);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, long id) {
        Sucursal sucursal = obtenerSucursalException(id);

        validarCambiosUnicos(request,id);
        sucursal.actualizar(
                request.nombre(),
                request.direccion()
        );

        log.info("Sucursal con id {} actualizado correctamente", id);

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalException(id);
        log.info("Eliminando la sucursal con id: {}", id);
        sucursalRepository.delete(sucursal);
        log.info("Sucursal con id {} eliminada", id);
    }

    private Sucursal obtenerSucursalException(Long id){
        log.info("Buscando sucursal con id: {}", id);
        return sucursalRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Recurso no encontrado con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request){
        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nonbre de: " + request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id){
        log.info("Validando cambio en nombre unico...");
        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nonbre de: " + request.nombre());
    }
}
