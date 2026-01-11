package com.ecommerce.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de domínio - Produto
 * Representa um produto no catálogo do e-commerce
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Verifica se o produto está disponível em estoque
     */
    public boolean isAvailable() {
        return stock != null && stock > 0;
    }

    /**
     * Verifica se há estoque suficiente
     */
    public boolean hasStock(int quantity) {
        return stock != null && stock >= quantity;
    }

    /**
     * Reduz o estoque do produto
     */
    public void decreaseStock(int quantity) {
        if (!hasStock(quantity)) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        this.stock -= quantity;
    }

    /**
     * Aumenta o estoque do produto
     */
    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.stock += quantity;
    }
}

