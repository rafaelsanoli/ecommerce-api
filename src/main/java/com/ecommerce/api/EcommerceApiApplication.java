package com.ecommerce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação E-commerce API
 *
 * API REST desenvolvida com:
 * - Java 21
 * - Spring Boot 3.x
 * - Arquitetura Hexagonal (Ports & Adapters)
 * - Clean Code
 *
 * Funcionalidades:
 * - Catálogo de Produtos
 * - Categorias e Subcategorias
 * - Carrinho de Compras
 * - Sistema de Avaliações
 * - Lista de Desejos (Wishlist)
 *
 * Documentação Swagger: http://localhost:8080/swagger-ui.html
 * Console H2: http://localhost:8080/h2-console
 */
@SpringBootApplication
public class EcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
        System.out.println("\n" +
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                "║                                                               ║\n" +
                "║          🚀 E-COMMERCE API ESTÁ RODANDO! 🚀                   ║\n" +
                "║                                                               ║\n" +
                "║  📚 Swagger UI: http://localhost:8080/swagger-ui.html        ║\n" +
                "║  💾 H2 Console: http://localhost:8080/h2-console             ║\n" +
                "║  🔗 API Base:   http://localhost:8080/api/v1                 ║\n" +
                "║                                                               ║\n" +
                "║  Desenvolvida com ❤️  para estudantes de frontend            ║\n" +
                "║                                                               ║\n" +
                "╚═══════════════════════════════════════════════════════════════╝\n");
    }
}

