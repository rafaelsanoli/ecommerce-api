# 📡 Exemplos de Requisições - E-commerce API

## 🛍️ PRODUTOS

### 1. Listar Todos os Produtos (com paginação)
```bash
GET http://localhost:8080/api/v1/products?page=0&size=10&sort=name,asc
```

**Response 200 OK:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Notebook Dell Inspiron",
      "description": "Notebook Dell i5 11ª geração, 8GB RAM, 256GB SSD",
      "price": 3499.90,
      "stock": 15,
      "imageUrl": "https://picsum.photos/seed/notebook1/400/300",
      "category": {
        "id": 2,
        "name": "Computadores",
        "description": "Notebooks, desktops e acessórios",
        "parentCategoryId": 1
      },
      "averageRating": 4.5,
      "reviewCount": 2,
      "createdAt": "2026-01-11T10:00:00",
      "updatedAt": "2026-01-11T10:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 2,
  "totalElements": 15
}
```

### 2. Buscar Produto por ID
```bash
GET http://localhost:8080/api/v1/products/1
```

### 3. Criar Novo Produto
```bash
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "Notebook Gamer Acer",
  "description": "Notebook Gamer com RTX 4060, 16GB RAM, 512GB SSD",
  "price": 6999.90,
  "stock": 8,
  "imageUrl": "https://picsum.photos/seed/acer/400/300",
  "categoryId": 2
}
```

### 4. Atualizar Produto
```bash
PUT http://localhost:8080/api/v1/products/1
Content-Type: application/json

{
  "name": "Notebook Dell Inspiron (Atualizado)",
  "description": "Notebook Dell i5 11ª geração, 8GB RAM, 256GB SSD - PROMOÇÃO",
  "price": 2999.90,
  "stock": 20,
  "imageUrl": "https://picsum.photos/seed/notebook1/400/300",
  "categoryId": 2
}
```

### 5. Deletar Produto
```bash
DELETE http://localhost:8080/api/v1/products/1
```

### 6. Buscar Produtos por Categoria
```bash
GET http://localhost:8080/api/v1/products/category/2
```

### 7. Buscar Produtos por Nome
```bash
GET http://localhost:8080/api/v1/products/search?name=notebook
```

---

## 📂 CATEGORIAS

### 1. Listar Todas as Categorias
```bash
GET http://localhost:8080/api/v1/categories
```

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "name": "Eletrônicos",
    "description": "Produtos eletrônicos em geral",
    "parentCategoryId": null
  },
  {
    "id": 2,
    "name": "Computadores",
    "description": "Notebooks, desktops e acessórios",
    "parentCategoryId": 1
  }
]
```

### 2. Criar Nova Categoria
```bash
POST http://localhost:8080/api/v1/categories
Content-Type: application/json

{
  "name": "Games",
  "description": "Jogos e consoles",
  "parentCategoryId": 1
}
```

### 3. Listar Subcategorias
```bash
GET http://localhost:8080/api/v1/categories/1/subcategories
```

---

## 🛒 CARRINHO

### 1. Criar Novo Carrinho
```bash
POST http://localhost:8080/api/v1/cart
Content-Type: application/json

{
  "userId": "user-123"
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "userId": "user-123",
  "items": [],
  "totalItems": 0,
  "totalPrice": 0.00,
  "createdAt": "2026-01-11T10:00:00",
  "updatedAt": "2026-01-11T10:00:00"
}
```

### 2. Buscar Carrinho por ID
```bash
GET http://localhost:8080/api/v1/cart/1
```

### 3. Buscar Carrinho por User ID
```bash
GET http://localhost:8080/api/v1/cart/user/user-123
```

### 4. Adicionar Item ao Carrinho
```bash
POST http://localhost:8080/api/v1/cart/1/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

**Response 200 OK:**
```json
{
  "id": 1,
  "userId": "user-123",
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "Notebook Dell Inspiron",
      "quantity": 2,
      "priceAtAddTime": 3499.90,
      "subtotal": 6999.80
    }
  ],
  "totalItems": 2,
  "totalPrice": 6999.80,
  "createdAt": "2026-01-11T10:00:00",
  "updatedAt": "2026-01-11T10:05:00"
}
```

### 5. Atualizar Quantidade do Item
```bash
PUT http://localhost:8080/api/v1/cart/items/1
Content-Type: application/json

{
  "quantity": 5
}
```

### 6. Remover Item do Carrinho
```bash
DELETE http://localhost:8080/api/v1/cart/items/1
```

### 7. Limpar Carrinho
```bash
DELETE http://localhost:8080/api/v1/cart/1/clear
```

### 8. Deletar Carrinho
```bash
DELETE http://localhost:8080/api/v1/cart/1
```

---

## ⭐ AVALIAÇÕES (REVIEWS)

### 1. Criar Avaliação
```bash
POST http://localhost:8080/api/v1/products/1/reviews
Content-Type: application/json

{
  "userName": "João Silva",
  "rating": 5,
  "comment": "Excelente produto! Superou minhas expectativas. Entrega rápida e produto bem embalado."
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "productId": 1,
  "userName": "João Silva",
  "rating": 5,
  "comment": "Excelente produto! Superou minhas expectativas.",
  "createdAt": "2026-01-11T10:00:00"
}
```

### 2. Listar Avaliações de um Produto
```bash
GET http://localhost:8080/api/v1/products/1/reviews
```

### 3. Obter Média de Avaliações
```bash
GET http://localhost:8080/api/v1/products/1/rating
```

**Response 200 OK:**
```json
{
  "productId": 1,
  "averageRating": 4.5,
  "reviewCount": 10
}
```

### 4. Atualizar Avaliação
```bash
PUT http://localhost:8080/api/v1/reviews/1
Content-Type: application/json

{
  "userName": "João Silva",
  "rating": 4,
  "comment": "Bom produto, mas poderia ser melhor."
}
```

### 5. Deletar Avaliação
```bash
DELETE http://localhost:8080/api/v1/reviews/1
```

---

## ❤️ LISTA DE DESEJOS (WISHLIST)

### 1. Adicionar à Wishlist
```bash
POST http://localhost:8080/api/v1/wishlist
Content-Type: application/json

{
  "userId": "user-123",
  "productId": 1
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "userId": "user-123",
  "productId": 1,
  "product": {
    "id": 1,
    "name": "Notebook Dell Inspiron",
    "price": 3499.90,
    "imageUrl": "https://picsum.photos/seed/notebook1/400/300"
  },
  "addedAt": "2026-01-11T10:00:00"
}
```

### 2. Listar Wishlist do Usuário
```bash
GET http://localhost:8080/api/v1/wishlist/user/user-123
```

### 3. Remover da Wishlist
```bash
DELETE http://localhost:8080/api/v1/wishlist/1
```

### 4. Verificar se Produto está na Wishlist
```bash
GET http://localhost:8080/api/v1/wishlist/check?userId=user-123&productId=1
```

**Response 200 OK:**
```json
true
```

---

## ❌ TRATAMENTO DE ERROS

### Produto Não Encontrado
```bash
GET http://localhost:8080/api/v1/products/999
```

**Response 400 Bad Request:**
```json
{
  "timestamp": "2026-01-11T10:00:00",
  "status": 400,
  "error": "Business Error",
  "message": "Produto não encontrado com ID: 999",
  "path": "/api/v1/products/999"
}
```

### Validação de Campos
```bash
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "AB",
  "price": -100
}
```

**Response 400 Bad Request:**
```json
{
  "timestamp": "2026-01-11T10:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação dos campos",
  "path": "/api/v1/products",
  "errors": {
    "name": "Nome deve ter entre 3 e 100 caracteres",
    "price": "Preço deve ser maior que zero",
    "stock": "Estoque é obrigatório",
    "categoryId": "ID da categoria é obrigatório"
  }
}
```

### Estoque Insuficiente
```bash
POST http://localhost:8080/api/v1/cart/1/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 1000
}
```

**Response 400 Bad Request:**
```json
{
  "timestamp": "2026-01-11T10:00:00",
  "status": 400,
  "error": "Business Error",
  "message": "Estoque insuficiente para o produto 'Notebook Dell Inspiron'. Solicitado: 1000, Disponível: 15",
  "path": "/api/v1/cart/1/items"
}
```

---

## 🧪 TESTANDO COM CURL

### Listar Produtos
```bash
curl -X GET "http://localhost:8080/api/v1/products" \
     -H "Content-Type: application/json"
```

### Criar Produto
```bash
curl -X POST "http://localhost:8080/api/v1/products" \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Mouse Gamer",
       "description": "Mouse gamer RGB 16000 DPI",
       "price": 299.90,
       "stock": 50,
       "imageUrl": "https://picsum.photos/seed/mouse/400/300",
       "categoryId": 2
     }'
```

### Adicionar ao Carrinho
```bash
curl -X POST "http://localhost:8080/api/v1/cart/1/items" \
     -H "Content-Type: application/json" \
     -d '{
       "productId": 1,
       "quantity": 2
     }'
```

---

## 🎯 DICAS

1. **Use o Swagger UI** para testar interativamente: `http://localhost:8080/swagger-ui.html`
2. **Todos os endpoints suportam JSON** - sempre use `Content-Type: application/json`
3. **A paginação é automática** - use parâmetros `page`, `size` e `sort`
4. **CORS está habilitado** - você pode fazer requisições de qualquer origem
5. **Dados são resetados** a cada restart da aplicação (H2 in-memory)

---

**Documentação completa disponível em:** http://localhost:8080/swagger-ui.html

