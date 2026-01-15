package com.ecommerce.api.domain.ports.out;

import com.ecommerce.api.domain.entities.User;

import java.util.Optional;

/**
 * Port OUT - Repositório de Usuários
 */
public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

