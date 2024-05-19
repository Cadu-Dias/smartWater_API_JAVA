package smartwater.api.pi.config.docs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;


@Component
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
        .path("localhost:8080/api/timeseries/v0.5/smartcampusmaua/", null)
        .schema("http", null)
        .components( new Components())
        .info( new Info()
            .title("Smart Water API")
            .description("API Rest da aplicação Smart Water, contendo a funcionalidade de obtenção dos dados emitidos por dispositivos contendo dados relacionados a Caixas D'águas e Hidrometros")
            .license( new License()
                .name("MIT")
                .url("https://opensource.org/license/MIT")
            )
            .version("0.5")
        );
    }
}
