package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;

import java.util.Optional;

public interface IProductoExistenciaService {

    public Optional<ProductoExistenciaDTO> findById(Long id);

    public ProductoExistenciaDTO create(ProductoExistenciaDTO productoExistenciaDTO);

    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO productoExistenciaDTO, Long id);

    public void delete(Long id);
}
