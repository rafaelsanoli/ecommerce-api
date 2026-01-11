package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.Wishlist;
import com.ecommerce.api.domain.exceptions.BusinessException;
import com.ecommerce.api.domain.ports.in.ProductUseCase;
import com.ecommerce.api.domain.ports.in.WishlistUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.AddToWishlistRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.ProductResponse;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.WishlistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller - Wishlist
 */
@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "Endpoints para gerenciamento da lista de desejos")
public class WishlistController {

    private final WishlistUseCase wishlistUseCase;
    private final ProductUseCase productUseCase;

    @PostMapping
    @Operation(summary = "Adicionar produto à lista de desejos")
    public ResponseEntity<WishlistResponse> addToWishlist(@Valid @RequestBody AddToWishlistRequest request) {
        Wishlist wishlist = wishlistUseCase.addToWishlist(request.getUserId(), request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(wishlist));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar itens da lista de desejos de um usuário")
    public ResponseEntity<List<WishlistResponse>> getWishlistByUser(@PathVariable String userId) {
        List<Wishlist> wishlistItems = wishlistUseCase.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlistItems.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{wishlistId}")
    @Operation(summary = "Buscar item da wishlist por ID")
    public ResponseEntity<WishlistResponse> getWishlistById(@PathVariable Long wishlistId) {
        Wishlist wishlist = wishlistUseCase.getWishlistById(wishlistId)
                .orElseThrow(() -> new BusinessException("Item não encontrado na wishlist"));
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @DeleteMapping("/{wishlistId}")
    @Operation(summary = "Remover item da lista de desejos")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long wishlistId) {
        wishlistUseCase.removeFromWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    @Operation(summary = "Verificar se produto está na wishlist do usuário")
    public ResponseEntity<Boolean> checkProductInWishlist(
            @RequestParam String userId,
            @RequestParam Long productId) {
        boolean isInWishlist = wishlistUseCase.isProductInWishlist(userId, productId);
        return ResponseEntity.ok(isInWishlist);
    }

    // Mapper
    private WishlistResponse toResponse(Wishlist wishlist) {
        ProductResponse productResponse = null;

        try {
            productResponse = productUseCase.getProductById(wishlist.getProductId())
                    .map(product -> ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .stock(product.getStock())
                            .imageUrl(product.getImageUrl())
                            .createdAt(product.getCreatedAt())
                            .updatedAt(product.getUpdatedAt())
                            .build())
                    .orElse(null);
        } catch (Exception e) {
            // Ignora se produto não encontrado
        }

        return WishlistResponse.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUserId())
                .productId(wishlist.getProductId())
                .product(productResponse)
                .addedAt(wishlist.getAddedAt())
                .build();
    }
}

