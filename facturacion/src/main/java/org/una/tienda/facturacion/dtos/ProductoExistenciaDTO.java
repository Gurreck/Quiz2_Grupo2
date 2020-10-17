package org.una.tienda.facturacion.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoExistenciaDTO {
    private Long id;
    private Double cantidad;
    private boolean estado;
    private Date fechaModificacion;
    private Date fechaRegistro;
    //private Producto producto;
}
