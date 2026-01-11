package com.ecommerce.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de domínio - Categoria
 * Representa uma categoria de produtos (pode ter subcategorias)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
    private String description;
    private Long parentCategoryId;

    /**
     * Verifica se é uma categoria raiz (sem pai)
     */
    public boolean isRootCategory() {
        return parentCategoryId == null;
    }

    /**
     * Verifica se é uma subcategoria
     */
    public boolean isSubcategory() {
        return parentCategoryId != null;
    }
}

