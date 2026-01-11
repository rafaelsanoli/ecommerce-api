package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.Wishlist;
import com.ecommerce.api.domain.exceptions.BusinessException;
import com.ecommerce.api.domain.exceptions.ProductNotFoundException;
import com.ecommerce.api.domain.ports.in.WishlistUseCase;
import com.ecommerce.api.domain.ports.out.ProductRepositoryPort;
import com.ecommerce.api.domain.ports.out.WishlistRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Wishlist
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService implements WishlistUseCase {

    private final WishlistRepositoryPort wishlistRepository;
    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional
    public Wishlist addToWishlist(String userId, Long productId) {
        log.info("Adicionando produto à wishlist. UserId: {}, ProductId: {}", userId, productId);

        // Valida se o produto existe
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }

        // Verifica se o produto já está na wishlist
        if (wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new BusinessException("Produto já está na lista de desejos");
        }

        Wishlist wishlist = Wishlist.builder()
                .userId(userId)
                .productId(productId)
                .addedAt(LocalDateTime.now())
                .build();

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        log.info("Produto adicionado à wishlist com sucesso. ID: {}", savedWishlist.getId());

        return savedWishlist;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getWishlistByUserId(String userId) {
        log.debug("Buscando wishlist do usuário: {}", userId);
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Wishlist> getWishlistById(Long id) {
        log.debug("Buscando item da wishlist por ID: {}", id);
        return wishlistRepository.findById(id);
    }

    @Override
    @Transactional
    public void removeFromWishlist(Long wishlistId) {
        log.info("Removendo item da wishlist. ID: {}", wishlistId);

        if (!wishlistRepository.findById(wishlistId).isPresent()) {
            throw new BusinessException("Item não encontrado na wishlist com ID: " + wishlistId);
        }

        wishlistRepository.deleteById(wishlistId);
        log.info("Item removido da wishlist com sucesso");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isProductInWishlist(String userId, Long productId) {
        log.debug("Verificando se produto está na wishlist. UserId: {}, ProductId: {}",
                userId, productId);
        return wishlistRepository.existsByUserIdAndProductId(userId, productId);
    }
}

