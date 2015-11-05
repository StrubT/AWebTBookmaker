-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 05, 2015 at 03:01 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `bookmaker`
--

-- --------------------------------------------------------

CREATE DATABASE `bookmaker` DEFAULT CHARSET=utf8;

GRANT USAGE ON *.* TO 'bookmaker'@'localhost' IDENTIFIED BY PASSWORD '*85A1E8C55B6DA70103A11FD56A6AC9ECFF4C8E65'; -- password: bookmaker
GRANT ALL PRIVILEGES ON `bookmaker`.* TO 'bookmaker'@'localhost' WITH GRANT OPTION;

USE `bookmaker`;
DROP TABLE IF EXISTS `bets`;
CREATE TABLE `bets` (
`id` int(11) NOT NULL,
  `game` int(11) NOT NULL,
  `type` char(3) NOT NULL,
  `odds` decimal(10,3) NOT NULL,
  `occurred` bit(1) DEFAULT NULL,
  `team` varchar(10) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `goals` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bets`
--

INSERT INTO `bets` (`id`, `game`, `type`, `odds`, `occurred`, `team`, `time`, `goals`) VALUES
(1, 1, 'LDT', '3.000', NULL, 'SUI', '00:45:00', NULL),
(3, 1, 'LDT', '1.300', NULL, 'ESP', '00:45:00', NULL),
(4, 1, 'EVT', '1.500', NULL, NULL, '00:45:00', NULL),
(5, 1, 'LDT', '4.000', NULL, 'SUI', '01:30:00', NULL),
(6, 1, 'LDG', '6.000', NULL, 'SUI', '01:30:00', 3),
(9, 1, 'LDT', '1.200', NULL, 'ESP', '01:30:00', NULL),
(10, 1, 'LDG', '2.000', NULL, 'ESP', '01:30:00', 3),
(11, 1, 'EVT', '1.100', NULL, NULL, '01:30:00', NULL),
(12, 1, 'WIN', '5.000', NULL, 'SUI', NULL, NULL),
(13, 1, 'WIN', '1.100', NULL, 'ESP', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `bettypes`
--

DROP TABLE IF EXISTS `bettypes`;
CREATE TABLE `bettypes` (
  `code` char(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bettypes`
--

INSERT INTO `bettypes` (`code`) VALUES
('EVN'),
('EVT'),
('LDG'),
('LDT'),
('WIN');

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
CREATE TABLE `games` (
`id` int(11) NOT NULL,
  `team1` varchar(10) NOT NULL,
  `team2` varchar(10) NOT NULL,
  `starttime` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`id`, `team1`, `team2`, `starttime`) VALUES
(1, 'SUI', 'ESP', '2015-11-05 20:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
CREATE TABLE `teams` (
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
CREATE TABLE `users` (
`id` int(11) NOT NULL,
  `login` varchar(25) NOT NULL,
  `password` binary(16) NOT NULL,
  `manager` bit(1) NOT NULL DEFAULT b'0',
  `locale` varchar(10) NOT NULL,
  `balance` decimal(10,3) NOT NULL DEFAULT '0',
  `cardnumber` char(16) DEFAULT NULL,
  `cardexpiration` date DEFAULT NULL,
  `cardcode` char(3) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `manager`, `locale`, `balance`, `cardnumber`, `cardexpiration`, `cardcode`) VALUES
(1, 'strut1', 0x69384e2f74dcbf163ca1a2f7ba12fb9b, b'1', 'en', '0', NULL, NULL, NULL),
(2, 'touwm1', 0x69384e2f74dcbf163ca1a2f7ba12fb9b, b'1', 'de', '0', NULL, NULL, NULL),
(4, 'player', 0x6f8548ca26a842f4e08fb2257bce5a4d, b'0', 'de', '0', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users_bets`
--

DROP TABLE IF EXISTS `users_bets`;
CREATE TABLE `users_bets` (
  `user` int(11) NOT NULL,
  `bet` int(11) NOT NULL,
  `stake` decimal(10,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bets`
--
ALTER TABLE `bets`
 ADD PRIMARY KEY (`id`), ADD KEY `type` (`type`), ADD KEY `game` (`game`), ADD KEY `team` (`team`);

--
-- Indexes for table `bettypes`
--
ALTER TABLE `bettypes`
 ADD PRIMARY KEY (`code`);

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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `bets`
--
ALTER TABLE `bets`
ADD CONSTRAINT `bets_ibfk_1` FOREIGN KEY (`game`) REFERENCES `games` (`id`),
ADD CONSTRAINT `bets_ibfk_2` FOREIGN KEY (`type`) REFERENCES `bettypes` (`code`),
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
