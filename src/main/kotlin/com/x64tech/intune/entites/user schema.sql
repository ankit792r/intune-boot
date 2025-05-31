CREATE TABLE users
(
    id       BINARY(16)   NOT NULL,
    email    VARCHAR(60)  NOT NULL,
    password VARCHAR(240) NOT NULL,
    username VARCHAR(100) NOT NULL,
    name     VARCHAR(120) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);