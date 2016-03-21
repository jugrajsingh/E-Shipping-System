CREATE DATABASE  IF NOT EXISTS `database` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `database`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: database
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `phone` bigint(11) NOT NULL,
  `DOB` date NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `usertype` varchar(45) NOT NULL,
  `defaultaddress` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES ('picker','12345','picker','picker',9876543210,'1994-07-08','jfhgvhgfvhfh@gmail.com','picker','jhdgjhdfjvjghvhg','male'),('seller','12345','ravi','singh',78965412365,'2004-01-06','ravi@gmail.com','seller','hno12','male');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bidding`
--

DROP TABLE IF EXISTS `bidding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bidding` (
  `bidID` int(11) NOT NULL AUTO_INCREMENT,
  `bidder` varchar(45) DEFAULT NULL,
  `Bidon` int(11) DEFAULT NULL,
  `biddingoffer` float DEFAULT NULL,
  PRIMARY KEY (`bidID`),
  UNIQUE KEY `bidID_UNIQUE` (`bidID`),
  KEY `bidder_idx` (`bidder`),
  KEY `bidon_idx` (`Bidon`),
  CONSTRAINT `bidder` FOREIGN KEY (`bidder`) REFERENCES `accounts` (`username`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `bidon` FOREIGN KEY (`Bidon`) REFERENCES `package_records` (`packageid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidding`
--

LOCK TABLES `bidding` WRITE;
/*!40000 ALTER TABLE `bidding` DISABLE KEYS */;
/*!40000 ALTER TABLE `bidding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `commentid` int(11) NOT NULL AUTO_INCREMENT,
  `commenter` varchar(45) DEFAULT NULL,
  `commentedon` int(11) NOT NULL,
  `comment` varchar(100) NOT NULL,
  `timeofcomment` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commentid`),
  UNIQUE KEY `commentid_UNIQUE` (`commentid`),
  KEY `commenter_idx` (`commenter`),
  KEY `commentedon_idx` (`commentedon`),
  CONSTRAINT `commenter` FOREIGN KEY (`commenter`) REFERENCES `accounts` (`username`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `commenton` FOREIGN KEY (`commentedon`) REFERENCES `package_records` (`packageid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confrimations`
--

DROP TABLE IF EXISTS `confrimations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `confrimations` (
  `serialno` int(11) NOT NULL AUTO_INCREMENT,
  `packageid` varchar(45) NOT NULL,
  `pickerid` varchar(45) NOT NULL,
  PRIMARY KEY (`serialno`),
  UNIQUE KEY `serialno_UNIQUE` (`serialno`),
  UNIQUE KEY `packageid_UNIQUE` (`packageid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confrimations`
--

LOCK TABLES `confrimations` WRITE;
/*!40000 ALTER TABLE `confrimations` DISABLE KEYS */;
/*!40000 ALTER TABLE `confrimations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `messagefrom` varchar(45) NOT NULL,
  `messagefor` varchar(45) NOT NULL,
  `title` varchar(45) NOT NULL,
  `body` varchar(45) DEFAULT NULL,
  `status` enum('sent','saved','unread','read') NOT NULL DEFAULT 'sent',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `messageid_UNIQUE` (`id`),
  KEY `from_idx` (`messagefrom`),
  KEY `to_idx` (`messagefor`),
  CONSTRAINT `from` FOREIGN KEY (`messagefrom`) REFERENCES `accounts` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `to` FOREIGN KEY (`messagefor`) REFERENCES `accounts` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_records`
--

DROP TABLE IF EXISTS `package_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_records` (
  `packageid` int(11) NOT NULL AUTO_INCREMENT,
  `packagename` varchar(45) NOT NULL,
  `sender` varchar(45) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `weight` double NOT NULL DEFAULT '1',
  `entry_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Description` varchar(100) DEFAULT NULL,
  `amount_to_pay` float NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `userid` varchar(45) DEFAULT NULL,
  `pickerid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`packageid`),
  UNIQUE KEY `packageid_UNIQUE` (`packageid`),
  KEY `pickerid_idx` (`pickerid`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `pickerid` FOREIGN KEY (`pickerid`) REFERENCES `accounts` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `accounts` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_records`
--

LOCK TABLES `package_records` WRITE;
/*!40000 ALTER TABLE `package_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `package_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `watchlist`
--

DROP TABLE IF EXISTS `watchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `watchlist` (
  `idwatchlist` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(45) NOT NULL,
  `packageid` int(11) NOT NULL,
  PRIMARY KEY (`idwatchlist`),
  UNIQUE KEY `idwatchlist_UNIQUE` (`idwatchlist`),
  KEY `user_idx` (`user`),
  KEY `pid_idx` (`packageid`),
  CONSTRAINT `pid` FOREIGN KEY (`packageid`) REFERENCES `package_records` (`packageid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usr` FOREIGN KEY (`user`) REFERENCES `accounts` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `watchlist`
--

LOCK TABLES `watchlist` WRITE;
/*!40000 ALTER TABLE `watchlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `watchlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-22 16:12:17
