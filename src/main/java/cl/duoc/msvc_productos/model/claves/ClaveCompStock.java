package cl.duoc.msvc_productos.model.claves;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClaveCompStock implements Serializable{

    private Integer idProducto;
    private Integer idBodega;
    private Integer periodo;
}
