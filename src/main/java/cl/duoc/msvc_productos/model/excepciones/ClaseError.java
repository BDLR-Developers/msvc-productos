package cl.duoc.msvc_productos.model.excepciones;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClaseError {
    @Schema(description = "Código HTTP", example = "404")
    private Integer codigo;
    @Schema(description = "Mensaje del Error", example = "Solicitud inválida")
    private String error;
    @Schema(description = "Detalle del error", example = "No se encuentra el id de producto")
    private String detalle;


}
