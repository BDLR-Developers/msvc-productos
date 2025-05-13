package cl.duoc.msvc_productos.services;

import java.util.Optional;

import cl.duoc.msvc_productos.model.Bodega;

public interface BodegaService {
    
    Optional<Bodega> findById(Integer id);

    Bodega guardar(Bodega bodega);
    
    Optional<Bodega> update(Integer id, Bodega bodega);

    Optional<Bodega> delete(Integer id);
}
