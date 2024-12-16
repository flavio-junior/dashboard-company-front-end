CREATE TABLE IF NOT EXISTS tb_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(60)
);

CREATE TABLE tb_user_category (
    fk_user INT REFERENCES tb_user(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_user, fk_category)
);

CREATE TABLE IF NOT EXISTS tb_reservation(
    id SERIAL PRIMARY KEY,
    name VARCHAR(60)
);

CREATE TABLE tb_user_reservation (
    fk_user INT REFERENCES tb_user(id),
    fk_reservation INT REFERENCES tb_reservation(id),
    PRIMARY KEY (fk_user, fk_reservation)
);

CREATE TABLE IF NOT EXISTS tb_item(
    id SERIAL PRIMARY KEY,
    name VARCHAR(60),
    price NUMERIC(10, 2) DEFAULT 0.0
);

CREATE TABLE tb_user_item (
    fk_user INT REFERENCES tb_user(id),
    fk_item INT REFERENCES tb_item(id),
    PRIMARY KEY (fk_user, fk_item)
);

CREATE TABLE IF NOT EXISTS tb_food (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(60) NOT NULL,
    price NUMERIC(10, 2) DEFAULT 0.0
);

CREATE TABLE tb_user_food (
    fk_user INT REFERENCES tb_user(id),
    fk_food INT REFERENCES tb_food(id),
    PRIMARY KEY (fk_user, fk_food)
);

CREATE TABLE IF NOT EXISTS tb_product (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(60) NOT NULL,
    price NUMERIC(10, 2) DEFAULT 0.0,
    stock_quantity INT NOT NULL
);

CREATE TABLE tb_user_product (
    fk_user INT REFERENCES tb_user(id),
    fk_product INT REFERENCES tb_product(id),
    PRIMARY KEY (fk_user, fk_product)
);

CREATE TABLE tb_food_category (
    fk_food INT REFERENCES tb_food(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_food, fk_category)
);

CREATE TABLE tb_product_category (
    fk_product INT REFERENCES tb_product(id),
    fk_category INT REFERENCES tb_category(id),
    PRIMARY KEY (fk_product, fk_category)
);

CREATE TABLE IF NOT EXISTS tb_order (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    type varchar(30) check (type in ('DELIVERY', 'ORDER', 'RESERVATION')),
    status varchar(30) check (status in ('OPEN', 'CLOSED')),
    quantity INT NOT NULL,
    total NUMERIC(10, 2) DEFAULT 0.0
);

CREATE TABLE tb_user_order (
    fk_order INT REFERENCES tb_order(id),
    fk_user INT REFERENCES tb_user(id),
    PRIMARY KEY (fk_order, fk_user)
);

CREATE TABLE tb_order_reservation (
    fk_order INT REFERENCES tb_order(id),
    fk_reservation INT REFERENCES tb_reservation(id),
    PRIMARY KEY (fk_order, fk_reservation)
);

CREATE TABLE IF NOT EXISTS tb_address (
    id SERIAL PRIMARY KEY,
    status varchar(30) check (status in ('PENDING_DELIVERY', 'SENDING', 'DELIVERED')),
    street VARCHAR(40) NOT NULL,
    number INT NULL,
    district VARCHAR(30) NOT NULL,
    complement VARCHAR(100) NOT NULL
);

CREATE TABLE tb_order_address (
    fk_order INT REFERENCES tb_order(id),
    fk_address INT REFERENCES tb_address(id),
    PRIMARY KEY (fk_order, fk_address)
);

CREATE TABLE IF NOT EXISTS tb_object (
    id SERIAL PRIMARY KEY,
    identifier INT NULL,
    type varchar(30) check (type in ('FOOD', 'PRODUCT', 'ITEM')),
    name VARCHAR(60) NOT NULL,
    price NUMERIC(10, 2) DEFAULT 0.0,
    quantity INT NOT NULL,
    total NUMERIC(10, 2) DEFAULT 0.0,
    status varchar(30) check (status in ('PENDING', 'DELIVERED'))
);

CREATE TABLE tb_order_object (
    fk_order INT REFERENCES tb_order(id),
    fk_object INT REFERENCES tb_object(id),
    PRIMARY KEY (fk_order, fk_object)
);

CREATE TABLE IF NOT EXISTS tb_payment (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    type varchar(30) check (type in ('CREDIT_CAD', 'DEBIT_CAD', 'MONEY', 'PIX')),
    total NUMERIC(10, 2) DEFAULT 0.0
);

CREATE TABLE tb_order_payment (
    fk_order INT REFERENCES tb_order(id),
    fk_payment INT REFERENCES tb_payment(id),
    PRIMARY KEY (fk_order, fk_payment)
);

CREATE TABLE tb_checkout_details (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    total NUMERIC(10, 2) DEFAULT 0.0
);
