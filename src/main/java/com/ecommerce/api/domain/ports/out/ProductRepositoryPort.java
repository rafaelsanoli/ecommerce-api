package com.ecommerce.api.domain.ports.out;

import com.ecommerce.api.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port OUT - Interface de repositório para Produtos
 */
public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContaining(String name);
    void deleteById(Long id);
    boolean existsById(Long id);
}

