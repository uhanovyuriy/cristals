CREATE TABLE chat_message_private
(
    id           INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    sender_id    INTEGER                           NOT NULL,
    sender_name  VARCHAR(50)                       NOT NULL,
    recipient_id INTEGER                           NOT NULL,
    message      VARCHAR(4096)                     NOT NULL,
    created      TIMESTAMP           DEFAULT now() NOT NULL,
    expire_time  TIMESTAMP                         NOT NULL,
    status       VARCHAR(10)                       NOT NULL
);