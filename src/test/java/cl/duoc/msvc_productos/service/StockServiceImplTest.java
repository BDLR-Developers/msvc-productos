package cl.duoc.msvc_productos.service;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.claves.ClaveCompStock;
import cl.duoc.msvc_productos.model.interfaces.StockInterface;
import cl.duoc.msvc_productos.repositories.StockRepository;
import cl.duoc.msvc_productos.services.StockServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Test
    void testFindById() {
        Integer idProd = 1, idBodega = 2, periodo = 202406;

        StockInterface mockStockInterface = mock(StockInterface.class);

        when(stockRepository.consultarByIdProductoAndIdBodegaAndPeriodo(idProd, idBodega, periodo))
                .thenReturn(Optional.of(mockStockInterface));

        Optional<StockInterface> result = stockService.findById(idProd, idBodega, periodo);

        assertTrue(result.isPresent());
        verify(stockRepository, times(1))
                .consultarByIdProductoAndIdBodegaAndPeriodo(idProd, idBodega, periodo);
    }

    @Test
    void testSave() {
        Stock stock = new Stock();
        stock.setIdProducto(1);
        stock.setIdBodega(2);
        stock.setPeriodo(202406);
        stock.setTotal(100);

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.save(stock);

        assertEquals(stock, result);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void testUpdateWhenExists() {
        Integer idProd = 1, idBodega = 2, periodo = 202406;

        Stock existingStock = new Stock();
        existingStock.setIdProducto(idProd);
        existingStock.setIdBodega(idBodega);
        existingStock.setPeriodo(periodo);
        existingStock.setTotal(50);

        Stock updatedStock = new Stock();
        updatedStock.setIdProducto(idProd);
        updatedStock.setIdBodega(idBodega);
        updatedStock.setPeriodo(periodo);
        updatedStock.setTotal(200);

        when(stockRepository.findById(any(ClaveCompStock.class))).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(updatedStock);

        Optional<Stock> result = stockService.update(idProd, idBodega, periodo, updatedStock);

        assertTrue(result.isPresent());
        assertEquals(200, result.get().getTotal());
        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    void testUpdateWhenNotExists() {
        when(stockRepository.findById(any(ClaveCompStock.class))).thenReturn(Optional.empty());

        Optional<Stock> result = stockService.update(1, 2, 202406, new Stock());

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteWhenExists() {
        Stock stock = new Stock();
        stock.setIdProducto(1);
        stock.setIdBodega(2);
        stock.setPeriodo(202406);

        when(stockRepository.findById(any(ClaveCompStock.class))).thenReturn(Optional.of(stock));

        Optional<Stock> result = stockService.delete(1, 2, 202406);

        assertTrue(result.isPresent());
        verify(stockRepository).delete(stock);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(stockRepository.findById(any(ClaveCompStock.class))).thenReturn(Optional.empty());

        Optional<Stock> result = stockService.delete(1, 2, 202406);

        assertFalse(result.isPresent());
        verify(stockRepository, never()).delete(any());
    }
}

