CREATE TABLE IF NOT EXISTS tb_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) CONSTRAINT name_category_unique UNIQUE
);

