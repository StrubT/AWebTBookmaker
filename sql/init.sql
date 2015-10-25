CREATE DATABASE `bookmaker` DEFAULT CHARSET=utf8;

GRANT USAGE ON *.* TO 'bookmaker'@'localhost' IDENTIFIED BY PASSWORD '*85A1E8C55B6DA70103A11FD56A6AC9ECFF4C8E65';
GRANT ALL PRIVILEGES ON `bookmaker`.* TO 'bookmaker'@'localhost' WITH GRANT OPTION;

USE `bookmaker`;

CREATE TABLE `users` (
`id` int(11) NOT NULL,
  `login` varchar(25) NOT NULL,
  `password` binary(16) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`, `login`, `password`) VALUES
(1, 'strut1', 0x69384e2f74dcbf163ca1a2f7ba12fb9b),
(2, 'touwm1', 0x69384e2f74dcbf163ca1a2f7ba12fb9b);

ALTER TABLE `users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `login` (`login`);

ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
