INSERT INTO users (name, login, password, email, created, last_login_time, enabled, melee, melanges, solitaires, score,
                   record, free_swaps, undo)
VALUES ('test', 'test@mail.ru', '$2a$10$F7bZXdBwZC25MZjNGP97rOvwRs59XzQfprE164kFfI3Y5rrg6Ssuy', 'test@mail.ru',
        '2021-06-08 12:30:00', '2021-06-08 13:00:00', true, 100, 50, 10, 499, 500, 10, 5),
       ('test2', 'test2@mail.ru', 'password', 'test2@mail.ru',
        '2021-06-09', '2021-06-09', true, 10, 5, 2, 280, 300, 5, 2);