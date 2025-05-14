package cl.duoc.msvc_productos.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msvc_productos.model.Producto;
import cl.duoc.msvc_productos.repositories.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Override
    public Optional<Producto> delete(Integer id) {
        Optional<Producto> productoOptional = repository.findById(id);
        productoOptional.ifPresent(productoDb -> {
            repository.delete(productoDb);
        });
        return productoOptional;
    }

    @Override
    public Optional<Producto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Producto guardar(Producto prod) {
        Date today = new Date(System.currentTimeMillis());
        prod.setNombreProducto(prod.getNombreProducto());
        prod.setRutUsuario(prod.getRutUsuario());
        prod.setCodigoBarra(prod.getCodigoBarra());
        prod.setDescripcion(prod.getDescripcion());
        prod.setFechaCreacion(today);
        prod.setFechaActualizacion(today);
        return repository.save(prod);
    }

    @Override
    public Optional<Producto> update(Integer id, Producto producto) {
        Optional<Producto> productoOptional = repository.findById(id);
        Date today = new Date(System.currentTimeMillis());
        if (productoOptional.isPresent()) {
            Producto productoDb = productoOptional.orElseThrow();
            productoDb.setIdProducto(producto.getIdProducto());
            productoDb.setNombreProducto(producto.getNombreProducto());
            productoDb.setRutUsuario(producto.getRutUsuario());
            productoDb.setFechaActualizacion(today);
            return Optional.of(repository.save(productoDb));
            
        }
        return productoOptional;
    }
    
}
