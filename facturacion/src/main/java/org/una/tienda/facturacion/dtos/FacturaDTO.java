package org.una.tienda.facturacion.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Adrian
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FacturaDTO
{
    private Long id;
    private Integer caja;
    private Double descuentoGeneral;
    private Boolean estado;
    private Date fechaModificacion;
    private Date fechaRegistro;
    private ClienteDTO cliente;
}
