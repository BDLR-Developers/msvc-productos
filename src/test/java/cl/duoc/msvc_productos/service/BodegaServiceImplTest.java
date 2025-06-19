package cl.duoc.msvc_productos.service;

import cl.duoc.msvc_productos.model.Bodega;
import cl.duoc.msvc_productos.repositories.BodegaRepository;
import cl.duoc.msvc_productos.services.BodegaServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

//import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodegaServiceImplTest {

    @InjectMocks
    private BodegaServiceImpl bodegaService;

    @Mock
    private BodegaRepository bodegaRepository;

    @Test
    void testGuardarBodega() {
        Bodega bodegaInput = new Bodega();
        bodegaInput.setNombreBodega("Central");
        bodegaInput.setIdUsuario(10);

        when(bodegaRepository.save(any(Bodega.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Bodega resultado = bodegaService.guardar(bodegaInput);

        assertNotNull(resultado);
        assertEquals("Central", resultado.getNombreBodega());
        assertEquals(10, resultado.getIdUsuario());
        assertEquals(1, resultado.getIdSucursal());
        assertEquals(1, resultado.getEstado());
        assertNotNull(resultado.getFechaCreacion());
        assertNotNull(resultado.getFechaActualizacion());

        verify(bodegaRepository).save(any(Bodega.class));
    }

    @Test
    void testFindByIdWhenExists() {
        Bodega bodega = new Bodega();
        bodega.setIdBodega(1);
        bodega.setNombreBodega("Sur");

        when(bodegaRepository.findById(1)).thenReturn(Optional.of(bodega));

        Optional<Bodega> result = bodegaService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Sur", result.get().getNombreBodega());
        verify(bodegaRepository).findById(1);
    }

    @Test
    void testFindByIdWhenNotExists() {
        when(bodegaRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Bodega> result = bodegaService.findById(99);

        assertFalse(result.isPresent());
        verify(bodegaRepository).findById(99);
    }

    @Test
    void testDeleteWhenExists() {
        Bodega bodega = new Bodega();
        bodega.setIdBodega(1);

        when(bodegaRepository.findById(1)).thenReturn(Optional.of(bodega));

        Optional<Bodega> result = bodegaService.delete(1);

        assertTrue(result.isPresent());
        verify(bodegaRepository).delete(bodega);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(bodegaRepository.findById(100)).thenReturn(Optional.empty());

        Optional<Bodega> result = bodegaService.delete(100);

        assertFalse(result.isPresent());
        verify(bodegaRepository, never()).delete(any());
    }

    @Test
    void testUpdateWhenExists() {
        Bodega original = new Bodega();
        original.setIdBodega(1);
        original.setIdSucursal(1);
        original.setNombreBodega("Original");

        Bodega modificado = new Bodega();
        modificado.setIdBodega(1);
        modificado.setIdSucursal(2);
        modificado.setNombreBodega("Modificado");

        when(bodegaRepository.findById(1)).thenReturn(Optional.of(original));
        when(bodegaRepository.save(any(Bodega.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Bodega> result = bodegaService.update(1, modificado);

        assertTrue(result.isPresent());
        assertEquals("Modificado", result.get().getNombreBodega());
        assertEquals(2, result.get().getIdSucursal());
        assertNotNull(result.get().getFechaActualizacion());

        verify(bodegaRepository).findById(1);
        verify(bodegaRepository).save(any(Bodega.class));
    }

    @Test
    void testUpdateWhenNotExists() {
        when(bodegaRepository.findById(42)).thenReturn(Optional.empty());

        Optional<Bodega> result = bodegaService.update(42, new Bodega());

        assertFalse(result.isPresent());
        verify(bodegaRepository, never()).save(any());
    }
}

