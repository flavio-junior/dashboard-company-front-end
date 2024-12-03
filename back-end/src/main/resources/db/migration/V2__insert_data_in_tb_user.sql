INSERT INTO
    tb_user (created_at, name, surname, email, password, type_account, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES
    (DATE_TRUNC('second', NOW()), 'John', 'Doe', 'john.doe@example.com', '$2a$10$eSsOh5oFkRlcMqJz71ex6eLJH9wYAvON8wXdwNapVYAgmHBsraGge', 'ADMIN', true, true, true, true),
    (DATE_TRUNC('second', NOW()), 'Flávio', 'Júnior', 'flaviojunior.work@gmail.com', '$2a$10$MkKZNBzZTRGyVYjQW1mUh.NLO/V1w.w/bL88qUcxcrYsvhFzPU3tG', 'ADMIN', true, true, true, true);
