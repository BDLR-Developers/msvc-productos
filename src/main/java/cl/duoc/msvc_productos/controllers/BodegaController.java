package cl.duoc.msvc_productos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msvc_productos.model.Bodega;
import cl.duoc.msvc_productos.services.BodegaService;

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
public class BodegaController {

    @Autowired
    private BodegaService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarBodega(@PathVariable("id") Integer id) {
        Optional<Bodega> bodegaOptional = service.findById(id);
        if(bodegaOptional.isPresent()){
            return ResponseEntity.ok(bodegaOptional.orElseThrow());
        }
        Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Solicitud inválida");
            errorBody.put("codigo", 404);
            errorBody.put("detalle", "No existe la bodega");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Bodega bodega) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(bodega));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Bodega bodega) {
        Optional<Bodega> bodegaOptional = service.update(id, bodega);
        if(bodegaOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(bodegaOptional.orElseThrow());
        }
        Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Solicitud inválida");
            errorBody.put("codigo", 404);
            errorBody.put("detalle", "No existe el id de bodega");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Bodega> bodegaOptional = service.delete(id);
        if (bodegaOptional.isPresent()) {
            return ResponseEntity.ok(bodegaOptional.orElseThrow());
        }
        Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", "Solicitud inválida");
            errorBody.put("codigo", 404);
            errorBody.put("detalle", "No existe el id de bodega");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }
    
}
