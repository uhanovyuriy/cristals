DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS levels;
DROP TABLE IF EXISTS units;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id            INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    name          VARCHAR(50)                       NOT NULL,
    login         VARCHAR(50)                       NOT NULL,
    password      VARCHAR(50)                       NOT NULL,
    email         VARCHAR(50)                       NOT NULL,
    created       TIMESTAMP           DEFAULT now() NOT NULL,
    lastLoginTime TIMESTAMP                         NOT NULL,
    enabled       BOOLEAN             DEFAULT TRUE  NOT NULL,
    melee         INTEGER             DEFAULT 0     NOT NULL,
    melanges      INTEGER             DEFAULT 0     NOT NULL,
    solitaires    INTEGER             DEFAULT 0     NOT NULL
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TYPE e_unit_type AS ENUM (
    'Crystal',
    'PartWall'
    );
CREATE TABLE units
(
    id        INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    name      VARCHAR(50) NOT NULL,
    unit_type e_unit_type NOT NULL
);

CREATE TABLE levels
(
    id     INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    name   VARCHAR(50)                   NOT NULL,
    height INTEGER             DEFAULT 0 NOT NULL,
    weight INTEGER             DEFAULT 0 NOT NULL
);