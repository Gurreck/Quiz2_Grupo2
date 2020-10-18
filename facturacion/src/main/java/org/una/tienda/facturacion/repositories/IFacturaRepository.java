package org.una.tienda.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.una.tienda.facturacion.entities.Factura;

public interface IFacturaRepository extends JpaRepository<Factura, Long>{

}
