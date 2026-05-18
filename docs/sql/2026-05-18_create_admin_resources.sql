CREATE TABLE IF NOT EXISTS admin_resources (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    resource_type ENUM('ARTICLE','VIDEO','PROBLEM') NOT NULL,
    title VARCHAR(200) NOT NULL,
    url VARCHAR(1000) NOT NULL,
    knowledge_points JSON NULL,
    status TINYINT NOT NULL DEFAULT 1,
    created_by BIGINT UNSIGNED NULL,
    updated_by BIGINT UNSIGNED NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_admin_resources_type_status_created_at (resource_type, status, created_at),
    KEY idx_admin_resources_status_created_at (status, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
