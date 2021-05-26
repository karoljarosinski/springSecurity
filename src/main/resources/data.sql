INSERT INTO user (username, password)
VALUES
       ('admin', '{noop}asdf'),
       ('karol', '{noop}asdf');

INSERT INTO user_role (user_id, role)
VALUES (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');