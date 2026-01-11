package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.Category;
import com.ecommerce.api.domain.exceptions.CategoryNotFoundException;
import com.ecommerce.api.domain.ports.in.CategoryUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.CreateCategoryRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.CategoryResponse;
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
 * REST Controller - Categorias
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Endpoints para gerenciamento de categorias")
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping
    @Operation(summary = "Criar nova categoria")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentCategoryId(request.getParentCategoryId())
                .build();

        Category createdCategory = categoryUseCase.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(createdCategory));
    }

    @GetMapping
    @Operation(summary = "Listar todas as categorias")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryUseCase.getAllCategories();
        return ResponseEntity.ok(categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryUseCase.getCategoryById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return ResponseEntity.ok(toResponse(category));
    }

    @GetMapping("/{id}/subcategories")
    @Operation(summary = "Listar subcategorias de uma categoria")
    public ResponseEntity<List<CategoryResponse>> getSubcategories(@PathVariable Long id) {
        List<Category> subcategories = categoryUseCase.getSubcategories(id);
        return ResponseEntity.ok(subcategories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentCategoryId(request.getParentCategoryId())
                .build();

        Category updatedCategory = categoryUseCase.updateCategory(id, category);
        return ResponseEntity.ok(toResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar categoria")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryUseCase.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Mapper
    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentCategoryId(category.getParentCategoryId())
                .build();
    }
}

