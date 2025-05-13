package cl.duoc.msvc_productos.services;

import java.util.Optional;

import cl.duoc.msvc_productos.model.Producto;

public interface ProductoService {
    
    Optional<Producto> findById(Integer id);

    Optional<Producto> update(Integer id, Producto product);

    Optional<Producto> delete(Integer id);

    Producto guardar(Producto prod);

     
}
