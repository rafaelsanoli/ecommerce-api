package com.ecommerce.api.domain.exceptions;

/**
 * Exceção lançada quando há estoque insuficiente
 */
public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(String productName, int requested, int available) {
        super(String.format("Estoque insuficiente para o produto '%s'. Solicitado: %d, Disponível: %d",
                productName, requested, available));
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}

