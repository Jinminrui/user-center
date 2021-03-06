-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: tw_user_center
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `pk_id` varchar(35) NOT NULL,
  `sender_id` varchar(35) NOT NULL COMMENT '发送者id',
  `sender_role` int NOT NULL COMMENT '发送者角色',
  `receiver_id` varchar(35) NOT NULL COMMENT '接受者id',
  `type` int NOT NULL COMMENT '消息类型',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '消息状态：已读或未读',
  `title` varchar(45) NOT NULL,
  `content` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `pk_id_UNIQUE` (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `pk_id` varchar(35) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '团队描述',
  `creator_id` varchar(35) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `pk_id_UNIQUE` (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES ('7d97b389c9004533a61ac18783370dec','123',NULL,'1','2020-04-18 23:59:25','2020-04-18 23:59:25'),('b907f9ec7c6146d49587ca98f9a60a90','Scrum产品研发团队','我们遇到什么困难都不要怕，微笑着面对它。','efa5a42313ca4f3b9570e9a1c95871bd','2020-01-20 15:18:16','2020-01-26 20:59:26'),('e67c4ad87a1a40d5820dab48c1b468b1','123',NULL,'1','2020-04-19 10:50:04','2020-04-19 10:50:04');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `pk_id` varchar(35) NOT NULL,
  `username` varchar(45) DEFAULT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `phone` varchar(45) NOT NULL COMMENT '手机号',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信名称\n',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `role` int DEFAULT NULL COMMENT '角色',
  `position` varchar(45) DEFAULT NULL COMMENT '职位',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `pk_id_UNIQUE` (`pk_id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('2fa6becfa9714043be78d64d2f589d12','张海涛','123','18252086662',NULL,NULL,NULL,'http://oss.jinminrui.cn/avatars/2fa6becfa9714043be78d64d2f589d12/20200414101153.jpeg',1,'安卓开发工程师','2020-04-14 10:10:27','2020-04-14 10:11:57',NULL),('71022b4908a54243b4ac352003b06950','蜡笔小新','123','18094498861',NULL,NULL,NULL,'http://oss.jinminrui.cn/avatars/71022b4908a54243b4ac352003b06950/20200409161537.jpg',1,'前端开发工程师','2020-03-03 16:32:04','2020-04-09 16:16:01',NULL),('71ac3f0df4b6413a884db5b838778403','罗飞','123','18851770060','nmsl@luofei.com',NULL,NULL,'http://oss.jinminrui.cn/avatars/default.svg',1,'后端开发工程师','2020-01-21 09:53:10','2020-01-21 09:54:47','奥利给！！'),('8d68826010d54b418fa4192571135bd6','杨志云','123456','18851777939','593323580@qq.com','哈哈哈',NULL,'http://oss.jinminrui.cn/avatars/default.svg',1,'后端开发工程师','2020-01-20 18:22:39','2020-01-20 18:26:41','奥利给！！！'),('efa5a42313ca4f3b9570e9a1c95871bd','金敏睿','980328','17314976003','969172689@qq.com','brlive',NULL,'http://oss.jinminrui.cn/avatars/efa5a42313ca4f3b9570e9a1c95871bd/20200120122152.png',1,'前端开发工程师','2020-01-20 12:20:14','2020-04-19 11:48:55','面对疾风吧！！！');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_team_relation`
--

DROP TABLE IF EXISTS `user_team_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_team_relation` (
  `pk_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(35) NOT NULL,
  `team_id` varchar(35) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_role` int DEFAULT NULL,
  PRIMARY KEY (`pk_id`),
  KEY `fk_user_team_user_idx` (`user_id`),
  KEY `fk_user_team_team1_idx` (`team_id`),
  CONSTRAINT `fk_user_team_team1` FOREIGN KEY (`team_id`) REFERENCES `team` (`pk_id`),
  CONSTRAINT `fk_user_team_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_team_relation`
--

LOCK TABLES `user_team_relation` WRITE;
/*!40000 ALTER TABLE `user_team_relation` DISABLE KEYS */;
INSERT INTO `user_team_relation` VALUES (1,'efa5a42313ca4f3b9570e9a1c95871bd','b907f9ec7c6146d49587ca98f9a60a90','2020-01-20 15:18:16','2020-01-20 15:18:16',1),(13,'71022b4908a54243b4ac352003b06950','b907f9ec7c6146d49587ca98f9a60a90','2020-03-04 11:35:19','2020-03-04 11:35:19',2),(17,'8d68826010d54b418fa4192571135bd6','b907f9ec7c6146d49587ca98f9a60a90','2020-03-04 15:47:55','2020-03-04 15:47:55',2),(18,'2fa6becfa9714043be78d64d2f589d12','b907f9ec7c6146d49587ca98f9a60a90','2020-04-14 10:12:57','2020-04-14 10:12:57',2);
/*!40000 ALTER TABLE `user_team_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-14 19:46:08
