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
public class ClienteDTO
{
    private Long id;
    private String direccion;
    private String email;
    private Boolean estado;
    private Date fechaModificacion;
    private Date fechaRegistro;
    private String nombre;
    private String telefono;
}

