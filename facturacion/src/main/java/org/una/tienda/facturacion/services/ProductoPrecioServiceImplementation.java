package org.una.tienda.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.ProductoPrecio;
import org.una.tienda.facturacion.repositories.IProductoPrecioRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

import java.util.Optional;

@Service
public class ProductoPrecioServiceImplementation implements IProductoPrecioService{

    @Autowired
    private IProductoPrecioRepository productoPrecioRepository;

    private Optional<ProductoPrecioDTO> oneToDto(Optional<ProductoPrecio> one) {
        if (one.isPresent()) {
            ProductoPrecioDTO ProductoPrecioDTO = MapperUtils.DtoFromEntity(one.get(),   ProductoPrecioDTO.class);
            return Optional.ofNullable(ProductoPrecioDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoPrecioDTO> findById(Long id) {
        return oneToDto(productoPrecioRepository.findById(id));

    }

    @Override
    @Transactional
    public ProductoPrecioDTO create(ProductoPrecioDTO ProductoPrecioDTO) {
        ProductoPrecio usuario = MapperUtils.EntityFromDto(ProductoPrecioDTO, ProductoPrecio.class);
        usuario = productoPrecioRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, ProductoPrecioDTO.class);
    }

    @Override
    @Transactional
    public Optional<ProductoPrecioDTO> update(ProductoPrecioDTO productoDTO, Long id) {
        if (productoPrecioRepository.findById(id).isPresent() && productoPrecioRepository.findById(id).get().getEstado() != false) {
            ProductoPrecio producto = MapperUtils.EntityFromDto(productoDTO, ProductoPrecio.class);
            producto = productoPrecioRepository.save(producto);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(producto, ProductoPrecioDTO.class));
        } else {
            return null;
        }

    }
    @Override
    @Transactional
    public void delete(Long id) {
        productoPrecioRepository.deleteById(id);
    }

}
