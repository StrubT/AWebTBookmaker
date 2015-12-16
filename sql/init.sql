-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2015 at 01:07 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `bookmaker`
--
CREATE DATABASE IF NOT EXISTS `bookmaker` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

GRANT USAGE ON *.* TO 'bookmaker'@'localhost' IDENTIFIED BY PASSWORD '*85A1E8C55B6DA70103A11FD56A6AC9ECFF4C8E65'; -- password: bookmaker
GRANT ALL PRIVILEGES ON `bookmaker`.* TO 'bookmaker'@'localhost' WITH GRANT OPTION;

USE `bookmaker`;

-- --------------------------------------------------------

--
-- Table structure for table `bets`
--

DROP TABLE IF EXISTS `bets`;
CREATE TABLE IF NOT EXISTS `bets` (
`id` int(11) NOT NULL,
  `game` int(11) NOT NULL,
  `type` char(3) NOT NULL,
  `odds` decimal(10,3) NOT NULL,
  `occurred` bit(1) DEFAULT NULL,
  `team` varchar(10) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `goals` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bets`
--

INSERT INTO `bets` (`id`, `game`, `type`, `odds`, `occurred`, `team`, `time`, `goals`) VALUES
(1, 1, 'LDT', '3.000', NULL, 'SUI', '00:45:00', NULL),
(2, 1, 'LDT', '1.300', NULL, 'ESP', '00:45:00', NULL),
(3, 1, 'EVT', '1.500', NULL, NULL, '00:45:00', NULL),
(4, 1, 'LDT', '4.000', NULL, 'SUI', '01:30:00', NULL),
(5, 1, 'LDG', '6.000', NULL, 'SUI', '01:30:00', 4),
(6, 1, 'LDT', '1.200', NULL, 'ESP', '01:30:00', NULL),
(7, 1, 'LDG', '2.000', NULL, 'ESP', '01:30:00', 4),
(8, 1, 'EVT', '1.100', NULL, NULL, '01:30:00', NULL),
(9, 1, 'WIN', '5.000', NULL, 'SUI', NULL, NULL),
(10, 1, 'WIN', '1.100', NULL, 'ESP', NULL, NULL),
(11, 2, 'KCK', '1.300', NULL, 'SCO', NULL, NULL),
(12, 2, 'KCK', '2.100', NULL, 'ENG', NULL, NULL),
(13, 2, 'FGL', '3.700', NULL, 'SCO', NULL, NULL),
(14, 2, 'FGL', '1.125', NULL, 'ENG', NULL, NULL),
(15, 2, 'FTW', '1.800', NULL, 'SCO', NULL, NULL),
(16, 2, 'FTW', '3.600', NULL, 'ENG', NULL, NULL),
(17, 2, 'LDT', '2.800', NULL, 'SCO', '00:45:00', NULL),
(18, 2, 'LDG', '3.100', NULL, 'SCO', '00:45:00', 2),
(19, 2, 'LDG', '4.600', NULL, 'SCO', '00:45:00', 4),
(20, 2, 'LDT', '1.200', NULL, 'ENG', '00:45:00', NULL),
(21, 2, 'LDG', '1.900', NULL, 'ENG', '00:45:00', 2),
(22, 2, 'LDG', '2.700', NULL, 'ENG', '00:45:00', 4),
(23, 2, 'LDG', '3.900', NULL, 'ENG', '00:45:00', 6),
(24, 2, 'GLT', '1.200', NULL, NULL, '00:45:00', 2),
(25, 2, 'GLT', '1.800', NULL, NULL, '00:45:00', 4),
(26, 2, 'EVT', '2.400', NULL, NULL, '00:45:00', NULL),
(27, 2, 'WIN', '3.900', NULL, 'SCO', NULL, NULL),
(28, 2, 'WIN', '1.700', NULL, 'ENG', NULL, NULL),
(29, 2, 'WNG', '2.500', NULL, 'ENG', NULL, 3),
(30, 2, 'EVN', '6.800', NULL, NULL, NULL, NULL),
(31, 2, 'GLS', '2.300', NULL, NULL, NULL, 4),
(32, 2, 'GLS', '4.300', NULL, NULL, NULL, 7),
(33, 2, 'CRD', '2.200', NULL, NULL, NULL, 5),
(34, 2, 'CRD', '6.800', NULL, NULL, NULL, 10),
(35, 2, 'CRN', '9.700', NULL, NULL, NULL, 15),
(36, 3, 'KCK', '1.700', b'0', 'POR', NULL, NULL),
(37, 3, 'KCK', '1.400', b'1', 'WAL', NULL, NULL),
(38, 3, 'LDT', '1.600', b'0', 'POR', '00:30:00', NULL),
(39, 3, 'LDT', '2.400', b'1', 'WAL', '00:30:00', NULL),
(40, 3, 'LDT', '1.800', b'1', 'POR', '00:45:00', NULL),
(41, 3, 'LDT', '1.700', b'0', 'WAL', '00:45:00', NULL),
(42, 3, 'LDG', '2.600', b'0', 'WAL', '00:45:00', 2),
(43, 3, 'EVT', '4.600', b'0', NULL, '00:45:00', NULL),
(44, 3, 'LDT', '2.100', b'1', 'POR', '01:00:00', NULL),
(45, 3, 'LDG', '3.400', b'0', 'POR', '01:00:00', 4),
(46, 3, 'LDT', '1.600', b'0', 'WAL', '01:30:00', NULL),
(47, 3, 'LDG', '2.900', b'0', 'WAL', '01:30:00', 5),
(48, 3, 'WIN', '1.200', b'1', 'POR', NULL, NULL),
(49, 3, 'WIN', '1.900', b'0', 'WAL', NULL, NULL),
(50, 3, 'EVN', '3.500', b'0', NULL, NULL, NULL),
(51, 4, 'FGL', '1.300', b'0', 'GER', NULL, NULL),
(52, 4, 'FGL', '1.900', b'1', 'LIE', NULL, NULL),
(53, 4, 'LDT', '1.200', b'1', 'GER', '00:45:00', NULL),
(54, 4, 'LDG', '1.800', b'0', 'GER', '00:45:00', 3),
(55, 4, 'LDG', '2.700', b'0', 'GER', '00:45:00', 6),
(56, 4, 'LDT', '2.400', b'0', 'LIE', '00:45:00', NULL),
(57, 4, 'WIN', '1.100', b'1', 'GER', NULL, NULL),
(58, 4, 'WNG', '1.600', b'1', 'GER', NULL, 3),
(59, 4, 'WNG', '2.300', b'0', 'GER', NULL, 6),
(60, 4, 'WIN', '3.800', b'0', 'LIE', NULL, NULL),
(61, 5, 'KCK', '1.800', NULL, 'ITA', NULL, NULL),
(62, 5, 'KCK', '1.200', NULL, 'BEL', NULL, NULL),
(63, 5, 'FGL', '1.300', NULL, 'ITA', NULL, NULL),
(64, 5, 'FGL', '1.800', NULL, 'BEL', NULL, NULL),
(65, 5, 'FTW', '1.700', NULL, 'ITA', NULL, NULL),
(66, 5, 'FTW', '2.100', NULL, 'BEL', NULL, NULL),
(67, 5, 'LDT', '1.500', NULL, 'ITA', '00:30:00', NULL),
(68, 5, 'LDT', '1.900', NULL, 'BEL', '00:30:00', NULL),
(69, 5, 'LDT', '1.400', NULL, 'ITA', '00:45:00', NULL),
(70, 5, 'LDT', '2.300', NULL, 'BEL', '00:45:00', NULL),
(71, 5, 'LDT', '1.300', NULL, 'ITA', '01:00:00', NULL),
(72, 5, 'LDT', '2.700', NULL, 'BEL', '01:00:00', NULL),
(73, 5, 'LDT', '1.200', NULL, 'ITA', '01:30:00', NULL),
(74, 5, 'LDT', '3.100', NULL, 'BEL', '01:30:00', NULL),
(75, 5, 'WIN', '1.100', NULL, 'ITA', NULL, NULL),
(76, 5, 'WIN', '3.800', NULL, 'BEL', NULL, NULL),
(77, 5, 'EVN', '4.500', NULL, NULL, NULL, NULL),
(78, 5, 'CRD', '1.800', NULL, NULL, NULL, 3),
(79, 5, 'CRN', '1.600', NULL, NULL, NULL, 5),
(80, 5, 'CRN', '2.700', NULL, NULL, NULL, 10);

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
CREATE TABLE IF NOT EXISTS `games` (
`id` int(11) NOT NULL,
  `team1` varchar(10) NOT NULL,
  `team2` varchar(10) NOT NULL,
  `starttime` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`id`, `team1`, `team2`, `starttime`) VALUES
(4, 'GER', 'LIE', '2015-11-20 19:30:00'),
(5, 'ITA', 'BEL', '2015-12-16 19:30:00'),
(3, 'POR', 'WAL', '2015-12-01 19:30:00'),
(2, 'SCO', 'ENG', '2015-12-06 19:30:00'),
(1, 'SUI', 'ESP', '2016-01-20 19:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
CREATE TABLE IF NOT EXISTS `teams` (
  `code` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`code`) VALUES
('AFG'),
('AIA'),
('ALB'),
('ALG'),
('AND'),
('ANG'),
('ARG'),
('ARM'),
('ARU'),
('ASA'),
('ATG'),
('AUS'),
('AUT'),
('AZE'),
('BAH'),
('BAN'),
('BDI'),
('BEL'),
('BEN'),
('BER'),
('BFA'),
('BHR'),
('BHU'),
('BIH'),
('BLR'),
('BLZ'),
('BOE'),
('BOL'),
('BOT'),
('BRA'),
('BRB'),
('BRU'),
('BUL'),
('CAM'),
('CAN'),
('CAY'),
('CGO'),
('CHA'),
('CHI'),
('CHN'),
('CIV'),
('CMR'),
('COD'),
('COK'),
('COL'),
('COM'),
('CPV'),
('CRC'),
('CRO'),
('CTA'),
('CUB'),
('CUW'),
('CYP'),
('CZE'),
('DEN'),
('DJI'),
('DMA'),
('DOM'),
('ECU'),
('EGY'),
('ENG'),
('EQG'),
('ERI'),
('ESP'),
('EST'),
('ETH'),
('FIJ'),
('FIN'),
('FRA'),
('FRO'),
('FSM'),
('GAB'),
('GAM'),
('GBR'),
('GEO'),
('GER'),
('GHA'),
('GIB'),
('GNB'),
('GPE'),
('GRE'),
('GRN'),
('GUA'),
('GUI'),
('GUM'),
('GUY'),
('GYF'),
('HAI'),
('HKG'),
('HON'),
('HUN'),
('IDN'),
('IND'),
('IRL'),
('IRN'),
('IRQ'),
('ISL'),
('ISR'),
('ITA'),
('JAM'),
('JOR'),
('JPN'),
('KAZ'),
('KEN'),
('KGZ'),
('KIR'),
('KOR'),
('KOS'),
('KSA'),
('KUW'),
('LAO'),
('LBR'),
('LBY'),
('LCA'),
('LES'),
('LIB'),
('LIE'),
('LTU'),
('LUX'),
('LVA'),
('MAC'),
('MAD'),
('MAR'),
('MAS'),
('MDA'),
('MDV'),
('MEX'),
('MKD'),
('MLI'),
('MLT'),
('MNE'),
('MNG'),
('MOZ'),
('MRI'),
('MSR'),
('MTN'),
('MTQ'),
('MWI'),
('MYA'),
('NAM'),
('NCA'),
('NCL'),
('NED'),
('NEP'),
('NGA'),
('NIG'),
('NIR'),
('NIU'),
('NMI'),
('NOR'),
('NZL'),
('OMA'),
('PAK'),
('PAN'),
('PAR'),
('PER'),
('PHI'),
('PLE'),
('PLW'),
('PNG'),
('POL'),
('POR'),
('PRK'),
('PUR'),
('QAT'),
('REU'),
('ROU'),
('RSA'),
('RUS'),
('RWA'),
('SAM'),
('SCO'),
('SDN'),
('SEN'),
('SEY'),
('SIN'),
('SKN'),
('SLE'),
('SLV'),
('SMR'),
('SMT'),
('SOL'),
('SOM'),
('SRB'),
('SRI'),
('SSD'),
('STP'),
('SUI'),
('SUR'),
('SVK'),
('SVN'),
('SWE'),
('SWZ'),
('SXM'),
('SYR'),
('TAH'),
('TAN'),
('TCA'),
('TGA'),
('THA'),
('TJK'),
('TKM'),
('TLS'),
('TOG'),
('TPE'),
('TRI'),
('TUN'),
('TUR'),
('TUV'),
('UAE'),
('UGA'),
('UKR'),
('URU'),
('USA'),
('UZB'),
('VAN'),
('VEN'),
('VGB'),
('VIE'),
('VIN'),
('VIR'),
('WAL'),
('YEM'),
('ZAM'),
('ZAN'),
('ZIM');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
`id` int(11) NOT NULL,
  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `login` varchar(25) NOT NULL,
  `password` binary(16) NOT NULL,
  `manager` bit(1) NOT NULL DEFAULT b'0',
  `locale` varchar(10) NOT NULL,
  `timezone` varchar(25) NOT NULL,
  `balance` decimal(10,3) NOT NULL DEFAULT '0.000'
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `version`, `login`, `password`, `manager`, `locale`, `timezone`, `balance`) VALUES
(5, '2015-12-16 08:43:40', 'strut1', 0x5026ccd9d129de23ebccbcb96ce3565d, b'1', 'en_GB', 'Europe/Zurich', '0.000'), -- password: bookmaker
(6, '2015-12-15 14:03:27', 'touwm1', 0x20ead0f27dbfa657f8a6a24e445ae7b0, b'1', 'de_CH', 'Europe/Zurich', '0.000'), -- password: bookmaker
(7, '2015-12-15 14:02:10', 'player', 0x5c2809d7a6852462c344f4e5cc8721b1, b'0', 'en_GB', 'Europe/Zurich', '0.000'), -- password: player
(8, '2015-12-15 14:03:25', 'manager', 0x84316afc59c58d7747701241c9769d48, b'1', 'en_GB', 'Europe/Zurich', '0.000'), -- password: manager
(9, '2015-12-15 14:05:09', 'player0', 0xc6d999de4fb90c98ec2ecb221af098b7, b'0', 'en_GB', 'Europe/Zurich', '0.000'), -- password: player
(10, '2015-12-15 14:05:15', 'player1', 0x3c91d0a1b89ef710ad720f2429a80559, b'0', 'en_GB', 'Europe/Zurich', '0.000'), -- password: player
(11, '2015-12-16 12:05:46', 'player2', 0xa944de2a96e6c4db5e79439f385bb898, b'0', 'en_GB', 'Europe/Zurich', '324.000'), -- password: player
(12, '2015-12-16 12:02:58', 'player3', 0x2ce1f0ecafeed68569010cd5f18b65f8, b'0', 'en_GB', 'Europe/Zurich', '1250.000'), -- password: player
(13, '2015-12-16 12:02:58', 'player4', 0x03dc5bfd2bf1d2f329a1c96bdf407d26, b'0', 'en_GB', 'Europe/Zurich', '8700.000'), -- password: player
(14, '2015-12-15 14:05:43', 'player5', 0xe1c057fbe297479d5d1177d4475c0d13, b'0', 'en_GB', 'Europe/Zurich', '0.000'), -- password: player
(15, '2015-12-15 14:05:50', 'player6', 0x3addb7de1050f16a348e388a79e9ac7d, b'0', 'en_GB', 'Europe/Zurich', '0.000'), -- password: player
(16, '2015-12-16 12:02:57', 'player7', 0xe0ce656e38d4cfc7e7921e4d0105b1dc, b'0', 'en_GB', 'Europe/Zurich', '475.000'), -- password: player
(17, '2015-12-15 14:06:03', 'player8', 0xbce20e9cd9ae24d998dbc93a27e1ee2f, b'0', 'en_GB', 'Europe/Zurich', '0.000'), -- password: player
(18, '2015-12-15 14:06:10', 'player9', 0xb74cefac604fc4fefae4375825edd741, b'0', 'en_GB', 'Europe/Zurich', '0.000'); -- password: player

-- --------------------------------------------------------

--
-- Table structure for table `users_bets`
--

DROP TABLE IF EXISTS `users_bets`;
CREATE TABLE IF NOT EXISTS `users_bets` (
  `user` int(11) NOT NULL,
  `bet` int(11) NOT NULL,
  `stake` decimal(10,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_bets`
--

INSERT INTO `users_bets` (`user`, `bet`, `stake`) VALUES
(9, 1, '25.000'),
(9, 4, '60.000'),
(9, 5, '15.000'),
(9, 9, '100.000'),
(9, 11, '5.000'),
(9, 13, '10.000'),
(9, 14, '50.000'),
(9, 17, '50.000'),
(9, 20, '30.000'),
(9, 24, '100.000'),
(9, 25, '50.000'),
(9, 27, '100.000'),
(9, 28, '50.000'),
(9, 30, '30.000'),
(9, 31, '250.000'),
(9, 33, '100.000'),
(10, 14, '50.000'),
(10, 16, '50.000'),
(10, 20, '100.000'),
(10, 21, '50.000'),
(10, 22, '30.000'),
(10, 28, '300.000'),
(10, 29, '100.000'),
(10, 30, '50.000'),
(10, 31, '100.000'),
(10, 32, '30.000'),
(10, 33, '100.000'),
(11, 36, '20.000'),
(11, 37, '30.000'),
(11, 38, '20.000'),
(11, 39, '50.000'),
(11, 40, '25.000'),
(11, 41, '10.000'),
(11, 42, '5.000'),
(11, 43, '100.000'),
(11, 44, '50.000'),
(11, 45, '30.000'),
(11, 46, '50.000'),
(11, 47, '20.000'),
(11, 48, '10.000'),
(11, 49, '15.000'),
(11, 50, '5.000'),
(11, 62, '50.000'),
(11, 64, '30.000'),
(11, 66, '50.000'),
(11, 68, '50.000'),
(11, 70, '20.000'),
(11, 72, '75.000'),
(11, 74, '100.000'),
(11, 76, '100.000'),
(11, 78, '20.000'),
(11, 79, '50.000'),
(12, 51, '100.000'),
(12, 53, '250.000'),
(12, 54, '100.000'),
(12, 55, '50.000'),
(12, 57, '500.000'),
(12, 58, '250.000'),
(12, 59, '100.000'),
(13, 57, '5000.000'),
(13, 58, '2000.000'),
(13, 59, '500.000'),
(13, 69, '250.000'),
(13, 71, '500.000'),
(13, 73, '500.000'),
(13, 75, '500.000'),
(13, 79, '100.000'),
(14, 62, '50.000'),
(14, 68, '100.000'),
(14, 70, '100.000'),
(14, 72, '100.000'),
(14, 76, '100.000'),
(15, 1, '50.000'),
(15, 2, '30.000'),
(15, 3, '50.000'),
(15, 4, '100.000'),
(15, 5, '50.000'),
(15, 6, '20.000'),
(15, 7, '10.000'),
(15, 8, '30.000'),
(15, 9, '100.000'),
(15, 10, '50.000'),
(16, 52, '250.000'),
(16, 56, '500.000'),
(16, 60, '500.000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bets`
--
ALTER TABLE `bets`
 ADD PRIMARY KEY (`id`), ADD KEY `type` (`type`), ADD KEY `game` (`game`), ADD KEY `team` (`team`);

--
-- Indexes for table `games`
--
ALTER TABLE `games`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `team1` (`team1`,`team2`,`starttime`), ADD KEY `team2` (`team2`);

--
-- Indexes for table `teams`
--
ALTER TABLE `teams`
 ADD PRIMARY KEY (`code`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `login` (`login`);

--
-- Indexes for table `users_bets`
--
ALTER TABLE `users_bets`
 ADD PRIMARY KEY (`user`,`bet`), ADD KEY `bet` (`bet`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bets`
--
ALTER TABLE `bets`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=81;
--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `bets`
--
ALTER TABLE `bets`
ADD CONSTRAINT `bets_ibfk_1` FOREIGN KEY (`game`) REFERENCES `games` (`id`),
ADD CONSTRAINT `bets_ibfk_3` FOREIGN KEY (`team`) REFERENCES `teams` (`code`);

--
-- Constraints for table `games`
--
ALTER TABLE `games`
ADD CONSTRAINT `games_ibfk_1` FOREIGN KEY (`team1`) REFERENCES `teams` (`code`),
ADD CONSTRAINT `games_ibfk_2` FOREIGN KEY (`team2`) REFERENCES `teams` (`code`);

--
-- Constraints for table `users_bets`
--
ALTER TABLE `users_bets`
ADD CONSTRAINT `users_bets_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`id`),
ADD CONSTRAINT `users_bets_ibfk_2` FOREIGN KEY (`bet`) REFERENCES `bets` (`id`);
