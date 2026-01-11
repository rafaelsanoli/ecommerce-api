# 💻 Guia Completo para Desenvolvedores Frontend

Este guia foi criado especialmente para você que está desenvolvendo a interface do e-commerce!

---

## 🎯 Visão Geral

Esta API fornece tudo que você precisa para construir um e-commerce completo:
- ✅ Catálogo de produtos com paginação
- ✅ Sistema de categorias
- ✅ Carrinho de compras
- ✅ Avaliações de produtos
- ✅ Lista de favoritos (wishlist)

---

## 🚀 Começando em 5 Minutos

### 1. Certifique-se que a API está rodando

```bash
# Terminal
mvn spring-boot:run

# Aguarde ver: "🚀 E-COMMERCE API ESTÁ RODANDO!"
```

### 2. Teste no navegador

Acesse: http://localhost:8080/api/v1/products

Você deve ver JSON com produtos! 🎉

### 3. Configure CORS (já está pronto!)

A API já está configurada para aceitar requisições de qualquer origem. Sem preocupações! ✅

---

## 📡 Base URL e Endpoints

### Base URL
```
http://localhost:8080/api/v1
```

### Endpoints Principais
| Recurso | Endpoint | Descrição |
|---------|----------|-----------|
| Produtos | `/products` | Listar/criar produtos |
| Categorias | `/categories` | Listar categorias |
| Carrinho | `/cart` | Gerenciar carrinho |
| Reviews | `/products/{id}/reviews` | Avaliações |
| Wishlist | `/wishlist` | Lista de favoritos |

---

## 🎨 Exemplos por Framework

### React / Next.js

#### Listar Produtos
```jsx
import { useEffect, useState } from 'react';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/v1/products')
      .then(res => res.json())
      .then(data => {
        setProducts(data.content); // Paginado!
        setLoading(false);
      })
      .catch(error => {
        console.error('Erro:', error);
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Carregando...</div>;

  return (
    <div className="product-grid">
      {products.map(product => (
        <div key={product.id} className="product-card">
          <img src={product.imageUrl} alt={product.name} />
          <h3>{product.name}</h3>
          <p>{product.description}</p>
          <span className="price">R$ {product.price}</span>
          <div className="rating">
            ⭐ {product.averageRating} ({product.reviewCount} avaliações)
          </div>
        </div>
      ))}
    </div>
  );
}
```

#### Adicionar ao Carrinho
```jsx
async function addToCart(productId, quantity = 1) {
  // 1. Buscar ou criar carrinho
  const userId = 'user-123'; // ID do seu usuário
  let cartId = localStorage.getItem('cartId');

  if (!cartId) {
    // Criar novo carrinho
    const cartResponse = await fetch('http://localhost:8080/api/v1/cart', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId })
    });
    const cart = await cartResponse.json();
    cartId = cart.id;
    localStorage.setItem('cartId', cartId);
  }

  // 2. Adicionar item ao carrinho
  const response = await fetch(
    `http://localhost:8080/api/v1/cart/${cartId}/items`,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ productId, quantity })
    }
  );

  const updatedCart = await response.json();
  console.log('Carrinho atualizado:', updatedCart);
  return updatedCart;
}

// Usar no componente
<button onClick={() => addToCart(product.id, 2)}>
  Adicionar ao Carrinho
</button>
```

#### Hook Customizado para Carrinho
```jsx
// hooks/useCart.js
import { useState, useEffect } from 'react';

export function useCart(userId) {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadCart();
  }, [userId]);

  async function loadCart() {
    const cartId = localStorage.getItem('cartId');
    if (!cartId) {
      // Criar novo carrinho
      const response = await fetch('http://localhost:8080/api/v1/cart', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId })
      });
      const newCart = await response.json();
      localStorage.setItem('cartId', newCart.id);
      setCart(newCart);
    } else {
      // Carregar carrinho existente
      const response = await fetch(
        `http://localhost:8080/api/v1/cart/${cartId}`
      );
      const existingCart = await response.json();
      setCart(existingCart);
    }
    setLoading(false);
  }

  async function addItem(productId, quantity) {
    const response = await fetch(
      `http://localhost:8080/api/v1/cart/${cart.id}/items`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ productId, quantity })
      }
    );
    const updatedCart = await response.json();
    setCart(updatedCart);
    return updatedCart;
  }

  async function removeItem(itemId) {
    await fetch(`http://localhost:8080/api/v1/cart/items/${itemId}`, {
      method: 'DELETE'
    });
    await loadCart(); // Recarregar carrinho
  }

  async function updateQuantity(itemId, quantity) {
    const response = await fetch(
      `http://localhost:8080/api/v1/cart/items/${itemId}`,
      {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ quantity })
      }
    );
    const updatedCart = await response.json();
    setCart(updatedCart);
  }

  return {
    cart,
    loading,
    addItem,
    removeItem,
    updateQuantity,
    reload: loadCart
  };
}

// Usar no componente
function CartPage() {
  const { cart, loading, addItem, removeItem, updateQuantity } = 
    useCart('user-123');

  if (loading) return <div>Carregando carrinho...</div>;

  return (
    <div>
      <h2>Meu Carrinho ({cart.totalItems} itens)</h2>
      {cart.items.map(item => (
        <div key={item.id}>
          <span>{item.productName}</span>
          <input 
            type="number" 
            value={item.quantity}
            onChange={(e) => updateQuantity(item.id, e.target.value)}
          />
          <button onClick={() => removeItem(item.id)}>Remover</button>
          <span>R$ {item.subtotal}</span>
        </div>
      ))}
      <div>
        <strong>Total: R$ {cart.totalPrice}</strong>
      </div>
    </div>
  );
}
```

---

### Vue.js / Nuxt

#### Composable para Produtos
```vue
<!-- composables/useProducts.js -->
<script setup>
import { ref, onMounted } from 'vue';

export const useProducts = () => {
  const products = ref([]);
  const loading = ref(true);
  const error = ref(null);

  const fetchProducts = async (page = 0, size = 20) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/products?page=${page}&size=${size}`
      );
      const data = await response.json();
      products.value = data.content;
    } catch (err) {
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  };

  onMounted(() => fetchProducts());

  return {
    products,
    loading,
    error,
    fetchProducts
  };
};
</script>

<!-- Componente -->
<template>
  <div class="products">
    <div v-if="loading">Carregando...</div>
    <div v-else-if="error">Erro: {{ error }}</div>
    <div v-else class="product-grid">
      <ProductCard 
        v-for="product in products" 
        :key="product.id"
        :product="product"
      />
    </div>
  </div>
</template>

<script setup>
const { products, loading, error } = useProducts();
</script>
```

---

### Angular

#### Service para Produtos
```typescript
// services/product.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  imageUrl: string;
  averageRating: number;
  reviewCount: number;
}

export interface PageResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/v1/products';

  constructor(private http: HttpClient) {}

  getProducts(page: number = 0, size: number = 20): 
    Observable<PageResponse<Product>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponse<Product>>(this.apiUrl, { params });
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  searchProducts(name: string): Observable<Product[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Product[]>(`${this.apiUrl}/search`, { params });
  }
}

// Component
import { Component, OnInit } from '@angular/core';
import { ProductService, Product } from './services/product.service';

@Component({
  selector: 'app-product-list',
  template: `
    <div *ngIf="loading">Carregando...</div>
    <div *ngIf="!loading" class="product-grid">
      <div *ngFor="let product of products" class="product-card">
        <img [src]="product.imageUrl" [alt]="product.name">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
        <span class="price">R$ {{ product.price }}</span>
      </div>
    </div>
  `
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  loading = true;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getProducts().subscribe(
      data => {
        this.products = data.content;
        this.loading = false;
      },
      error => {
        console.error('Erro ao carregar produtos:', error);
        this.loading = false;
      }
    );
  }
}
```

---

### Vanilla JavaScript

#### API Client Simples
```javascript
// api/client.js
const API_BASE_URL = 'http://localhost:8080/api/v1';

class EcommerceAPI {
  // Produtos
  async getProducts(page = 0, size = 20) {
    const response = await fetch(
      `${API_BASE_URL}/products?page=${page}&size=${size}`
    );
    return response.json();
  }

  async getProduct(id) {
    const response = await fetch(`${API_BASE_URL}/products/${id}`);
    return response.json();
  }

  async searchProducts(name) {
    const response = await fetch(
      `${API_BASE_URL}/products/search?name=${encodeURIComponent(name)}`
    );
    return response.json();
  }

  // Carrinho
  async createCart(userId) {
    const response = await fetch(`${API_BASE_URL}/cart`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId })
    });
    return response.json();
  }

  async addToCart(cartId, productId, quantity) {
    const response = await fetch(
      `${API_BASE_URL}/cart/${cartId}/items`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ productId, quantity })
      }
    );
    return response.json();
  }

  async getCart(cartId) {
    const response = await fetch(`${API_BASE_URL}/cart/${cartId}`);
    return response.json();
  }

  // Reviews
  async addReview(productId, userName, rating, comment) {
    const response = await fetch(
      `${API_BASE_URL}/products/${productId}/reviews`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userName, rating, comment })
      }
    );
    return response.json();
  }

  async getReviews(productId) {
    const response = await fetch(
      `${API_BASE_URL}/products/${productId}/reviews`
    );
    return response.json();
  }

  // Wishlist
  async addToWishlist(userId, productId) {
    const response = await fetch(`${API_BASE_URL}/wishlist`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId, productId })
    });
    return response.json();
  }

  async getWishlist(userId) {
    const response = await fetch(
      `${API_BASE_URL}/wishlist/user/${userId}`
    );
    return response.json();
  }
}

// Exportar instância
const api = new EcommerceAPI();
export default api;

// Usar
import api from './api/client.js';

async function loadProducts() {
  const data = await api.getProducts();
  displayProducts(data.content);
}
```

---

## 🎨 Recursos da API para UI

### Paginação
```javascript
// Carregar página específica
fetch('http://localhost:8080/api/v1/products?page=2&size=12')
  .then(res => res.json())
  .then(data => {
    console.log('Página:', data.number);
    console.log('Total de páginas:', data.totalPages);
    console.log('Produtos:', data.content);
  });
```

### Ordenação
```javascript
// Ordenar por preço (mais barato primeiro)
fetch('http://localhost:8080/api/v1/products?sort=price,asc')

// Ordenar por nome
fetch('http://localhost:8080/api/v1/products?sort=name,asc')

// Múltiplos critérios
fetch('http://localhost:8080/api/v1/products?sort=price,asc&sort=name,asc')
```

### Busca
```javascript
// Buscar por nome
fetch('http://localhost:8080/api/v1/products/search?name=notebook')

// Filtrar por categoria
fetch('http://localhost:8080/api/v1/products/category/2')
```

---

## ⚠️ Tratamento de Erros

### Exemplo Completo
```javascript
async function addProductToCart(productId, quantity) {
  try {
    const cartId = getCartId(); // Seu método para pegar cartId
    
    const response = await fetch(
      `http://localhost:8080/api/v1/cart/${cartId}/items`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ productId, quantity })
      }
    );

    if (!response.ok) {
      const error = await response.json();
      
      // Tratar diferentes tipos de erro
      switch (response.status) {
        case 400:
          // Erro de validação ou negócio
          alert(`Erro: ${error.message}`);
          // Ex: "Estoque insuficiente"
          break;
        case 404:
          alert('Produto ou carrinho não encontrado');
          break;
        case 500:
          alert('Erro no servidor. Tente novamente mais tarde.');
          break;
        default:
          alert('Erro desconhecido');
      }
      
      return null;
    }

    const cart = await response.json();
    return cart;
    
  } catch (error) {
    console.error('Erro de rede:', error);
    alert('Não foi possível conectar ao servidor');
    return null;
  }
}
```

---

## 💡 Dicas e Melhores Práticas

### 1. Gerenciamento de Estado
```javascript
// Usar contexto/store global para carrinho
// React Context, Vuex, NgRx, Redux, etc.

// Exemplo: Context API (React)
const CartContext = createContext();

function CartProvider({ children }) {
  const [cart, setCart] = useState(null);
  
  return (
    <CartContext.Provider value={{ cart, setCart }}>
      {children}
    </CartContext.Provider>
  );
}
```

### 2. Cache de Dados
```javascript
// Usar React Query, SWR, ou cache manual
import useSWR from 'swr';

function useProducts() {
  const { data, error } = useSWR(
    'http://localhost:8080/api/v1/products',
    fetcher,
    { refreshInterval: 30000 } // Revalidar a cada 30s
  );
  
  return {
    products: data?.content,
    loading: !error && !data,
    error
  };
}
```

### 3. Loading States
```jsx
// Sempre mostre feedback visual
{loading && <Spinner />}
{error && <ErrorMessage error={error} />}
{data && <ProductList products={data} />}
```

### 4. Otimização de Imagens
```jsx
// As URLs de imagem já vêm da API
<img 
  src={product.imageUrl} 
  alt={product.name}
  loading="lazy" // Lazy loading nativo
/>
```

---

## 🎯 Checklist do Frontend

### Funcionalidades Essenciais
- [ ] Listar produtos com paginação
- [ ] Exibir detalhes do produto
- [ ] Buscar produtos
- [ ] Filtrar por categoria
- [ ] Adicionar ao carrinho
- [ ] Visualizar carrinho
- [ ] Atualizar quantidades no carrinho
- [ ] Remover itens do carrinho
- [ ] Adicionar review
- [ ] Exibir reviews
- [ ] Adicionar aos favoritos
- [ ] Listar favoritos

### UX/UI
- [ ] Loading states
- [ ] Error handling
- [ ] Empty states
- [ ] Animações suaves
- [ ] Responsividade
- [ ] Acessibilidade

---

## 📚 Próximos Passos

1. ✅ Explore todos os [endpoints da API](./04-api-products.md)
2. ✅ Veja mais [exemplos de código](./10-code-examples.md)
3. ✅ Aprenda sobre [tratamento de erros](./11-error-handling.md)

---

**Dúvidas?** Consulte o [FAQ](./16-faq.md) ou use o Swagger UI: http://localhost:8080/swagger-ui.html

Bom desenvolvimento! 🚀

