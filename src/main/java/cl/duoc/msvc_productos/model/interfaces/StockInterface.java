package cl.duoc.msvc_productos.model.interfaces;

public interface StockInterface {
    Integer getIdProducto();
    String getNombreProducto();
    Integer getPeriodo();
    Integer getTotal();
    Integer getIdBodega();
}
