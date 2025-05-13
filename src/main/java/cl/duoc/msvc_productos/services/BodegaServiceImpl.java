package cl.duoc.msvc_productos.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msvc_productos.model.Bodega;
import cl.duoc.msvc_productos.repositories.BodegaRepository;

@Service
public class BodegaServiceImpl implements BodegaService{

    @Autowired
    private BodegaRepository repository;

    @Override
    public Optional<Bodega> delete(Integer id) {
        Optional<Bodega> bodegaOptional = repository.findById(id);
        bodegaOptional.ifPresent(bodegaDb -> {
            repository.delete(bodegaDb);
        });
        return bodegaOptional;
    }
    
    @Override
    public Optional<Bodega> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Bodega guardar(Bodega bodega) {
        return repository.save(bodega);
    }

    @Override
    public Optional<Bodega> update(Integer id, Bodega bodega) {
        Optional<Bodega> bodegaOptional = repository.findById(id);
        Date today = new Date(System.currentTimeMillis());
        if (bodegaOptional.isPresent()) {
            Bodega bodegaDb = bodegaOptional.orElseThrow();
            bodegaDb.setIdBodega(bodega.getIdBodega());
            bodegaDb.setIdSucursal(bodega.getIdSucursal());
            bodegaDb.setNombreBodega(bodega.getNombreBodega());
            bodegaDb.setFechaActualizacion(today);
            return Optional.of(repository.save(bodegaDb));
            
        }
        return bodegaOptional;
    }
    
}