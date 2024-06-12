package smartwater.api.pi.infra.docs;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;


@Configuration
public class SpringDocConfigurations {

    private static final Logger logger = LoggerFactory.getLogger(SpringDocConfigurations.class);

    @Bean
    public OpenAPI customOpenApi() {
        logger.info("Iniciando essa porra");
        return new OpenAPI()
        .components( new Components()
            .addSecuritySchemes("bearer-key",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
            )
        )
        .servers(Arrays.asList(
            new Server().url("http://localhost:8080").description("Localhost Server")
        ))
        .tags(Arrays.asList(
            new Tag().name("Authentication").description("Group Containing the routes that are used for Authentication and Authorization"),
            new Tag().name("User").description("Routes for doing CRUD with the Users that can access the API"),
            new Tag().name("SmartLights").description("Collection of routes for managing SmartLights data within the system."),
            new Tag().name("WaterTanks").description("Set of routes for accessing Hidrometers data stored in the database."),
            new Tag().name("Hidrometers").description("Routes for retrieving and managing ArtesianWell data in the database."),
            new Tag().name("ArtesianWell").description("Group of routes for monitoring WaterTanksLevel data within the system.")
        ))
        .schema("http", null)
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

    @Bean
    public GroupedOpenApi timeSeriesApi() {
        return GroupedOpenApi.builder()
            .group("time-series")
            .pathsToMatch("/api/timeseries/v0.5/smartcampusmaua/**")
            .build();
    }
}
