CREATE TABLE transactions (
    id                  VARCHAR(36)     PRIMARY KEY
,   transaction_id      VARCHAR(255)
,   fraud_score         INT
,   flagged             BOOLEAN
,   created_at          DATETIME
,   FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);