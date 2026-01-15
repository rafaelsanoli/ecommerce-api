package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.User;
import com.ecommerce.api.domain.exceptions.UserNotFoundException;
import com.ecommerce.api.domain.ports.in.UserUseCase;
import com.ecommerce.api.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Usuário
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        log.debug("Buscando usuário por username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        log.debug("Buscando usuário por ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUserProfile(String username) {
        log.debug("Obtendo perfil do usuário: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserNotFoundException.byUsername(username));
    }
}

