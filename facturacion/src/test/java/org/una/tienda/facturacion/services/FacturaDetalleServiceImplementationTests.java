package org.una.tienda.facturacion.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.dtos.FacturaDTO;
import org.una.tienda.facturacion.dtos.FacturaDetalleDTO;
import org.una.tienda.facturacion.dtos.ProductoDTO;
import org.una.tienda.facturacion.dtos.ProductoExistenciaDTO;
import org.una.tienda.facturacion.dtos.ProductoPrecioDTO;
import org.una.tienda.facturacion.exceptions.ProductoConDescuentoMayorAlPermitidoException;

/**
 *
 * @author Esteban Vargas
 */
@SpringBootTest
public class FacturaDetalleServiceImplementationTests {
    
    @Autowired
    private IFacturaDetalleService facturaDetalleService;
    
    @Autowired
    private IProductoService productoService;
    
    @Autowired
    private IProductoExistenciaService productoExistenciaService;
    
    @Autowired
    private IProductoPrecioService productoPrecioService;
    
    @Autowired
    private IClienteService clienteService;
    
    @Autowired
    private IFacturaService facturaService;

    FacturaDetalleDTO facturaDetalleEjemplo, facturaDetallePruebaConExtraDescuento;
    ProductoDTO productoPrueba;
    ProductoExistenciaDTO productoExistenciaPrueba;
    ProductoPrecioDTO productoPrecioPrueba;
    ClienteDTO clientePrueba;
    FacturaDTO facturaPrueba;

    @BeforeEach
    public void setup() {
        
        initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido();
        facturaDetalleEjemplo = new FacturaDetalleDTO() {
            {
               setCantidad(1.0);
                setProducto(productoPrueba);
                setFactura(facturaPrueba);
                setDescuento_final(productoPrecioPrueba.getDescuento_maximo() - 5);
            }
        };
    }

    private void initDataForSeEvitaFacturarUnProductoConDescuentoMayorAlPermitido() {
        productoPrueba = new ProductoDTO() {
            {
                setDescripcion("Producto De Ejemplo");
                setImpuesto(0.10);
            }
        };
        productoPrueba = productoService.create(productoPrueba);

 

        productoExistenciaPrueba = new ProductoExistenciaDTO() {
            {
                setProducto(productoPrueba);
                setCantidad(1.0);
            }
        };
        productoExistenciaPrueba = productoExistenciaService.create(productoExistenciaPrueba);

 

        productoPrecioPrueba = new ProductoPrecioDTO() {
            {
                setProducto(productoPrueba);
                setPrecio_colones(1000.0);
                setDescuento_maximo(10.0);
                setDescuento_promocional(2.0);
            }
        };
        productoPrecioPrueba = productoPrecioService.create(productoPrecioPrueba);

 

        clientePrueba = new ClienteDTO() {
            {
                setNombre("ClienteDePrueba");
            }
        };
        clientePrueba = clienteService.create(clientePrueba);

 

        facturaPrueba = new FacturaDTO() {
            {
                setCaja(991);
                setCliente(clientePrueba);
            }
        };
        facturaPrueba = facturaService.create(facturaPrueba);

 

        facturaDetallePruebaConExtraDescuento = new FacturaDetalleDTO() {
            {
                setCantidad(1.0);
                setProducto(productoPrueba);
                setFactura(facturaPrueba);
                setDescuento_final(productoPrecioPrueba.getDescuento_maximo() + 1);
            }
        };

    }
    
    @Test
    public void sePuedeCrearUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException {
 
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
    public void sePuedeModificarUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException{
        
        facturaDetalleEjemplo = facturaDetalleService.create(facturaDetalleEjemplo);
        
        facturaDetalleEjemplo.setDescuento_final(8.0);
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
    public void sePuedeEliminarUnaFacturaDetalleCorrectamente() throws ProductoConDescuentoMayorAlPermitidoException{
 
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
    
    @Test
    public void seEvitaFacturarUnProductoConDescuentoMayorAlPermitido() throws ProductoConDescuentoMayorAlPermitidoException{
        
        assertThrows(ProductoConDescuentoMayorAlPermitidoException.class,
                () -> {
                    facturaDetalleService.create(facturaDetallePruebaConExtraDescuento);
                }
        ); 
    }

    
    @AfterEach
    public void tearDown() {
        
        if (facturaDetalleEjemplo != null) {
            if(facturaDetalleEjemplo.getId() != null && facturaDetalleService.findById(facturaDetalleEjemplo.getId()) != null){
                facturaDetalleService.delete(facturaDetalleEjemplo.getId());
            }
            facturaDetalleEjemplo = null;
        }
        
        if (facturaDetallePruebaConExtraDescuento != null) {
            if(facturaDetallePruebaConExtraDescuento.getId() != null && facturaDetalleService.findById(facturaDetallePruebaConExtraDescuento.getId()) != null){
                facturaDetalleService.delete(facturaDetallePruebaConExtraDescuento.getId());
            }
            facturaDetallePruebaConExtraDescuento = null;
        }
        
        if (productoPrueba != null) {
            if(productoPrueba.getId() != null && productoService.findById(productoPrueba.getId()) != null){
                productoService.delete(productoPrueba.getId());
            }
            productoPrueba = null;
        }

        if (productoExistenciaPrueba != null) {
            if(productoExistenciaPrueba.getId() != null && productoExistenciaService.findById(productoExistenciaPrueba.getId()) != null){
                productoExistenciaService.delete(productoExistenciaPrueba.getId());
            }
            productoExistenciaPrueba = null;
        }

        if (productoPrecioPrueba != null) {
            if(productoPrecioPrueba.getId() != null && productoPrecioService.findById(productoPrecioPrueba.getId()) != null){
                productoPrecioService.delete(productoPrecioPrueba.getId());
            }
            productoPrecioPrueba = null;
        }

        if (clientePrueba != null) {
            if(clientePrueba.getId() != null && clienteService.findById(clientePrueba.getId()) != null){
                clienteService.delete(clientePrueba.getId());
            }
            clientePrueba = null;
        }
        
        if (facturaPrueba != null) {
            if(facturaPrueba.getId() != null && facturaService.findById(facturaPrueba.getId()) != null){
                facturaService.delete(facturaPrueba.getId());
            }
            facturaPrueba = null;
        }
       
       
    }

}
