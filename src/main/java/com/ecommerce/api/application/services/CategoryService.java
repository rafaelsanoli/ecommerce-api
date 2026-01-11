package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.Category;
import com.ecommerce.api.domain.exceptions.CategoryNotFoundException;
import com.ecommerce.api.domain.ports.in.CategoryUseCase;
import com.ecommerce.api.domain.ports.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Categoria
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepositoryPort categoryRepository;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        log.info("Criando nova categoria: {}", category.getName());

        // Valida se a categoria pai existe (se for uma subcategoria)
        if (category.getParentCategoryId() != null
                && !categoryRepository.existsById(category.getParentCategoryId())) {
            throw new CategoryNotFoundException(
                    "Categoria pai não encontrada com ID: " + category.getParentCategoryId());
        }

        Category savedCategory = categoryRepository.save(category);
        log.info("Categoria criada com sucesso. ID: {}", savedCategory.getId());

        return savedCategory;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        log.debug("Buscando categoria por ID: {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        log.debug("Buscando todas as categorias");
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getSubcategories(Long parentCategoryId) {
        log.debug("Buscando subcategorias da categoria ID: {}", parentCategoryId);

        if (!categoryRepository.existsById(parentCategoryId)) {
            throw new CategoryNotFoundException(parentCategoryId);
        }

        return categoryRepository.findByParentCategoryId(parentCategoryId);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        log.info("Atualizando categoria ID: {}", id);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        // Valida se a nova categoria pai existe
        if (category.getParentCategoryId() != null
                && !categoryRepository.existsById(category.getParentCategoryId())) {
            throw new CategoryNotFoundException(
                    "Categoria pai não encontrada com ID: " + category.getParentCategoryId());
        }

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setParentCategoryId(category.getParentCategoryId());

        Category updatedCategory = categoryRepository.save(existingCategory);
        log.info("Categoria atualizada com sucesso. ID: {}", updatedCategory.getId());

        return updatedCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Deletando categoria ID: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }

        categoryRepository.deleteById(id);
        log.info("Categoria deletada com sucesso. ID: {}", id);
    }
}

