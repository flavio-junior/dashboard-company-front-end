CREATE TABLE IF NOT EXISTS tb_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) CONSTRAINT name_category_unique UNIQUE
);

CREATE TABLE IF NOT EXISTS tb_product (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(255) NOT NULL CONSTRAINT name_product_unique UNIQUE,
    description TEXT,
    price NUMERIC(10, 2) DEFAULT 0.0,
    stock_quantity INT NOT NULL
);

CREATE TABLE tb_product_category (
    fk_product INT REFERENCES tb_product(id) ON DELETE CASCADE,
    fk_category INT REFERENCES tb_category(id) ON DELETE CASCADE,
    PRIMARY KEY (fk_product, fk_category)
);
