DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS levels;
DROP TABLE IF EXISTS unit_types;
DROP TABLE IF EXISTS units;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id            INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    name          VARCHAR                           NOT NULL,
    login         VARCHAR                           NOT NULL,
    password      VARCHAR                           NOT NULL,
    email         VARCHAR                           NOT NULL,
    created       TIMESTAMP           DEFAULT now() NOT NULL,
    lastLoginTime TIMESTAMP                         NOT NULL,
    enabled       BOOLEAN             DEFAULT TRUE  NOT NULL,
    reals         INTEGER             DEFAULT 0     NOT NULL
);
CREATE UNIQUE INDEX users_unique_login_idx ON users(login);
CREATE UNIQUE INDEX users_unique_email_idx ON users(email);