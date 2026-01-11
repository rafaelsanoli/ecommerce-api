package com.ecommerce.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade de domínio - Wishlist (Lista de Desejos)
 * Representa um item na lista de favoritos de um usuário
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    private Long id;
    private String userId;
    private Long productId;
    private LocalDateTime addedAt;
}

