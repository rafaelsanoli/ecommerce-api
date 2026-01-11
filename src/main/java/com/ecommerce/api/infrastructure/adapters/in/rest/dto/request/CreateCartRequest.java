package com.ecommerce.api.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request - Criar Carrinho
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequest {

    @NotBlank(message = "ID do usuário é obrigatório")
    private String userId;
}

