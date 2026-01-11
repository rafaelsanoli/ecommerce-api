package com.ecommerce.api.domain.exceptions;

/**
 * Exceção lançada quando uma categoria não é encontrada
 */
public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(Long id) {
        super("Categoria não encontrada com ID: " + id);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}

