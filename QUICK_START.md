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

### 3. Endpoints Principais

**Base URL:** `http://localhost:8080/api/v1`

#### Listar Produtos
```bash
GET http://localhost:8080/api/v1/products
```

#### Buscar Produto por ID
```bash
GET http://localhost:8080/api/v1/products/1
```

#### Criar Carrinho
```bash
POST http://localhost:8080/api/v1/cart
Content-Type: application/json

{
  "userId": "meu-usuario-123"
}
```

#### Adicionar Item ao Carrinho
```bash
POST http://localhost:8080/api/v1/cart/1/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

### 4. Dados Disponíveis

A API já vem com **15 produtos pré-cadastrados** em diversas categorias:
- Eletrônicos (Notebooks, Smartphones)
- Roupas (Masculino, Feminino)
- Livros (Programação)
- Casa e Decoração

### 5. Testar no Postman/Insomnia

Importe esta collection básica:

```json
{
  "name": "E-commerce API",
  "requests": [
    {
      "name": "Listar Produtos",
      "method": "GET",
      "url": "http://localhost:8080/api/v1/products"
    },
    {
      "name": "Buscar Produto",
      "method": "GET", 
      "url": "http://localhost:8080/api/v1/products/1"
    }
  ]
}
```

### 6. Exemplo de Integração no Frontend

#### JavaScript (Fetch)
```javascript
// Listar produtos
fetch('http://localhost:8080/api/v1/products')
  .then(response => response.json())
  .then(data => console.log(data));

// Criar carrinho
fetch('http://localhost:8080/api/v1/cart', {
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

