package com.ecommerce.api.infrastructure.adapters.out.persistence.adapters;

import com.ecommerce.api.domain.entities.Category;
import com.ecommerce.api.domain.ports.out.CategoryRepositoryPort;
import com.ecommerce.api.infrastructure.adapters.out.persistence.entities.CategoryEntity;
import com.ecommerce.api.infrastructure.adapters.out.persistence.repositories.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter - Implementação do Port OUT para Categoria
 */
@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository jpaRepository;

    @Override
    public Category save(Category category) {
        CategoryEntity entity = toEntity(category);
        CategoryEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findByParentCategoryId(Long parentCategoryId) {
        return jpaRepository.findByParentCategoryId(parentCategoryId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    // Mappers
    private CategoryEntity toEntity(Category domain) {
        return CategoryEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .parentCategoryId(domain.getParentCategoryId())
                .build();
    }

    private Category toDomain(CategoryEntity entity) {
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .parentCategoryId(entity.getParentCategoryId())
                .build();
    }
}

