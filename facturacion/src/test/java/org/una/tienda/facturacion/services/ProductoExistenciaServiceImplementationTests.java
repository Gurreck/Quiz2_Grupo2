package org.una.tienda.facturacion.services;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;

@SpringBootTest
public class ProductoExistenciaServiceImplementationTests {

    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    ProductoExistenciaDTO productoExistenciaEjemplo;

    @BeforeEach
    public void setup() {
        productoExistenciaEjemplo = new ProductoExistenciaDTO() {
            {
                setCantidad(0.10);
                setEstado(false);
            }
        };
    }

    @Test
    public void sePuedeCrearUnProductoExistenciaCorrectamente() {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        Optional<ProductoExistenciaDTO> productoEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            ProductoExistenciaDTO producto = productoEncontrado.get();
            assertEquals(productoExistenciaEjemplo.getId(), producto.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnProductoExistenciaCorrectamente() {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        productoExistenciaEjemplo.setCantidad(0.20);

        productoExistenciaService.update(productoExistenciaEjemplo, productoExistenciaEjemplo.getId());

        Optional<ProductoExistenciaDTO> productoEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoEncontrado.isPresent()) {
            if(productoEncontrado.get().getEstado() != false && productoEncontrado.get().getCantidad().equals(productoExistenciaEjemplo.getCantidad()))  {
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
    public void sePuedeEliminarUnProductoExistenciaCorrectamente() {

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        Optional<ProductoExistenciaDTO> productoExistenciaEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (productoExistenciaEncontrado.isPresent()) {

            productoExistenciaService.delete(productoExistenciaEncontrado.get().getId());
            if(productoExistenciaService.findById(productoExistenciaEncontrado.get().getId()) == null){
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
        if (productoExistenciaEjemplo != null) {
            if(productoExistenciaService.findById(productoExistenciaEjemplo.getId()) != null){
                productoExistenciaService.delete(productoExistenciaEjemplo.getId());
            }
            productoExistenciaEjemplo = null;
        }

    }

}
