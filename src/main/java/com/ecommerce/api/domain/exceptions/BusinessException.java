package com.ecommerce.api.domain.exceptions;

/**
 * Exceção base para regras de negócio
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

