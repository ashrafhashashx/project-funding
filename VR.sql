-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 13. Aug 2015 um 23:04
-- Server Version: 5.5.44-0ubuntu0.14.04.1
-- PHP-Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `swt`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Booking`
--

CREATE TABLE IF NOT EXISTS `Booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `arrivalTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `departureTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `paid` tinyint(1) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `price` double NOT NULL,
  `hid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Daten für Tabelle `Booking`
--

INSERT INTO `Booking` (`id`, `creationDate`, `arrivalTime`, `departureTime`, `paid`, `name`, `email`, `price`, `hid`) VALUES
(1, '2015-08-13 21:02:55', '2015-08-18 22:00:00', '2015-08-24 22:00:00', 0, 'Smith', 'smith@smith.de', 144, 14);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `HolidayOffer`
--

CREATE TABLE IF NOT EXISTS `HolidayOffer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `street` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `town` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `capacity` int(11) NOT NULL,
  `fee` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Daten für Tabelle `HolidayOffer`
--

INSERT INTO `HolidayOffer` (`id`, `startTime`, `endTime`, `street`, `town`, `capacity`, `fee`) VALUES
(11, '2015-04-30 22:00:00', '2015-05-30 22:00:00', 'Kokostr. 18', '', 8, 45.9),
(12, '2015-06-08 22:00:00', '2015-07-08 22:00:00', 'Peterstr. 17', '', 5, 23.5),
(13, '2015-06-08 22:00:00', '2015-06-29 22:00:00', 'Goethestr. 18', '', 4, 45),
(14, '2015-08-12 22:00:00', '2015-08-30 22:00:00', 'MÃ¼llerstr. 5', 'Duisburg', 6, 24);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
