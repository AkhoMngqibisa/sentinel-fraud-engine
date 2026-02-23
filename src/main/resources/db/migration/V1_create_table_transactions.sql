CREATE TABLE transactions (
    id          VARCHAR(36)     PRIMARY KEY
,   user_id     VARCHAR(255)
,   amount      DECIMAL(12,2)
,   currency    VARCHAR(10)
,   merchant    VARCHAR(255)
,   location    VARCHAR(50)
,   timestamp   DATETIME
);