package com.ecommerce.api.domain.ports.out;

import com.ecommerce.api.domain.entities.Review;

import java.util.List;
import java.util.Optional;

/**
 * Port OUT - Interface de repositório para Reviews
 */
public interface ReviewRepositoryPort {
    Review save(Review review);
    Optional<Review> findById(Long id);
    List<Review> findByProductId(Long productId);
    void deleteById(Long id);
    boolean existsById(Long id);
    Double getAverageRatingByProductId(Long productId);
    Long countByProductId(Long productId);
}

