package com.ecommerce.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade de domínio - Carrinho de Compras
 * Representa um carrinho de compras de um usuário
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long id;
    private String userId;
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Adiciona um item ao carrinho
     */
    public void addItem(CartItem item) {
        // Verifica se o produto já existe no carrinho
        CartItem existingItem = findItemByProductId(item.getProductId());
        if (existingItem != null) {
            existingItem.increaseQuantity(item.getQuantity());
        } else {
            items.add(item);
        }
    }

    /**
     * Remove um item do carrinho
     */
    public void removeItem(Long itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }

    /**
     * Busca um item pelo ID do produto
     */
    public CartItem findItemByProductId(Long productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Calcula o total de itens no carrinho
     */
    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    /**
     * Calcula o valor total do carrinho
     */
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Limpa todos os itens do carrinho
     */
    public void clear() {
        items.clear();
    }

    /**
     * Verifica se o carrinho está vazio
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
}

