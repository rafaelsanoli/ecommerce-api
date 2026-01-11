package com.ecommerce.api.domain.exceptions;

/**
 * Exceção lançada quando um review não é encontrado
 */
public class ReviewNotFoundException extends BusinessException {
    public ReviewNotFoundException(Long id) {
        super("Avaliação não encontrada com ID: " + id);
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}

