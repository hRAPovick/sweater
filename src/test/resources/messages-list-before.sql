DELETE FROM message;

INSERT INTO message(id, text, tag, user_id) VALUES
(1, 'red', 'color', 1),
(2, 'orange', 'best_color', 1),
(3, 'yellow', 'best_color', 1),
(4, 'green', 'color', 1),
(5, 'blue', 'color', 1),
(6, 'indigo', 'color', 1),
(7, 'violet', 'color', 1);

ALTER SEQUENCE hibernate_sequence RESTART WITH 10;