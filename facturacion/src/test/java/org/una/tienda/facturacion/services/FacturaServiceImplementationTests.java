package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.utils.MapperUtils;

/**
 *
 * @author Adrian
 */
@SpringBootTest
public class FacturaServiceImplementationTests {
    
    @Autowired
    private IFacturaService facturaService;

    FacturaDTO facturaEjemplo;
    ClienteDTO clienteEjemplo;

    @BeforeEach
    public void setup() {
        
        clienteEjemplo = new ClienteDTO() ;
        clienteEjemplo.setNombre("Juan Pablo");
        clienteEjemplo.setTelefono("4643213");
        clienteEjemplo.setEmail("juan.com");
        clienteEjemplo.setDireccion("SJ");
        
        facturaEjemplo = new FacturaDTO() {
            {
                 setDescuentoGeneral(5000.0);
                 setCaja(5);
                // setCliente(MapperUtils.EntityFromDto(clienteEjemplo, Cliente.class));
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamente() {
 
        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

   @Test
    public void sePuedeModificarUnaFacturaCorrectamente() {
        
        facturaEjemplo = facturaService.create(facturaEjemplo);
        
        facturaEjemplo.setDescuentoGeneral(500.0);
        facturaEjemplo.setCaja(5);
   
        facturaService.update(facturaEjemplo, facturaEjemplo.getId());

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());
              
        if (facturaEncontrado.isPresent()) {
            if(facturaEncontrado.get().getEstado() != false && facturaEncontrado.get().getDescuentoGeneral().equals(facturaEjemplo.getDescuentoGeneral())
                    && facturaEncontrado.get().getCaja() == facturaEjemplo.getCaja()){
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
    public void sePuedeEliminarUnaFacturaCorrectamente() {
 
        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado.isPresent()) {
            
            facturaService.delete(facturaEncontrado.get().getId());
            if(facturaService.findById(facturaEncontrado.get().getId()) == null){
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
        if (facturaEjemplo != null) {
            if(facturaService.findById(facturaEjemplo.getId()) != null){
                facturaService.delete(facturaEjemplo.getId());
            }
            
            facturaEjemplo = null;
        }
    }

}
