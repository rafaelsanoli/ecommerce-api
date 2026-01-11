-- ===================================
-- DADOS INICIAIS - E-COMMERCE API
-- ===================================

-- Inserir Categorias
INSERT INTO categories (id, name, description, parent_category_id) VALUES
(1, 'Eletrônicos', 'Produtos eletrônicos em geral', NULL),
(2, 'Computadores', 'Notebooks, desktops e acessórios', 1),
(3, 'Smartphones', 'Celulares e tablets', 1),
(4, 'Roupas', 'Vestuário em geral', NULL),
(5, 'Masculino', 'Roupas masculinas', 4),
(6, 'Feminino', 'Roupas femininas', 4),
(7, 'Livros', 'Livros físicos e digitais', NULL),
(8, 'Programação', 'Livros de programação e tecnologia', 7),
(9, 'Casa e Decoração', 'Itens para casa', NULL),
(10, 'Móveis', 'Móveis em geral', 9);

-- Inserir Produtos
INSERT INTO products (id, name, description, price, stock, image_url, category_id, created_at, updated_at) VALUES
-- Eletrônicos
(1, 'Notebook Dell Inspiron', 'Notebook Dell i5 11ª geração, 8GB RAM, 256GB SSD, Tela 15.6"', 3499.90, 15, 'https://picsum.photos/seed/notebook1/400/300', 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(2, 'MacBook Air M2', 'MacBook Air com chip M2, 8GB RAM, 256GB SSD, Tela Retina 13.6"', 9999.00, 8, 'https://picsum.photos/seed/macbook/400/300', 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(3, 'iPhone 14 Pro', 'iPhone 14 Pro 128GB, Câmera 48MP, Tela Super Retina XDR 6.1"', 7999.00, 12, 'https://picsum.photos/seed/iphone14/400/300', 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(4, 'Samsung Galaxy S23', 'Samsung Galaxy S23 5G 128GB, Câmera tripla 50MP', 4299.00, 20, 'https://picsum.photos/seed/galaxys23/400/300', 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(5, 'Mouse Logitech MX Master 3', 'Mouse sem fio ergonômico, sensor 4000 DPI', 549.90, 35, 'https://picsum.photos/seed/mouse/400/300', 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- Roupas
(6, 'Camiseta Básica Preta', 'Camiseta 100% algodão, tamanhos P ao GG', 49.90, 100, 'https://picsum.photos/seed/camiseta1/400/300', 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(7, 'Jeans Skinny', 'Calça jeans skinny feminina, diversos tamanhos', 129.90, 50, 'https://picsum.photos/seed/jeans/400/300', 6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(8, 'Tênis Nike Air Max', 'Tênis esportivo Nike Air Max, numeração 38-44', 599.90, 25, 'https://picsum.photos/seed/tenis/400/300', 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(9, 'Vestido Floral', 'Vestido longo floral, ideal para verão', 189.90, 30, 'https://picsum.photos/seed/vestido/400/300', 6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- Livros
(10, 'Clean Code', 'Clean Code: A Handbook of Agile Software Craftsmanship - Robert C. Martin', 89.90, 45, 'https://picsum.photos/seed/cleancode/400/300', 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(11, 'Design Patterns', 'Design Patterns: Elements of Reusable Object-Oriented Software', 119.90, 20, 'https://picsum.photos/seed/designpatterns/400/300', 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(12, 'JavaScript: The Good Parts', 'JavaScript: The Good Parts - Douglas Crockford', 69.90, 35, 'https://picsum.photos/seed/javascript/400/300', 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- Casa e Decoração
(13, 'Sofá 3 Lugares', 'Sofá retrátil e reclinável 3 lugares, tecido suede', 1899.00, 10, 'https://picsum.photos/seed/sofa/400/300', 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(14, 'Mesa de Centro', 'Mesa de centro moderna, madeira MDF', 349.90, 18, 'https://picsum.photos/seed/mesa/400/300', 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(15, 'Luminária LED', 'Luminária de mesa LED regulável, design minimalista', 129.90, 40, 'https://picsum.photos/seed/luminaria/400/300', 9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Inserir Reviews
INSERT INTO reviews (id, product_id, user_name, rating, comment, created_at) VALUES
(1, 1, 'João Silva', 5, 'Excelente notebook! Rápido e eficiente para trabalho.', CURRENT_TIMESTAMP()),
(2, 1, 'Maria Santos', 4, 'Bom custo-benefício, mas a bateria poderia durar mais.', CURRENT_TIMESTAMP()),
(3, 3, 'Carlos Oliveira', 5, 'iPhone perfeito! Câmera incrível e desempenho excepcional.', CURRENT_TIMESTAMP()),
(4, 10, 'Ana Costa', 5, 'Livro essencial para todo desenvolvedor! Mudou minha forma de programar.', CURRENT_TIMESTAMP()),
(5, 10, 'Pedro Alves', 5, 'Melhor livro sobre boas práticas de código. Recomendo!', CURRENT_TIMESTAMP()),
(6, 4, 'Juliana Lima', 4, 'Ótimo celular, mas o preço está um pouco alto.', CURRENT_TIMESTAMP()),
(7, 8, 'Rafael Moura', 5, 'Tênis muito confortável, perfeito para corrida.', CURRENT_TIMESTAMP()),
(8, 13, 'Fernanda Cruz', 5, 'Sofá lindo e confortável, vale muito a pena!', CURRENT_TIMESTAMP()),
(9, 2, 'Lucas Ferreira', 5, 'MacBook incrível! Processamento rápido e silencioso.', CURRENT_TIMESTAMP()),
(10, 5, 'Patricia Rocha', 4, 'Mouse ergonômico e preciso, mas um pouco caro.', CURRENT_TIMESTAMP());

-- Criar alguns carrinhos de exemplo
INSERT INTO carts (id, user_id, created_at, updated_at) VALUES
(1, 'user-001', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(2, 'user-002', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Adicionar itens aos carrinhos
INSERT INTO cart_items (id, cart_id, product_id, product_name, quantity, price_at_add_time) VALUES
(1, 1, 1, 'Notebook Dell Inspiron', 1, 3499.90),
(2, 1, 5, 'Mouse Logitech MX Master 3', 1, 549.90),
(3, 2, 10, 'Clean Code', 2, 89.90),
(4, 2, 11, 'Design Patterns', 1, 119.90);

-- Adicionar itens à wishlist
INSERT INTO wishlist (id, user_id, product_id, added_at) VALUES
(1, 'user-001', 2, CURRENT_TIMESTAMP()),
(2, 'user-001', 3, CURRENT_TIMESTAMP()),
(3, 'user-002', 8, CURRENT_TIMESTAMP()),
(4, 'user-002', 13, CURRENT_TIMESTAMP());

