package org.una.tienda.facturacion.services;

import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;

import java.util.Optional;

public interface IProductoPrecioService {
    public Optional<ProductoPrecioDTO> findById(Long id);

    public ProductoPrecioDTO create(ProductoPrecioDTO productoPrecioDTO);

    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO productoPrecioDTO, Long id);

    public void delete(Long id);
}
