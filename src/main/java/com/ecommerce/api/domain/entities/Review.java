package com.ecommerce.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade de domínio - Avaliação/Review
 * Representa uma avaliação de um produto por um usuário
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long productId;
    private String userName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    /**
     * Valida se o rating está entre 1 e 5
     */
    public void validateRating() {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating deve estar entre 1 e 5");
        }
    }

    /**
     * Verifica se o review tem comentário
     */
    public boolean hasComment() {
        return comment != null && !comment.trim().isEmpty();
    }
}

