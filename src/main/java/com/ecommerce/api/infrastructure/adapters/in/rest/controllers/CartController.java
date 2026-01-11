package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.Cart;
import com.ecommerce.api.domain.entities.CartItem;
import com.ecommerce.api.domain.exceptions.CartNotFoundException;
import com.ecommerce.api.domain.ports.in.CartUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.AddToCartRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.CreateCartRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.UpdateCartItemRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.CartItemResponse;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * REST Controller - Carrinho
 */
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Endpoints para gerenciamento do carrinho de compras")
public class CartController {

    private final CartUseCase cartUseCase;

    @PostMapping
    @Operation(summary = "Criar novo carrinho")
    public ResponseEntity<CartResponse> createCart(@Valid @RequestBody CreateCartRequest request) {
        Cart cart = cartUseCase.createCart(request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(cart));
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Buscar carrinho por ID")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long cartId) {
        Cart cart = cartUseCase.getCartById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        return ResponseEntity.ok(toResponse(cart));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar carrinho por ID do usuário")
    public ResponseEntity<CartResponse> getCartByUserId(@PathVariable String userId) {
        Cart cart = cartUseCase.getCartByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado para o usuário: " + userId));
        return ResponseEntity.ok(toResponse(cart));
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adicionar item ao carrinho")
    public ResponseEntity<CartResponse> addItemToCart(
            @PathVariable Long cartId,
            @Valid @RequestBody AddToCartRequest request) {
        Cart cart = cartUseCase.addItemToCart(cartId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(toResponse(cart));
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Atualizar quantidade de um item")
    public ResponseEntity<CartResponse> updateCartItemQuantity(
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        Cart cart = cartUseCase.updateCartItemQuantity(itemId, request.getQuantity());
        return ResponseEntity.ok(toResponse(cart));
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remover item do carrinho")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long itemId) {
        Cart cart = cartUseCase.removeItemFromCart(itemId);
        return ResponseEntity.ok(toResponse(cart));
    }

    @DeleteMapping("/{cartId}/clear")
    @Operation(summary = "Limpar todos os itens do carrinho")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartUseCase.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    @Operation(summary = "Deletar carrinho")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartUseCase.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    // Mappers
    private CartResponse toResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cart.getItems() != null ? cart.getItems().stream()
                        .map(this::toCartItemResponse)
                        .collect(Collectors.toList()) : null)
                .totalItems(cart.getTotalItems())
                .totalPrice(cart.getTotalPrice())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    private CartItemResponse toCartItemResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .priceAtAddTime(item.getPriceAtAddTime())
                .subtotal(item.getSubtotal())
                .build();
    }
}

