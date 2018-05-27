INSERT INTO user (username, password, company_id, activated) VALUES
  ('pera', 'b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1', 1, TRUE);
INSERT INTO user (username, password, company_id, activated)
VALUES ('marko', '52ed4ba2d6dadf0d61f2be04ed1b48e883d53d02279b63df5eef8a75930680cbebf1cd7dd157a29c', 1, TRUE);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_UPLOADER');

INSERT INTO user_authority (username, authority) VALUES ('pera', 'ROLE_ADMIN');
# INSERT INTO user_authority (username, authority) VALUES ('pera', 'ROLE_USER');
# INSERT INTO user_authority (username, authority) VALUES ('pera', 'ROLE_UPLOADER');
INSERT INTO user_authority (username, authority) VALUES ('marko', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('marko', 'ROLE_UPLOADER');