package cl.duoc.msvc_productos.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.claves.ClaveCompStock;
import cl.duoc.msvc_productos.model.interfaces.StockInterface;
import cl.duoc.msvc_productos.repositories.StockRepository;

@Service
public class StockServiceImpl implements StockService{

    @Autowired
    private StockRepository repository;

    
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public Optional<StockInterface> findById(Integer idProd, Integer idBodega, Integer periodo) {
        ClaveCompStock clave = new ClaveCompStock();
        clave.setIdProducto(idProd);
        clave.setIdBodega(idBodega);
        clave.setPeriodo(periodo);
        logger.info("Buscando stock con producto={},bodega={}, periodo={}", idProd,idBodega, periodo);
        return repository.consultarByIdProductoAndIdBodegaAndPeriodo(idProd, idBodega, periodo);
    }

    @Override
    public Stock save(Stock stock) {
        return repository.save(stock);
    }

    @Override
    public Optional<Stock> update(Integer idProd, Integer idBodega, Integer periodo, Stock stock) {
        ClaveCompStock clave = new ClaveCompStock();
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
        ClaveCompStock clave = new ClaveCompStock();
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

