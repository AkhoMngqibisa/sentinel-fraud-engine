CREATE TABLE users (
     id                     VARCHAR(50)     PRIMARY KEY
,    registered_country     VARCHAR(5)      NOT NULL
,    created_at             TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_country ON users(registered_country);