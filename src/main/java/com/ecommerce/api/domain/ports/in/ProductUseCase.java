package com.ecommerce.api.domain.ports.in;

import com.ecommerce.api.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port IN - Casos de uso para Produtos
 */
public interface ProductUseCase {
    Product createProduct(Product product);
    Optional<Product> getProductById(Long id);
    Page<Product> getAllProducts(Pageable pageable);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> searchProductsByName(String name);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}

