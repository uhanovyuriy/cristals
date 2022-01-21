CREATE TABLE subscriptions
(
    user_id           INTEGER NOT NULL,
    subscribe_user_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, subscribe_user_id)
);