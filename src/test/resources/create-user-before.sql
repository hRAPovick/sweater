DELETE FROM user_role;
DELETE FROM usr;

-- pass = 'p'

INSERT INTO usr(id, active, password, username) VALUES
(1, true, '$2a$08$/3c9w77Idu6u6xd5te1U3OZELcGtt04MGwRzL.HpjYUHUc6VEub5W', 'admin'),
(2, true, '$2a$08$/3c9w77Idu6u6xd5te1U3OZELcGtt04MGwRzL.HpjYUHUc6VEub5W', 'user');

INSERT INTO user_role(user_id, roles) VALUES
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');