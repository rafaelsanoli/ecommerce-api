package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.User;
import com.ecommerce.api.domain.ports.in.UserUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/me")
    @Operation(summary = "Obter perfil do usuário autenticado",
               description = "Retorna os dados do usuário autenticado",
               security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        log.info("Buscando perfil do usuário: {}", authentication.getName());

        User user = userUseCase.getCurrentUserProfile(authentication.getName());

        return ResponseEntity.ok(UserResponse.fromDomain(user));
    }
}

