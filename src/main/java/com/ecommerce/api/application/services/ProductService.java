package com.ecommerce.api.application.services;

import com.ecommerce.api.domain.entities.Product;
import com.ecommerce.api.domain.exceptions.CategoryNotFoundException;
import com.ecommerce.api.domain.exceptions.ProductNotFoundException;
import com.ecommerce.api.domain.ports.in.ProductUseCase;
import com.ecommerce.api.domain.ports.out.CategoryRepositoryPort;
import com.ecommerce.api.domain.ports.out.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service - Implementação dos casos de uso de Produto
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        log.info("Criando novo produto: {}", product.getName());

        // Valida se a categoria existe
        if (product.getCategoryId() != null && !categoryRepository.existsById(product.getCategoryId())) {
            throw new CategoryNotFoundException(product.getCategoryId());
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        log.info("Produto criado com sucesso. ID: {}", savedProduct.getId());

        return savedProduct;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        log.debug("Buscando produto por ID: {}", id);
        return productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        log.debug("Buscando todos os produtos. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        log.debug("Buscando produtos por categoria ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }

        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        log.debug("Buscando produtos por nome: {}", name);
        return productRepository.findByNameContaining(name);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
        log.info("Atualizando produto ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        // Valida se a nova categoria existe
        if (product.getCategoryId() != null && !categoryRepository.existsById(product.getCategoryId())) {
            throw new CategoryNotFoundException(product.getCategoryId());
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Produto atualizado com sucesso. ID: {}", updatedProduct.getId());

        return updatedProduct;
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deletando produto ID: {}", id);

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
        log.info("Produto deletado com sucesso. ID: {}", id);
    }
}

