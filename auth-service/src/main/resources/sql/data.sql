INSERT INTO user (username, password, company_id, activated) VALUES
  ('admin', 'b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1', 1, TRUE);
INSERT INTO user (username, password, company_id, activated)
VALUES ('user', 'b173c95a721cd9dda0329d182fe618ea80edc091b88344fe3f602eab8020c9832a879a1a3c596857', 1, TRUE);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_UPLOADER');

INSERT INTO user_authority (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_authority (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('admin', 'ROLE_UPLOADER');
INSERT INTO user_authority (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('user', 'ROLE_UPLOADER');