package cl.duoc.msvc_productos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.claves.ClaveCompStock;
import cl.duoc.msvc_productos.model.interfaces.StockInterface;



public interface StockRepository extends JpaRepository<Stock,ClaveCompStock>{
    
    @Query(value = """
    SELECT s.id_producto as idProducto, 
            s.id_bodega as idBodega, 
            s.periodo, 
            s.total, 
            p.nombre_producto as nombreProducto 
    FROM stock s 
    JOIN producto p ON s.id_producto = p.id_producto
    WHERE s.id_producto = :idProducto 
      AND s.id_bodega = :idBodega 
      AND s.periodo = :periodo
    """, nativeQuery = true)
    Optional<StockInterface> consultarByIdProductoAndIdBodegaAndPeriodo(
        @Param("idProducto") Integer idProducto,
        @Param("idBodega") Integer idBodega,
        @Param("periodo") Integer periodo);
}
