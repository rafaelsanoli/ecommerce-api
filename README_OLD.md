# 🛍️ E-commerce API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)
![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-purple)

API REST completa de E-commerce desenvolvida com **Java 21**, **Spring Boot 3.x** e **Arquitetura Hexagonal** (Ports & Adapters), seguindo princípios de **Clean Code** e boas práticas de engenharia de software.

## 📋 Sobre o Projeto

Esta API foi criada especialmente para **estudantes de frontend** praticarem suas habilidades desenvolvendo aplicações web completas. A API oferece todos os recursos necessários para criar um e-commerce funcional.

## ✨ Funcionalidades

- ✅ **Catálogo de Produtos** - CRUD completo com paginação e busca
- ✅ **Categorias e Subcategorias** - Hierarquia de categorias
- ✅ **Carrinho de Compras** - Gerenciamento completo do carrinho
- ✅ **Sistema de Avaliações** - Reviews com rating de 1-5 estrelas
- ✅ **Lista de Desejos (Wishlist)** - Favoritos do usuário
- ✅ **Documentação Swagger** - API totalmente documentada
- ✅ **CORS Habilitado** - Pronto para integração frontend
- ✅ **Dados Pré-carregados** - 15 produtos de exemplo

## 🏗️ Arquitetura

O projeto segue a **Arquitetura Hexagonal** (Ports & Adapters):

```
src/main/java/com/ecommerce/api/
├── domain/                          # Camada de Domínio (Núcleo)
│   ├── entities/                    # Entidades de negócio
│   ├── exceptions/                  # Exceções de negócio
│   └── ports/
│       ├── in/                      # Portas de entrada (Use Cases)
│       └── out/                     # Portas de saída (Repositories)
├── application/                     # Camada de Aplicação
│   └── services/                    # Implementação dos Use Cases
└── infrastructure/                  # Camada de Infraestrutura
    ├── adapters/
    │   ├── in/rest/                # Adaptadores REST (Controllers, DTOs)
    │   └── out/persistence/        # Adaptadores de Persistência (JPA)
    └── config/                      # Configurações (CORS, Swagger, etc)
```

## 🚀 Tecnologias Utilizadas

- **Java 21** - Última versão LTS
- **Spring Boot 3.2.1** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória
- **Spring Validation** - Validações
- **Lombok** - Redução de boilerplate
- **Springdoc OpenAPI** - Documentação Swagger
- **Maven** - Gerenciamento de dependências

## 📦 Como Executar

### Pré-requisitos

- Java 21 ou superior
- Maven 3.8+

### Passos

1. **Clone o repositório**
```bash
git clone <seu-repositorio>
cd projetoapi
```

2. **Compile o projeto**
```bash
mvn clean install
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

4. **Acesse a aplicação**
- API: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

## 📚 Documentação da API

### Base URL
```
http://localhost:8080/api/v1
```

### Principais Endpoints

#### 🛍️ Produtos
- `GET /products` - Listar produtos (com paginação)
- `GET /products/{id}` - Buscar produto por ID
- `POST /products` - Criar novo produto
- `PUT /products/{id}` - Atualizar produto
- `DELETE /products/{id}` - Deletar produto
- `GET /products/category/{categoryId}` - Produtos por categoria
- `GET /products/search?name={name}` - Buscar por nome

#### 📂 Categorias
- `GET /categories` - Listar todas categorias
- `GET /categories/{id}` - Buscar categoria por ID
- `POST /categories` - Criar nova categoria
- `PUT /categories/{id}` - Atualizar categoria
- `DELETE /categories/{id}` - Deletar categoria
- `GET /categories/{id}/subcategories` - Listar subcategorias

#### 🛒 Carrinho
- `POST /cart` - Criar novo carrinho
- `GET /cart/{cartId}` - Buscar carrinho
- `GET /cart/user/{userId}` - Carrinho por usuário
- `POST /cart/{cartId}/items` - Adicionar item
- `PUT /cart/items/{itemId}` - Atualizar quantidade
- `DELETE /cart/items/{itemId}` - Remover item
- `DELETE /cart/{cartId}` - Deletar carrinho

#### ⭐ Reviews
- `POST /products/{productId}/reviews` - Criar avaliação
- `GET /products/{productId}/reviews` - Listar avaliações
- `GET /products/{productId}/rating` - Média de avaliações
- `PUT /reviews/{reviewId}` - Atualizar avaliação
- `DELETE /reviews/{reviewId}` - Deletar avaliação

#### ❤️ Wishlist
- `POST /wishlist` - Adicionar à wishlist
- `GET /wishlist/user/{userId}` - Wishlist do usuário
- `DELETE /wishlist/{wishlistId}` - Remover da wishlist
- `GET /wishlist/check?userId={userId}&productId={productId}` - Verificar se está na wishlist

### Exemplos de Requisições

#### Criar Produto
```json
POST /api/v1/products
Content-Type: application/json

{
  "name": "Notebook Gamer",
  "description": "Notebook com RTX 4060",
  "price": 5999.90,
  "stock": 10,
  "imageUrl": "https://example.com/image.jpg",
  "categoryId": 2
}
```

#### Adicionar ao Carrinho
```json
POST /api/v1/cart/1/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

#### Criar Avaliação
```json
POST /api/v1/products/1/reviews
Content-Type: application/json

{
  "userName": "João Silva",
  "rating": 5,
  "comment": "Produto excelente!"
}
```

## 🗄️ Banco de Dados

### H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ecommerce`
- Username: `sa`
- Password: *(vazio)*

### Dados Pré-carregados
A aplicação já vem com:
- 10 categorias (com subcategorias)
- 15 produtos de exemplo
- 10 avaliações
- 2 carrinhos com itens
- 4 itens na wishlist

## 🔧 Configuração

Todas as configurações podem ser ajustadas em `src/main/resources/application.yml`:

```yaml
server:
  port: 8080  # Porta da aplicação

spring:
  datasource:
    url: jdbc:h2:mem:ecommerce  # URL do banco
  
  h2:
    console:
      enabled: true  # Habilitar console H2
```

## 🧪 Testes

Execute os testes com:
```bash
mvn test
```

## 📖 Para Desenvolvedores Frontend

### Headers Necessários
```
Content-Type: application/json
```

### Paginação
Todos os endpoints de listagem suportam paginação:
```
GET /api/v1/products?page=0&size=20&sort=name,asc
```

### Tratamento de Erros
Todas as respostas de erro seguem o padrão:
```json
{
  "timestamp": "2026-01-11T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Produto não encontrado com ID: 999",
  "path": "/api/v1/products/999"
}
```

### Validações
- Nome do produto: 3-100 caracteres
- Preço: maior que 0
- Estoque: não negativo
- Rating: 1-5 estrelas
- Quantidade no carrinho: maior que 0

## 📝 Boas Práticas Implementadas

- ✅ Arquitetura Hexagonal (Ports & Adapters)
- ✅ Clean Code e SOLID
- ✅ Separação de responsabilidades
- ✅ DTOs para entrada e saída
- ✅ Tratamento centralizado de exceções
- ✅ Validações com Bean Validation
- ✅ Logs estruturados
- ✅ Documentação completa com Swagger
- ✅ Código bem comentado

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abrir um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autor

Desenvolvido com ❤️ para a comunidade de desenvolvedores iniciantes.

---

## 🎯 Próximos Passos

Sugestões para expandir o projeto:

- [ ] Adicionar autenticação JWT
- [ ] Implementar sistema de pedidos
- [ ] Adicionar upload de imagens
- [ ] Criar sistema de pagamento
- [ ] Adicionar busca avançada com filtros
- [ ] Implementar cache com Redis
- [ ] Adicionar testes de integração
- [ ] Deploy na nuvem (AWS/Azure/Heroku)

---

**Divirta-se programando! 🚀**

