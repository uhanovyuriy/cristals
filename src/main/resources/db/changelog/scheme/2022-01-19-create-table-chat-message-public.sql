CREATE TABLE chat_message_public
(
    id          INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    sender_id   INTEGER                           NOT NULL,
    sender_name VARCHAR(50)                       NOT NULL,
    locale_id   VARCHAR(20)                       NOT NULL,
    message     VARCHAR(4096)                     NOT NULL,
    created     TIMESTAMP           DEFAULT now() NOT NULL,
    expire_time TIMESTAMP                         NOT NULL
);

CREATE UNIQUE INDEX sender_id_public_unique_idx ON chat_message_public (sender_id);
CREATE UNIQUE INDEX locale_id_unique_idx ON chat_message_public (locale_id);