package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ProductoDTO;

/**
 *
 * @author Esteban Vargas
 */
@SpringBootTest
public class ProductoServiceImplementationTests {

    @Autowired
    private IProductoService productoService;

    ProductoDTO productoEjemplo;

    @BeforeEach
    public void setup() {
        productoEjemplo = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoCorrectamente() {
 
        productoEjemplo = productoService.create(productoEjemplo);

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoDTO producto = productoEncontrado.get();
            assertEquals(productoEjemplo.getId(), producto.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnProductoCorrectamente() {
        
        productoEjemplo = productoService.create(productoEjemplo);
        
        productoEjemplo.setDescripcion("Producto De Ejemplo Actualizado");
        productoEjemplo.setImpuesto(0.25);
 
        
        productoService.update(productoEjemplo, productoEjemplo.getId());

        Optional<ProductoDTO> productoEncontrado = productoService.findById(productoEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            if(productoEncontrado.get().getImpuesto().equals(productoEjemplo.getImpuesto())
                    && productoEncontrado.get().getDescripcion().equals(productoEjemplo.getDescripcion())){
                assert(true);
            }
            else{
                fail("No se actualizo la información en la BD");
            }
        }
        else{
            fail("No se encontro la información en la BD");
        }
       
    }
    
    @Test
    public void sePuedeEliminarUnaFacturaDetalleCorrectamente() {
 
        productoEjemplo = productoService.create(productoEjemplo);

        Optional<ProductoDTO> facturaDetalleEncontrado = productoService.findById(productoEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent()) {
            
            productoService.delete(facturaDetalleEncontrado.get().getId());
            if(productoService.findById(facturaDetalleEncontrado.get().getId()) == null){
                assert(true);
            }
            else{
                fail("No se elimino la información en la BD");
            }
        } else {
            fail("No se encontro la información en la BD");
        }
    }
    
    @AfterEach
    public void tearDown() {
        if (productoEjemplo != null) {
            if(productoService.findById(productoEjemplo.getId()) != null){
                productoService.delete(productoEjemplo.getId());
            }
            productoEjemplo = null;
        }

    }

}
