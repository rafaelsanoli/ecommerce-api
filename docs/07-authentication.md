# 🔐 Autenticação JWT

## 📋 Visão Geral

A partir da versão **1.1.0**, a API implementa autenticação JWT (JSON Web Token) para proteger endpoints e gerenciar sessões de usuário de forma segura e stateless.

---

## 🎯 O que é JWT?

JWT (JSON Web Token) é um padrão aberto ([RFC 7519](https://tools.ietf.org/html/rfc7519)) que define uma maneira compacta e autocontida de transmitir informações com segurança entre partes como um objeto JSON.

### Estrutura do Token

Um JWT consiste em três partes separadas por pontos (`.`):

```
header.payload.signature
```

Exemplo:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

---

## 🚀 Como Usar

### 1. Registrar um Novo Usuário

**Endpoint:** `POST /api/v1/auth/register`

**Request Body:**
```json
{
  "username": "joaosilva",
  "email": "joao@example.com",
  "password": "senha123",
  "fullName": "João Silva"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "username": "joaosilva",
  "email": "joao@example.com",
  "fullName": "João Silva",
  "role": "CUSTOMER",
  "active": true,
  "createdAt": "2024-01-15T10:30:00"
}
```

### 2. Fazer Login

**Endpoint:** `POST /api/v1/auth/login`

**Request Body:**
```json
{
  "username": "joaosilva",
  "password": "senha123"
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "joaosilva",
  "email": "joao@example.com"
}
```

### 3. Usar o Token

Após obter o token, inclua-o no header `Authorization` de todas as requisições:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 4. Obter Perfil do Usuário

**Endpoint:** `GET /api/v1/users/me`

**Headers:**
```
Authorization: Bearer {seu-token-aqui}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "username": "joaosilva",
  "email": "joao@example.com",
  "fullName": "João Silva",
  "role": "CUSTOMER",
  "active": true,
  "createdAt": "2024-01-15T10:30:00"
}
```

---

## 🔑 Usuários de Teste

A aplicação vem com usuários pré-cadastrados para testes:

### Admin
```json
{
  "username": "admin",
  "password": "password123",
  "email": "admin@ecommerce.com",
  "role": "ADMIN"
}
```

### Customer
```json
{
  "username": "customer",
  "password": "password123",
  "email": "customer@example.com",
  "role": "CUSTOMER"
}
```

### João (Customer)
```json
{
  "username": "joao",
  "password": "password123",
  "email": "joao@example.com",
  "role": "CUSTOMER"
}
```

---

## 🛡️ Roles e Permissões

A API possui dois níveis de acesso:

### CUSTOMER (Padrão)
- ✅ Ver produtos e categorias
- ✅ Adicionar produtos ao carrinho
- ✅ Gerenciar wishlist
- ✅ Criar reviews
- ✅ Ver próprio perfil

### ADMIN
- ✅ Todas as permissões de CUSTOMER
- ✅ Criar, editar e excluir produtos
- ✅ Criar, editar e excluir categorias
- ✅ Gerenciar usuários

---

## 🔒 Endpoints Protegidos

### Públicos (Sem Autenticação)
- `POST /api/v1/auth/register` - Registrar usuário
- `POST /api/v1/auth/login` - Fazer login
- `GET /api/v1/products/**` - Ver produtos
- `GET /api/v1/categories/**` - Ver categorias
- Swagger UI e documentação

### Requer Autenticação (CUSTOMER ou ADMIN)
- `GET /api/v1/users/me` - Ver próprio perfil
- `POST /api/v1/cart/**` - Gerenciar carrinho
- `POST /api/v1/wishlist/**` - Gerenciar wishlist
- `POST /api/v1/products/{id}/reviews` - Criar review

### Requer ADMIN
- `POST /api/v1/products` - Criar produto
- `PUT /api/v1/products/{id}` - Atualizar produto
- `DELETE /api/v1/products/{id}` - Deletar produto
- `POST /api/v1/categories` - Criar categoria
- `PUT /api/v1/categories/{id}` - Atualizar categoria
- `DELETE /api/v1/categories/{id}` - Deletar categoria

---

## ⚙️ Configuração

### Propriedades JWT (application.yml)

```yaml
jwt:
  secret: sua-chave-secreta-aqui-minimo-256-bits
  expiration: 86400000  # 24 horas em milissegundos
```

⚠️ **IMPORTANTE:** Em produção, use uma chave secreta forte e armazene-a em variáveis de ambiente:

```yaml
jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION:86400000}
```

### Tempo de Expiração

| Valor | Tempo |
|-------|-------|
| 3600000 | 1 hora |
| 86400000 | 24 horas (padrão) |
| 604800000 | 7 dias |

---

## 💻 Exemplos de Código

### JavaScript/Fetch

```javascript
// Login
const login = async (username, password) => {
  const response = await fetch('http://localhost:8080/api/v1/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });
  
  const data = await response.json();
  
  // Salvar token
  localStorage.setItem('token', data.token);
  
  return data;
};

// Usar token em requisições
const getProfile = async () => {
  const token = localStorage.getItem('token');
  
  const response = await fetch('http://localhost:8080/api/v1/users/me', {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  
  return response.json();
};
```

### Axios

```javascript
import axios from 'axios';

// Configurar interceptor
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Login
const login = async (username, password) => {
  const { data } = await axios.post('/api/v1/auth/login', {
    username,
    password
  });
  
  localStorage.setItem('token', data.token);
  return data;
};

// Buscar perfil
const getProfile = () => axios.get('/api/v1/users/me');
```

### React Hook

```javascript
import { useState, useEffect } from 'react';

const useAuth = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      fetch('http://localhost:8080/api/v1/users/me', {
        headers: { 'Authorization': `Bearer ${token}` }
      })
        .then(res => res.json())
        .then(setUser)
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const login = async (username, password) => {
    const response = await fetch('http://localhost:8080/api/v1/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });
    
    const data = await response.json();
    localStorage.setItem('token', data.token);
    setUser(data);
    return data;
  };

  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
  };

  return { user, login, logout, loading };
};

export default useAuth;
```

---

## 🚨 Tratamento de Erros

### Erros Comuns

#### 401 Unauthorized
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token JWT inválido ou expirado",
  "path": "/api/v1/users/me"
}
```

**Solução:** Faça login novamente para obter um novo token.

#### 403 Forbidden
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Acesso negado",
  "path": "/api/v1/products"
}
```

**Solução:** Você não tem permissão para acessar este recurso. Verifique seu role.

#### 400 Bad Request - Credenciais Inválidas
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Business Error",
  "message": "Credenciais inválidas",
  "path": "/api/v1/auth/login"
}
```

#### 400 Bad Request - Usuário já existe
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Business Error",
  "message": "Usuário com username 'joaosilva' já existe",
  "path": "/api/v1/auth/register"
}
```

---

## 🔍 Testando com Swagger

1. Acesse: http://localhost:8080/swagger-ui.html
2. Faça login usando o endpoint `/api/v1/auth/login`
3. Copie o token da resposta
4. Clique no botão **"Authorize"** 🔓 no topo da página
5. Cole o token (sem o prefixo "Bearer")
6. Clique em **"Authorize"**
7. Agora você pode testar os endpoints protegidos!

---

## 🧪 Testando com cURL

### Registrar
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "teste",
    "email": "teste@example.com",
    "password": "senha123",
    "fullName": "Usuário Teste"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

### Acessar endpoint protegido
```bash
curl -X GET http://localhost:8080/api/v1/users/me \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

## 📚 Referências

- [JWT.io - Introdução ao JWT](https://jwt.io/introduction)
- [RFC 7519 - JSON Web Token](https://tools.ietf.org/html/rfc7519)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [JJWT - Java JWT Library](https://github.com/jwtk/jjwt)

---

## 🆘 Problemas Comuns

### Token não está sendo aceito

1. Verifique se está incluindo o prefixo "Bearer " no header
2. Confirme que o token não expirou (válido por 24h)
3. Certifique-se de que está usando o header correto: `Authorization`

### Erro 403 ao tentar criar produto

Apenas usuários com role ADMIN podem criar/editar/deletar produtos. Use o usuário `admin` para testar.

### Token expira muito rápido

Altere a propriedade `jwt.expiration` no `application.yml` para um valor maior (em milissegundos).

---

[← Voltar para Instalação](01-installation.md) | [Início](README.md) | [Guia Frontend →](09-frontend-guide.md)

