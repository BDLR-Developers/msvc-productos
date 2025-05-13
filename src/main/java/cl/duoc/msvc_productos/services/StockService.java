package cl.duoc.msvc_productos.services;

import java.util.Optional;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.interfaces.StockInterface;

public interface StockService {

    Optional<StockInterface> findById(Integer idProd, Integer idBodega, 
        Integer periodo);

    Stock save(Stock stock);
    
    Optional<Stock> update(Integer idProd, Integer idBodega, 
    Integer periodo, Stock stock);

    Optional<Stock> delete(Integer idProd, Integer idBodega, 
    Integer periodo);
}
