package com.ecommerce.api.domain.ports.in;

import com.ecommerce.api.domain.entities.User;

import java.util.Optional;

/**
 * Port IN - Casos de uso de Usuário
 */
public interface UserUseCase {

    /**
     * Buscar usuário por username
     */
    Optional<User> findByUsername(String username);

    /**
     * Buscar usuário por ID
     */
    Optional<User> findById(Long id);

    /**
     * Obter perfil do usuário atual
     */
    User getCurrentUserProfile(String username);
}

