package ru.surf.core.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class SwaggerConfiguration {

    @Bean
    fun customOpenApiConfiguration(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Module Core")
                .version("1.0.0")
        )

}