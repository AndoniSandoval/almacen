package com.andoni.almacen.repositories;

import com.andoni.almacen.entities.Venta;
import com.andoni.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>
{
    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    //Listar solamente REGISTRADAS
    //Obtener por id solamente REGISTRADS
    Optional<Venta> findByIdAndEstadoVenta(
            Long id,
            EstadoVenta estadoVenta
    );
}
