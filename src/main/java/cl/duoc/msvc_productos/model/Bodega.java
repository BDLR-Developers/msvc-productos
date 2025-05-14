package cl.duoc.msvc_productos.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "bodega")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bodega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBodega;
    private String nombreBodega;
    private Integer idSucursal;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Integer idUsuario;
    private Integer estado;
}
