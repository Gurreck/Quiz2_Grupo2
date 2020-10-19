package org.una.tienda.facturacion.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;
import org.una.tienda.facturacion.utils.MapperUtils;
import org.una.tienda.facturacion.dtos.*;
import org.una.tienda.facturacion.entities.Factura;
import org.una.tienda.facturacion.entities.Producto;
import org.una.tienda.facturacion.entities.ProductoPrecio;
import org.una.tienda.facturacion.utils.MapperUtils;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Adrian
 */
@SpringBootTest
public class FacturaServiceImplementationTests {
    
    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IFacturaDetalleService facturaDetalleService;

    @Autowired
    private IProductoPrecioService productoPrecioService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IProductoExistenciaService productoExistenciaService;

    FacturaDTO facturaEjemplo;
    ClienteDTO clienteEjemplo;

    FacturaDetalleDTO facturaDetalleEjemplo;

    ProductoPrecioDTO productoPrecioEjemplo;

    ProductoDTO productoEjemplo;

    ProductoExistenciaDTO productoExistenciaEjemplo;

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
        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
                setDescuento_final(610.2);
                setCantidad(2.0);
            }
        };
        productoPrecioEjemplo = new ProductoPrecioDTO() {
            {
                setDescuento_maximo(0.10);
                setDescuento_promocional(0.10);
                setEstado(false);
                setPrecio_colones(0.10);
            }
        };
        productoEjemplo=new ProductoDTO(){
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoExistenciaEjemplo=new ProductoExistenciaDTO(){
            {
                setCantidad(0.10);
                setEstado(false);
            }
        };
    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamente() {
 
        facturaEjemplo = facturaService.create(facturaEjemplo);

        Optional<FacturaDTO> facturaEncontrado2 = facturaService.findById(facturaEjemplo.getId());

        if (facturaEncontrado2.isPresent()) {
            FacturaDTO factura = facturaEncontrado2.get();
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

    @Test
    public void sePuedeCrearUnaFacturaCorrectamenteSiLaCantidadNoEsCero() throws ProductoConDescuentoMayorAlPermitidoException {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        facturaDetalleEjemplo.setFactura(facturaEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        Optional<FacturaDetalleDTO> facturaDetalleEncontrado = facturaDetalleService.findById(facturaDetalleEjemplo.getId());

        if (facturaEncontrado.isPresent()&&facturaDetalleEjemplo.getCantidad()!=0.0) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamenteSiElPrecioNoEsCero() throws ProductoConDescuentoMayorAlPermitidoException {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        productoEjemplo = productoService.create(productoEjemplo);

        productoPrecioEjemplo = productoPrecioService.create(productoPrecioEjemplo);

        facturaDetalleEjemplo.setFactura(facturaEjemplo);

        facturaDetalleEjemplo.setProducto(productoEjemplo);

        productoPrecioEjemplo.setProducto(productoEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        Optional<ProductoPrecioDTO> productoPrecioEncontrado = productoPrecioService.findById(productoPrecioEjemplo.getId());

        if (facturaEncontrado.isPresent()&& productoPrecioEjemplo.getPrecio_colones()!=0.0) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

        } else {
            fail("No se encontro la información en la BD");
        }
    }

    @Test
    public void sePuedeCrearUnaFacturaCorrectamenteSiElInventarioNoEsCero() throws ProductoConDescuentoMayorAlPermitidoException {

        facturaEjemplo = facturaService.create(facturaEjemplo);

        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);

        productoEjemplo = productoService.create(productoEjemplo);

        productoExistenciaEjemplo = productoExistenciaService.create(productoExistenciaEjemplo);

        facturaDetalleEjemplo.setFactura(facturaEjemplo);

        facturaDetalleEjemplo.setProducto(productoEjemplo);

        productoExistenciaEjemplo.setProducto(productoEjemplo);

        Optional<FacturaDTO> facturaEncontrado = facturaService.findById(facturaEjemplo.getId());

        Optional<ProductoExistenciaDTO> productoExistenciaEncontrado = productoExistenciaService.findById(productoExistenciaEjemplo.getId());

        if (facturaEncontrado.isPresent()&& productoExistenciaEjemplo.getCantidad()!=0.0) {
            FacturaDTO factura = facturaEncontrado.get();
            assertEquals(facturaEjemplo.getId(), factura.getId());

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
