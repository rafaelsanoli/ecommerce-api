package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.User;
import com.ecommerce.api.domain.exceptions.InvalidCredentialsException;
import com.ecommerce.api.domain.exceptions.UserAlreadyExistsException;
import com.ecommerce.api.domain.ports.in.AuthUseCase;
import com.ecommerce.api.domain.ports.out.UserRepositoryPort;
import com.ecommerce.api.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Autenticação
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public User register(String username, String email, String password, String fullName) {
        log.debug("Registrando novo usuário: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw UserAlreadyExistsException.byUsername(username);
        }

        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException.byEmail(email);
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .role(User.Role.CUSTOMER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        log.info("Usuário registrado com sucesso: {}", savedUser.getUsername());

        return savedUser;
    }

    @Override
    public String login(String username, String password) {
        log.debug("Tentativa de login do usuário: {}", username);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtTokenProvider.generateToken(authentication);
            log.info("Login realizado com sucesso: {}", username);

            return token;
        } catch (Exception e) {
            log.error("Falha no login para usuário: {}", username);
            throw InvalidCredentialsException.create();
        }
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
