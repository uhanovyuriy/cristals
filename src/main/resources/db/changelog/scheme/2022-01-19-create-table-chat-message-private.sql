CREATE TYPE message_statuses AS ENUM ('RECEIVED', 'DELIVERED');

CREATE TABLE chat_message_private
(
    id           INTEGER PRIMARY KEY DEFAULT NEXTVAL('global_seq'),
    sender_id    INTEGER                           NOT NULL,
    sender_name  VARCHAR(50)                       NOT NULL,
    recipient_id INTEGER                           NOT NULL,
    message      VARCHAR(4096)                     NOT NULL,
    created      TIMESTAMP           DEFAULT now() NOT NULL,
    expire_time  TIMESTAMP                         NOT NULL,
    status       message_statuses                  NOT NULL
);

CREATE UNIQUE INDEX sender_id_privat_unique_idx ON chat_message_private (sender_id);
CREATE UNIQUE INDEX recipient_id_unique_idx ON chat_message_private (recipient_id);