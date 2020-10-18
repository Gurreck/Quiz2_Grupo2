package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;

/**
 *
 * @author Esteban Vargas
 */
@SpringBootTest
public class FacturaDetalleServiceImplementationTests {
    
    @Autowired
    private IFacturaDetalleService facturaDetalleService;

    FacturaDetalleDTO facturaDetalleEjemplo;

    @BeforeEach
    public void setup() {
        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setDescuento_final(610.2);
                setCantidad(2.0);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaDetalleCorrectamente() {
 
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent() && facturaDetalleEncontrado.get().getCantidad() != 0) {
            FacturaDetalleDTO facturaDetalle = facturaDetalleEncontrado.get();
            assertEquals(facturaDetalleEjemplo.getId(), facturaDetalle.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeModificarUnaFacturaDetalleCorrectamente() {
        
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);
        
        facturaDetalleEjemplo.setDescuento_final(1000.0);
        facturaDetalleEjemplo.setCantidad(5.0);
   
        facturaDetalleService.update(facturaDetalleEjemplo, facturaDetalleEjemplo.getId());

        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent()) {
            if(facturaDetalleEncontrado.get().getEstado() != false && facturaDetalleEncontrado.get().getDescuento_final().equals(facturaDetalleEjemplo.getDescuento_final())
                    && facturaDetalleEncontrado.get().getCantidad().equals(facturaDetalleEjemplo.getCantidad())){
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
 
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaDetalleEncontrado.isPresent()) {
            
            facturaDetalleService.delete(facturaDetalleEncontrado.get().getId());
            if(facturaDetalleService.findById(facturaDetalleEncontrado.get().getId()) == null){
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
        if (facturaDetalleEjemplo != null) {
            if(facturaDetalleService.findById(facturaDetalleEjemplo.getId()) != null){
                facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            }
            
            facturaDetalleEjemplo = null;
        }

    }

}
