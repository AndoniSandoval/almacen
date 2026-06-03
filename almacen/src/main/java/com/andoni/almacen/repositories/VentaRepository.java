package com.andoni.almacen.repositories;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaAttributeConverter
{
}
