package cl.duoc.msvc_productos.model.excepciones;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClaseAceptado {
    @Schema(description = "Código HTTP", example = "200")
    private Integer codigo;
    @Schema(description = "Mensaje del Error", example = "Solicitud valida")
    private String error;
    @Schema(description = "Detalle del Error", example = "Producto borrado con exito")
    private String detalle;
    @Schema(description = "Codigo Producto", example = "Codigo del producto borrado ")
    private Integer codProd;
    @Schema(description = "Nombre Producto", example = "Nombre del producto borrado ")
    private String nombreProd;
}
