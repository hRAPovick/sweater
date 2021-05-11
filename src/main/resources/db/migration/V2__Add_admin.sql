INSERT INTO usr(id, active, password, username)
    VALUES (1, true, 'p', 'admin');

INSERT INTO user_role(user_id, roles)
    VALUES (1, 'USER'), (1, 'ADMIN');