INSERT INTO
    tb_category (name)
VALUES
    ('Bebidas'),
    ('Refrigerantes'),
    ('Comidas Típicas');

INSERT INTO
    tb_user_category (fk_user, fk_category)
VALUES
    (1, 1),
    (1, 2),
    (1, 3);

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
    tb_user_item (fk_user, fk_item)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6);

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
    tb_user_reservation (fk_user, fk_reservation)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10);

INSERT INTO
    tb_food (created_at, name, price)
VALUES
    (DATE_TRUNC('second', NOW()), 'Acarajé', 12.50),
    (DATE_TRUNC('second', NOW()), 'Baião de Dois', 15.00),
    (DATE_TRUNC('second', NOW()), 'Tapioca', 8.00),
    (DATE_TRUNC('second', NOW()), 'Caruru', 14.00),
    (DATE_TRUNC('second', NOW()), 'Cuscuz Nordestino', 10.00),
    (DATE_TRUNC('second', NOW()), 'Moqueca de Peixe', 25.00),
    (DATE_TRUNC('second', NOW()), 'Sarapatel', 18.00),
    (DATE_TRUNC('second', NOW()), 'Buchada de Bode', 20.00),
    (DATE_TRUNC('second', NOW()), 'Camarão na Moranga', 30.00),
    (DATE_TRUNC('second', NOW()), 'Paçoca de Carne de Sol', 12.00);

INSERT INTO
    tb_user_food (fk_food, fk_user)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1);

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
    (DATE_TRUNC('second', DATE_TRUNC('second', NOW())), 'Coca Cola Zero', 9.99, 100);

INSERT INTO
    tb_product_category (fk_product, fk_category)
VALUES
    (1, 1);

INSERT INTO
    tb_user_product (fk_product, fk_user)
VALUES
    (1, 1);

INSERT INTO
    tb_order (created_at, type, status, quantity, total)
VALUES
    (DATE_TRUNC('second', NOW()), 'DELIVERY', 'OPEN', 4, 99.99),
    (DATE_TRUNC('second', NOW()), 'ORDER', 'OPEN', 5, 110.00),
    (DATE_TRUNC('second', NOW()), 'RESERVATION', 'OPEN', 3, 62.00);

INSERT INTO
    tb_user_order (fk_order, fk_user)
VALUES
    (1, 1),
    (2, 1),
    (3, 1);


INSERT INTO
    tb_order_reservation (fk_order, fk_reservation)
VALUES
    (3, 1),
    (3, 2),
    (3, 3);

INSERT INTO
    tb_address (status, street, number, district, complement)
VALUES
    ('PENDING_DELIVERY', 'Frei Damião', 65, 'Centro', 'Ao lado da casa de Roberto Tavares');

INSERT INTO
    tb_order_address (fk_order, fk_address)
VALUES
    (1, 1);

INSERT INTO
    tb_object (identifier, type, name, price, quantity, total, status)
VALUES
    (9, 'FOOD', 'Camarão na Moranga', 30.00, 3, 90.0, 'PENDING'),
    (1, 'PRODUCT', 'Coca Cola Zero',  9.99, 1, 9.99, 'PENDING'),
    (6, 'PRODUCT', 'Moqueca de Peixe', 25.00, 4, 100.00, 'PENDING'),
    (5, 'PRODUCT', 'Cuscuz Nordestino', 10.00, 1, 10.00, 'PENDING'),
    (6, 'PRODUCT', 'Buchada de Bode', 20.00, 1, 20.00, 'PENDING'),
    (7, 'PRODUCT', 'Camarão na Moranga', 30.00, 1, 30.00, 'PENDING'),
    (8, 'PRODUCT', 'Paçoca de Carne de Sol', 12.00, 1, 12.00, 'PENDING');

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
    tb_payment (created_at, type)
VALUES
    (null, null),
    (null, null),
    (null, null);

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
