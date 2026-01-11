package com.ecommerce.api.infrastructure.adapters.out.persistence.repositories;

import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository - Carrinho
 */
@Repository
public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(String userId);
}

