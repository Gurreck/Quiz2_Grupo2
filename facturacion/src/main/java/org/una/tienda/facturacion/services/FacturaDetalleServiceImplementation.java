package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.entities.FacturaDetalle;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;
import org.una.tienda.facturacion.repositories.IFacturaDetalleRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author Esteban Vargas
 */
@Service
public class FacturaDetalleServiceImplementation implements IFacturaDetalleService{
    
    @Autowired
    private IFacturaDetalleRepository facturaDetalleRepository;
    
    @Autowired
    private IProductoPrecioService productoPrecioService;
    
    private Optional<FacturaDetalleDTO> oneToDto(Optional<FacturaDetalle> one) {
        if (one.isPresent()) {
            FacturaDetalleDTO FacturaDetalleDTO = MapperUtils.DtoFromEntity(one.get(),   FacturaDetalleDTO.class);
            return Optional.ofNullable(FacturaDetalleDTO);
        } else {
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaDetalleDTO> findById(Long id) {
        return oneToDto(facturaDetalleRepository.findById(id));
    }

    @Override
    @Transactional
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalle) throws ProductoConDescuentoMayorAlPermitidoException {

        Optional<ProductoPrecioDTO> productoPrecio = productoPrecioService.findById(facturaDetalle.getProducto().getId());

        if (productoPrecio.isEmpty()) {
            //TODO:implementar verificar existencia de asignacion de precios
            return null;
        }
        if (facturaDetalle.getDescuento_final() > productoPrecio.get().getDescuento_maximo()) {
            throw new ProductoConDescuentoMayorAlPermitidoException("Se intenta facturar un producto con un descuento mayor al permitido");
        }
        FacturaDetalle usuario = MapperUtils.EntityFromDto(facturaDetalle, FacturaDetalle.class);
        usuario = facturaDetalleRepository.save(usuario);
        return MapperUtils.DtoFromEntity(usuario, FacturaDetalleDTO.class);
    }


    @Override
    @Transactional
    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO, Long id) {
        if (facturaDetalleRepository.findById(id).isPresent() && facturaDetalleRepository.findById(id).get().getEstado() != false) {
            FacturaDetalle facturaDetalle = MapperUtils.EntityFromDto(facturaDetalleDTO, FacturaDetalle.class);
            facturaDetalle = facturaDetalleRepository.save(facturaDetalle);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(facturaDetalle, FacturaDetalleDTO.class));
        } else {
            return null;
        } 
    }
    
   @Override
    @Transactional
    public void delete(Long id) {
        facturaDetalleRepository.deleteById(id);
    }
}
