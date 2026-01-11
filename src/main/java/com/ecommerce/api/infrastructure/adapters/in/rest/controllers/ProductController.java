package com.ecommerce.api.infrastructure.adapters.in.rest.controllers;

import com.ecommerce.api.domain.entities.Product;
import com.ecommerce.api.domain.exceptions.ProductNotFoundException;
import com.ecommerce.api.domain.ports.in.ProductUseCase;
import com.ecommerce.api.domain.ports.in.CategoryUseCase;
import com.ecommerce.api.domain.ports.in.ReviewUseCase;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.CreateProductRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.request.UpdateProductRequest;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.CategoryResponse;
import com.ecommerce.api.infrastructure.adapters.in.rest.dto.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller - Produtos
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    private final ProductUseCase productUseCase;
    private final CategoryUseCase categoryUseCase;
    private final ReviewUseCase reviewUseCase;

    @PostMapping
    @Operation(summary = "Criar novo produto")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .categoryId(request.getCategoryId())
                .build();

        Product createdProduct = productUseCase.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(createdProduct));
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos com paginação")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> products = productUseCase.getAllProducts(pageable);
        return ResponseEntity.ok(products.map(this::toResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productUseCase.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.ok(toResponse(product));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Listar produtos por categoria")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productUseCase.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos por nome")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        List<Product> products = productUseCase.searchProductsByName(name);
        return ResponseEntity.ok(products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .categoryId(request.getCategoryId())
                .build();

        Product updatedProduct = productUseCase.updateProduct(id, product);
        return ResponseEntity.ok(toResponse(updatedProduct));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Mapper
    private ProductResponse toResponse(Product product) {
        CategoryResponse categoryResponse = null;
        if (product.getCategoryId() != null) {
            categoryResponse = categoryUseCase.getCategoryById(product.getCategoryId())
                    .map(category -> CategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .description(category.getDescription())
                            .parentCategoryId(category.getParentCategoryId())
                            .build())
                    .orElse(null);
        }

        Double averageRating = reviewUseCase.getAverageRatingByProductId(product.getId());
        Long reviewCount = 0L;
        try {
            reviewCount = (long) reviewUseCase.getReviewsByProductId(product.getId()).size();
        } catch (Exception e) {
            // Ignora erro se produto ainda não tem reviews
        }

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .category(categoryResponse)
                .averageRating(averageRating != null ? averageRating : 0.0)
                .reviewCount(reviewCount)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}

