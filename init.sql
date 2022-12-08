
CREATE TABLE `user` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(200) NOT NULL,
	`password` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
	`id` INT(11) NOT NULL,
	`name` VARCHAR(50) NOT NULL
);

CREATE TABLE `user_role` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`rid` INT(11) NOT NULL,
	`uid` BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
);

INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'ADMIN'),
	(2, 'USER');

-- admin:admin123
INSERT INTO `user` (`username`, `password`) VALUES
	('admin', '$2a$10$z4pCy1srSmQ94RuT.DUUmepjbFsiU.qNspYCzKkgyD.noAn4avXnS');

INSERT INTO `user_role` (`rid`, `uid`) VALUES
	(1, 1);
