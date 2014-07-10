-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: newsmth
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.13.10.1

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `boardName` varchar(128) NOT NULL,
  `boardId` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `gid` int(11) NOT NULL,
  `url` varchar(512) NOT NULL,
  `title` varchar(512) NOT NULL,
  `author` varchar(128) NOT NULL,
  `sign` mediumblob NOT NULL,
  `content` mediumblob NOT NULL,
  `raw` mediumblob NOT NULL,
  `atauthor` varchar(64) NOT NULL,
  `atmsg` mediumblob NOT NULL,
  `msg` mediumblob NOT NULL,
  `channel` varchar(64) NOT NULL,
  `time` varchar(128) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `indexstatus` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `alias` varchar(128) NOT NULL,
  `priority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1002,'ITjob','IT类兼职',1),(675,'SecondComputer','二手电脑市场',0),(26,'ITExpress','IT业界特快',0),(198,'Entrepreneur','创业者论坛',0),(225,'EconForum','经济论坛',0),(835,'SecondDigi','二手数码产品',0),(676,'SecondBook','二手图书市场',0),(123,'SecondMarket','二手市场主版',0),(383,'WorkLife','职业生涯',0),(398,'PieLove','鹊桥·征男友女友',0),(1104,'Career_Upgrade','社会招聘',0),(268,'Career_Plaza',' 求职广场',0),(828,'Career_Investment','求职投行',0),(412,'ExecutiveSearch','猎头招聘',0),(135,'Beauty','美丽的秘密',0),(638,'HealthyLife','健康生活',0),(1241,'NetNovel','网络小说',0);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doc`
--

DROP TABLE IF EXISTS `doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc` (
  `docId` int(11) NOT NULL,
  `tfidf` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc`
--

LOCK TABLES `doc` WRITE;
/*!40000 ALTER TABLE `doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `label`
--

DROP TABLE IF EXISTS `label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `label` (
  `id` int(11) NOT NULL,
  `label` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label`
--

LOCK TABLES `label` WRITE;
/*!40000 ALTER TABLE `label` DISABLE KEYS */;
INSERT INTO `label` VALUES (1,'金融'),(2,'java'),(3,'比特币'),(4,'互联网'),(5,'key'),(6,'baidu'),(7,'百度'),(8,'腾讯'),(9,'银行'),(10,'猎头'),(11,'招聘'),(12,'中信'),(13,'民生'),(14,'农行'),(15,'美容'),(16,'面膜'),(17,'瘦身'),(18,'医院'),(19,'心血管'),(20,'医生'),(21,'小说'),(22,'网络小说'),(23,'美女'),(24,'IT'),(25,'创业'),(26,'微信'),(27,'中信银行');
/*!40000 ALTER TABLE `label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `email` varchar(128) NOT NULL,
  `title` varchar(512) NOT NULL,
  `gid` int(11) NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `boardName` varchar(512) NOT NULL,
  `boardId` int(11) NOT NULL,
  `page` int(32) NOT NULL,
  `gid` int(11) NOT NULL,
  `title` varchar(512) NOT NULL,
  `url` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uboard`
--

DROP TABLE IF EXISTS `uboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uboard` (
  `uid` int(11) NOT NULL,
  `bid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uboard`
--

LOCK TABLES `uboard` WRITE;
/*!40000 ALTER TABLE `uboard` DISABLE KEYS */;
/*!40000 ALTER TABLE `uboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ulabel`
--

DROP TABLE IF EXISTS `ulabel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ulabel` (
  `uid` int(11) NOT NULL,
  `lid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ulabel`
--

LOCK TABLES `ulabel` WRITE;
/*!40000 ALTER TABLE `ulabel` DISABLE KEYS */;
INSERT INTO `ulabel` VALUES (1,1),(1,2),(1,3),(2,4),(2,1),(1,7),(1,8),(2,9),(2,10),(2,11),(2,12),(2,13),(2,14),(4,23),(4,1),(4,24),(4,4),(4,25),(4,26),(4,27);
/*!40000 ALTER TABLE `ulabel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usearch`
--

DROP TABLE IF EXISTS `usearch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usearch` (
  `uid` int(11) NOT NULL,
  `skey` varchar(32) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usearch`
--

LOCK TABLES `usearch` WRITE;
/*!40000 ALTER TABLE `usearch` DISABLE KEYS */;
/*!40000 ALTER TABLE `usearch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','coola58@163.com'),(2,'290119597@qq.com','000000','290119597@qq.com'),(3,'419831862@qq.com','000000','419831862@qq.com'),(4,'582843680@qq.com','000000','582843680@qq.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `word`
--

DROP TABLE IF EXISTS `word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `word` (
  `docId` int(11) NOT NULL,
  `word` varchar(256) NOT NULL,
  `tfidf` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `word`
--

LOCK TABLES `word` WRITE;
/*!40000 ALTER TABLE `word` DISABLE KEYS */;
/*!40000 ALTER TABLE `word` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-11  1:02:28
