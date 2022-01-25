DROP TABLE IF EXISTS chat_message_private;
DROP TABLE IF EXISTS chat_message_public;

CREATE TABLE chat_message_private
(
    id           SERIAL PRIMARY KEY      NOT NULL,
    sender_id    INTEGER                 NOT NULL,
    sender_name  VARCHAR(50)             NOT NULL,
    recipient_id INTEGER                 NOT NULL,
    message      VARCHAR(4096)           NOT NULL,
    created      TIMESTAMP DEFAULT now() NOT NULL,
    expire_time  TIMESTAMP               NOT NULL,
    status       VARCHAR(10)             NOT NULL
);

CREATE TABLE chat_message_public
(
    id          SERIAL PRIMARY KEY      NOT NULL,
    sender_id   INTEGER                 NOT NULL,
    sender_name VARCHAR(50)             NOT NULL,
    locale_id   VARCHAR(20)             NOT NULL,
    message     VARCHAR(4096)           NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL,
    expire_time TIMESTAMP               NOT NULL
);