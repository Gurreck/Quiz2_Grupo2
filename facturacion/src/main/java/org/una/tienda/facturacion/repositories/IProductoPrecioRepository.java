package org.una.tienda.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.una.tienda.facturacion.entities.ProductoPrecio;

public interface IProductoPrecioRepository extends JpaRepository<ProductoPrecio, Long>  {

}
