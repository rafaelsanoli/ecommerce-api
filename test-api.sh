#!/bin/bash

# Script de Teste - E-commerce API v1.1.0
# Testa os principais endpoints da API incluindo autenticação JWT

BASE_URL="http://localhost:8080/api/v1"
BOLD='\033[1m'
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BOLD}==================================${NC}"
echo -e "${BOLD}E-commerce API - Test Script v1.1.0${NC}"
echo -e "${BOLD}==================================${NC}\n"

# Função para testar endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3
    local data=$4
    local token=$5

    echo -e "${BLUE}Testando:${NC} $description"
    echo -e "  ${method} ${endpoint}"

    if [ -z "$data" ]; then
        if [ -z "$token" ]; then
            response=$(curl -s -w "\n%{http_code}" -X ${method} "${BASE_URL}${endpoint}")
        else
            response=$(curl -s -w "\n%{http_code}" -X ${method} "${BASE_URL}${endpoint}" \
                -H "Authorization: Bearer ${token}")
        fi
    else
        if [ -z "$token" ]; then
            response=$(curl -s -w "\n%{http_code}" -X ${method} "${BASE_URL}${endpoint}" \
                -H "Content-Type: application/json" \
                -d "${data}")
        else
            response=$(curl -s -w "\n%{http_code}" -X ${method} "${BASE_URL}${endpoint}" \
                -H "Content-Type: application/json" \
                -H "Authorization: Bearer ${token}" \
                -d "${data}")
        fi
    fi

    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')

    if [ "$http_code" -ge 200 ] && [ "$http_code" -lt 300 ]; then
        echo -e "  ${GREEN}✓ Sucesso (HTTP $http_code)${NC}\n"
        return 0
    else
        echo -e "  ${RED}✗ Erro (HTTP $http_code)${NC}"
        echo -e "  Resposta: $body\n"
        return 1
    fi
}

# Contador de testes
TOTAL=0
PASSED=0

echo -e "${BOLD}1. Testando Autenticação JWT${NC}\n"

# Login como admin
echo -e "${BLUE}Fazendo login como admin...${NC}"
login_response=$(curl -s -X POST "${BASE_URL}/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"username":"admin","password":"password123"}')

TOKEN=$(echo $login_response | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo -e "${RED}✗ Falha ao obter token JWT${NC}"
    echo -e "Resposta: $login_response\n"
    echo -e "${RED}Testes interrompidos. Verifique se a API está rodando.${NC}"
    exit 1
else
    echo -e "${GREEN}✓ Login realizado com sucesso${NC}"
    echo -e "  Token obtido (primeiros 20 chars): ${TOKEN:0:20}...\n"
    TOTAL=$((TOTAL + 1))
    PASSED=$((PASSED + 1))
fi

# Registrar novo usuário
TOTAL=$((TOTAL + 1))
if test_endpoint "POST" "/auth/register" "Registrar novo usuário" \
    '{"username":"testuser","email":"test@example.com","password":"test123","fullName":"Test User"}'; then
    PASSED=$((PASSED + 1))
fi

# Obter perfil do usuário
TOTAL=$((TOTAL + 1))
if test_endpoint "GET" "/users/me" "Obter perfil do usuário autenticado" "" "$TOKEN"; then
    PASSED=$((PASSED + 1))
fi

echo -e "${BOLD}2. Testando Endpoints Públicos${NC}\n"

# Listar produtos
TOTAL=$((TOTAL + 1))
if test_endpoint "GET" "/products" "Listar produtos"; then
    PASSED=$((PASSED + 1))
fi

# Listar categorias
TOTAL=$((TOTAL + 1))
if test_endpoint "GET" "/categories" "Listar categorias"; then
    PASSED=$((PASSED + 1))
fi

# Buscar produto específico
TOTAL=$((TOTAL + 1))
if test_endpoint "GET" "/products/1" "Buscar produto por ID"; then
    PASSED=$((PASSED + 1))
fi

echo -e "${BOLD}3. Testando Endpoints Protegidos (com token)${NC}\n"

# Criar carrinho
TOTAL=$((TOTAL + 1))
if test_endpoint "POST" "/cart" "Criar carrinho" '{"userId":"testuser"}' "$TOKEN"; then
    PASSED=$((PASSED + 1))
fi

# Adicionar à wishlist
TOTAL=$((TOTAL + 1))
if test_endpoint "POST" "/wishlist" "Adicionar à wishlist" \
    '{"userId":"testuser","productId":1}' "$TOKEN"; then
    PASSED=$((PASSED + 1))
fi

# Criar review
TOTAL=$((TOTAL + 1))
if test_endpoint "POST" "/products/1/reviews" "Criar review" \
    '{"userName":"testuser","rating":5,"comment":"Produto excelente!"}' "$TOKEN"; then
    PASSED=$((PASSED + 1))
fi

echo -e "${BOLD}4. Testando Endpoints Admin (requer role ADMIN)${NC}\n"

# Criar produto (deve funcionar com token admin)
TOTAL=$((TOTAL + 1))
if test_endpoint "POST" "/products" "Criar produto como ADMIN" \
    '{"name":"Produto Teste","description":"Descrição teste","price":99.90,"stock":10,"categoryId":1}' "$TOKEN"; then
    PASSED=$((PASSED + 1))
fi

# Criar categoria (deve funcionar com token admin)
TOTAL=$((TOTAL + 1))
if test_endpoint "POST" "/categories" "Criar categoria como ADMIN" \
    '{"name":"Categoria Teste","description":"Descrição teste"}' "$TOKEN"; then
    PASSED=$((PASSED + 1))
fi

# Resumo
echo -e "${BOLD}==================================${NC}"
echo -e "${BOLD}Resumo dos Testes${NC}"
echo -e "${BOLD}==================================${NC}"
echo -e "Total de testes: ${TOTAL}"
echo -e "Testes aprovados: ${GREEN}${PASSED}${NC}"
echo -e "Testes falhados: ${RED}$((TOTAL - PASSED))${NC}"

if [ $PASSED -eq $TOTAL ]; then
    echo -e "\n${GREEN}${BOLD}✓ Todos os testes passaram!${NC}\n"
    exit 0
else
    echo -e "\n${RED}${BOLD}✗ Alguns testes falharam.${NC}\n"
    exit 1
fi

