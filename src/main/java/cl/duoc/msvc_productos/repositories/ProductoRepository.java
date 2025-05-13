package cl.duoc.msvc_productos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.msvc_productos.model.Producto;

public interface ProductoRepository  extends JpaRepository<Producto,Integer>{
    
}
