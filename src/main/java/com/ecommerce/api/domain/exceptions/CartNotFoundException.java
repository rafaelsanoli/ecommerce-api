package com.ecommerce.api.domain.exceptions;

/**
 * Exceção lançada quando um carrinho não é encontrado
 */
public class CartNotFoundException extends BusinessException {
    public CartNotFoundException(Long id) {
        super("Carrinho não encontrado com ID: " + id);
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}

