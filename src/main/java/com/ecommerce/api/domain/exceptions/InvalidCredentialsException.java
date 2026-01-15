package com.ecommerce.api.domain.exceptions;

/**
 * Domain Exception - Credenciais inválidas
 */
public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public static InvalidCredentialsException create() {
        return new InvalidCredentialsException("Credenciais inválidas");
    }
}

