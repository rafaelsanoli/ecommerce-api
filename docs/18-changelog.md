# 📝 Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/lang/pt-BR/).

---

## [1.1.0] - 2026-01-15

### 🔐 Adicionado - Autenticação JWT

#### Novas Funcionalidades
- **Autenticação JWT**: Sistema completo de autenticação baseado em tokens JWT
- **Registro de Usuários**: Endpoint para criar novos usuários
- **Login**: Endpoint para autenticar usuários e obter token
- **Perfil de Usuário**: Endpoint para obter dados do usuário autenticado
- **Controle de Acesso**: Sistema de roles (CUSTOMER e ADMIN)
- **Spring Security**: Integração completa com Spring Security

#### Novos Endpoints
- `POST /api/v1/auth/register` - Registrar novo usuário
- `POST /api/v1/auth/login` - Fazer login e obter token JWT
- `GET /api/v1/users/me` - Obter perfil do usuário autenticado

#### Segurança
- Endpoints GET de produtos e categorias são públicos
- Endpoints de carrinho, wishlist e reviews requerem autenticação
- Endpoints POST/PUT/DELETE de produtos e categorias requerem role ADMIN
- Senhas criptografadas com BCrypt
- Tokens JWT com expiração de 24 horas

#### Documentação
- Novo guia completo de autenticação JWT: `docs/07-authentication.md`
- Atualização do README principal com informações sobre JWT
- Atualização do FAQ com seção de autenticação
- Swagger UI atualizado com suporte a JWT Bearer token
- Exemplos de código com autenticação

#### Dados de Teste
- 3 usuários pré-cadastrados (admin, customer, joao)
- Senha padrão para todos: `password123`

#### Dependências Adicionadas
- `spring-boot-starter-security` - Framework de segurança
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` (v0.12.3) - Biblioteca JWT

#### Infraestrutura
- Nova entidade de domínio: `User`
- Novos serviços: `AuthService`, `UserService`
- Novas exceções: `UserAlreadyExistsException`, `InvalidCredentialsException`, `UserNotFoundException`
- Novos componentes de segurança: `JwtTokenProvider`, `JwtAuthenticationFilter`, `CustomUserDetailsService`
- Nova configuração: `SecurityConfig`

#### Breaking Changes
⚠️ **IMPORTANTE**: A partir desta versão, a API requer autenticação para a maioria dos endpoints. Endpoints públicos:
- `POST /api/v1/auth/**` - Autenticação
- `GET /api/v1/products/**` - Visualizar produtos
- `GET /api/v1/categories/**` - Visualizar categorias
- `/swagger-ui/**`, `/v3/api-docs/**` - Documentação
- `/h2-console/**` - Console H2

### 📝 Alterado
- Versão da aplicação atualizada de 1.0.0 para 1.1.0
- OpenAPI config atualizado com esquema de segurança JWT
- CORS config mantido para facilitar desenvolvimento frontend
- Documentação completa atualizada

---

## [1.0.0] - 2026-01-08

### 🎉 Lançamento Inicial

#### Funcionalidades
- **Catálogo de Produtos**: CRUD completo com paginação, busca e filtros
- **Categorias**: Hierarquia de categorias com subcategorias
- **Carrinho de Compras**: Sistema completo de carrinho com validação de estoque
- **Sistema de Avaliações**: Reviews com rating e comentários
- **Wishlist**: Lista de desejos por usuário

#### Arquitetura
- Arquitetura Hexagonal (Ports & Adapters)
- Separação clara de camadas (Domain, Application, Infrastructure)
- Clean Code e princípios SOLID
- Código bem documentado e comentado

#### Documentação
- README completo com guias de instalação
- Documentação Swagger/OpenAPI interativa
- Guia completo para desenvolvedores frontend
- FAQ extensivo
- Exemplos de código em múltiplas linguagens

#### Tecnologias
- Java 21 LTS
- Spring Boot 3.2.1
- Spring Data JPA
- H2 Database (em memória)
- Lombok
- Bean Validation
- Springdoc OpenAPI
- JUnit 5 + Mockito

#### Dados Iniciais
- 10 categorias pré-cadastradas
- 15 produtos de exemplo
- 10 avaliações
- 2 carrinhos com itens
- 4 itens na wishlist

#### API Features
- Paginação automática
- Tratamento de erros robusto
- Validações completas
- CORS configurado
- Console H2 habilitado

---

## Tipos de Mudanças

- `Adicionado` - Novas funcionalidades
- `Alterado` - Mudanças em funcionalidades existentes
- `Deprecated` - Funcionalidades que serão removidas
- `Removido` - Funcionalidades removidas
- `Corrigido` - Correções de bugs
- `Segurança` - Correções de vulnerabilidades

---

## Links

- [1.1.0] - Autenticação JWT (atual)
- [1.0.0] - Lançamento inicial

---

**Mantido por:** Rafael Sanoli  
**Última atualização:** 15 de Janeiro de 2024

