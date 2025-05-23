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
import cl.duoc.msvc_productos.services.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Integer id) {
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()) {
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Solicitud inválida");
            errorBody.put("codigo", 404);
            errorBody.put("detalle", "No se encuentra el id de producto");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Producto product, @PathVariable Integer id) {
        Optional<Producto> productoOptional = service.update(id, product);
        if (productoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productoOptional.orElseThrow());
        }
        Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Solicitud inválida");
            errorBody.put("codigo", 404);
            errorBody.put("detalle", "no se encuentra el id de producto");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Producto> productoOptional = service.delete(id);
        if (productoOptional.isPresent()) {
            Map<String, Object> prodBorrar = new HashMap<>();
            prodBorrar.put("error", "Solicitud Válida");
            prodBorrar.put("codigo", 200);
            prodBorrar.put("detalle", "Producto agregado con exito");
            prodBorrar.put("Codigo", productoOptional.orElseThrow().getIdProducto());
            prodBorrar.put("Nombre", productoOptional.orElseThrow().getNombreProducto());
            return ResponseEntity.ok(prodBorrar);
        }
        Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Solicitud inválida");
            errorBody.put("codigo", 404);
            errorBody.put("detalle", "No se encuentra el id de producto");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }
}