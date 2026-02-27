CREATE TABLE transactions (
    id              VARCHAR(36)     PRIMARY KEY
,   user_id         VARCHAR(255)
,   amount          DECIMAL(12,2)
,   currency        VARCHAR(10)
,   merchant        VARCHAR(255)
,   location        VARCHAR(50)
,   device_id       VARCHAR(100)
,   flagged         BOOLEAN         DEFAULT FALSE
,   rule_code       VARCHAR(50)
,   country         VARCHAR(5)
,   timestamp       DATETIME
);

CREATE INDEX idx_tx_user ON transactions(user_id);
CREATE INDEX idx_tx_user_amount ON transactions(user_id, amount);
CREATE INDEX idx_tx_created ON transactions(timestamp);
CREATE INDEX idx_tx_flagged ON transactions(flagged);