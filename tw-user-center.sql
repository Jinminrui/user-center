-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: tw_user_center
-- ------------------------------------------------------
-- Server version	8.0.11

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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `pk_id` varchar(35) NOT NULL,
  `sender_id` varchar(35) NOT NULL COMMENT '发送者id',
  `sender_role` int(11) NOT NULL COMMENT '发送者角色',
  `receiver_id` varchar(35) NOT NULL COMMENT '接受者id',
  `type` int(11) NOT NULL COMMENT '消息类型',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `message_text_id` varchar(35) DEFAULT NULL COMMENT '消息内容',
  `status` tinyint(1) DEFAULT NULL COMMENT '消息状态：已读或未读',
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `pk_id_UNIQUE` (`pk_id`),
  KEY `message_message_text_pk_id_fk` (`message_text_id`),
  CONSTRAINT `message_message_text_pk_id_fk` FOREIGN KEY (`message_text_id`) REFERENCES `message_text` (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES ('c6c27d5021c74be6947344a5d312bf16','efa5a42313ca4f3b9570e9a1c95871bd',1,'8d68826010d54b418fa4192571135bd6',1,'2020-02-16 23:16:35','2020-02-16 23:16:35','e0c9ad2c927442569d535b502476841b',0),('fe8852123c4148a29bebd81a0d04078c','efa5a42313ca4f3b9570e9a1c95871bd',1,'8d68826010d54b418fa4192571135bd6',1,'2020-02-16 23:15:23','2020-02-16 23:15:23','eca9d231a86047f7b9e6c30dfa8ccd1b',0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_text`
--

DROP TABLE IF EXISTS `message_text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_text` (
  `pk_id` varchar(35) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `message_text_pk_id_uindex` (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息内容表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_text`
--

LOCK TABLES `message_text` WRITE;
/*!40000 ALTER TABLE `message_text` DISABLE KEYS */;
INSERT INTO `message_text` VALUES ('e0c9ad2c927442569d535b502476841b','你好','你好','2020-02-16 23:16:35','2020-02-16 23:16:35'),('eca9d231a86047f7b9e6c30dfa8ccd1b','你好','你好','2020-02-16 23:15:23','2020-02-16 23:15:23');
/*!40000 ALTER TABLE `message_text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `team` VALUES ('b907f9ec7c6146d49587ca98f9a60a90','Scrum产品研发团队','我们遇到什么困难都不要怕，微笑着面对它。','efa5a42313ca4f3b9570e9a1c95871bd','2020-01-20 15:18:16','2020-01-26 20:59:26');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `pk_id` varchar(35) NOT NULL,
  `username` varchar(45) DEFAULT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `phone` varchar(45) NOT NULL COMMENT '手机号',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信名称\n',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `role` int(11) DEFAULT NULL COMMENT '角色',
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
INSERT INTO `user` VALUES ('71ac3f0df4b6413a884db5b838778403','罗飞',NULL,'18851770060','nmsl@luofei.com',NULL,NULL,'http://oss.jinminrui.cn/avatars/default.svg',2,'后端开发工程师','2020-01-21 09:53:10','2020-01-21 09:54:47','奥利给！！'),('8d68826010d54b418fa4192571135bd6','杨志云','123456','18851777939','593323580@qq.com','哈哈哈',NULL,'http://oss.jinminrui.cn/avatars/default.svg',2,'后端开发工程师','2020-01-20 18:22:39','2020-01-20 18:26:41','奥利给！！！'),('efa5a42313ca4f3b9570e9a1c95871bd','KeenKing','980328','17314976003','969172689@qq.com','brlive',NULL,'http://oss.jinminrui.cn/avatars/efa5a42313ca4f3b9570e9a1c95871bd/20200120122152.png',1,'前端开发工程师','2020-01-20 12:20:14','2020-01-26 15:20:51','面对疾风吧！！！');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_team_relation`
--

DROP TABLE IF EXISTS `user_team_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_team_relation` (
  `pk_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(35) NOT NULL,
  `team_id` varchar(35) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pk_id`),
  KEY `fk_user_team_user_idx` (`user_id`),
  KEY `fk_user_team_team1_idx` (`team_id`),
  CONSTRAINT `fk_user_team_team1` FOREIGN KEY (`team_id`) REFERENCES `team` (`pk_id`),
  CONSTRAINT `fk_user_team_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_team_relation`
--

LOCK TABLES `user_team_relation` WRITE;
/*!40000 ALTER TABLE `user_team_relation` DISABLE KEYS */;
INSERT INTO `user_team_relation` VALUES (1,'efa5a42313ca4f3b9570e9a1c95871bd','b907f9ec7c6146d49587ca98f9a60a90','2020-01-20 15:18:16','2020-01-20 15:18:16'),(4,'8d68826010d54b418fa4192571135bd6','b907f9ec7c6146d49587ca98f9a60a90','2020-01-28 17:09:56','2020-01-28 17:09:57'),(5,'71ac3f0df4b6413a884db5b838778403','b907f9ec7c6146d49587ca98f9a60a90','2020-01-28 17:10:22','2020-01-28 17:10:24');
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

-- Dump completed on 2020-02-20  0:04:18
