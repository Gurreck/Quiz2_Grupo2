package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dtos.FacturaDTO;

/**
 *
 * @author Adrian
 */
public interface IFacturaService {
    
    public Optional<FacturaDTO> findById(Long id);
    
    public FacturaDTO create(FacturaDTO facturaDTO);
    
    public Optional<FacturaDTO> update(FacturaDTO facturaDTO, Long id);
    
    public void delete(Long id);
}
