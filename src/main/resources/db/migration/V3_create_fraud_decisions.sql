CREATE TABLE fraud_decision (
    id                  VARCHAR(36)     PRIMARY KEY
,   transaction_id      VARCHAR(255)
,   fraud_score         INT
,   rule_code           VARCHAR(50)     NOT NULL,
,   flagged             BOOLEAN
,   created_at          DATETIME
,   FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);

CREATE INDEX idx_audit_tx ON fraud_audit_log(transaction_id);