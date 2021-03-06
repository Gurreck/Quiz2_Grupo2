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
public class ProductoPrecioDTO {
    private Long id;
    private Double descuento_maximo;
    private Double descuento_promocional;
    private Boolean estado;
    private Date fechaModificacion;
    private Date fechaRegistro;
    private Double precio_colones;
    private ProductoDTO producto;
}
