package com.ecommerce.api.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request - Adicionar à Wishlist
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToWishlistRequest {

    @NotBlank(message = "ID do usuário é obrigatório")
    private String userId;

    @NotNull(message = "ID do produto é obrigatório")
    private Long productId;
}

