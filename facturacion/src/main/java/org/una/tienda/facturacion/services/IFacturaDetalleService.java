package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

/**
 *
 * @author Esteban Vargas
 */
public interface IFacturaDetalleService {
    
    public Optional<FacturaDetalleDTO> findById(Long id);
    
    public FacturaDetalleDTO create(FacturaDetalleDTO facturaDetalleDTO) throws ProductoConDescuentoMayorAlPermitidoException;
    
    public Optional<FacturaDetalleDTO> update(FacturaDetalleDTO facturaDetalleDTO, Long id);
    
    public void delete(Long id);
}
