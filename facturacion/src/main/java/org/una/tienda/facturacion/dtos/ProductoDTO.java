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
public class ProductoDTO {
    
    private Long id; 
    private String descripcion;   
    private Boolean estado; 
    private Date fechaRegistro; 
    private Date fechaModificacion; 
    private Double impuesto;
}
