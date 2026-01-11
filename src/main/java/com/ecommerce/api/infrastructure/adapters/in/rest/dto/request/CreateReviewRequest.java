package com.ecommerce.api.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request - Criar Review
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {

    @NotBlank(message = "Nome do usuário é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String userName;

    @NotNull(message = "Rating é obrigatório")
    @Min(value = 1, message = "Rating deve ser no mínimo 1")
    @Max(value = 5, message = "Rating deve ser no máximo 5")
    private Integer rating;

    @Size(max = 1000, message = "Comentário deve ter no máximo 1000 caracteres")
    private String comment;
}

