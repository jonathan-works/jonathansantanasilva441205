package br.gov.mt.seplag.seletivo.ArtistHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI artistHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ArtistHub API")
                        .description("API para gerenciamento de artistas e álbuns")
                        .version("v1"));
    }
}