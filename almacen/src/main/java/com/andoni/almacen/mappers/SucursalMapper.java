package com.andoni.almacen.mappers;


import com.andoni.almacen.dto.sucursales.SucursalRequest;
import com.andoni.almacen.dto.sucursales.SucursalResponse;
import com.andoni.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal requestAEntidad(SucursalRequest request) {
        if (request == null) return null;

        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion().trim())
                .build();
    }
    public SucursalResponse entidadAResponse(Sucursal entidad){
        if (entidad == null)return null;

        return new SucursalResponse(
                entidad.getId(),
                entidad.getDireccion(),
                entidad.getNombre()
        );
    }
}
