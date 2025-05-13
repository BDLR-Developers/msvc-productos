package cl.duoc.msvc_productos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.claves.ClaveCompStock;

public interface StockRepository extends JpaRepository<Stock,ClaveCompStock>{
    
}
