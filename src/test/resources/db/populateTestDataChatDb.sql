INSERT INTO chat_message_private (sender_id, sender_name, recipient_id, message, created, expire_time, status)
VALUES (1, 'test', 2, 'private message 1', '2022-01-08 12:30:00', '2022-01-08 13:30:00', 'DELIVERED');

INSERT INTO chat_message_private (sender_id, sender_name, recipient_id, message, created, expire_time, status)
VALUES (1, 'test', 2, 'private message 2', '2022-01-09 12:40:00', '2022-01-09 13:40:00', 'RECEIVED');

INSERT INTO chat_message_private (sender_id, sender_name, recipient_id, message, created, expire_time, status)
VALUES (2, 'test2', 1, 'private message 3', '2022-01-09 12:45:00', '2022-01-09 13:45:00', 'RECEIVED');

INSERT INTO chat_message_public (sender_id, sender_name, locale_id, message, created, expire_time)
VALUES (1, 'test', 'ruRU', 'public message 1', '2022-01-08 12:35:00', '2022-01-08 13:35:00');

INSERT INTO chat_message_public (sender_id, sender_name, locale_id, message, created, expire_time)
VALUES (1, 'test', 'ruRU', 'public message 2', '2022-01-08 12:36:00', '2022-01-08 13:36:00');

INSERT INTO chat_message_public (sender_id, sender_name, locale_id, message, created, expire_time)
VALUES (1, 'test', 'ruRU', 'public message 3', '2022-01-08 12:37:00', '2022-01-08 13:37:00');

INSERT INTO chat_message_public (sender_id, sender_name, locale_id, message, created, expire_time)
VALUES (1, 'test', 'ruRU', 'public message 4', '2022-01-08 12:38:00', '2022-01-08 13:38:00');

INSERT INTO chat_message_public (sender_id, sender_name, locale_id, message, created, expire_time)
VALUES (2, 'test2', 'enEN', 'public message 5', '2022-01-08 13:35:00', '2022-01-08 14:35:00');