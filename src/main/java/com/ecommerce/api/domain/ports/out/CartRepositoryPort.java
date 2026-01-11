package com.ecommerce.api.domain.ports.out;

import com.ecommerce.api.domain.entities.Cart;
import com.ecommerce.api.domain.entities.CartItem;

import java.util.Optional;

/**
 * Port OUT - Interface de repositório para Carrinho
 */
public interface CartRepositoryPort {
    Cart save(Cart cart);
    Optional<Cart> findById(Long id);
    Optional<Cart> findByUserId(String userId);
    void deleteById(Long id);
    boolean existsById(Long id);

    CartItem saveCartItem(CartItem cartItem);
    Optional<CartItem> findCartItemById(Long id);
    void deleteCartItem(Long id);
    Optional<CartItem> findCartItemByCartIdAndProductId(Long cartId, Long productId);
}

