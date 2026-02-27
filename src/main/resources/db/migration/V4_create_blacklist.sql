CREATE TABLE blacklist (
     id             BIGINT AUTO_INCREMENT           PRIMARY KEY
,    type           ENUM('MERCHANT', 'ACCOUNT')     NOT NULL
,    value          VARCHAR(100)                    NOT NULL
,    created_at     TIMESTAMP                       DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_blacklist_unique ON blacklist(type, value);