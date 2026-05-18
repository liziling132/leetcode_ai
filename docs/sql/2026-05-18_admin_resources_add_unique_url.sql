-- 防重复：建议先执行一次
ALTER TABLE admin_resources
ADD UNIQUE KEY uk_admin_resources_url (url);
