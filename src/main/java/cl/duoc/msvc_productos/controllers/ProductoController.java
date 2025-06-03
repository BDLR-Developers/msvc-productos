package cl.duoc.msvc_productos.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_productos.model.Producto;
import cl.duoc.msvc_productos.model.excepciones.ClaseAceptado;
import cl.duoc.msvc_productos.model.excepciones.ClaseError;
import cl.duoc.msvc_productos.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/api/v1/productos")
@Tags({
    @Tag(name = "Api V1 de Productos", description = "Operaciones relacionadas con los productos")
})
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "Producto encontrado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Producto.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Producto no encontrado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@Parameter(description = "Id del producto",required = true, example = "5") @PathVariable Integer id) {
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404,"Solicitud inválida","No se encuentra el id de producto"));
    }

    @Operation(summary = "Creacion de nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado", 
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = Producto.class)))
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(producto));
    }

    @Operation(summary = "Modificar datos de un producto por id")
    @ApiResponses({
        @ApiResponse(responseCode = "201", 
                     description = "Producto actualizado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Producto.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Producto no encontrado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Parameter(description = "Json del producto" ,required= true, example= )@RequestBody Producto product, 
                    @Parameter (description="Id del producto", required= true, example="7") @PathVariable Integer id) {
        Optional<Producto> productoOptional = service.update(id, product);
        if (productoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productoOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404,"Solicitud inválida","No se encuentra el id de producto"));
    }

    @Operation(summary = "Borrar producto por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "Producto borrado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseAceptado.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Producto no encontrado",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Parameter (description="Id del producto", required= true, example="7") @PathVariable Integer id) {
        Optional<Producto> productoOptional = service.delete(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(new ClaseAceptado(200,"Solicitud Valida",
            "Producto borrado con exito",productoOptional.orElseThrow().getIdProducto(),
            productoOptional.orElseThrow().getNombreProducto()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404,"Solicitud inválida","No se encuentra el id de producto"));
    }
}