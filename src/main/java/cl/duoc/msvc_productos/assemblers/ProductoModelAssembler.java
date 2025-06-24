package cl.duoc.msvc_productos.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.duoc.msvc_productos.controllers.ProductoController;
import cl.duoc.msvc_productos.model.Producto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>>{

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            // Link a sí mismo (GET /{id})
            linkTo(methodOn(ProductoController.class).view(producto.getIdProducto())).withSelfRel(),
            
            // Link para eliminar (DELETE /{id})
            linkTo(methodOn(ProductoController.class).delete(producto.getIdProducto())).withRel("eliminar"),
            
            // Link para actualizar (PUT /{id})
            linkTo(methodOn(ProductoController.class).update(producto,producto.getIdProducto())).withRel("actualizar"),
            
            // Link para crear uno nuevo (POST)
            linkTo(methodOn(ProductoController.class).create(new Producto())).withRel("crear")
        );
    }
    
}
