package com.ecommerce.api.infrastructure.adapters.out.persistence.repositories;

import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository - Item do Carrinho
 */
@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
}

