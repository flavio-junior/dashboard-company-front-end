INSERT INTO
    tb_user (created_at, name, surname, email, password, type_account, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES
    (DATE_TRUNC('second', NOW()), 'John', 'Doe', 'john.doe@example.com', '$2a$10$eSsOh5oFkRlcMqJz71ex6eLJH9wYAvON8wXdwNapVYAgmHBsraGge', 'ADMIN', true, true, true, true),
    (DATE_TRUNC('second', NOW()), 'Flávio', 'Júnior', 'flaviojunior.work@gmail.com', 'pbkdf2}0a2e29f40ff6cb94170117ceebc95170bf0468c419d706f38907c7dc8c30846b7199e9a81ad80c62', 'ADMIN', true, true, true, true);
