CREATE TABLE IF NOT EXISTS `post` (
	`id` integer not null,
	`user_id` integer not null,
	`title` varchar(255) default '',
	`body` text,
	PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;