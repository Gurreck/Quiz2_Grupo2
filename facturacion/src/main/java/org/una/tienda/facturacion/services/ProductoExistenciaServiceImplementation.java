package org.una.tienda.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.entities.Producto;
import org.una.tienda.facturacion.entities.ProductoExistencia;
import org.una.tienda.facturacion.repositories.IProductoExistenciaRepository;
import org.una.tienda.facturacion.utils.MapperUtils;
import java.util.Optional;
@Service
public class ProductoExistenciaServiceImplementation implements IProductoExistenciaService {

    @Autowired
    private IProductoExistenciaRepository productoExistenciaRepository;

    private Optional<ProductoExistenciaDTO> oneToDto(Optional<ProductoExistencia> one) {
        if (one.isPresent()) {
            ProductoExistenciaDTO ProductoExistenciaDTO = MapperUtils.DtoFromEntity(one.get(),   ProductoExistenciaDTO.class);
            return Optional.ofNullable(ProductoExistenciaDTO);
        } else {
            return null;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoExistenciaDTO> findById(Long id) {
        return oneToDto(productoExistenciaRepository.findById(id));

    }

    @Override
    @Transactional
    public ProductoExistenciaDTO create(ProductoExistenciaDTO ProductoExistenciaDTO) {
        ProductoExistencia usuario = MapperUtils.EntityFromDto(ProductoExistenciaDTO, ProductoExistencia.class);
        usuario = productoExistenciaRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, ProductoExistenciaDTO.class);
    }

    @Override
    @Transactional
    public Optional<ProductoExistenciaDTO> update(ProductoExistenciaDTO productoExistenciaDTO, Long id) {
        if (productoExistenciaRepository.findById(id).isPresent()) {
            ProductoExistencia producto = MapperUtils.EntityFromDto(productoExistenciaDTO, ProductoExistencia.class);
            producto = productoExistenciaRepository.save(producto);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(producto, ProductoExistenciaDTO.class));
        } else {
            return null;
        }

    }

    @Override
    @Transactional
    public void delete(Long id) {
        productoExistenciaRepository.deleteById(id);
    }

}
