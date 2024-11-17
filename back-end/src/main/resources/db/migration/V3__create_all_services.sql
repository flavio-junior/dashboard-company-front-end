CREATE TABLE IF NOT EXISTS tb_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) CONSTRAINT name_category_unique UNIQUE
);

CREATE TABLE IF NOT EXISTS tb_reservation(
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) CONSTRAINT name_reservation_unique UNIQUE
);

CREATE TABLE IF NOT EXISTS tb_item(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) CONSTRAINT name_item_unique UNIQUE,
    price NUMERIC(10, 2) DEFAULT 0.0
);

CREATE TABLE IF NOT EXISTS tb_food (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(60) NOT NULL CONSTRAINT name_food_unique UNIQUE,
    description VARCHAR(200) NOT NULL CONSTRAINT description_food_unique UNIQUE,
    price NUMERIC(10, 2) DEFAULT 0.0
);

CREATE TABLE IF NOT EXISTS tb_product (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(60) NOT NULL CONSTRAINT name_product_unique UNIQUE,
    description VARCHAR(200) NOT NULL CONSTRAINT description_product_unique UNIQUE,
    price NUMERIC(10, 2) DEFAULT 0.0,
    stock_quantity INT NOT NULL
);

CREATE TABLE tb_food_category (
    fk_food INT REFERENCES tb_food(id) ON DELETE CASCADE,
    fk_category INT REFERENCES tb_category(id) ON DELETE CASCADE,
    PRIMARY KEY (fk_food, fk_category)
);

CREATE TABLE tb_product_category (
    fk_product INT REFERENCES tb_product(id) ON DELETE CASCADE,
    fk_category INT REFERENCES tb_category(id) ON DELETE CASCADE,
    PRIMARY KEY (fk_product, fk_category)
);
