package com.ecommerce.api.infrastructure.adapters.out.persistence.adapters;

import com.ecommerce.api.domain.entities.Cart;
import com.ecommerce.api.domain.entities.CartItem;
import com.ecommerce.api.domain.ports.out.CartRepositoryPort;
import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.CartEntity;
import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.CartItemEntity;
import com.ecommerce.api.infrastructure.adapters.out.persistence.repositories.CartItemJpaRepository;
import com.ecommerce.api.infrastructure.adapters.out.persistence.repositories.CartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter - Implementação do Port OUT para Carrinho
 */
@Component
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepositoryPort {

    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = toEntity(cart);
        CartEntity savedEntity = cartJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Cart> findByUserId(String userId) {
        return cartJpaRepository.findByUserId(userId).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cartJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cartJpaRepository.existsById(id);
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        CartItemEntity entity = toCartItemEntity(cartItem);
        CartItemEntity savedEntity = cartItemJpaRepository.save(entity);
        return toCartItemDomain(savedEntity);
    }

    @Override
    public Optional<CartItem> findCartItemById(Long id) {
        return cartItemJpaRepository.findById(id).map(this::toCartItemDomain);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemJpaRepository.deleteById(id);
    }

    @Override
    public Optional<CartItem> findCartItemByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemJpaRepository.findByCartIdAndProductId(cartId, productId)
                .map(this::toCartItemDomain);
    }

    // Mappers - Cart
    private CartEntity toEntity(Cart domain) {
        CartEntity entity = CartEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
        return entity;
    }

    private Cart toDomain(CartEntity entity) {
        Cart cart = Cart.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        if (entity.getItems() != null) {
            cart.setItems(entity.getItems().stream()
                    .map(this::toCartItemDomain)
                    .collect(Collectors.toList()));
        }

        return cart;
    }

    // Mappers - CartItem
    private CartItemEntity toCartItemEntity(CartItem domain) {
        CartEntity cartEntity = cartJpaRepository.findById(domain.getCartId()).orElse(null);

        return CartItemEntity.builder()
                .id(domain.getId())
                .cart(cartEntity)
                .productId(domain.getProductId())
                .productName(domain.getProductName())
                .quantity(domain.getQuantity())
                .priceAtAddTime(domain.getPriceAtAddTime())
                .build();
    }

    private CartItem toCartItemDomain(CartItemEntity entity) {
        return CartItem.builder()
                .id(entity.getId())
                .cartId(entity.getCart() != null ? entity.getCart().getId() : null)
                .productId(entity.getProductId())
                .productName(entity.getProductName())
                .quantity(entity.getQuantity())
                .priceAtAddTime(entity.getPriceAtAddTime())
                .build();
    }
}

