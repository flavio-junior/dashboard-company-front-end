INSERT INTO
    tb_category (name)
VALUES
    ('Bebidas'),
    ('Refrigerantes');

INSERT INTO
    tb_product (created_at, name, description, price, stock_quantity)
VALUES
    (NOW(), 'Coca Cola Zero', 'A Coca-Cola Sem Açúcar é um refrigerante que não contém calorias, produzida com adoçantes que mantêm o sabor de Coca-Cola com 0% de açúcar.', 9.99, 100);

INSERT INTO
    tb_product_category (fk_product, fk_category)
VALUES
    (1, 1);
