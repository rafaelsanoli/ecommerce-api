package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.User;
import com.ecommerce.api.domain.ports.in.AuthUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.LoginRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.RegisterRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.AuthResponse;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Requisição de registro para usuário: {}", request.getUsername());

        User user = authUseCase.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFullName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromDomain(user));
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna o token JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Requisição de login para usuário: {}", request.getUsername());

        String token = authUseCase.login(request.getUsername(), request.getPassword());

        User user = authUseCase.findByUsername(request.getUsername()).orElseThrow();

        AuthResponse response = AuthResponse.of(token, user.getUsername(), user.getEmail());

        return ResponseEntity.ok(response);
    }
}

