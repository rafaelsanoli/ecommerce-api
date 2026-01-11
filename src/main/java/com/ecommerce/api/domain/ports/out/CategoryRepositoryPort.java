package com.ecommerce.api.domain.ports.out;

import com.ecommerce.api.domain.entities.Category;

import java.util.List;
import java.util.Optional;

/**
 * Port OUT - Interface de repositório para Categorias
 */
public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    List<Category> findByParentCategoryId(Long parentCategoryId);
    void deleteById(Long id);
    boolean existsById(Long id);
}

