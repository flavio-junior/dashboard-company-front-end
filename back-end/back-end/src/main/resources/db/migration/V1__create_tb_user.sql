CREATE TABLE IF NOT EXISTS tb_user (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL UNIQUE,
    type_account VARCHAR(10) NOT NULL,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    enabled BOOLEAN DEFAULT TRUE
);
