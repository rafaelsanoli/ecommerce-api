package com.ecommerce.api.infrastructure.adapters.out.persistence.adapters;

import com.ecommerce.api.domain.entities.Wishlist;
import com.ecommerce.api.domain.ports.out.WishlistRepositoryPort;
import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.WishlistEntity;
import com.ecommerce.api.infrastructure.adapters.out.persistence.repositories.WishlistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter - Implementação do Port OUT para Wishlist
 */
@Component
@RequiredArgsConstructor
public class WishlistRepositoryAdapter implements WishlistRepositoryPort {

    private final WishlistJpaRepository jpaRepository;

    @Override
    public Wishlist save(Wishlist wishlist) {
        WishlistEntity entity = toEntity(wishlist);
        WishlistEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Wishlist> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Wishlist> findByUserId(String userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndProductId(String userId, Long productId) {
        return jpaRepository.existsByUserIdAndProductId(userId, productId);
    }

    @Override
    public Optional<Wishlist> findByUserIdAndProductId(String userId, Long productId) {
        return jpaRepository.findByUserIdAndProductId(userId, productId)
                .map(this::toDomain);
    }

    // Mappers
    private WishlistEntity toEntity(Wishlist domain) {
        return WishlistEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .productId(domain.getProductId())
                .addedAt(domain.getAddedAt())
                .build();
    }

    private Wishlist toDomain(WishlistEntity entity) {
        return Wishlist.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .addedAt(entity.getAddedAt())
                .build();
    }
}

