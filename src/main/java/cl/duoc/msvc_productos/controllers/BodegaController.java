package cl.duoc.msvc_productos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_productos.model.Bodega;
import cl.duoc.msvc_productos.model.Producto;
import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.excepciones.ClaseError;
import cl.duoc.msvc_productos.services.BodegaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/bodega")
@Tag(name = "Api V1 de Bodega", description = "Operaciones relacionadas con la bodega de los productos.")
public class BodegaController {

    @Autowired
    private BodegaService service;

    @Operation(summary = "Obtener bodega por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "Datos de bodega encontrados.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Bodega.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Solicitud inválida.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> consultarBodega(@PathVariable("id") Integer id) {
        Optional<Bodega> bodegaOptional = service.findById(id);
        if(bodegaOptional.isPresent()){
            return ResponseEntity.ok(bodegaOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404,"Solicitud inválida","No existe la bodega"));
    }

    @Operation(summary="Ingresar nuevos datos de una bodega para un producto")
    @ApiResponse(responseCode = "201", description="Ingreso valido de la informacion de la bodega")
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Bodega bodega) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(bodega));
    }

    @Operation(summary = "Modificar Bodega por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", 
                     description = "Bodega actualizada",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Bodega.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Bodega no encontrada",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Parameter(description = "Id de la bodega",required = true, example = "1")@PathVariable Integer id, @RequestBody Bodega bodega) {
        Optional<Bodega> bodegaOptional = service.update(id, bodega);
        if(bodegaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(bodegaOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404, "Solicitud inválida", "No existe el id de bodega"));
    } 

    @Operation(summary = "Eliminar bodega por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "bodega elimiada.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Bodega.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Solicitud inválida.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "Id de la bodega",required = true, example = "1")@PathVariable Integer id) {
        Optional<Bodega> bodegaOptional = service.delete(id);
        if (bodegaOptional.isPresent()) {
            return ResponseEntity.ok(bodegaOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404, "Solicitud inválida", "No existe el id de bodega"));
    }
    
}
