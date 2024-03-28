CREATE USER IF NOT EXISTS 'myuser'@'%' IDENTIFIED BY 'secret';
GRANT SELECT, INSERT, UPDATE, DELETE ON pelican_dev_db.* TO 'myuser'@'%';
GRANT ALTER, CREATE, DROP ON pelican_dev_db.* TO 'myuser'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `pelican_dev_db`;

USE `pelican_dev_db`;

CREATE TABLE IF NOT EXISTS `post` (
	`id` integer not null,
	`user_id` integer not null,
	`title` varchar(255) default '',
	`body` text,
	PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

