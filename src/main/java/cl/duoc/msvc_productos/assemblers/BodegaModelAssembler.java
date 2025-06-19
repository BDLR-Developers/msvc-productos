package cl.duoc.msvc_productos.assemblers;

import cl.duoc.msvc_productos.controllers.BodegaController;
import cl.duoc.msvc_productos.model.Bodega;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BodegaModelAssembler implements RepresentationModelAssembler<Bodega, EntityModel<Bodega>> {

    @Override
    public EntityModel<Bodega> toModel(Bodega bodega) {
        return EntityModel.of(bodega,
            // Link a sí mismo (GET /{id})
            linkTo(methodOn(BodegaController.class).consultarBodega(bodega.getIdBodega())).withSelfRel(),
            
            // Link para eliminar (DELETE /{id})
            linkTo(methodOn(BodegaController.class).delete(bodega.getIdBodega())).withRel("eliminar"),
            
            // Link para actualizar (PUT /{id})
            linkTo(methodOn(BodegaController.class).actualizar(bodega.getIdBodega(), bodega)).withRel("actualizar"),
            
            // Link para crear uno nuevo (POST)
            linkTo(methodOn(BodegaController.class).guardar(new Bodega())).withRel("crear")
        );
    }
}

