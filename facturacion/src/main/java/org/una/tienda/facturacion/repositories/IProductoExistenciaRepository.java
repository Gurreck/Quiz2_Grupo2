package org.una.tienda.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.una.tienda.facturacion.entities.ProductoExistencia;

public interface IProductoExistenciaRepository extends JpaRepository<ProductoExistencia, Long> {
}
