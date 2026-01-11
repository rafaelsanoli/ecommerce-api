package com.ecommerce.api.domain.ports.in;

import com.ecommerce.api.domain.entities.Wishlist;

import java.util.List;
import java.util.Optional;

/**
 * Port IN - Casos de uso para Wishlist
 */
public interface WishlistUseCase {
    Wishlist addToWishlist(String userId, Long productId);
    List<Wishlist> getWishlistByUserId(String userId);
    Optional<Wishlist> getWishlistById(Long id);
    void removeFromWishlist(Long wishlistId);
    boolean isProductInWishlist(String userId, Long productId);
}

