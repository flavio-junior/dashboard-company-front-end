package br.com.dashboard.company.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI().info(
            Info().title("API RESTFul with Kotlin 1.9.25 and Spring Boot 3.3.5")
                .version("V1")
                .contact(
                    Contact()
                        .name("Flávio Júnior")
                        .email("flaviojunior.work@gmail.com")
                        .url("https://www.flaviojunior.net/")
                )
                .description("Some description about your API.")
                .license(License().name("Apache 2.0").url("https://www.guidepoint.com.br/licence"))
        )
    }

}