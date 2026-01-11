package com.ecommerce.api.infrastructure.adapters.out.persistence.adapters;

import com.ecommerce.api.domain.entities.Review;
import com.ecommerce.api.domain.ports.out.ReviewRepositoryPort;
import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.ReviewEntity;
import com.ecommerce.api.infrastructure.adapters.out.persistence.repositories.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter - Implementação do Port OUT para Review
 */
@Component
@RequiredArgsConstructor
public class ReviewRepositoryAdapter implements ReviewRepositoryPort {

    private final ReviewJpaRepository jpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity entity = toEntity(review);
        ReviewEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Review> findByProductId(Long productId) {
        return jpaRepository.findByProductId(productId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public Double getAverageRatingByProductId(Long productId) {
        return jpaRepository.getAverageRatingByProductId(productId);
    }

    @Override
    public Long countByProductId(Long productId) {
        return jpaRepository.countByProductId(productId);
    }

    // Mappers
    private ReviewEntity toEntity(Review domain) {
        return ReviewEntity.builder()
                .id(domain.getId())
                .productId(domain.getProductId())
                .userName(domain.getUserName())
                .rating(domain.getRating())
                .comment(domain.getComment())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    private Review toDomain(ReviewEntity entity) {
        return Review.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .userName(entity.getUserName())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

