package com.ecommerce.api.infrastructure.adapters.out.persistence.repositories;

import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository - Wishlist
 */
@Repository
public interface WishlistJpaRepository extends JpaRepository<WishlistEntity, Long> {
    List<WishlistEntity> findByUserId(String userId);
    boolean existsByUserIdAndProductId(String userId, Long productId);
    Optional<WishlistEntity> findByUserIdAndProductId(String userId, Long productId);
}

