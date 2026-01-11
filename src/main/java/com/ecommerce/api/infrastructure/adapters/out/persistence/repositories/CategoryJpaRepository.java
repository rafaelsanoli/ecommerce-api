package com.ecommerce.api.infrastructure.adapters.out.persistence.repositories;

import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository - Categoria
 */
@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByParentCategoryId(Long parentCategoryId);
}

