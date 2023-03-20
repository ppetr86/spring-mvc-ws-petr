DROP DATABASE IF EXISTS photo_app_monolith;
DROP USER IF EXISTS `photo_app_monolith_admin`@`%`;
DROP USER IF EXISTS `photo_app_monolith_user`@`%`;
CREATE DATABASE IF NOT EXISTS photo_app_monolith CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `photo_app_monolith_admin`@`%` IDENTIFIED WITH mysql_native_password BY 'photo_app_monolith_admin';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `photo_app_monolith`.* TO `photo_app_monolith_admin`@`%`;
CREATE USER IF NOT EXISTS `photo_app_monolith_user`@`%` IDENTIFIED WITH mysql_native_password BY 'photo_app_monolith_user';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `photo_app_monolith`.* TO `photo_app_monolith_user`@`%`;
FLUSH PRIVILEGES;