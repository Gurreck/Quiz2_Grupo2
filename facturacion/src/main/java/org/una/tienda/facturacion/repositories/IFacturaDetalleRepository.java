package org.una.tienda.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.una.tienda.facturacion.entities.FacturaDetalle;

/**
 *
 * @author Esteban Vargas
 */
public interface IFacturaDetalleRepository extends JpaRepository<FacturaDetalle, Long>{
    
}
