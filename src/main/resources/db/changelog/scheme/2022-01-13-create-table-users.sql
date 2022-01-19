CREATE TABLE users
(
    id              INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    name            VARCHAR(50)                       NOT NULL,
    login           VARCHAR(50)                       NOT NULL,
    password        VARCHAR(200)                      NOT NULL,
    email           VARCHAR(50)                       NOT NULL,
    created         TIMESTAMP           DEFAULT now() NOT NULL,
    last_login_time TIMESTAMP,
    enabled         BOOLEAN             DEFAULT TRUE  NOT NULL,
    score           INTEGER             DEFAULT 0     NOT NULL
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);