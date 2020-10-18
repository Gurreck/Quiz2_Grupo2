package org.una.tienda.facturacion.services;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ProductoPrecioServiceImplementationTests {

    @Autowired
    private IProductoPrecioService productoPrecioService;

   ProductoPrecioDTO productoPrecioEjemplo;

    @BeforeEach
    public void setup() {
        productoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setDescuento_maximo(0.10);
                setDescuento_promocional(0.10);
                setEstado(false);
                setPrecio_colones(0.10);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoPrecioCorrectamente() {

        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

        Optional<ProductoPrecioDTO> productoEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (productoEncontrado.isPresent()) {
           ProductoPrecioDTO producto = productoEncontrado.get();
            assertEquals(productoPrecioEjemplo.getId(), producto.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnProductoPrecioCorrectamente() {

        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

        productoPrecioEjemplo.setDescuento_maximo(0.75);
        productoPrecioEjemplo.setDescuento_promocional(0.55);
        productoPrecioEjemplo.setPrecio_colones(5.00);


        productoPrecioService.update(productoPrecioEjemplo, productoPrecioEjemplo.getId());

        Optional<ProductoPrecioDTO> productoEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            if(productoEncontrado.get().getDescuento_maximo().equals(productoPrecioEjemplo.getDescuento_maximo())
                    && productoEncontrado.get().getDescuento_promocional().equals(productoPrecioEjemplo.getDescuento_promocional())
                        && productoEncontrado.get().getPrecio_colones().equals(productoPrecioEjemplo.getPrecio_colones())){
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
    public void sePuedeEliminarUnProductoPrecio() {

        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

        Optional<ProductoPrecioDTO> productoPrecioEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (productoPrecioEncontrado.isPresent()) {

            productoPrecioService.delete(productoPrecioEncontrado.get().getId());
            if(productoPrecioService.findById(productoPrecioEncontrado.get().getId()) == null){
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
        if (productoPrecioEjemplo != null) {
            if(productoPrecioService.findById(productoPrecioEjemplo.getId()) != null){
                productoPrecioService.delete(productoPrecioEjemplo.getId());
            }
            productoPrecioEjemplo = null;
        }

    }

}
