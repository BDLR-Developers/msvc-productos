package cl.duoc.msvc_productos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msvc_productos.model.Producto;
import cl.duoc.msvc_productos.repositories.ProductoRepository;
import cl.duoc.msvc_productos.services.ProductoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductoTest {
    

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoServiceImpl service;

    Producto prod =  new Producto(1,"Freestanding Wine Rack" ,"Stylish wine rack to store and display bottles.",
    "84-535-9199",5000,null,null,3);


    @Test
    void traerProducto_RespuestaOk(){

    Integer id = 1;

    when(repository.findById(id)).thenReturn(Optional.of(prod));

    Optional<Producto> resultado = service.findById(1);

    assertEquals(prod.getNombreProducto(), resultado.get().getNombreProducto());

    }

    @Test
    void traerProductoInexistente() {

        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Producto> resultado = service.findById(1);

        assertTrue(resultado.isEmpty(), "Se esperaba un Optional vacío");
        verify(repository).findById(1);

    }

    @Test
    void guardarProductoTest(){
        when(repository.save(any(Producto.class))).thenReturn(prod);

        Producto resultado = service.guardar(prod);

        assert resultado != null;
        verify(repository).save(resultado);

    }

    @Test
    void testUpdateProducto_Existe() {
        Integer id = 1;
        Producto productoActualizado = new Producto();
        productoActualizado.setNombreProducto("Nuevo Nombre");

        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(id);
        productoExistente.setNombreProducto("Freestanding Wine Rack");
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(Mockito.any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

    
        Optional<Producto> resultado = service.update(id, productoActualizado);

        
        assertTrue(resultado.isPresent());
        assertEquals("Nuevo Nombre", resultado.get().getNombreProducto());
        assertNotNull(resultado.get().getFechaActualizacion());
    }

    @Test
    void testUpdateProducto_NoExiste() {
        Integer id = 999;
        Producto producto = new Producto();
        producto.setNombreProducto("Inexistente");

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        
        Optional<Producto> resultado = service.update(id, producto);

        
        assertFalse(resultado.isPresent());
        verify(repository, Mockito.never()).save(Mockito.any());
    }
}
