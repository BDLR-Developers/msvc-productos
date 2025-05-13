package cl.duoc.msvc_productos.model;

import cl.duoc.msvc_productos.model.claves.ClaveCompStock;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock")
@IdClass(ClaveCompStock.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stock {

    @Id
    private Integer idProducto;

    @Id
    private Integer idBodega;

    @Id
    private Integer periodo;

    private Integer total;

    

    
}
