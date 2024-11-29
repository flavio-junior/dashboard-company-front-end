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
    tb_product (created_at, name, price, stock_quantity)
VALUES
    (NOW(), 'Coca Cola Zero', 9.99, 100);

INSERT INTO
    tb_product_category (fk_product, fk_category)
VALUES
    (1, 1);

INSERT INTO
    tb_order (created_at, type, status, quantity, price)
VALUES
    (NOW(), 'DELIVERY', 'OPEN', 4, 99.99),
    (NOW(), 'ORDER', 'OPEN', 5, 110.00),
    (NOW(), 'RESERVATION', 'OPEN', 3, 62.00);

INSERT INTO
    tb_order_reservation (fk_order, fk_reservation)
VALUES
    (3, 1),
    (3, 2),
    (3, 3);

INSERT INTO
    tb_address (street, number, district, complement)
VALUES
    ('Frei Damião', 65, 'Centro', 'Ao lado da casa de Roberto Tavares');

INSERT INTO
    tb_order_address (fk_order, fk_address)
VALUES
    (1, 1);

INSERT INTO
    tb_object (identifier, type, name, price, quantity, total)
VALUES
    (9, 'FOOD', 'Camarão na Moranga', 30.00, 3, 90.0),
    (1, 'PRODUCT', 'Coca Cola Zero',  9.99, 1, 9.99),
    (6, 'PRODUCT', 'Moqueca de Peixe', 25.00, 4, 100.00),
    (5, 'PRODUCT', 'Cuscuz Nordestino', 10.00, 1, 10.00),
    (6, 'PRODUCT', 'Buchada de Bode', 20.00, 1, 20.00),
    (7, 'PRODUCT', 'Camarão na Moranga', 30.00, 1, 30.00),
    (8, 'PRODUCT', 'Paçoca de Carne de Sol', 12.00, 1, 12.00);

INSERT INTO
    tb_order_object (fk_order, fk_object)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 5),
    (3, 6),
    (3, 7);

INSERT INTO
    tb_payment (created_at, status, type)
VALUES
    (NOW(), 'PENDING', null),
    (NOW(), 'PENDING', null),
    (NOW(), 'PENDING', null);

INSERT INTO
    tb_order_payment (fk_order, fk_payment)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE, 100.50);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE, 200.75);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 150.00);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 300.25);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 50.00);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 400.10);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 250.40);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 120.60);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 80.90);
INSERT INTO tb_checkout_details (date, total) VALUES (CURRENT_DATE - INTERVAL '1 day', 170.30);
