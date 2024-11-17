INSERT INTO
    tb_category (name)
VALUES
    ('Bebidas'),
    ('Refrigerantes'),
    ('Comidas Típicas');

INSERT INTO
    tb_item (name, price)
VALUES
    ('Batata Frita', 15.00),
    ('Carne de Sol', 30.00),
    ('Bisteca', 18.90),
    ('Verdura', 10.00),
    ('Suco de Laranja', 5.00),
    ('Suco de maracujá', 5.00);

INSERT INTO
    tb_reservation (name)
VALUES
    ('Mesa 01'),
    ('Mesa 02'),
    ('Mesa 03'),
    ('Mesa 04'),
    ('Mesa 05'),
    ('Mesa 06'),
    ('Mesa 07'),
    ('Mesa 08'),
    ('Mesa 09'),
    ('Mesa 10');

INSERT INTO
    tb_food (created_at, name, description, price)
VALUES
    (NOW(), 'Acarajé', 'Bolinho frito de feijão-fradinho recheado com vatapá e camarão seco', 12.50),
    (NOW(), 'Baião de Dois', 'Mistura de arroz, feijão, carne seca e queijo coalho', 15.00),
    (NOW(), 'Tapioca', 'Massa de mandioca recheada com coco ralado e leite condensado ou queijo', 8.00),
    (NOW(), 'Caruru', 'Prato feito com quiabo, azeite de dendê, camarões secos e temperos', 14.00),
    (NOW(), 'Cuscuz Nordestino', 'Flocos de milho cozidos, acompanhados de manteiga ou queijo', 10.00),
    (NOW(), 'Moqueca de Peixe', 'Peixe cozido com leite de coco, azeite de dendê e temperos', 25.00),
    (NOW(), 'Sarapatel', 'Prato feito com miúdos de porco temperados e cozidos', 18.00),
    (NOW(), 'Buchada de Bode', 'Prato tradicional feito com estômago de bode e miúdos temperados', 20.00),
    (NOW(), 'Camarão na Moranga', 'Abóbora recheada com creme e camarões temperados', 30.00),
    (NOW(), 'Paçoca de Carne de Sol', 'Mistura de carne de sol desfiada, farinha de mandioca e temperos', 12.00);

INSERT INTO
    tb_food_category (fk_food, fk_category)
VALUES
    (1, 3),
    (2, 3),
    (3, 3),
    (4, 3),
    (5, 3),
    (6, 3),
    (7, 3),
    (8, 3),
    (9, 3),
    (10, 3);

INSERT INTO
    tb_product (created_at, name, description, price, stock_quantity)
VALUES
    (NOW(), 'Coca Cola Zero', 'A Coca-Cola Sem Açúcar é um refrigerante que não contém calorias, produzida com adoçantes que mantêm o sabor de Coca-Cola com 0% de açúcar.', 9.99, 100);

INSERT INTO
    tb_product_category (fk_product, fk_category)
VALUES
    (1, 1);
