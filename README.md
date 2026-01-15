<div align="center">

# 🛍️ E-commerce API

### API REST Completa para E-commerce com Arquitetura Hexagonal

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

<p align="center">
  <a href="#-sobre">Sobre</a> •
  <a href="#-funcionalidades">Funcionalidades</a> •
  <a href="#-tecnologias">Tecnologias</a> •
  <a href="#-início-rápido">Início Rápido</a> •
  <a href="#-documentação">Documentação</a> •
  <a href="#-arquitetura">Arquitetura</a>
</p>

![E-commerce API](https://raw.githubusercontent.com/rafaelsanoli/ecommerce-api/main/.github/banner.png)

</div>

---

## 📋 Sobre

**E-commerce API** é uma API REST completa e profissional desenvolvida com **Java 21** e **Spring Boot 3.x**, seguindo os princípios da **Arquitetura Hexagonal** (Ports & Adapters) e **Clean Code**.

Este projeto foi criado especialmente para **estudantes de desenvolvimento frontend** praticarem a construção de aplicações web completas, fornecendo todos os recursos necessários para um e-commerce funcional.

### ✨ Diferenciais

- 🏗️ **Arquitetura Hexagonal** - Código desacoplado e altamente testável
- 📖 **Documentação Completa** - Swagger/OpenAPI + Guias detalhados
- 🎯 **Production Ready** - Seguindo melhores práticas da indústria
- 🚀 **Plug & Play** - Dados pré-carregados, pronto para uso
- 🌐 **CORS Configurado** - Funciona direto do seu frontend
- 📚 **Código Educacional** - Bem comentado e fácil de entender

---

## 🎯 Funcionalidades

<table>
<tr>
<td width="50%">

### Core Features
- ✅ **Catálogo de Produtos**
  - CRUD completo
  - Paginação e ordenação
  - Busca por nome
  - Filtro por categoria
  - Controle de estoque

- ✅ **Categorias**
  - Hierarquia (categorias e subcategorias)
  - CRUD completo
  - Relacionamento com produtos

- ✅ **Carrinho de Compras**
  - Adicionar/remover itens
  - Atualizar quantidades
  - Cálculo automático de totais
  - Validação de estoque

</td>
<td width="50%">

### Advanced Features
- ✅ **Sistema de Avaliações**
  - Reviews com rating 1-5 estrelas
  - Comentários opcionais
  - Cálculo de média
  - Contagem por produto

- ✅ **Lista de Desejos (Wishlist)**
  - Adicionar/remover favoritos
  - Listar por usuário
  - Verificar existência

- ✅ **API Features**
  - Paginação automática
  - Tratamento de erros robusto
  - Validações completas
  - Documentação Swagger

</td>
</tr>
</table>

---

## 🛠️ Tecnologias

### Core
- ☕ **Java 21** (LTS) - Linguagem de programação
- 🍃 **Spring Boot 3.2.1** - Framework principal
- 💾 **Spring Data JPA** - Camada de persistência
- 🗄️ **H2 Database** - Banco de dados em memória

### Libraries & Tools
- 📝 **Lombok** - Redução de boilerplate
- ✅ **Bean Validation** - Validações
- 📖 **Springdoc OpenAPI** - Documentação automática
- 🧪 **JUnit 5 + Mockito** - Testes
- 📦 **Maven** - Gerenciamento de dependências

---

## 🚀 Início Rápido

### Pré-requisitos

```bash
# Verificar Java 21
java -version

# Verificar Maven
mvn -version
```

### Instalação

```bash
# 1. Clone o repositório
git clone https://github.com/rafaelsanoli/ecommerce-api.git
cd ecommerce-api

# 2. Compile o projeto
mvn clean install

# 3. Execute a aplicação
mvn spring-boot:run
```

### Primeira Requisição

```bash
# Fazer login (obtém token JWT)
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'

# Listar produtos (público)
curl http://localhost:8080/api/v1/products

# Acessar perfil (protegido - requer token)
curl http://localhost:8080/api/v1/users/me \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Acessar Documentação

Abra no navegador:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

---

## 📚 Documentação

### 📖 Documentação Completa
Acesse a [**documentação completa**](./docs/README.md) com guias detalhados:

| Documento | Descrição |
|-----------|-----------|
| [🚀 Instalação](./docs/01-installation.md) | Guia completo de instalação |
| [🔐 Autenticação JWT](./docs/07-authentication.md) | Como usar autenticação JWT |
| [💻 Frontend Guide](./docs/09-frontend-guide.md) | Integração com React, Vue, Angular |
| [🏗️ Arquitetura](./docs/03-architecture.md) | Entenda a arquitetura hexagonal |
| [📡 API Reference](./docs/04-api-products.md) | Documentação detalhada dos endpoints |
| [❓ FAQ](./docs/16-faq.md) | Perguntas frequentes |

### 🎯 Guias Rápidos

<details>
<summary><b>🔥 Para Desenvolvedores Frontend</b></summary>

#### React Example
```jsx
import { useEffect, useState } from 'react';

function ProductList() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/v1/products')
      .then(res => res.json())
      .then(data => setProducts(data.content));
  }, []);

  return (
    <div className="products">
      {products.map(product => (
        <div key={product.id}>
          <h3>{product.name}</h3>
          <p>R$ {product.price}</p>
          <span>⭐ {product.averageRating}</span>
        </div>
      ))}
    </div>
  );
}
```

[Ver guia completo](./docs/09-frontend-guide.md)

</details>

<details>
<summary><b>📡 Endpoints Principais</b></summary>

### Base URL
```
http://localhost:8080/api/v1
```

### Produtos
- `GET /products` - Listar produtos (paginado)
- `GET /products/{id}` - Buscar produto
- `POST /products` - Criar produto
- `PUT /products/{id}` - Atualizar produto
- `DELETE /products/{id}` - Deletar produto

### Carrinho
- `POST /cart` - Criar carrinho
- `GET /cart/{id}` - Buscar carrinho
- `POST /cart/{id}/items` - Adicionar item
- `PUT /cart/items/{itemId}` - Atualizar quantidade
- `DELETE /cart/items/{itemId}` - Remover item

[Ver todos os endpoints](./docs/04-api-products.md)

</details>

<details>
<summary><b>🗄️ Dados Pré-carregados</b></summary>

A API já vem com dados de exemplo:
- ✅ **10 Categorias** (Eletrônicos, Roupas, Livros, etc)
- ✅ **15 Produtos** em diversas categorias
- ✅ **10 Avaliações** de exemplo
- ✅ **2 Carrinhos** com itens
- ✅ **4 Itens** na wishlist

Pronto para usar imediatamente! 🎉

</details>

---

## 🏗️ Arquitetura

Este projeto segue a **Arquitetura Hexagonal** (Ports & Adapters), garantindo separação de responsabilidades e alta testabilidade.

```
┌─────────────────────────────────────────────────────┐
│                  ADAPTERS IN (REST)                 │
│            Controllers + DTOs + Validation          │
└───────────────────────┬─────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────┐
│               APPLICATION LAYER                      │
│          Services (Use Case Implementation)          │
└───────────────────────┬─────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────┐
│                 DOMAIN LAYER                         │
│   Entities + Business Logic + Ports (Interfaces)    │
└───────────────────────┬─────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────┐
│           ADAPTERS OUT (Persistence)                 │
│         JPA Repositories + Database                  │
└─────────────────────────────────────────────────────┘
```

### Estrutura de Pastas

```
src/main/java/com/ecommerce/api/
├── domain/                    # Camada de Domínio (Núcleo)
│   ├── entities/             # Entidades de negócio
│   ├── exceptions/           # Exceções de domínio
│   └── ports/
│       ├── in/              # Portas de entrada (Use Cases)
│       └── out/             # Portas de saída (Repositories)
├── application/              # Camada de Aplicação
│   └── services/            # Implementação dos Use Cases
└── infrastructure/           # Camada de Infraestrutura
    ├── adapters/
    │   ├── in/rest/        # Controllers REST
    │   └── out/persistence/ # Implementação JPA
    └── config/              # Configurações
```

[Saiba mais sobre a arquitetura](./docs/03-architecture.md)

---

## 📊 Status do Projeto

<div align="center">

| Métrica | Status |
|---------|--------|
| **Arquivos Java** | 68 |
| **Endpoints REST** | 32 |
| **Linhas de Código** | 3.333 |
| **Documentação** | ✅ Completa |
| **Status** | ✅ Production Ready |

</div>

---

## 🤝 Contribuindo

Contribuições são muito bem-vindas! Este projeto é open source e feito para a comunidade.

### Como Contribuir

1. **Fork** o projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'feat: adiciona nova funcionalidade'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. Abra um **Pull Request**

### Padrão de Commits

Seguimos o [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: nova funcionalidade
fix: correção de bug
docs: mudanças na documentação
style: formatação de código
refactor: refatoração
test: adição de testes
chore: tarefas de manutenção
```

[Ver guia de contribuição completo](./docs/13-contributing.md)

---

## 📝 Roadmap

### Versão Atual (v1.0.0) ✅
- [x] CRUD de Produtos
- [x] Sistema de Categorias
- [x] Carrinho de Compras
- [x] Sistema de Reviews
- [x] Lista de Favoritos
- [x] Documentação Completa

### Próximas Versões 🚀
- [ ] **v1.1.0** - Autenticação JWT
- [ ] **v1.2.0** - Sistema de Pedidos
- [ ] **v1.3.0** - Upload de Imagens
- [ ] **v1.4.0** - Sistema de Pagamento
- [ ] **v2.0.0** - Migração para PostgreSQL
- [ ] **v2.1.0** - Cache com Redis
- [ ] **v2.2.0** - Deploy Cloud (AWS/Azure)

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

```
MIT License - Copyright (c) 2026 Rafael Sanoli
```

---

## 👥 Autor

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/rafaelsanoli">
        <img src="https://github.com/rafaelsanoli.png" width="100px;" alt="Rafael Sanoli"/>
        <br />
        <sub><b>Rafael Sanoli</b></sub>
      </a>
      <br />
      <a href="https://github.com/rafaelsanoli" title="GitHub">
        <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
      </a>
      <a href="https://linkedin.com/in/rafaelsanoli" title="LinkedIn">
        <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
      </a>
      <a href="https://rafaelsanoli.vercel.app" title="Portfolio">
        <img src="https://img.shields.io/badge/Portfolio-000000?style=for-the-badge&logo=vercel&logoColor=white" alt="Portfolio"/>
      </a>
    </td>
  </tr>
</table>

---

## 🌟 Agradecimentos

Este projeto foi desenvolvido com ❤️ para ajudar estudantes de desenvolvimento a aprenderem e praticarem.

Agradecimentos especiais:
- Comunidade Spring Boot
- Comunidade Java
- Todos os contribuidores
- Você, por usar este projeto! 🙏

---

## 📞 Suporte e Contato

### 💬 Dúvidas?
- 📖 Consulte a [documentação completa](./docs/README.md)
- ❓ Veja as [perguntas frequentes](./docs/16-faq.md)
- 🐛 Reporte bugs na [página de issues](https://github.com/rafaelsanoli/ecommerce-api/issues)

### 🔗 Links Úteis
- [Swagger UI](http://localhost:8080/swagger-ui.html) - Documentação interativa
- [H2 Console](http://localhost:8080/h2-console) - Console do banco de dados
- [Postman Collection](./postman_collection.json) - Coleção de requisições

---

<div align="center">

### ⭐ Star o projeto se ele te ajudou!

[![GitHub stars](https://img.shields.io/github/stars/rafaelsanoli/ecommerce-api?style=social)](https://github.com/rafaelsanoli/ecommerce-api/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/rafaelsanoli/ecommerce-api?style=social)](https://github.com/rafaelsanoli/ecommerce-api/network/members)

---

**Feito com ❤️ e ☕ por [Rafael Sanoli](https://github.com/rafaelsanoli)**

[⬆ Voltar ao topo](#-e-commerce-api)

</div>

