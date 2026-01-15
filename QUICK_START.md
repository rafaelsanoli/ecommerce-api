# 🚀 Guia Rápido de Inicialização

## Para Desenvolvedores Frontend

### 1. Executar a API

```bash
# No diretório do projeto
mvn spring-boot:run
```

Aguarde até ver a mensagem:
```
╔═══════════════════════════════════════════════════════════════╗
║          🚀 E-COMMERCE API ESTÁ RODANDO! 🚀                   ║
╚═══════════════════════════════════════════════════════════════╝
```

### 2. Acessar a Documentação

Abra seu navegador em: **http://localhost:8080/swagger-ui.html**

Você verá todos os endpoints disponíveis e poderá testar diretamente pelo Swagger!

### 3. Autenticação JWT (v1.1.0+)

⚠️ **IMPORTANTE:** A partir da v1.1.0, a API requer autenticação para a maioria dos endpoints.

#### Fazer Login
```bash
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin",
  "email": "admin@ecommerce.com"
}
```

#### Usar o Token
Inclua o token no header de todas as requisições protegidas:
```
Authorization: Bearer SEU_TOKEN_AQUI
```

#### Usuários de Teste
- **Admin**: `admin` / `password123` (pode criar/editar produtos)
- **Customer**: `customer` / `password123` (pode comprar)

📖 [Guia completo de Autenticação](./docs/07-authentication.md)

### 4. Endpoints Principais

**Base URL:** `http://localhost:8080/api/v1`

#### 🔓 Públicos (sem autenticação)

**Listar Produtos**
```bash
GET http://localhost:8080/api/v1/products
```

**Buscar Produto por ID**
```bash
GET http://localhost:8080/api/v1/products/1
```

#### 🔒 Protegidos (requer token)

**Criar Carrinho**
```bash
POST http://localhost:8080/api/v1/cart
Content-Type: application/json
Authorization: Bearer SEU_TOKEN

{
  "userId": "meu-usuario-123"
}
```

**Adicionar Item ao Carrinho**
```bash
POST http://localhost:8080/api/v1/cart/1/items
Content-Type: application/json
Authorization: Bearer SEU_TOKEN

{
  "productId": 1,
  "quantity": 2
}
```

### 5. Dados Disponíveis

A API já vem com dados pré-cadastrados:
- **3 usuários** (admin, customer, joao)
- **15 produtos** em diversas categorias
- **10 categorias** (Eletrônicos, Roupas, Livros, etc)
- **10 avaliações** de exemplo

### 6. Testar com Script Automático

Execute o script de testes incluído:
```bash
./test-api.sh
```

Ele testará todos os principais endpoints incluindo autenticação!

### 7. Exemplo de Integração no Frontend

#### JavaScript (Fetch) com JWT
```javascript
// 1. Fazer login
async function login() {
  const response = await fetch('http://localhost:8080/api/v1/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      username: 'admin',
      password: 'password123'
    })
  });
  
  const data = await response.json();
  localStorage.setItem('token', data.token);
  return data.token;
}

// 2. Listar produtos (público)
fetch('http://localhost:8080/api/v1/products')
  .then(response => response.json())
  .then(data => console.log(data));

// 3. Criar carrinho (protegido)
async function createCart() {
  const token = localStorage.getItem('token');
  
  const response = await fetch('http://localhost:8080/api/v1/cart', {
    method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ userId: 'user-123' })
})
  .then(response => response.json())
  .then(cart => console.log('Carrinho criado:', cart));
```

#### React
```jsx
// Hook para buscar produtos
const [products, setProducts] = useState([]);

useEffect(() => {
  fetch('http://localhost:8080/api/v1/products')
    .then(res => res.json())
    .then(data => setProducts(data.content));
}, []);
```

### 7. Dicas Importantes

✅ **CORS está habilitado** - Você pode fazer requisições de qualquer origem  
✅ **Não precisa de autenticação** - A API é aberta para facilitar os estudos  
✅ **Banco H2** - Os dados são resetados a cada restart (ideal para testes)  
✅ **Paginação automática** - Use `?page=0&size=20` nos endpoints de listagem  

### 8. Problemas Comuns

**Porta 8080 já está em uso?**
```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**Erro ao compilar?**
```bash
mvn clean install -U
```

### 9. Suporte

Se encontrar algum problema:
1. Verifique se o Java 21 está instalado: `java -version`
2. Verifique se o Maven está instalado: `mvn -version`
3. Veja os logs em `target/spring-boot.log`

---

**Bons estudos! 📚💻**

