package com.ecommerce.api.domain.entities;
}
    }
        ADMIN
        CUSTOMER,
    public enum Role {

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Boolean active;
    private Role role;
    private String fullName;
    private String password;
    private String email;
    private String username;
    private Long id;

public class User {
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
 */
 * Entidade de domínio que representa um usuário do sistema
 * Domain Entity - Usuário
/**

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;


