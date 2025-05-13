package cl.duoc.msvc_productos.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_productos.model.Stock;
import cl.duoc.msvc_productos.services.StockService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    
    @Autowired
    private StockService service;

    @GetMapping
    public ResponseEntity<?> consultarStock(@RequestParam Integer idProducto, @RequestParam Integer idBodega, @RequestParam Integer periodo) {
        Optional<Stock> stockOptional = service.findById(idProducto, idBodega, periodo);
        if (stockOptional.isPresent()) {
            return ResponseEntity.ok(stockOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> guardarStock(@RequestBody Stock stock) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(stock));
    }

    @PutMapping
    public ResponseEntity<?> actualizarStock(@RequestParam Integer idProducto, @RequestParam Integer idBodega, 
                @RequestParam Integer periodo, @RequestBody Stock stock) {
        Optional<Stock> stockOptional = service.update(idProducto, idBodega, periodo, stock);
        if (stockOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(stockOptional.orElseThrow());
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("path/{id}")
    public ResponseEntity<?> borrarStock(@RequestParam Integer idProducto, @RequestParam Integer idBodega, 
    @RequestParam Integer periodo, @RequestBody Stock stock) {
        Optional<Stock> stockOptional = service.delete(idProducto, idBodega, periodo);
        if (stockOptional.isPresent()) {
            return ResponseEntity.ok(stockOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    
}
