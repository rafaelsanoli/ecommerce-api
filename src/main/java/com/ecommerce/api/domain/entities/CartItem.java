package com.ecommerce.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade de domínio - Item do Carrinho
 * Representa um item dentro de um carrinho de compras
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtAddTime;

    /**
     * Calcula o subtotal do item (preço × quantidade)
     */
    public BigDecimal getSubtotal() {
        if (priceAtAddTime == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return priceAtAddTime.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Aumenta a quantidade do item
     */
    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantity += amount;
    }

    /**
     * Diminui a quantidade do item
     */
    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (this.quantity < amount) {
            throw new IllegalStateException("Quantidade insuficiente no carrinho");
        }
        this.quantity -= amount;
    }

    /**
     * Atualiza a quantidade do item
     */
    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantity = newQuantity;
    }
}

