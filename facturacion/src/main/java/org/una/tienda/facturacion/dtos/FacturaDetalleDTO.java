package org.una.tienda.facturacion.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Esteban Vargas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor 
@ToString
public class FacturaDetalleDTO {
    
    private Long id; 
    private Double cantidad;   
    private Double descuento_final; 
    private Boolean estado; 
    private Date fechaRegistro; 
    private Date fechaModificacion; 
    private FacturaDTO factura; 
    private ProductoDTO producto;
}
