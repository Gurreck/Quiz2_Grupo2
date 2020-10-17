package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dtos.ProductoDTO;

/**
 *
 * @author Esteban Vargas
 */
public interface IProductoService {
    
    public Optional<ProductoDTO> findById(Long id);
    
    public ProductoDTO create(ProductoDTO productoDTO);
    
    public Optional<ProductoDTO> update(ProductoDTO productoDTO, Long id);
    
    public void delete(Long id);
}
