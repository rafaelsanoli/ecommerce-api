package com.ecommerce.api.domain.ports.out;

import com.ecommerce.api.domain.entities.Wishlist;

import java.util.List;
import java.util.Optional;

/**
 * Port OUT - Interface de repositório para Wishlist
 */
public interface WishlistRepositoryPort {
    Wishlist save(Wishlist wishlist);
    Optional<Wishlist> findById(Long id);
    List<Wishlist> findByUserId(String userId);
    void deleteById(Long id);
    boolean existsByUserIdAndProductId(String userId, Long productId);
    Optional<Wishlist> findByUserIdAndProductId(String userId, Long productId);
}

