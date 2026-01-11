# 🚀 Instalação e Configuração

Guia completo para instalar e configurar a E-commerce API no seu ambiente.

---

## 📋 Pré-requisitos

### Obrigatórios
- ☕ **Java 21** ou superior (LTS)
- 📦 **Maven 3.8+** 

### Opcionais
- 🐳 **Docker** (para containerização)
- 📮 **Postman** ou **Insomnia** (para testar a API)
- 🔧 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

---

## ✅ Verificando Pré-requisitos

### 1. Verificar Java
```bash
java -version
```

**Saída esperada:**
```
openjdk version "21.0.x" 2024-xx-xx
```

**Não tem Java 21?** [Download aqui](https://adoptium.net/)

### 2. Verificar Maven
```bash
mvn -version
```

**Saída esperada:**
```
Apache Maven 3.8.x
```

**Não tem Maven?** [Download aqui](https://maven.apache.org/download.cgi)

---

## 📥 Instalação

### Opção 1: Clone via Git

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/ecommerce-api.git

# Entre no diretório
cd ecommerce-api

# Instale as dependências
mvn clean install
```

### Opção 2: Download ZIP

1. Baixe o ZIP do projeto
2. Extraia para uma pasta
3. Abra o terminal na pasta
4. Execute: `mvn clean install`

---

## ⚙️ Configuração

### 1. Estrutura do Projeto

Após clonar, você terá:
```
ecommerce-api/
├── src/
│   ├── main/
│   │   ├── java/          # Código fonte
│   │   └── resources/     # Configurações
│   └── test/              # Testes
├── docs/                  # Documentação
├── pom.xml               # Dependências Maven
└── README.md             # Documentação principal
```

### 2. Configurações Padrão

O arquivo `src/main/resources/application.yml` contém:

```yaml
server:
  port: 8080              # Porta da aplicação

spring:
  datasource:
    url: jdbc:h2:mem:ecommerce  # Banco H2 em memória
    username: sa
    password:
  
  h2:
    console:
      enabled: true       # Console H2 habilitado
```

### 3. Personalizando Configurações

**Mudar a porta:**
```yaml
server:
  port: 9090  # Sua porta customizada
```

**Habilitar logs detalhados:**
```yaml
logging:
  level:
    com.ecommerce.api: DEBUG
```

### 4. Variáveis de Ambiente (Opcional)

Você pode configurar via variáveis de ambiente:

```bash
export SERVER_PORT=9090
export SPRING_DATASOURCE_URL=jdbc:h2:mem:mydb
```

---

## 🚀 Executando a Aplicação

### Modo Desenvolvimento

```bash
# Via Maven
mvn spring-boot:run

# Ou compile e execute
mvn clean package
java -jar target/ecommerce-api-1.0.0.jar
```

### Modo Produção

```bash
# Compile sem testes
mvn clean package -DskipTests

# Execute otimizado
java -jar -Dspring.profiles.active=prod target/ecommerce-api-1.0.0.jar
```

---

## ✅ Verificando a Instalação

### 1. Aplicação Rodando

Você deve ver:
```
╔═══════════════════════════════════════════════════════════════╗
║          🚀 E-COMMERCE API ESTÁ RODANDO! 🚀                   ║
║  📚 Swagger UI: http://localhost:8080/swagger-ui.html        ║
╚═══════════════════════════════════════════════════════════════╝
```

### 2. Testar Endpoints

```bash
# Listar produtos
curl http://localhost:8080/api/v1/products

# Deve retornar JSON com lista de produtos
```

### 3. Acessar Documentação

Abra no navegador:
- **Swagger**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

---

## 🐳 Docker (Opcional)

### Criar Imagem Docker

```bash
# Build da imagem
docker build -t ecommerce-api:1.0.0 .

# Executar container
docker run -p 8080:8080 ecommerce-api:1.0.0
```

### Docker Compose

```yaml
version: '3.8'
services:
  api:
    image: ecommerce-api:1.0.0
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
```

Execute:
```bash
docker-compose up
```

---

## 🔧 Problemas Comuns

### Porta 8080 em uso

**Erro:** `Port 8080 is already in use`

**Solução:**
```bash
# Linux/Mac - Encontrar processo
lsof -i :8080

# Windows
netstat -ano | findstr :8080

# Matar processo ou mudar porta no application.yml
```

### Erro de compilação

**Erro:** `compilation failed`

**Solução:**
```bash
# Limpar cache Maven
mvn clean

# Reinstalar dependências
mvn clean install -U
```

### Java não encontrado

**Erro:** `java: command not found`

**Solução:**
1. Instale Java 21
2. Configure JAVA_HOME:
```bash
export JAVA_HOME=/path/to/java21
export PATH=$JAVA_HOME/bin:$PATH
```

---

## 🎯 Próximos Passos

Instalação concluída! Agora:

1. ✅ [Guia de Início Rápido](./02-quick-start.md)
2. ✅ [Entenda a Arquitetura](./03-architecture.md)
3. ✅ [Explore os Endpoints](./04-api-products.md)

---

## 💡 Dicas

### IDE Configuration

**IntelliJ IDEA:**
1. File → Open → Selecione pasta do projeto
2. Maven será detectado automaticamente
3. Run → Run 'EcommerceApiApplication'

**VS Code:**
1. Instale extensão "Java Extension Pack"
2. Abra pasta do projeto
3. F5 para executar

### Hot Reload

Adicione no pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

---

**Precisa de ajuda?** Consulte o [FAQ](./16-faq.md) ou abra uma issue.

