DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
  username         VARCHAR(50) NOT NULL PRIMARY KEY,
  password         VARCHAR(500),
  activated        BOOLEAN     DEFAULT TRUE,
  company_id       BIGINT(20)
);

CREATE TABLE authority (
  name VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE user_authority (
  username  VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES user (username),
  FOREIGN KEY (authority) REFERENCES authority (name),
  PRIMARY KEY (username, authority)
);

CREATE TABLE oauth_access_token (
  token_id          VARCHAR(256) NOT NULL PRIMARY KEY,
  token             BLOB,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name         VARCHAR(256) DEFAULT NULL,
  client_id         VARCHAR(256) DEFAULT NULL,
  authentication    BLOB,
  refresh_token     VARCHAR(256) DEFAULT NULL
);

CREATE TABLE oauth_refresh_token (
  token_id       VARCHAR(256) NOT NULL PRIMARY KEY,
  token          BLOB,
  authentication BLOB
);