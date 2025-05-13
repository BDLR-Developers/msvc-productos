package cl.duoc.msvc_productos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.claves.ClaveCompStock;
import cl.duoc.msvc_productos.repositories.StockRepository;

public class StockServiceImpl implements StockService{

    @Autowired
    private StockRepository repository;

    @Autowired
    private ClaveCompStock clave;

    @Override
    public Optional<Stock> findById(Integer idProd, Integer idBodega, Integer periodo) {
        clave.setIdProducto(idProd);
        clave.setIdProducto(idBodega);
        clave.setPeriodo(periodo);
        return repository.findById(clave);
    }

    @Override
    public Stock save(Stock stock) {
        return repository.save(stock);
    }

    @Override
    public Optional<Stock> update(Integer idProd, Integer idBodega, Integer periodo, Stock stock) {
        clave.setIdProducto(idProd);
        clave.setIdProducto(idBodega);
        clave.setPeriodo(periodo);
        Optional<Stock> stockOptional = repository.findById(clave);
        if (stockOptional.isPresent()) {
            Stock stockDb = stockOptional.orElseThrow();
            
            stockDb.setIdProducto(stock.getIdProducto());
            stockDb.setIdBodega(stock.getIdBodega());
            stockDb.setPeriodo(stock.getPeriodo());
            stockDb.setTotal(stock.getTotal());
            return Optional.of(repository.save(stockDb));
            
        }
        return stockOptional;
    }

    @Override
    public Optional<Stock> delete(Integer idProd, Integer idBodega, Integer periodo) {
        clave.setIdProducto(idProd);
        clave.setIdProducto(idBodega);
        clave.setPeriodo(periodo);
        Optional<Stock> stockOptional = repository.findById(clave);
        stockOptional.ifPresent(stockDb -> {
            repository.delete(stockDb);
        });
        return stockOptional;
    }
    
}

