CREATE DATABASE `bookmaker` DEFAULT CHARSET=utf8;

GRANT USAGE ON *.* TO 'bookmaker'@'localhost' IDENTIFIED BY PASSWORD '*85A1E8C55B6DA70103A11FD56A6AC9ECFF4C8E65'; -- password: bookmaker
GRANT ALL PRIVILEGES ON `bookmaker`.* TO 'bookmaker'@'localhost' WITH GRANT OPTION;

USE `bookmaker`;

CREATE TABLE `users` (
`id` int(11) NOT NULL,
	`login` varchar(25) NOT NULL,
	`password` binary(16) NOT NULL,
	`manager` bit(1) NOT NULL DEFAULT 0,
	`locale` varchar(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`, `login`, `password`, `manager`, `locale`) VALUES
	(1, 'strut1', 0x69384e2f74dcbf163ca1a2f7ba12fb9b, 1, 'en'), -- password: bookmaker
	(2, 'touwm1', 0x69384e2f74dcbf163ca1a2f7ba12fb9b, 1, 'de'),
	(3, 'player', 0x6f8548ca26a842f4e08fb2257bce5a4d, 0, 'de'); -- password: player

ALTER TABLE `users`
	ADD PRIMARY KEY (`id`),
	ADD UNIQUE KEY `login` (`login`);

ALTER TABLE `users`
	MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
