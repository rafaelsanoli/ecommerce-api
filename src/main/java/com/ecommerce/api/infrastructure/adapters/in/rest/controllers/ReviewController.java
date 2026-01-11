package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.Review;
import com.ecommerce.api.domain.exceptions.ReviewNotFoundException;
import com.ecommerce.api.domain.ports.in.ReviewUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.CreateReviewRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST Controller - Reviews
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Endpoints para gerenciamento de avaliações")
public class ReviewController {

    private final ReviewUseCase reviewUseCase;

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "Criar nova avaliação para um produto")
    public ResponseEntity<ReviewResponse> createReview(
            @PathVariable Long productId,
            @Valid @RequestBody CreateReviewRequest request) {
        Review review = Review.builder()
                .productId(productId)
                .userName(request.getUserName())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        Review createdReview = reviewUseCase.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(createdReview));
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "Listar todas as avaliações de um produto")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProduct(@PathVariable Long productId) {
        List<Review> reviews = reviewUseCase.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/products/{productId}/rating")
    @Operation(summary = "Obter média de avaliações de um produto")
    public ResponseEntity<Map<String, Object>> getProductRating(@PathVariable Long productId) {
        Double averageRating = reviewUseCase.getAverageRatingByProductId(productId);
        Long reviewCount = (long) reviewUseCase.getReviewsByProductId(productId).size();

        Map<String, Object> response = new HashMap<>();
        response.put("productId", productId);
        response.put("averageRating", averageRating != null ? averageRating : 0.0);
        response.put("reviewCount", reviewCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "Buscar avaliação por ID")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewUseCase.getReviewById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        return ResponseEntity.ok(toResponse(review));
    }

    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "Atualizar avaliação")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody CreateReviewRequest request) {
        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        Review updatedReview = reviewUseCase.updateReview(reviewId, review);
        return ResponseEntity.ok(toResponse(updatedReview));
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "Deletar avaliação")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewUseCase.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    // Mapper
    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .userName(review.getUserName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

