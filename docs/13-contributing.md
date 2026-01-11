# 🤝 Guia de Contribuição

Obrigado por considerar contribuir com a E-commerce API! Este documento fornece diretrizes para contribuir com o projeto.

---

## 📋 Código de Conduta

Ao participar deste projeto, você concorda em manter um ambiente respeitoso e inclusivo para todos.

### Comportamentos Esperados
- ✅ Use linguagem acolhedora e inclusiva
- ✅ Respeite pontos de vista diferentes
- ✅ Aceite críticas construtivas
- ✅ Foque no que é melhor para a comunidade

### Comportamentos Inaceitáveis
- ❌ Linguagem ou imagens sexualizadas
- ❌ Comentários ofensivos ou depreciativos
- ❌ Assédio público ou privado
- ❌ Publicar informações privadas de outros

---

## 🚀 Como Contribuir

### 1. Reportar Bugs 🐛

Encontrou um bug? Ajude-nos abrindo uma issue!

**Antes de reportar:**
- Procure se o bug já foi reportado
- Verifique se está usando a versão mais recente
- Tente reproduzir o problema

**Como reportar:**
1. Vá para [Issues](https://github.com/seu-usuario/ecommerce-api/issues)
2. Clique em "New Issue"
3. Escolha "Bug Report"
4. Preencha o template com:
   - Descrição clara do bug
   - Passos para reproduzir
   - Comportamento esperado
   - Comportamento atual
   - Screenshots (se aplicável)
   - Ambiente (OS, Java version, etc)

**Exemplo de Bug Report:**
```markdown
## Descrição
Ao adicionar um produto ao carrinho com quantidade zero, a API aceita.

## Passos para Reproduzir
1. POST /api/v1/cart/1/items
2. Body: {"productId": 1, "quantity": 0}
3. API retorna 200 OK

## Comportamento Esperado
API deve retornar erro 400 com mensagem de validação.

## Comportamento Atual
API aceita quantity = 0

## Ambiente
- OS: Ubuntu 22.04
- Java: 21.0.1
- Versão: 1.0.0
```

---

### 2. Sugerir Melhorias 💡

Tem uma ideia para melhorar o projeto?

**Como sugerir:**
1. Abra uma issue
2. Escolha "Feature Request"
3. Descreva:
   - O problema que sua sugestão resolve
   - A solução proposta
   - Alternativas consideradas
   - Impacto na API

**Exemplo de Feature Request:**
```markdown
## Problema
Usuários não conseguem filtrar produtos por faixa de preço.

## Solução Proposta
Adicionar parâmetros minPrice e maxPrice no endpoint GET /products

GET /api/v1/products?minPrice=100&maxPrice=500

## Alternativas
- Usar um único parâmetro com range: ?price=100-500
- Criar endpoint separado: /products/by-price-range

## Impacto
- Backend: Adicionar query no repository
- Frontend: Facilita criação de filtros
- Nenhuma quebra de compatibilidade
```

---

### 3. Contribuir com Código 💻

Quer adicionar uma funcionalidade ou corrigir um bug?

#### Setup Inicial

```bash
# 1. Fork o repositório no GitHub

# 2. Clone seu fork
git clone https://github.com/SEU-USUARIO/ecommerce-api.git
cd ecommerce-api

# 3. Adicione o repositório original como upstream
git remote add upstream https://github.com/ORIGINAL/ecommerce-api.git

# 4. Crie uma branch para sua alteração
git checkout -b feature/minha-feature
# ou
git checkout -b fix/meu-bugfix
```

#### Padrões de Código

**Naming Conventions:**
- Classes: `PascalCase` (ex: `ProductService`)
- Métodos/Variáveis: `camelCase` (ex: `getProductById`)
- Constantes: `UPPER_SNAKE_CASE` (ex: `MAX_PRODUCTS_PER_PAGE`)
- Packages: `lowercase` (ex: `com.ecommerce.api.domain`)

**Estrutura de Classes:**
```java
package com.ecommerce.api.domain.services;

import ...;

/**
 * Service para gerenciar produtos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository repository;
    
    /**
     * Busca um produto por ID
     * 
     * @param id ID do produto
     * @return Optional com o produto, se encontrado
     */
    public Optional<Product> findById(Long id) {
        log.debug("Buscando produto com ID: {}", id);
        return repository.findById(id);
    }
}
```

**Clean Code:**
- Métodos pequenos (máximo 20 linhas)
- Uma responsabilidade por classe
- Nomes descritivos
- Evite comentários óbvios
- DRY (Don't Repeat Yourself)

#### Commits

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
<tipo>(<escopo>): <descrição>

[corpo opcional]

[rodapé opcional]
```

**Tipos:**
- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `docs`: Documentação
- `style`: Formatação (não afeta o código)
- `refactor`: Refatoração
- `test`: Adição de testes
- `chore`: Tarefas de manutenção
- `perf`: Melhoria de performance

**Exemplos:**
```bash
# Feature
git commit -m "feat(products): adiciona filtro por faixa de preço"

# Fix
git commit -m "fix(cart): corrige validação de quantidade zero"

# Docs
git commit -m "docs(readme): atualiza instruções de instalação"

# Com corpo
git commit -m "feat(reviews): adiciona limit de reviews por usuário

Adiciona validação para limitar um review por usuário por produto.
Retorna erro 400 se usuário já avaliou o produto.

Closes #123"
```

#### Testes

Sempre adicione testes para suas alterações:

```java
@Test
void shouldReturnProductWhenExists() {
    // Given
    Long productId = 1L;
    Product product = Product.builder()
            .id(productId)
            .name("Test Product")
            .build();
    when(repository.findById(productId))
            .thenReturn(Optional.of(product));
    
    // When
    Optional<Product> result = service.findById(productId);
    
    // Then
    assertTrue(result.isPresent());
    assertEquals("Test Product", result.get().getName());
}
```

#### Pull Request

```bash
# 1. Atualize sua branch com a main
git fetch upstream
git rebase upstream/main

# 2. Push para seu fork
git push origin feature/minha-feature

# 3. Abra Pull Request no GitHub
```

**Template do PR:**
```markdown
## Descrição
Breve descrição das mudanças

## Motivação
Por que essas mudanças são necessárias?

## Mudanças
- Adiciona endpoint X
- Corrige bug Y
- Atualiza documentação Z

## Tipo de Mudança
- [ ] Bug fix
- [ ] Nova funcionalidade
- [ ] Breaking change
- [ ] Documentação

## Checklist
- [ ] Código segue os padrões do projeto
- [ ] Testes adicionados/atualizados
- [ ] Documentação atualizada
- [ ] Commits seguem Conventional Commits
- [ ] Build passa sem erros
- [ ] Não há conflitos com main

## Screenshots (se aplicável)
...

## Issues Relacionadas
Closes #123
```

---

### 4. Melhorar Documentação 📚

A documentação sempre pode melhorar!

**O que documentar:**
- Correções de erros na doc
- Novos exemplos de código
- Traduções
- Guias adicionais
- Melhorias de clareza

**Como contribuir:**
1. Edite os arquivos em `/docs`
2. Siga o mesmo estilo dos documentos existentes
3. Use Markdown
4. Adicione exemplos práticos
5. Revise ortografia e gramática

---

## 📝 Diretrizes Específicas

### Arquitetura Hexagonal

Mantenha a separação de camadas:

```
✅ Domínio não depende de nada
✅ Aplicação depende só do Domínio
✅ Infraestrutura depende de Domínio e Aplicação
❌ Domínio nunca deve importar Infraestrutura
```

**Exemplo Correto:**
```java
// Domain
public interface ProductRepository {  // Port OUT
    Optional<Product> findById(Long id);
}

// Infrastructure
@Repository
public class ProductRepositoryAdapter implements ProductRepository {
    // Implementação JPA
}
```

**Exemplo Incorreto:**
```java
// Domain - ERRADO!
import org.springframework.data.jpa.repository.JpaRepository;

public class Product {
    @Entity  // ❌ Anotação JPA no domínio
    private Long id;
}
```

### DTOs

- Use DTOs separados para Request e Response
- Não exponha entidades de domínio diretamente
- Valide com Bean Validation

```java
@Data
public class CreateProductRequest {
    @NotBlank
    private String name;
    
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;
}
```

### Tratamento de Erros

- Use exceções específicas
- Sempre retorne mensagens claras
- Não exponha detalhes internos

```java
if (!product.hasStock(quantity)) {
    throw new InsufficientStockException(
        product.getName(), 
        quantity, 
        product.getStock()
    );
}
```

---

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=ProductServiceTest

# Com cobertura
mvn test jacoco:report
```

### Cobertura Mínima

- Services: 80%
- Controllers: 70%
- Domínio: 90%

### Tipos de Testes

**Unitários:**
```java
@Test
void shouldAddItemToCart() {
    Cart cart = new Cart();
    CartItem item = CartItem.builder()
            .productId(1L)
            .quantity(2)
            .build();
    
    cart.addItem(item);
    
    assertEquals(1, cart.getItems().size());
    assertEquals(2, cart.getTotalItems());
}
```

**Integração:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
```

---

## 🎨 Style Guide

### Imports

Organize imports:
```java
// 1. Java standard
import java.util.List;
import java.util.Optional;

// 2. Third-party
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 3. Projeto
import com.ecommerce.api.domain.Product;
```

### Formatação

```java
// Bom
public Product createProduct(Product product) {
    validateProduct(product);
    return repository.save(product);
}

// Ruim
public Product createProduct(Product product){validateProduct(product);return repository.save(product);}
```

---

## 🎯 Prioridades

Contribuições mais valiosas:

1. 🐛 **Correção de Bugs** - Alta prioridade
2. 📚 **Documentação** - Alta prioridade
3. ✨ **Novas Features** - Média prioridade
4. 🎨 **Melhorias de UI** - Média prioridade
5. 🧪 **Mais Testes** - Baixa prioridade

---

## ❓ Dúvidas

### Onde tirar dúvidas?
- Abra uma issue com tag "question"
- Consulte a [FAQ](./16-faq.md)
- Veja discussões existentes

### Quanto tempo leva para revisar?
- Bugs: 1-3 dias
- Features: 3-7 dias
- Docs: 1-2 dias

### Meu PR foi rejeitado, e agora?
- Não desanime!
- Leia o feedback
- Faça as alterações sugeridas
- Resubmeta

---

## 🏆 Reconhecimento

Contribuidores são reconhecidos:
- No README.md
- Nos release notes
- Como "Contributor" no GitHub

---

## 📞 Contato

Precisa de ajuda para contribuir?
- Abra uma issue
- Marque @maintainer
- Email: contato@exemplo.com

---

**Obrigado por contribuir!** Cada contribuição, por menor que seja, faz diferença! 🎉

---

**Última atualização**: Janeiro 2026

