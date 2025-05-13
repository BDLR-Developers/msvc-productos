package cl.duoc.msvc_productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Producto {

    @Id
    private Integer idProducto;
    private String nombreProducto;
}
