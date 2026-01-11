package com.ecommerce.api.domain.ports.in;

import com.ecommerce.api.domain.entities.Category;

import java.util.List;
import java.util.Optional;

/**
 * Port IN - Casos de uso para Categorias
 */
public interface CategoryUseCase {
    Category createCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> getSubcategories(Long parentCategoryId);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}

