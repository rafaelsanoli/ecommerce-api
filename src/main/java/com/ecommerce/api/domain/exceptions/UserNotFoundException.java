package com.ecommerce.api.domain.exceptions;

/**
 * Domain Exception - Usuário não encontrado
 */
public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException byUsername(String username) {
        return new UserNotFoundException(
            String.format("Usuário '%s' não encontrado", username)
        );
    }

    public static UserNotFoundException byId(Long id) {
        return new UserNotFoundException(
            String.format("Usuário com ID %d não encontrado", id)
        );
    }
}

