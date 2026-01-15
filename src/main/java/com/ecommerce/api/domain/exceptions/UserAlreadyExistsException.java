package com.ecommerce.api.domain.exceptions;

/**
 * Domain Exception - Usuário já existe
 */
public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException byUsername(String username) {
        return new UserAlreadyExistsException(
            String.format("Usuário com username '%s' já existe", username)
        );
    }

    public static UserAlreadyExistsException byEmail(String email) {
        return new UserAlreadyExistsException(
            String.format("Usuário com email '%s' já existe", email)
        );
    }
}

