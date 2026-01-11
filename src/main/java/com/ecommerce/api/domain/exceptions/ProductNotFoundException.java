package com.ecommerce.api.domain.exceptions;

/**
 * Exceção lançada quando um produto não é encontrado
 */
public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(Long id) {
        super("Produto não encontrado com ID: " + id);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}

