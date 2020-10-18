package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.una.tienda.facturacion.dtos.ClienteDTO;

@SpringBootTest
public class ClienteServiceImplementationTests {

    @Autowired
    private IClienteService clienteService;

    ClienteDTO clienteEjemplo;
    Optional<ClienteDTO> clienteEjemplo2;
    
    @BeforeEach
    public void setup() {
        clienteEjemplo = new ClienteDTO() {
            {
                setDireccion("Direccion de ejemplo");
                setNombre("Cliente de ejemplo");
                setEmail("hola.com");
                setTelefono("869546");
            }
        };
    }

    @Test
    public void sePuedeCrearUnClienteCorrectamente() {

        clienteEjemplo = clienteService.create(clienteEjemplo);
        
        if (clienteEjemplo.getTelefono() != null && clienteEjemplo.getEmail() != null && clienteEjemplo.getDireccion() != null) 
        {
            Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getId());

            if (clienteEncontrado.isPresent()) {
                ClienteDTO cliente = clienteEncontrado.get();
                assertEquals(clienteEjemplo.getId(), cliente.getId());

            } else {
                fail("No se encontro la información en la BD");
             }
        }
        else {
            fail("No se pudo crear el cliente en la BD");
        }
    }
    
    @Test
    public void sePuedeModificarUnClienteCorrectamente() {
        
        clienteEjemplo = clienteService.create(clienteEjemplo);
        
        clienteEjemplo.setDireccion("Direccion actualizada");
        clienteEjemplo.setNombre("Nombre cliente actualizadp");
  
        clienteService.update(clienteEjemplo, clienteEjemplo.getId());

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (clienteEncontrado.isPresent()) {
            if(clienteEncontrado.get().getEstado() != false && clienteEncontrado.get().getNombre().equals(clienteEjemplo.getNombre())
                    && clienteEncontrado.get().getDireccion().equals(clienteEjemplo.getDireccion())){
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
    public void sePuedeEliminarUnClienteCorrectamente() {
 
        clienteEjemplo = clienteService.create(clienteEjemplo);

        Optional<ClienteDTO> clienteEncontrado = clienteService.findById(clienteEjemplo.getId());

        if (clienteEncontrado.isPresent()) {
            
            clienteService.delete(clienteEncontrado.get().getId());
            if(clienteService.findById(clienteEncontrado.get().getId()) == null){
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
        if (clienteEjemplo != null) {
            if(clienteService.findById(clienteEjemplo.getId()) != null){
                clienteService.delete(clienteEjemplo.getId());
            }
            
            clienteEjemplo = null;
        }
    }
}
