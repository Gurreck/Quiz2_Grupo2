package org.una.tienda.facturacion.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.una.tienda.facturacion.entities.Cliente;

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
    private int caja;
    private double descuentoGeneral;
    private boolean estado;
    private Date fechaModificacion;
    private Date fechaRegistro;
    private Cliente cliente;
}
