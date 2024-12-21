-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: myweb_db
-- ------------------------------------------------------
-- Server version	8.4.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board_comment_tbl`
--

DROP TABLE IF EXISTS `board_comment_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_comment_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `boardId` bigint unsigned NOT NULL,
  `likeQty` int DEFAULT '0',
  `createId` bigint unsigned DEFAULT NULL,
  `createDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updateId` bigint unsigned DEFAULT NULL,
  `updateDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleteId` bigint unsigned DEFAULT NULL,
  `deleteDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleteFlag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_comment_tbl_board_tbl_FK` (`boardId`),
  KEY `board_comment_tbl_user_tbl_createId` (`createId`),
  KEY `board_comment_tbl_user_tbl_updateId` (`updateId`),
  KEY `board_comment_tbl_user_tbl_deleteId` (`deleteId`),
  KEY `board_comment_tbl_boardId_IDX` (`boardId`,`deleteFlag`) USING BTREE,
  KEY `board_comment_tbl_id_IDX` (`id`,`deleteFlag`) USING BTREE,
  CONSTRAINT `board_comment_tbl_board_tbl_FK` FOREIGN KEY (`boardId`) REFERENCES `board_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_comment_tbl_user_tbl_createId` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_comment_tbl_user_tbl_deleteId` FOREIGN KEY (`deleteId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_comment_tbl_user_tbl_updateId` FOREIGN KEY (`updateId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_comment_tbl`
--

LOCK TABLES `board_comment_tbl` WRITE;
/*!40000 ALTER TABLE `board_comment_tbl` DISABLE KEYS */;
INSERT INTO `board_comment_tbl` VALUES (6,'1',7,2,13,'2024-11-30 20:26:07',NULL,NULL,NULL,NULL,0),(7,'2',7,1,13,'2024-11-30 20:26:09',NULL,NULL,NULL,NULL,0),(8,'ㅂㄴㅂㄴㅂㄴ',7,1,13,'2024-11-30 20:26:12',13,'2024-11-30 20:54:37',NULL,NULL,0),(9,'123123123',7,2,13,'2024-11-30 23:10:48',NULL,NULL,13,'2024-12-01 20:35:57',1),(10,'ggg',6,1,13,'2024-12-01 20:35:25',NULL,NULL,13,'2024-12-01 20:35:50',1),(11,'asdad',23,0,13,'2024-12-02 10:55:24',13,'2024-12-02 10:55:28',NULL,NULL,0),(12,'zxzxzxzx',24,0,13,'2024-12-02 12:30:41',NULL,NULL,NULL,NULL,0),(13,'fsdd',31,1,13,'2024-12-09 00:13:19',NULL,NULL,NULL,NULL,0),(14,'tyty',31,0,14,'2024-12-09 00:51:25',NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `board_comment_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_file_tbl`
--

DROP TABLE IF EXISTS `board_file_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_file_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ord` int unsigned NOT NULL DEFAULT '1',
  `fileType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uniqName` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `length` int unsigned NOT NULL DEFAULT '0',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tbl` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `boardId` bigint unsigned NOT NULL DEFAULT '0',
  `deleteFlag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_file_tbl_id_IDX` (`id`,`deleteFlag`) USING BTREE,
  KEY `board_file_tbl_tbl_boardId_IDX` (`tbl`,`boardId`) USING BTREE,
  KEY `board_file_tbl_tbl_boardId_deleteFlag_IDX` (`tbl`,`boardId`,`deleteFlag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_file_tbl`
--

LOCK TABLES `board_file_tbl` WRITE;
/*!40000 ALTER TABLE `board_file_tbl` DISABLE KEYS */;
INSERT INTO `board_file_tbl` VALUES (5,'emong.jpg',0,'.jpg','4a55e509-f52f-4272-8063-f0b19d04f2c3',29531,NULL,'board',29,1),(6,'emong02.jpg',0,'.jpg','13c9e34f-1204-4570-a91b-5e0b0641251e',5816,NULL,'board',30,1),(7,'emong.jpg',0,'.jpg','02b27bf0-ac5e-40bf-bb0b-681726c90fd5',29531,NULL,'board',31,1),(8,'emong02.jpg',1,'.jpg','e8903046-c212-4c35-aec8-3e5f9a648e54',5816,NULL,'board',31,1),(9,'emong.jpg',0,'.jpg','ccce01b6-6b06-437a-8aa3-58162f99f10b',29531,NULL,'board',31,1),(10,'emong.jpg',0,'.jpg','d2b2979a-5aa2-4697-8ff7-95863e4178d3',29531,NULL,'board',31,1),(11,'emong02.jpg',0,'.jpg','9bb29fa6-3509-4528-ae1e-4f534f042299',5816,NULL,'board',31,1),(12,'emong.jpg',0,'.jpg','20f2cd45-89c9-4786-ad0a-617781497322',29531,NULL,'board',31,1),(13,'emong.jpg',0,'.jpg','d3be6080-5990-4d03-be19-ac771ab5aa16',29531,NULL,'board',31,1),(14,'emong.jpg',0,'.jpg','e0c2098c-8c58-4bc5-ab71-da2b5a609b61',29531,NULL,'board',31,1),(15,'emong02.jpg',1,'.jpg','04949a8c-98e3-4e7b-a676-6e8d08691be4',5816,NULL,'board',31,1),(16,'emong.jpg',0,'.jpg','fa2030da-6e57-40c7-8205-ae5dafecc00a',29531,NULL,'board',31,1),(17,'emong02.jpg',0,'.jpg','ff723851-3261-40bb-b4f2-c68a65e5c8e3',5816,NULL,'board',31,1),(18,'emong.jpg',0,'.jpg','b979fb72-c656-4a83-8027-3cec1b846131',29531,NULL,'board',31,1),(19,'emong02.jpg',1,'.jpg','475f2f56-973e-4535-b66f-462220fbef2c',5816,NULL,'board',31,1),(20,'emong.jpg',0,'.jpg','38d36e64-a81f-45b9-b77b-e4c0169b86f3',29531,NULL,'board',29,1),(21,'emong02.jpg',0,'.jpg','3b93c1a1-3863-413b-b0d7-78544235677f',5816,NULL,'board',29,0),(22,'emong.jpg',0,'.jpg','0c64f057-7b10-4794-9ebf-bd31ee9b0218',29531,NULL,'board',31,1),(23,'emong.jpg',0,'.jpg','995141d7-3d49-4f08-acdc-eddf3598ba8e',29531,NULL,'board',31,0),(24,'emong.jpg',0,'.jpg','ad5873c5-1702-44a5-93ba-08abcc1b787f',29531,NULL,'board',32,1),(25,'emong02.jpg',1,'.jpg','f7039c61-daf4-4e04-a159-75f04ec60ac0',5816,NULL,'board',32,0),(26,'emong.jpg',0,'.jpg','153b0c14-4b7c-4c4c-8050-cc181192e89e',29531,NULL,'board',33,0),(27,'emong.jpg',0,'.jpg','db4e56d8-f10e-4f31-957e-9fa054bb4e3f',29531,NULL,'board',34,0),(28,'emong.jpg',0,'.jpg','bba615c6-fd03-4fcf-8266-f414dac8f5f9',29531,NULL,'board',35,0),(29,'emong02.jpg',1,'.jpg','b3a9e22f-71fe-4279-81e7-1dbe2b19b7f4',5816,NULL,'board',35,0),(30,'emong.jpg',0,'.jpg','c888a44e-7c39-4b0b-b678-c9278fc24c4f',29531,NULL,'board',36,0),(31,'emong.jpg',0,'.jpg','9735bec6-c05c-4ac7-ae79-15052fdd0904',29531,NULL,'board',37,1),(32,'emong.jpg',1,'.jpg','2c0da682-e316-4094-b33f-de8a0155dc30',29531,NULL,'board',37,1),(33,'emong.jpg',0,'.jpg','8f5ee088-e9d4-4d75-a807-2f6e5d1ed14e',29531,NULL,'board',38,0),(34,'emong02.jpg',1,'.jpg','d29c4921-175c-40db-8a25-1b54a815d601',5816,NULL,'board',38,0),(35,'emong.jpg',0,'.jpg','ba071e84-88c6-4242-bf02-2811d91b3ddf',29531,NULL,'board',39,0),(36,'emong.jpg',0,'.jpg','85a07076-1147-439d-8b0c-d9f5374d1a0e',29531,NULL,'board',41,0),(37,'emong02.jpg',1,'.jpg','878ec37d-eea5-4333-a191-1b8b7c98448c',5816,NULL,'board',41,0),(38,'emong02.jpg',0,'.jpg','5f1e377a-5517-4ea1-bb8e-1632b069ac54',5816,NULL,'board',43,0),(39,'emong.jpg',0,'.jpg','9862f75f-1fb0-44d1-80f9-355e8c237275',29531,NULL,'board',44,0),(40,'emong02.jpg',1,'.jpg','e5d2846b-506a-4020-afe6-f2f0576473f6',5816,NULL,'board',44,0),(41,'emong02.jpg',0,'.jpg','aa67bd7c-5edd-44b4-aa15-d5b1b8a8a257',5816,NULL,'board',46,0),(42,'emong02.jpg',0,'.jpg','4d08fecd-b357-48ae-803b-463d7c469dd8',5816,NULL,'board',47,0),(43,'emong.jpg',0,'.jpg','ea02f716-ca20-4a90-9d26-df85f5ac0d71',29531,NULL,'board',48,0),(44,'emong.jpg',0,'.jpg','0de91a21-f9ae-45e3-908a-f2479f357275',29531,NULL,'board',51,0),(45,'emong.jpg',0,'.jpg','5feaf100-bf0b-408c-804d-6c02461567c5',29531,NULL,'board',52,0),(46,'emong02.jpg',1,'.jpg','90dd778a-69ef-4470-8976-c2c00e0a69b4',5816,NULL,'board',52,0),(47,'emong02.jpg',0,'.jpg','945f1730-3e43-432d-8e54-7f0665b0c5c2',5816,NULL,'board',53,0),(48,'emong02.jpg',1,'.jpg','3f5b43ce-7c35-4b06-baaa-fe0e37094b00',5816,NULL,'board',53,0),(49,'emong02.jpg',2,'.jpg','3ff6a936-f5dd-4e52-a10e-16839b46d33e',5816,NULL,'board',53,0),(50,'emong.jpg',0,'.jpg','278e58fc-a544-4491-b9fd-0638077beaa7',29531,NULL,'board',54,0),(51,'emong.jpg',0,'.jpg','fa29cb24-51d5-46da-a68c-6b551d7d33c9',29531,NULL,'board',58,0),(52,'emong.jpg',1,'.jpg','b38b4c78-c26e-4636-9157-79610ad9b72e',29531,NULL,'board',58,0),(53,'emong.jpg',0,'.jpg','33b53225-24bc-4894-89f8-de3352454aad',29531,NULL,'board',59,0),(54,'emong.jpg',0,'.jpg','35c08498-d4e3-4fd4-bd71-c918702768a6',29531,NULL,'board',60,0),(55,'emong.jpg',0,'.jpg','93df5c41-5edb-4d98-9aad-f37183ded91e',29531,NULL,'board',62,0),(56,'emong.jpg',0,'.jpg','87b15d4e-20ee-4106-84d9-a9cbe2b478bf',29531,NULL,'board',63,0),(57,'emong02.jpg',1,'.jpg','1b030eeb-cb96-4209-bc63-9fb490aedc5c',5816,NULL,'board',63,0),(58,'emong.jpg',0,'.jpg','02eaa9af-1681-4633-8110-ba58a5afe794',29531,NULL,'board',65,0),(59,'emong.jpg',0,'.jpg','3785ee34-e070-44d9-b268-9cdfca1d2e5b',29531,NULL,'board',67,0),(60,'emong02.jpg',0,'.jpg','7f4aa7bd-7a81-40ae-b49f-9c42a782dd6c',5816,NULL,'board',68,0),(61,'emong.jpg',0,'.jpg','b2ca0f07-8567-4f16-8b9a-2a37b5e675e6',29531,NULL,'board',69,0),(62,'emong.jpg',1,'.jpg','9cf8983b-445a-47d1-a18a-5493bd34dd23',29531,NULL,'board',69,0),(63,'emong02.jpg',2,'.jpg','ad7c3023-6930-41fa-8cca-f1e3cadb205a',5816,NULL,'board',69,0),(64,'emong02.jpg',3,'.jpg','3a794b22-6bce-45ec-9780-06dc74e7c3cc',5816,NULL,'board',69,0),(65,'emong.jpg',4,'.jpg','ab8fafeb-f0af-4ed0-bba6-dc7ef03df176',29531,NULL,'board',69,0),(66,'emong02.jpg',5,'.jpg','1187379d-c3f7-493f-afaf-1678c95e2cc5',5816,NULL,'board',69,0),(67,'emong.jpg',6,'.jpg','8444ce01-2101-4eb2-a6e0-3e315e73ab2e',29531,NULL,'board',69,0),(68,'emong02.jpg',7,'.jpg','0cd672db-d6e3-427f-b9fd-3cdc24dda029',5816,NULL,'board',69,0),(69,'emong.jpg',0,'.jpg','aa5b6bc2-333a-4cb6-b608-dea19b1ab06f',29531,NULL,'board',70,0),(70,'emong.jpg',1,'.jpg','c6cff8eb-7488-4ecd-a795-8e9761bb3110',29531,NULL,'board',70,0),(71,'emong.jpg',2,'.jpg','4caf261e-a19d-47d6-a988-fe80c1b4381f',29531,NULL,'board',70,0),(72,'emong.jpg',3,'.jpg','34b56de6-129f-47c3-9c1c-12ba5c0daab7',29531,NULL,'board',70,0),(73,'emong.jpg',4,'.jpg','4e1821aa-3cfe-4e13-9f17-074397cc6971',29531,NULL,'board',70,0),(74,'emong02.jpg',0,'.jpg','26c2bee4-109f-4292-8d19-9566605fa63a',5816,NULL,'board',71,0),(75,'emong02.jpg',1,'.jpg','a005a5f7-0901-4d46-ab76-0552d2512052',5816,NULL,'board',71,0),(76,'emong02.jpg',2,'.jpg','5dda7d8b-6002-47c8-b5bc-2cae703e5676',5816,NULL,'board',71,0),(77,'emong02.jpg',3,'.jpg','d10a2d96-094e-4190-8171-5f9f9b9571a0',5816,NULL,'board',71,0),(78,'emong.jpg',0,'.jpg','8dc88caa-096e-4010-a79e-9962244e31bb',29531,NULL,'board',72,0),(79,'emong.jpg',0,'.jpg','cbd7c6cf-3431-4096-a6eb-54d5c9af3016',29531,NULL,'board',73,0),(80,'emong.jpg',0,'.jpg','1c542b58-a91c-4e5d-bb67-bf12dadb4248',29531,NULL,'board',74,0),(81,'emong02.jpg',1,'.jpg','697b05fa-1d4e-4625-93f1-33437e88b6d2',5816,NULL,'board',74,0),(82,'emong.jpg',2,'.jpg','1662ca42-ee9c-45f0-8f9c-10f2f2586693',29531,NULL,'board',74,0),(83,'emong02.jpg',3,'.jpg','25094fbf-ddeb-4299-9475-9e4f7c071320',5816,NULL,'board',74,0),(84,'emong02.jpg',0,'.jpg','346db434-3051-4ba7-b924-335661df424f',5816,NULL,'board',75,0),(85,'emong02.jpg',1,'.jpg','f8881c37-f174-4021-bae8-225e00e09883',5816,NULL,'board',75,0),(86,'emong02.jpg',0,'.jpg','7b8cb329-7f5c-4cc9-8a56-5252b2252af2',5816,NULL,'board',76,1),(87,'emong02.jpg',1,'.jpg','24d7bd00-82d8-4f42-af8d-2cb1e2db2f15',5816,NULL,'board',76,1),(88,'emong02.jpg',2,'.jpg','67a96bad-c120-40c2-9345-73857ec6794d',5816,NULL,'board',76,1),(89,'emong02.jpg',3,'.jpg','966242a6-83c1-4717-ac61-250ee8911599',5816,NULL,'board',76,1),(90,'emong.jpg',0,'.jpg','61100a03-f91c-4e81-8d1f-b00a8cb7f02d',29531,NULL,'board',76,0),(91,'emong.jpg',1,'.jpg','e97a234f-0f30-41ae-93d6-0e6979de8468',29531,NULL,'board',76,0),(92,'emong.jpg',0,'.jpg','94319d66-cef5-4e8f-993d-a10119e885ff',29531,NULL,'board',77,1),(93,'emong02.jpg',1,'.jpg','a6a64009-8a87-4e92-ad81-3c11893bad7f',5816,NULL,'board',77,0),(94,'emong02.jpg',0,'.jpg','f9037539-a599-4eb3-872f-2c9bfb948686',5816,NULL,'board',78,1),(95,'emong02.jpg',1,'.jpg','91657787-00dc-4df6-bd9c-007ac6be5968',5816,NULL,'board',78,1),(96,'emong.jpg',0,'.jpg','d87a583c-d18c-4ace-b2a0-877483156067',29531,NULL,'board',78,1),(97,'emong02.jpg',0,'.jpg','43b2cfab-5abe-4cf3-9bc9-5f3dcc39692c',5816,NULL,'board',78,1),(98,'emong.jpg',0,'.jpg','3f42f261-a179-4269-b980-a167f4575937',29531,NULL,'board',78,1),(99,'emong.jpg',0,'.jpg','aeb65726-7559-419f-9fd1-6ed00c307c04',29531,NULL,'board',78,1),(100,'emong02.jpg',1,'.jpg','830b1fda-9c78-4feb-980f-deae3e2de0fa',5816,NULL,'board',78,1),(101,'emong02.jpg',0,'.jpg','14c55a67-33c8-4d8c-b6a9-826c3903a3ad',5816,NULL,'board',78,1),(102,'emong.jpg',0,'.jpg','91052d02-9f41-4ac7-a1c5-6acba1bf40c9',29531,NULL,'board',78,1),(103,'emong.jpg',0,'.jpg','c391f2e9-0620-4791-9f1d-1796b4015f70',29531,NULL,'board',78,1),(104,'emong02.jpg',0,'.jpg','700d0aca-5133-4186-9efa-6616baf7a8ca',5816,NULL,'board',78,1),(105,'emong.jpg',1,'.jpg','07a279fe-4c83-4b86-9f97-cdee73efa846',29531,NULL,'board',78,1);
/*!40000 ALTER TABLE `board_file_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_like_tbl`
--

DROP TABLE IF EXISTS `board_like_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_like_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createId` bigint unsigned NOT NULL,
  `boardId` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_like_tbl_user_tbl_createId` (`createId`),
  KEY `board_like_tbl_tbl_IDX` (`tbl`,`createId`,`boardId`) USING BTREE,
  CONSTRAINT `board_like_tbl_user_tbl_createId` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_like_tbl`
--

LOCK TABLES `board_like_tbl` WRITE;
/*!40000 ALTER TABLE `board_like_tbl` DISABLE KEYS */;
INSERT INTO `board_like_tbl` VALUES (47,'board',13,7),(50,'board',13,19),(51,'board',13,20),(52,'board',13,23),(53,'board',13,24),(57,'board',13,31),(49,'board',14,7),(58,'board',14,31),(59,'board',14,76);
/*!40000 ALTER TABLE `board_like_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_tbl`
--

DROP TABLE IF EXISTS `board_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `viewQty` int DEFAULT '0',
  `likeQty` int DEFAULT '0',
  `createId` bigint unsigned DEFAULT NULL,
  `createDt` datetime DEFAULT NULL,
  `updateId` bigint unsigned DEFAULT NULL,
  `updateDt` datetime DEFAULT NULL,
  `deleteId` bigint unsigned DEFAULT NULL,
  `deleteDt` datetime DEFAULT NULL,
  `deleteFlag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_tbl_user_tbl_createId` (`createId`),
  KEY `board_tbl_user_tbl_updateId` (`updateId`),
  KEY `board_tbl_user_tbl_deleteId` (`deleteId`),
  KEY `board_tbl_id_IDX` (`id`,`deleteFlag`) USING BTREE,
  KEY `board_tbl_title_IDX` (`title`,`deleteFlag`) USING BTREE,
  CONSTRAINT `board_tbl_user_tbl_createId` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_tbl_user_tbl_deleteId` FOREIGN KEY (`deleteId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_tbl_user_tbl_updateId` FOREIGN KEY (`updateId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_tbl`
--

LOCK TABLES `board_tbl` WRITE;
/*!40000 ALTER TABLE `board_tbl` DISABLE KEYS */;
INSERT INTO `board_tbl` VALUES (6,'123123','123123',103,0,13,'2024-11-27 13:53:09',13,'2024-11-27 15:21:18',13,'2024-12-09 14:56:49',1),(7,'asdf','qwer',545,2,13,'2024-11-28 10:04:01',13,'2024-11-28 10:04:30',NULL,NULL,0),(8,'1','1',1,0,13,'2024-12-01 20:50:46',NULL,NULL,NULL,NULL,0),(9,'2','2',0,0,13,'2024-12-01 20:51:23',NULL,NULL,NULL,NULL,0),(10,'3','3',0,0,13,'2024-12-01 20:51:27',NULL,NULL,NULL,NULL,0),(11,'4444444444444444','4444444444444',0,0,13,'2024-12-01 20:51:32',NULL,NULL,NULL,NULL,0),(12,'5555555','555555555',0,0,13,'2024-12-01 20:51:38',NULL,NULL,NULL,NULL,0),(13,'66666666','6666666666',0,0,13,'2024-12-01 20:51:45',NULL,NULL,NULL,NULL,0),(14,'77777777777','777777777777777',0,0,13,'2024-12-01 20:51:51',NULL,NULL,NULL,NULL,0),(15,'111111111111111','111111111111',1,0,13,'2024-12-01 20:51:57',NULL,NULL,NULL,NULL,0),(16,'2525525','2525252',0,0,13,'2024-12-01 20:52:02',NULL,NULL,NULL,NULL,0),(17,'5252525','252525',0,0,13,'2024-12-01 20:52:08',NULL,NULL,NULL,NULL,0),(18,'1515151','5151515',1,0,13,'2024-12-01 20:52:16',NULL,NULL,NULL,NULL,0),(19,'1515151','515151515',2,1,13,'2024-12-01 20:52:43',NULL,NULL,NULL,NULL,0),(20,'ㅇㄴㄻㄴㅇㄹ','ㅇㄴㅁㄹㅇㅁㄹㄴ',2,1,13,'2024-12-01 20:53:00',13,'2024-12-01 21:32:27',NULL,NULL,0),(21,'tttttttttttt','ttttttttttttt',0,0,13,'2024-12-02 10:51:39',NULL,NULL,NULL,NULL,0),(22,'tttttttttttt','ttttttttttttt',1,0,13,'2024-12-02 10:52:19',NULL,NULL,13,'2024-12-02 12:51:25',1),(23,'123123123','123123',4,1,13,'2024-12-02 10:54:57',NULL,NULL,NULL,NULL,0),(24,'asdasdasd','sadasdasd',7,1,13,'2024-12-02 11:24:41',13,'2024-12-02 12:30:49',NULL,NULL,0),(25,'downloadFile01','downloadFile01',2,0,13,'2024-12-04 10:02:37',NULL,NULL,NULL,NULL,0),(26,'downFile001','downFile001',5,0,13,'2024-12-04 10:03:16',NULL,NULL,NULL,NULL,0),(27,'imageTest01','imageTest01',6,0,13,'2024-12-04 13:07:29',NULL,NULL,NULL,NULL,0),(28,'tttttttttttttt','tttttttttttttt',3,0,13,'2024-12-04 13:21:20',NULL,NULL,NULL,NULL,0),(29,'ㄳㄳㅅㄱㄳ','ㄳㅅㄳㄳㄱㄳ',46,0,13,'2024-12-04 13:23:35',13,'2024-12-09 00:38:22',NULL,NULL,0),(30,'gfgffg','fgfgfg',12,0,13,'2024-12-04 13:38:50',13,'2024-12-09 00:13:36',NULL,NULL,0),(31,'123123123565665','123123123655665',72,2,13,'2024-12-04 14:23:03',13,'2024-12-09 00:49:22',NULL,NULL,0),(32,'hgghghgh','hghghghbbnbnbn',4,0,14,'2024-12-09 00:51:51',14,'2024-12-09 00:52:34',NULL,NULL,0),(33,'ghghh','ghghgh',2,0,14,'2024-12-09 00:52:16',14,'2024-12-09 00:52:23',NULL,NULL,0),(34,'tttttttttt','tttttttttttt',2,0,14,'2024-12-09 00:52:46',NULL,NULL,NULL,NULL,0),(35,'tttttttttttt','tttttttttttttttttt',2,0,14,'2024-12-09 00:53:02',NULL,NULL,NULL,NULL,0),(36,'77777777777777','7777777777777777777777777777',3,0,14,'2024-12-09 01:15:48',NULL,NULL,NULL,NULL,0),(37,'9999999999999','999999999999999999',4,0,14,'2024-12-09 01:19:22',14,'2024-12-09 01:19:32',NULL,NULL,0),(38,'88888','888888',1,0,14,'2024-12-09 01:24:32',NULL,NULL,NULL,NULL,0),(39,'88888','88888',1,0,14,'2024-12-09 01:24:44',NULL,NULL,NULL,NULL,0),(40,'88888','88888',1,0,14,'2024-12-09 01:25:05',NULL,NULL,NULL,NULL,0),(41,'1231123123','12312312321',1,0,14,'2024-12-09 01:25:34',NULL,NULL,NULL,NULL,0),(42,'656565','65565656',1,0,14,'2024-12-09 01:26:09',NULL,NULL,NULL,NULL,0),(43,'7676767','67676767',1,0,14,'2024-12-09 01:26:24',NULL,NULL,NULL,NULL,0),(44,'8888888888888','88888888888888888888888',1,0,14,'2024-12-09 01:26:40',NULL,NULL,NULL,NULL,0),(45,'99999999','999999999999999999',1,0,14,'2024-12-09 01:26:55',NULL,NULL,NULL,NULL,0),(46,'eeeeeeeeeee','eeeeeeeeeeeeeeeeeeeeeeeeee',3,0,14,'2024-12-09 01:28:42',NULL,NULL,NULL,NULL,0),(47,'56565','65566565',1,0,14,'2024-12-09 01:28:59',NULL,NULL,NULL,NULL,0),(48,'789789','79789',6,0,14,'2024-12-09 01:37:41',14,'2024-12-09 01:37:54',NULL,NULL,0),(49,'12121212','1212121212',1,0,14,'2024-12-09 01:38:35',NULL,NULL,NULL,NULL,0),(50,'98989898','98989898',2,0,14,'2024-12-09 01:39:23',NULL,NULL,NULL,NULL,0),(51,'nbbbnbbn','gtgrttr',2,0,14,'2024-12-09 01:40:36',NULL,NULL,NULL,NULL,0),(52,'rtrtrttr','trrtrtrtrtrtrt',1,0,14,'2024-12-09 01:40:47',NULL,NULL,NULL,NULL,0),(53,'wewewe','fafafaf',1,0,14,'2024-12-09 01:41:18',NULL,NULL,NULL,NULL,0),(54,'bbbbb','bbbbbb',1,0,14,'2024-12-09 01:43:03',NULL,NULL,NULL,NULL,0),(55,'bbbbb','bbbbbbbbbbbbbbbbbbbb',1,0,14,'2024-12-09 01:43:25',NULL,NULL,NULL,NULL,0),(56,'bbbbbbbbbbbbb','bbbbbbbbbbbbbbbbbbbb',1,0,14,'2024-12-09 01:43:39',NULL,NULL,NULL,NULL,0),(57,'bbbbbbbbbbbbbb','bbbbbbbbbbbbbbbbbb',1,0,14,'2024-12-09 01:43:50',NULL,NULL,NULL,NULL,0),(58,'bvbvbvbv','bvbvbvbvbv',2,0,14,'2024-12-09 01:44:14',NULL,NULL,NULL,NULL,0),(59,'fgfgfg','fgfggffg',2,0,14,'2024-12-09 01:44:26',NULL,NULL,NULL,NULL,0),(60,'ggggggggggg','ggggggggggggggggggggggg',1,0,14,'2024-12-09 01:47:29',NULL,NULL,NULL,NULL,0),(61,'7777777777','7777777777',1,0,14,'2024-12-09 01:58:50',NULL,NULL,NULL,NULL,0),(62,'777777','77777777777777777',2,0,14,'2024-12-09 01:58:59',NULL,NULL,NULL,NULL,0),(63,'777777777777','77777777777777777777',2,0,14,'2024-12-09 01:59:09',NULL,NULL,NULL,NULL,0),(64,'7777777','777777777777777',2,0,14,'2024-12-09 01:59:32',NULL,NULL,NULL,NULL,0),(65,'67676767','67676767',2,0,14,'2024-12-09 01:59:46',NULL,NULL,NULL,NULL,0),(66,'67676767','6767676767',1,0,14,'2024-12-09 02:00:02',NULL,NULL,NULL,NULL,0),(67,'fsadfadsf','dasfasdf',1,0,14,'2024-12-09 02:02:24',NULL,NULL,NULL,NULL,0),(68,'2222222','22222222',1,0,14,'2024-12-09 02:02:49',NULL,NULL,NULL,NULL,0),(69,'12212121','121212121212',3,0,14,'2024-12-09 02:03:49',NULL,NULL,NULL,NULL,0),(70,'affafafaf','afafafafaf',3,0,14,'2024-12-09 02:05:09',NULL,NULL,NULL,NULL,0),(71,'fafafafafa','fafafaf',1,0,14,'2024-12-09 02:05:41',NULL,NULL,NULL,NULL,0),(72,'676776','767667',17,0,14,'2024-12-09 02:09:28',NULL,NULL,NULL,NULL,0),(73,'faefeafae','faefawf',1,0,14,'2024-12-09 02:09:55',NULL,NULL,NULL,NULL,0),(74,'fffaefwaef','waefweafwfd',2,0,14,'2024-12-09 02:13:26',NULL,NULL,NULL,NULL,0),(75,'xxxxxxx','xxxxxxxxxxxxxxxxx',6,0,14,'2024-12-09 02:13:52',NULL,NULL,NULL,NULL,0),(76,'zzzzzzzzzzzㄷㄷ','ㅁㄴzzzzzㄷㄷ',129,1,14,'2024-12-09 02:14:26',14,'2024-12-09 02:44:52',NULL,NULL,0),(77,'ㄷㄻㄹㄷㅁㄹ545454','ㄷㅁㄻㄹㄷㅁ545454',9,0,14,'2024-12-09 02:45:18',14,'2024-12-09 02:49:31',NULL,NULL,0),(78,'saasssdasdasd','asasasdasdsa',12,0,13,'2024-12-09 14:51:55',13,'2024-12-09 14:54:30',13,'2024-12-09 14:54:46',1);
/*!40000 ALTER TABLE `board_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_like_tbl`
--

DROP TABLE IF EXISTS `comment_like_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_like_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `commentTbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createId` bigint unsigned NOT NULL,
  `commentId` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `comment_like_tbl_user_tbl_createId` (`createId`),
  KEY `comment_like_tbl_commentTbl_IDX` (`commentTbl`,`createId`,`commentId`) USING BTREE,
  CONSTRAINT `comment_like_tbl_user_tbl_createId` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_like_tbl`
--

LOCK TABLES `comment_like_tbl` WRITE;
/*!40000 ALTER TABLE `comment_like_tbl` DISABLE KEYS */;
INSERT INTO `comment_like_tbl` VALUES (248,'boardcomment',13,6),(241,'boardcomment',13,7),(244,'boardcomment',13,8),(247,'boardcomment',13,9),(264,'boardcomment',13,10),(265,'boardcomment',13,13),(263,'boardcomment',14,6),(258,'boardcomment',14,9);
/*!40000 ALTER TABLE `comment_like_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tbl`
--

DROP TABLE IF EXISTS `user_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `loginId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `photo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginId` (`loginId`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tbl`
--

LOCK TABLES `user_tbl` WRITE;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` VALUES (13,'testid01','$2a$10$pz7MD.k.h50O4nDGI7OHBe34Eqv9ubJ.0foBrXBtfq7tHoN6hOO5.','haily','man','psw','qwer1234@naver.com',NULL),(14,'testid02','$2a$10$PZVQbnL3GvCGFJdwQDQ54.y8zEk9E/vZ7EWpo9YnuGvDxKJJucNVm','qweqwe','man','qweqweqwe','qwer12345@naver.com',NULL),(15,'testid03','$2a$10$5dHkdFSJsD6FVmPlY7giquC25BtjqKA.I9uXdnP.RXgKPK5yPvIgW','mememe','man','psw03','testid03@naver.com',NULL);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'myweb_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-21  9:45:11
