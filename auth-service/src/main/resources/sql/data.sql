INSERT INTO user (username, email, password, activated) VALUES
  ('admin', 'admin@mail.me', 'b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1', TRUE);
INSERT INTO user (username, email, password, activated)
VALUES ('asd', 'asd@mail.me', 'b173c95a721cd9dda0329d182fe618ea80edc091b88344fe3f602eab8020c9832a879a1a3c596857', TRUE);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_UPLOADER');

INSERT INTO user_authority (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_authority (username, authority) VALUES ('asd', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('asd', 'ROLE_UPLOADER');