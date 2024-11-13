INSERT INTO tb_user (created_at, name, surname, email, password, type_account, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES (
    NOW(),
    'John',
    'Doe',
    'john.doe@example.com',
    '$2a$10$eSsOh5oFkRlcMqJz71ex6eLJH9wYAvON8wXdwNapVYAgmHBsraGge',
    'USER',
    TRUE,
    TRUE,
    TRUE,
    TRUE
);
