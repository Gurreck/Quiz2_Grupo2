package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.entities.Producto;
import org.una.tienda.facturacion.repositories.IProductoRepository;
import org.una.tienda.facturacion.utils.MapperUtils;



/**
 *
 * @author Esteban Vargas
 */
@Service
public class ProductoServiceImplementation implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;
    
    private Optional<ProductoDTO> oneToDto(Optional<Producto> one) {
        if (one.isPresent()) {
            ProductoDTO ProductoDTO = MapperUtils.DtoFromEntity(one.get(),   ProductoDTO.class);
            return Optional.ofNullable(ProductoDTO);
        } else {
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findById(Long id) {
        return oneToDto(productoRepository.findById(id));
    }

    @Override
    @Transactional
    public ProductoDTO create(ProductoDTO ProductoDTO) {
        Producto producto = MapperUtils.EntityFromDto(ProductoDTO, Producto.class);
        producto = productoRepository.save(producto);
        return MapperUtils.DtoFromEntity(producto, ProductoDTO.class);
    }

    @Override
    @Transactional
    public Optional<ProductoDTO> update(ProductoDTO productoDTO, Long id) {
        if (productoRepository.findById(id).isPresent() && productoRepository.findById(id).get().getEstado() != false) {
            Producto producto = MapperUtils.EntityFromDto(productoDTO, Producto.class);
            producto = productoRepository.save(producto);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(producto, ProductoDTO.class));
        } else {
            return null;
        } 

    }
    
   @Override
    @Transactional
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    
}
