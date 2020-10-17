package org.una.tienda.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.una.tienda.facturacion.entities.Producto;

/**
 *
 * @author Esteban Vargas
 */
public interface IProductoRepository extends JpaRepository<Producto, Long>{
    
}
