package cl.duoc.msvc_productos.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.model.excepciones.ClaseAceptado;
import cl.duoc.msvc_productos.model.excepciones.ClaseError;
import cl.duoc.msvc_productos.model.interfaces.StockInterface;
import cl.duoc.msvc_productos.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.annotations.media.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/stock")
@Tags({
    @Tag(name = "Api V1 de Stock", description = "Operaciones relacionadas con el stock de los productos")
})
public class StockController {
    
    @Autowired
    private StockService service;

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Operation(summary = "Obtener consultar stock por id de producto, id de bodega y periodo.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "Datos de stock encontrados.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Stock.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Solicitud inválida.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @GetMapping
    public ResponseEntity<?> consultarStock(@Parameter(description="Filtrar por id del producto", required=true,example="25") @RequestParam(name = "idProducto") Integer idProducto,
    @Parameter (description="Filtrar por id de la bodega", required=true,example="1") @RequestParam (name = "idBodega") Integer idBodega, 
    @Parameter (description="Filtrar por periodo", required=true,example="2025") @RequestParam (name = "periodo") Integer periodo) {
        //logger.info("idProducto "+idProducto);
        Optional<StockInterface> stockOptional = service.findById(idProducto, idBodega, periodo);
        if (stockOptional.isPresent()) {
            return ResponseEntity.ok(stockOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404,"Solicitud inválida", "No existen filas para la consulta"));
    }

    @Operation(summary="Ingresar nuevos datos de stock de un producto")
    @ApiResponse(responseCode = "201", description="Ingreso valido de informacion sobre el stock.")
    @PostMapping
    public ResponseEntity<?> guardarStock(@RequestBody Stock stock) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(stock));
    }

    @Operation(summary = "Actualizar stock de un producto.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", 
                     description = "Datos de stock actualizados.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = Stock.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Solicitud inválida.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @PutMapping
    public ResponseEntity<?> actualizarStock(
                @Parameter (description="Id del producto", required=true,example="2")@RequestParam Integer idProducto, 
                @Parameter (description="Id de la bodega", required=true,example="1")@RequestParam Integer idBodega, 
                @Parameter (description="Periodo del stock", required=true,example="2025")@RequestParam Integer periodo, 
                @Parameter (description="Json de actualizacion del stock") @RequestBody Stock stock) {
        Optional<Stock> stockOptional = service.update(idProducto, idBodega, periodo, stock);
        if (stockOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(stockOptional.orElseThrow());
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ClaseError(404, "Solcitud inválida", "No existen filas para la consulta"));
    }

    @Operation(summary = "Borrar stock de un producto.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", 
                     description = "Datos de stock eliminados.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseAceptado.class))
                    ),
        @ApiResponse(responseCode = "404", 
                     description = "Solicitud inválida.",
                     content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClaseError.class)))
    })
    @DeleteMapping
    public ResponseEntity<?> borrarStock(
        @Parameter (description="Id del producto", required=true,example="2")@RequestParam Integer idProducto, 
                @Parameter (description="Id de la bodega", required=true,example="1")@RequestParam Integer idBodega, 
                @Parameter (description="Periodo del stock", required=true,example="2025")@RequestParam Integer periodo, 
                @Parameter (description="Json de actualizacion del stock") @RequestBody Stock stock){
        Optional<Stock> stockOptional = service.delete(idProducto, idBodega, periodo);
        if (stockOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ClaseAceptado(200, "Solicitud valida", "Producto ha ido borrado.",
                                    stockOptional.orElseThrow().getIdProducto(),""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ClaseError(404,"Solicitud inválida", "No existen filas para la consulta."));
    }
    
    
}
