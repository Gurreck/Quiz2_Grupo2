package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.entities.Factura;
import org.una.tienda.facturacion.repositories.IFacturaRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author Adrian
 */

@Service
public class FacturaServiceImplementation implements IFacturaService{
    
    @Autowired
    private IFacturaRepository facturaRepository;
    
    private Optional<FacturaDTO> oneToDto(Optional<Factura> one) {
        if (one.isPresent()) {
            FacturaDTO FacturaDTO = MapperUtils.DtoFromEntity(one.get(),   FacturaDTO.class);
            return Optional.ofNullable(FacturaDTO);
        } else {
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaDTO> findById(Long id) {
        return oneToDto(facturaRepository.findById(id));
    }

    @Override
    @Transactional
    public FacturaDTO create(FacturaDTO FacturaDTO) {
        Factura factura = MapperUtils.EntityFromDto(FacturaDTO, Factura.class);
        factura = facturaRepository.save(factura);
        return MapperUtils.DtoFromEntity(factura, FacturaDTO.class);
    }

    @Override
    @Transactional
    public Optional<FacturaDTO> update(FacturaDTO facturaDTO, Long id) {
        if (facturaRepository.findById(id).isPresent() && facturaRepository.findById(id).get().getEstado() != false 
                && facturaRepository.findById(id).get().getCliente().getEstado() != null) {
            Factura factura = MapperUtils.EntityFromDto(facturaDTO, Factura.class);
            factura = facturaRepository.save(factura);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(factura, FacturaDTO.class));
        } else {
            return null;
        } 
    }
    
   @Override
    @Transactional
    public void delete(Long id) {
        facturaRepository.deleteById(id);
    }

}
