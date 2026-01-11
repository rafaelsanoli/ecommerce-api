# ❓ FAQ - Perguntas Frequentes

Respostas para as dúvidas mais comuns sobre a E-commerce API.

---

## 🚀 Geral

### P: Para que serve esta API?
**R:** Esta é uma API REST completa de e-commerce, desenvolvida para que estudantes de frontend possam praticar a construção de interfaces consumindo uma API real e profissional.

### P: Preciso pagar para usar?
**R:** Não! O projeto é 100% gratuito e open source sob licença MIT.

### P: Posso usar em produção?
**R:** Sim! O código segue boas práticas e está production-ready. Porém, recomendamos algumas adaptações como adicionar autenticação e migrar do H2 para PostgreSQL/MySQL.

### P: Posso modificar o código?
**R:** Claro! Você pode modificar, distribuir e usar como quiser. Veja a licença MIT para mais detalhes.

---

## 🛠️ Instalação e Configuração

### P: Quais são os requisitos mínimos?
**R:** 
- Java 21 ou superior
- Maven 3.8+
- 2GB de RAM livre
- Sistema operacional: Windows, Linux ou macOS

### P: Não tenho Java 21, posso usar outra versão?
**R:** Não recomendamos. O projeto usa recursos específicos do Java 21. Instale o Java 21 LTS através do [Adoptium](https://adoptium.net/).

### P: A aplicação não está iniciando. O que fazer?
**R:** Verifique:
1. Java 21 está instalado: `java -version`
2. Maven está instalado: `mvn -version`
3. Porta 8080 está livre
4. Execute: `mvn clean install` antes de rodar

### P: Como mudar a porta padrão (8080)?
**R:** Edite `src/main/resources/application.yml`:
```yaml
server:
  port: 9090  # Sua porta aqui
```

### P: Erro "Port 8080 is already in use"
**R:** A porta 8080 está sendo usada por outro processo.

**Linux/Mac:**
```bash
lsof -i :8080
kill -9 <PID>
```

**Windows:**
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

Ou mude a porta conforme explicado acima.

---

## 💻 Desenvolvimento

### P: Como eu testo a API?
**R:** Você tem várias opções:
1. **Swagger UI**: http://localhost:8080/swagger-ui.html (Mais fácil!)
2. **Postman**: Importe a collection em `postman_collection.json`
3. **cURL**: Via linha de comando
4. **Seu código**: React, Vue, Angular, etc

### P: A API tem dados de exemplo?
**R:** Sim! Ao iniciar, a API carrega automaticamente:
- 10 categorias
- 15 produtos
- 10 avaliações
- 2 carrinhos com itens
- 4 itens na wishlist

### P: Os dados são persistidos?
**R:** Não. Usamos H2 em memória, então os dados são resetados a cada restart. Isso é proposital para facilitar testes.

### P: Como adicionar meus próprios dados?
**R:** Edite o arquivo `src/main/resources/data.sql` e adicione seus INSERTs.

### P: Posso usar PostgreSQL/MySQL ao invés de H2?
**R:** Sim! Basta:
1. Adicionar a dependência do banco no `pom.xml`
2. Alterar as configurações em `application.yml`
3. Criar o banco de dados

---

## 📡 API e Endpoints

### P: Qual é a URL base da API?
**R:** `http://localhost:8080/api/v1`

### P: Preciso de autenticação?
**R:** Não na versão atual. A API é aberta para facilitar o aprendizado.

### P: Como funciona a paginação?
**R:** Adicione parâmetros na query:
```
GET /api/v1/products?page=0&size=20&sort=name,asc
```
- `page`: número da página (começa em 0)
- `size`: itens por página
- `sort`: campo e direção (asc/desc)

### P: Como buscar produtos?
**R:** Você tem duas opções:
```
# Por nome
GET /api/v1/products/search?name=notebook

# Por categoria
GET /api/v1/products/category/2
```

### P: Como o carrinho funciona?
**R:**
1. Crie um carrinho: `POST /api/v1/cart` com `{userId: "user-123"}`
2. Guarde o `cartId` retornado
3. Adicione itens: `POST /api/v1/cart/{cartId}/items`
4. Gerencie o carrinho usando o `cartId`

### P: Posso ter múltiplos carrinhos?
**R:** Sim, um carrinho por `userId`. Se criar outro carrinho com mesmo `userId`, será um carrinho diferente.

### P: Como funcionam as avaliações?
**R:** 
- Rating de 1 a 5 estrelas (obrigatório)
- Comentário opcional
- Um review por requisição
- A API calcula automaticamente a média

---

## 🐛 Erros Comuns

### P: Erro 404 - Not Found
**R:** O recurso não existe. Verifique:
- ID está correto?
- O recurso foi criado?
- URL está correta?

### P: Erro 400 - Bad Request
**R:** Dados inválidos. Verifique:
- JSON está bem formatado?
- Campos obrigatórios foram preenchidos?
- Valores estão no formato correto?
- Veja a mensagem de erro para detalhes

### P: Erro 500 - Internal Server Error
**R:** Erro no servidor. Verifique os logs da aplicação. Se persistir, reporte como bug.

### P: "Estoque insuficiente"
**R:** O produto não tem estoque suficiente. Verifique:
```
GET /api/v1/products/{id}
```
E veja o campo `stock`.

### P: CORS Error no navegador
**R:** CORS já está configurado. Se persistir:
1. Verifique se a API está rodando
2. Use a URL correta: `http://localhost:8080`
3. Limpe cache do navegador

---

## 🎨 Frontend

### P: Qual framework devo usar?
**R:** Qualquer um! A API funciona com:
- React / Next.js
- Vue / Nuxt
- Angular
- Svelte
- Vanilla JavaScript
- Qualquer tecnologia que faça HTTP requests

### P: Tem exemplo de código?
**R:** Sim! Veja:
- [Guia para Frontend](./09-frontend-guide.md) - Exemplos completos
- [Exemplos de Código](./10-code-examples.md) - Mais exemplos

### P: Como fazer paginação no frontend?
**R:** A API retorna:
```json
{
  "content": [...],      // Array de itens
  "totalPages": 5,       // Total de páginas
  "totalElements": 100,  // Total de itens
  "number": 0,           // Página atual
  "size": 20             // Itens por página
}
```

Use esses dados para criar sua UI de paginação.

### P: Como mostrar loading?
**R:** Controle o estado de loading:
```javascript
const [loading, setLoading] = useState(true);

fetch('...')
  .then(...)
  .finally(() => setLoading(false));

// No JSX
{loading ? <Spinner /> : <Products />}
```

### P: Como lidar com erros?
**R:** Use try-catch e verifique o status:
```javascript
try {
  const response = await fetch('...');
  if (!response.ok) {
    const error = await response.json();
    alert(error.message);
    return;
  }
  const data = await response.json();
  // Sucesso!
} catch (error) {
  console.error('Erro de rede:', error);
}
```

---

## 🏗️ Arquitetura

### P: O que é Arquitetura Hexagonal?
**R:** É um padrão arquitetural que separa o código em camadas independentes:
- **Domínio**: Regras de negócio (núcleo)
- **Aplicação**: Orquestração dos use cases
- **Infraestrutura**: Detalhes técnicos (REST, DB, etc)

[Saiba mais](./03-architecture.md)

### P: Por que usar Arquitetura Hexagonal?
**R:** 
- ✅ Código desacoplado
- ✅ Fácil de testar
- ✅ Fácil de manter
- ✅ Independente de frameworks
- ✅ Boas práticas da indústria

### P: O código é complexo demais?
**R:** Pode parecer, mas é bem organizado! A estrutura é clara e cada classe tem uma responsabilidade única (SOLID).

---

## 📚 Aprendizado

### P: Sou iniciante, por onde começo?
**R:** Recomendamos:
1. [Quick Start](./02-quick-start.md) - Primeiros passos
2. [Guia Frontend](./09-frontend-guide.md) - Como usar a API
3. [Exemplos](./10-code-examples.md) - Código prático

### P: Quero entender o código backend
**R:**
1. [Arquitetura](./03-architecture.md) - Entenda a estrutura
2. [Contributing](./13-contributing.md) - Como contribuir
3. Explore o código começando por `domain/entities`

### P: Tem vídeo tutorial?
**R:** Ainda não, mas você pode:
- Usar o Swagger UI para explorar interativamente
- Seguir os guias da documentação
- Ver os exemplos de código

---

## 🤝 Contribuindo

### P: Como posso contribuir?
**R:** Várias formas:
1. Reportar bugs
2. Sugerir melhorias
3. Melhorar documentação
4. Adicionar novos recursos
5. Ajudar outros usuários

[Guia de Contribuição](./13-contributing.md)

### P: Encontrei um bug, o que fazer?
**R:** Abra uma issue no GitHub com:
- Descrição do problema
- Passos para reproduzir
- Comportamento esperado
- Comportamento atual
- Screenshots (se aplicável)

### P: Tenho uma sugestão de funcionalidade
**R:** Ótimo! Abra uma issue com a tag "enhancement" descrevendo sua ideia.

---

## 📞 Suporte

### P: Onde buscar ajuda?
**R:**
1. Esta FAQ
2. [Documentação completa](./README.md)
3. Issues no GitHub
4. Swagger UI para testar

### P: A documentação não responde minha dúvida
**R:** Abra uma issue descrevendo sua dúvida. Vamos adicionar à FAQ!

### P: Posso usar em projeto comercial?
**R:** Sim! A licença MIT permite uso comercial. Apenas mantenha a licença no código.

---

## 🎯 Casos de Uso

### P: Que tipo de projeto posso fazer?
**R:** 
- 🛒 E-commerce completo
- 📱 App mobile de compras
- 🖥️ Dashboard administrativo
- 📊 Sistema de análise de vendas
- 🎨 UI Kit de componentes
- 🎓 Projeto acadêmico

### P: Posso adicionar mais funcionalidades?
**R:** Claro! Sugestões:
- Sistema de pagamento
- Autenticação de usuários
- Sistema de cupons/descontos
- Chat com vendedor
- Notificações
- Upload de imagens
- Rastreamento de pedidos

---

## 🔮 Futuro

### P: Haverá novas versões?
**R:** Sim! Confira o [Roadmap](../README.md#-roadmap).

### P: Como sugerir novos recursos?
**R:** Abra uma issue com a tag "enhancement".

### P: Posso usar em produção?
**R:** Sim, mas considere:
- Adicionar autenticação (JWT)
- Trocar H2 por PostgreSQL/MySQL
- Adicionar monitoramento
- Configurar logs adequados
- Implementar backup
- Adicionar rate limiting

---

## 💡 Dicas

### P: Melhores práticas ao usar a API?
**R:**
1. **Cache**: Armazene dados que mudam pouco
2. **Loading**: Sempre mostre feedback visual
3. **Erros**: Trate todos os erros adequadamente
4. **Paginação**: Use para listas grandes
5. **Debounce**: Em buscas em tempo real

### P: Como otimizar minha aplicação?
**R:**
- Use paginação sempre que possível
- Cache requisições GET
- Implemente busca com debounce
- Lazy load de imagens
- Use React Query / SWR para gerenciar estado

---

**Não encontrou sua dúvida?** 

Abra uma [issue](https://github.com/seu-usuario/ecommerce-api/issues) ou consulte a [documentação completa](./README.md).

---

Atualizado: Janeiro 2026

