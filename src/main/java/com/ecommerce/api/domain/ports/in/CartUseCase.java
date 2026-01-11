package com.ecommerce.api.domain.ports.in;

import com.ecommerce.api.domain.entities.Cart;
import com.ecommerce.api.domain.entities.CartItem;

import java.util.Optional;

/**
 * Port IN - Casos de uso para Carrinho
 */
public interface CartUseCase {
    Cart createCart(String userId);
    Optional<Cart> getCartById(Long id);
    Optional<Cart> getCartByUserId(String userId);
    Cart addItemToCart(Long cartId, Long productId, Integer quantity);
    Cart updateCartItemQuantity(Long itemId, Integer quantity);
    Cart removeItemFromCart(Long itemId);
    void clearCart(Long cartId);
    void deleteCart(Long cartId);
}

