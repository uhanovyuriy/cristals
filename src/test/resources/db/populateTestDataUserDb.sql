INSERT INTO subscriptions(user_id, subscribe_user_id)
VALUES (1, 2), (1, 3), (2, 1);

INSERT INTO users (name, login, password, email, created, last_login_time, enabled, score)
VALUES ('test', 'test@mail.ru', 'password1', 'test@mail.ru',
        '2021-06-08 12:30:00', '2021-06-08 13:00:00', true, 0),
       ('test2', 'test2@mail.ru', 'password2', 'test2@mail.ru',
        '2021-06-09', '2021-06-09', true, 10);