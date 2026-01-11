package com.ecommerce.api.domain.ports.in;

import com.ecommerce.api.domain.entities.Review;

import java.util.List;
import java.util.Optional;

/**
 * Port IN - Casos de uso para Reviews
 */
public interface ReviewUseCase {
    Review createReview(Review review);
    Optional<Review> getReviewById(Long id);
    List<Review> getReviewsByProductId(Long productId);
    Double getAverageRatingByProductId(Long productId);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
}

