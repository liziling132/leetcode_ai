ALTER TABLE recommendation_records
    ADD COLUMN ai_source VARCHAR(10) NOT NULL DEFAULT 'RULE' AFTER reason_text,
    ADD COLUMN ai_model VARCHAR(80) DEFAULT NULL AFTER ai_source;

UPDATE recommendation_records
SET ai_source = 'RULE',
    ai_model = 'rule-mvp-v1'
WHERE ai_source IS NULL OR ai_source = '';
