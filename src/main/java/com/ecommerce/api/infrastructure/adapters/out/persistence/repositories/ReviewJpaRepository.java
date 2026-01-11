package com.ecommerce.api.infrastructure.adapters.out.persistence.repositories;

import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository - Review
 */
@Repository
public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByProductId(Long productId);

    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.productId = :productId")
    Double getAverageRatingByProductId(@Param("productId") Long productId);

    Long countByProductId(Long productId);
}

