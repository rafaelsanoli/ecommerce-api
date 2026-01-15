package com.ecommerce.api.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração OpenAPI/Swagger - Documentação da API
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .version("1.1.0")
                        .description("API REST de E-commerce desenvolvida com Spring Boot 3.x e Arquitetura Hexagonal. " +
                                "Esta API foi criada para ajudar estudantes de frontend a praticarem suas habilidades " +
                                "construindo aplicações de e-commerce completas. " +
                                "Agora com autenticação JWT!")
                        .contact(new Contact()
                                .name("Comunidade de Devs")
                                .email("contato@devs.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Autenticação JWT. Use o endpoint /api/v1/auth/login para obter o token.")));
    }
}

