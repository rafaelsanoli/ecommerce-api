package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.Cart;
import com.ecommerce.api.domain.entities.CartItem;
import com.ecommerce.api.domain.entities.Product;
import com.ecommerce.api.domain.exceptions.CartNotFoundException;
import com.ecommerce.api.domain.exceptions.InsufficientStockException;
import com.ecommerce.api.domain.exceptions.ProductNotFoundException;
import com.ecommerce.api.domain.ports.in.CartUseCase;
import com.ecommerce.api.domain.ports.out.CartRepositoryPort;
import com.ecommerce.api.domain.ports.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Carrinho
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartService implements CartUseCase {

    private final CartRepositoryPort cartRepository;
    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional
    public Cart createCart(String userId) {
        log.info("Criando novo carrinho para usuário: {}", userId);

        Cart cart = Cart.builder()
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Cart savedCart = cartRepository.save(cart);
        log.info("Carrinho criado com sucesso. ID: {}", savedCart.getId());

        return savedCart;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> getCartById(Long id) {
        log.debug("Buscando carrinho por ID: {}", id);
        return cartRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cart> getCartByUserId(String userId) {
        log.debug("Buscando carrinho por usuário: {}", userId);
        return cartRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long cartId, Long productId, Integer quantity) {
        log.info("Adicionando item ao carrinho. CartId: {}, ProductId: {}, Quantity: {}",
                cartId, productId, quantity);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Verifica se há estoque suficiente
        if (!product.hasStock(quantity)) {
            throw new InsufficientStockException(product.getName(), quantity, product.getStock());
        }

        // Verifica se o item já existe no carrinho
        Optional<CartItem> existingItem = cartRepository
                .findCartItemByCartIdAndProductId(cartId, productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;

            if (!product.hasStock(newQuantity)) {
                throw new InsufficientStockException(product.getName(), newQuantity, product.getStock());
            }

            item.setQuantity(newQuantity);
            cartRepository.saveCartItem(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .cartId(cartId)
                    .productId(productId)
                    .productName(product.getName())
                    .quantity(quantity)
                    .priceAtAddTime(product.getPrice())
                    .build();

            cartRepository.saveCartItem(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        Cart updatedCart = cartRepository.save(cart);

        log.info("Item adicionado ao carrinho com sucesso");
        return cartRepository.findById(cartId).orElse(updatedCart);
    }

    @Override
    @Transactional
    public Cart updateCartItemQuantity(Long itemId, Integer quantity) {
        log.info("Atualizando quantidade do item. ItemId: {}, Quantity: {}", itemId, quantity);

        CartItem item = cartRepository.findCartItemById(itemId)
                .orElseThrow(() -> new CartNotFoundException("Item não encontrado com ID: " + itemId));

        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));

        if (!product.hasStock(quantity)) {
            throw new InsufficientStockException(product.getName(), quantity, product.getStock());
        }

        item.setQuantity(quantity);
        cartRepository.saveCartItem(item);

        Cart cart = cartRepository.findById(item.getCartId())
                .orElseThrow(() -> new CartNotFoundException(item.getCartId()));
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        log.info("Quantidade do item atualizada com sucesso");
        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    @Override
    @Transactional
    public Cart removeItemFromCart(Long itemId) {
        log.info("Removendo item do carrinho. ItemId: {}", itemId);

        CartItem item = cartRepository.findCartItemById(itemId)
                .orElseThrow(() -> new CartNotFoundException("Item não encontrado com ID: " + itemId));

        Long cartId = item.getCartId();
        cartRepository.deleteCartItem(itemId);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        log.info("Item removido do carrinho com sucesso");
        return cartRepository.findById(cartId).orElse(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long cartId) {
        log.info("Limpando carrinho ID: {}", cartId);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        cart.clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        log.info("Carrinho limpo com sucesso");
    }

    @Override
    @Transactional
    public void deleteCart(Long cartId) {
        log.info("Deletando carrinho ID: {}", cartId);

        if (!cartRepository.existsById(cartId)) {
            throw new CartNotFoundException(cartId);
        }

        cartRepository.deleteById(cartId);
        log.info("Carrinho deletado com sucesso");
    }
}

