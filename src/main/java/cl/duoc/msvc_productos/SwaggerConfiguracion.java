package cl.duoc.msvc_productos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguracion {
    
    @Bean
    public OpenAPI customizarOpenApi(){
        return new OpenAPI()
        .info(new Info()
                .title("Documentación de la API de Productos - Bodega - Stock")
                .version("1.0.0")
                .description("Esta API permite gestionar productos, bodega y stock.")
                .contact(new Contact()
                    .name("Equipo de Desarrollo BDLR-Developers"))
                   // .email("soporte@empresa.com"))
                    //.url("https://empresa.com"))
                /* .license(new License()
                    .name("Licencia MIT")
                    .url("https://opensource.org/licenses/MIT"))*/);
        }
}
