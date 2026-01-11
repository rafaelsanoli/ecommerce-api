package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.Review;
import com.ecommerce.api.domain.exceptions.ProductNotFoundException;
import com.ecommerce.api.domain.exceptions.ReviewNotFoundException;
import com.ecommerce.api.domain.ports.in.ReviewUseCase;
import com.ecommerce.api.domain.ports.out.ProductRepositoryPort;
import com.ecommerce.api.domain.ports.out.ReviewRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Review
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewUseCase {

    private final ReviewRepositoryPort reviewRepository;
    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional
    public Review createReview(Review review) {
        log.info("Criando nova avaliação para produto ID: {}", review.getProductId());

        // Valida se o produto existe
        if (!productRepository.existsById(review.getProductId())) {
            throw new ProductNotFoundException(review.getProductId());
        }

        // Valida o rating
        review.validateRating();

        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        log.info("Avaliação criada com sucesso. ID: {}", savedReview.getId());

        return savedReview;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> getReviewById(Long id) {
        log.debug("Buscando avaliação por ID: {}", id);
        return reviewRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getReviewsByProductId(Long productId) {
        log.debug("Buscando avaliações do produto ID: {}", productId);

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }

        return reviewRepository.findByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRatingByProductId(Long productId) {
        log.debug("Calculando média de avaliações do produto ID: {}", productId);

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }

        Double average = reviewRepository.getAverageRatingByProductId(productId);
        return average != null ? average : 0.0;
    }

    @Override
    @Transactional
    public Review updateReview(Long id, Review review) {
        log.info("Atualizando avaliação ID: {}", id);

        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        // Valida o novo rating
        review.validateRating();

        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());

        Review updatedReview = reviewRepository.save(existingReview);
        log.info("Avaliação atualizada com sucesso. ID: {}", updatedReview.getId());

        return updatedReview;
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        log.info("Deletando avaliação ID: {}", id);

        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException(id);
        }

        reviewRepository.deleteById(id);
        log.info("Avaliação deletada com sucesso. ID: {}", id);
    }
}

