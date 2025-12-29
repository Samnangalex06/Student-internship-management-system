CREATE TABLE internship_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(80) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT NULL,
    description VARCHAR(255) NOT NULL,
    performed_by VARCHAR(120) NOT NULL,
    created_at DATETIME NOT NULL
);

CREATE INDEX idx_internship_log_created_at ON internship_log (created_at);
CREATE INDEX idx_internship_log_action ON internship_log (action);

