package org.una.tienda.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.una.tienda.facturacion.entities.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long>{

}
