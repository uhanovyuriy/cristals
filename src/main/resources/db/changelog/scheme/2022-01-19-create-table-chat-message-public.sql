CREATE TABLE chat_message_public
(
    id          SERIAL PRIMARY KEY,
    sender_id   INTEGER                 NOT NULL,
    sender_name VARCHAR(50)             NOT NULL,
    locale_id   VARCHAR(20)             NOT NULL,
    message     VARCHAR(4096)           NOT NULL,
    created     TIMESTAMP DEFAULT now() NOT NULL,
    expire_time TIMESTAMP               NOT NULL
);