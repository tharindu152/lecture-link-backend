-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lecture_link
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `institute`
--

DROP TABLE IF EXISTS `institute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `institute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `district` varchar(500) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rating` int DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `subscribed` bit(1) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `ugc_reg_no` varchar(100) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institute`
--

LOCK TABLES `institute` WRITE;
/*!40000 ALTER TABLE `institute` DISABLE KEYS */;
INSERT INTO `institute` (`id`, `created_on`, `description`, `district`, `email`, `name`, `password`, `rating`, `status`, `subscribed`, `telephone`, `ugc_reg_no`, `updated_on`) VALUES (1,'2025-02-27 13:24:06.029838','A renowned institution offering diverse programs focused on innovation, critical thinking, and community engagement for future leaders.','Kandy','Edwina.Dach52@hotmail.com','Sanford, Kozey and Barton','3epfekivkOwiL4a',4,'ACTIVE',_binary '','+94712345678','1','2025-02-27 13:27:16.556024'),(2,'2025-02-27 14:20:15.994362','Committed to academic excellence, Greenfield Academy fosters holistic development through rigorous programs and a strong emphasis on sustainability.','Colombo','Orpha_Ankunding@yahoo.com','Ullrich, Stracke and Treutel','YExZE2_L315p53i',5,'ACTIVE',_binary '','+94712345678','y','2025-02-27 16:57:20.783286'),(3,'2025-02-27 15:12:05.478486','A prestigious college blending traditional academic disciplines with modern research, promoting entrepreneurship and leadership for global impact.','Galle','Dulce_Beahan@hotmail.com','Bogisich Inc','EdUsorHbFO1tAOq',3,'ACTIVE',_binary '','+94712345678','n','2025-02-27 16:44:38.022026'),(4,'2025-02-27 15:12:07.577829','A cutting-edge institution offering STEM-focused courses, designed to equip students with essential skills for the digital economy.','Nuwareliya','April.Mante99@gmail.com','Hansen, Bogan and Hermiston','LJOvNq386pStdYr',1,'ACTIVE',_binary '\0','+94712345678','k','2025-02-27 16:44:54.293577'),(5,'2025-02-27 15:12:10.008190','Offering a wide range of creative arts programs, Starlight nurtures artistic talent and fosters cultural expression in diverse media.','Matale','Madonna87@yahoo.com','Rau - Olson','8Aof_Fhu0c8oqrr',2,'ACTIVE',_binary '\0','+94712345678','5','2025-02-27 16:45:08.880961');
/*!40000 ALTER TABLE `institute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer`
--

DROP TABLE IF EXISTS `lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_no` varchar(15) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `district` varchar(500) NOT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_assigned` bit(1) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `pay_rate` decimal(19,2) DEFAULT NULL,
  `preference` varchar(255) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `subscribed` bit(1) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` (`id`, `contact_no`, `created_on`, `district`, `dob`, `email`, `is_assigned`, `language`, `name`, `password`, `pay_rate`, `preference`, `rating`, `status`, `subscribed`, `updated_on`) VALUES (1,'+94712345678','2025-02-27 13:24:45.984269','Matale','1993-04-04 00:00:00.000000','Bert15@hotmail.com',_binary '\0','TAMIL','Josefina Larkin','8g32xt6Nbj6TSWR',757.23,'Prefer to teach coding courses.',2,'ACTIVE',_binary '\0','2025-02-27 15:51:49.291740'),(2,'+94712345678','2025-02-27 15:48:42.973370','Nuwareliya','1993-04-04 00:00:00.000000','Alena.Weissnat97@gmail.com',_binary '','SINHALA','Catherine Collier','j9Z72rqdH0Pu4Gl',1145.24,'Prefer to teach software courses.',3,'ACTIVE',_binary '\0','2025-02-27 15:54:00.159243'),(3,'+94712345678','2025-02-27 16:31:06.217914','Galle','1993-04-04 00:00:00.000000','Conor.Hilpert@yahoo.com',_binary '\0','ENGLISH','Lena Monahan','ii3K0IAnelmsbkE',556.84,'Prefer to teach computer courses.',4,'ACTIVE',_binary '\0','2025-02-27 16:47:50.792129'),(4,'+94712345678','2025-02-27 16:31:07.267717','Kurunagala','1993-04-04 00:00:00.000000','Jedidiah.Wolff61@yahoo.com',_binary '\0','ENGLISH','Nancy Stracke','meB8y0dKaxua3sI',997.44,'Prefer to teach technology courses.',2,'ACTIVE',_binary '\0','2025-02-27 16:48:02.628959'),(5,'+94712345678','2025-02-27 16:31:08.141889','Colombo','1993-04-04 00:00:00.000000','Madyson.Reilly85@gmail.com',_binary '\0','ENGLISH','Bob Jenkins','bVclQy0YKfVMEQe',1000.00,'Prefer to teach algorithm courses.',3,'ACTIVE',_binary '\0','2025-02-27 16:48:13.963834'),(6,'+94712345678','2025-02-27 16:31:09.055317','Kandy','1993-04-04 00:00:00.000000','Dejuan58@gmail.com',_binary '\0','ENGLISH','Belinda Ruecker','kdYjUxdAV3WBu3s',611.77,'Prefer to teach scripting courses.',4,'ACTIVE',_binary '\0','2025-02-27 16:48:28.109036'),(7,'+94712345678','2025-02-27 16:31:09.948078','Galle','1993-04-04 00:00:00.000000','Rita_Bradtke74@hotmail.com',_binary '','ENGLISH','Christina Prohaska','CzdmPjF_ZnT81Um',659.63,'Prefer to teach IT courses.',3,'ACTIVE',_binary '\0','2025-02-27 16:48:37.811689'),(8,'+94712345678','2025-02-27 16:31:10.786592','Colombo','1993-04-04 00:00:00.000000','Robin71@yahoo.com',_binary '\0','ENGLISH','Linda Schuster','RY1m_gMVEcbaa99',2161.88,'Prefer to teach artificial intelligence courses.',2,'ACTIVE',_binary '\0','2025-02-27 16:48:47.826788'),(9,'+94712345678','2025-02-27 16:31:11.607386','Kandy','1993-04-04 00:00:00.000000','Stacey_Bartoletti@gmail.com',_binary '','ENGLISH','Miss Susan Mitchell','zKyK2t3v5b4Snyk',686.36,'Prefer to teach systems design courses.',4,'ACTIVE',_binary '\0','2025-02-27 16:49:04.388726'),(10,'+94712345678','2025-02-27 16:31:12.476044','Colombo','1993-04-04 00:00:00.000000','Arjun_Trantow88@gmail.com',_binary '','SINHALA','Luke Heidenreich','ZQD6PqTje47eqgj',731.66,'Prefer to teach algorithm courses.',5,'ACTIVE',_binary '\0','2025-02-27 16:49:17.468914'),(11,'+94712345678','2025-02-27 16:31:13.262898','Kurunagala','1993-04-04 00:00:00.000000','Clyde_Daniel@gmail.com',_binary '','SINHALA','Miss Edward Adams','Mbj8Xnt2SAreCdT',3771.79,'Prefer to teach coding theory courses.',5,'ACTIVE',_binary '\0','2025-02-27 16:49:29.349866'),(12,'+94712345678','2025-02-27 16:31:14.100787','Galle','1993-04-04 00:00:00.000000','Obie_Hansen@hotmail.com',_binary '\0','ENGLISH','Miss Angelo Turcotte','7EsolKyOcrNxxml',707.83,'Prefer to teach scripting courses.',2,'ACTIVE',_binary '\0','2025-02-27 16:49:43.115458'),(13,'+94712345678','2025-02-27 16:31:14.979378','Kandy','1993-04-04 00:00:00.000000','Janick.Nikolaus50@hotmail.com',_binary '\0','ENGLISH','Norma Rodriguez','BgK1Jr85IfpzYnJ',1614.70,'Prefer to teach game development courses.',3,'ACTIVE',_binary '\0','2025-02-27 16:49:53.314743'),(14,'+94712345678','2025-02-27 16:31:15.829884','Galle','1993-04-04 00:00:00.000000','Brayan21@yahoo.com',_binary '','ENGLISH','Paulette Hodkiewicz III','s61nuj5eKlH8uMB',560.19,'Prefer to teach data science courses.',4,'ACTIVE',_binary '\0','2025-02-27 16:51:30.504966'),(15,'+94712345678','2025-02-27 16:31:16.655423','Kurunagala','1993-04-04 00:00:00.000000','Felipe59@hotmail.com',_binary '\0','SINHALA','Leona Gutmann','glg6JGq8mVrUA6l',2254.76,'Prefer to teach artificial intelligence courses.',5,'ACTIVE',_binary '\0','2025-02-27 16:51:41.343488');
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logo`
--

DROP TABLE IF EXISTS `logo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `logo_path` varchar(400) DEFAULT NULL,
  `institute_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_glk4i5eqx8wonk46xbj5b9hm5` (`institute_id`),
  CONSTRAINT `FKgdrmi3wawef5by19x2a38q3p2` FOREIGN KEY (`institute_id`) REFERENCES `institute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logo`
--

LOCK TABLES `logo` WRITE;
/*!40000 ALTER TABLE `logo` DISABLE KEYS */;
INSERT INTO `logo` (`id`, `logo_path`, `institute_id`) VALUES (1,'institutes/41c73b90-6a88-42af-96ef-e31945ce14f0',1),(4,'institutes/594e3db5-f41a-419f-af3b-361e3d406188',3),(5,'institutes/08fa9ad0-92d2-46e7-b020-957f1a7403b3',4),(6,'institutes/7087ca62-4ba6-4a78-a196-5301372e77be',5),(8,'institutes/3c60e6ca-9b09-4388-a36c-5990fbe710f3',2);
/*!40000 ALTER TABLE `logo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `picture` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `picture_path` varchar(400) DEFAULT NULL,
  `lecturer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk6dc9go312fbf35d24xqtjgkt` (`lecturer_id`),
  CONSTRAINT `FKk6dc9go312fbf35d24xqtjgkt` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` (`id`, `picture_path`, `lecturer_id`) VALUES (1,'lecturers/cd2c056f-4479-4b98-8e99-17310cddce32',1),(2,'lecturers/c60025fb-9b6a-4ba9-af7b-a57a80dd5d7b',2),(3,'lecturers/24769e6d-3a10-4aca-9c44-679d6792ec39',3),(4,'lecturers/1c22ddd4-c964-4bc8-9eed-6de37308fc41',4),(5,'lecturers/f1ac2fc7-a67b-412c-bdf9-0d164e96beac',5),(6,'lecturers/48b37c8c-8837-4b6f-a861-724b3a85be88',6),(7,'lecturers/c7580365-3dce-4918-9ce0-b9951c48fbf9',7),(8,'lecturers/aae282b5-cbb3-4be9-bbc9-addb53f45148',8),(9,'lecturers/da12f097-4f2b-4a7a-ad4f-1c49b99f72dd',9),(10,'lecturers/75276fdb-7eba-4bfd-b311-94ec53cd1bf2',10),(11,'lecturers/fc8921a5-d36e-4b90-ad47-97742813f780',11),(12,'lecturers/de7afee6-c6c3-4d25-abb2-7afaf471cfea',12),(13,'lecturers/44659ce9-08f0-4675-879c-53b6079a0423',13),(14,'lecturers/f835dd4a-c6cd-4a6c-b8ad-ac41ed2e28b2',14),(15,'lecturers/20de9cf5-d01f-4dfa-9e4a-76677c6fca4c',15);
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `batch_id` varchar(255) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `duration_in_days` int DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `level` enum('MSC','BSC','HND','PGD','PHD') NOT NULL,
  `name` varchar(255) NOT NULL,
  `payment` decimal(19,2) NOT NULL,
  `student_count` int DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `institute_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK82uci5wjbamys0vveihnn8nba` (`institute_id`),
  CONSTRAINT `FK82uci5wjbamys0vveihnn8nba` FOREIGN KEY (`institute_id`) REFERENCES `institute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` (`id`, `batch_id`, `created_on`, `description`, `duration_in_days`, `language`, `level`, `name`, `payment`, `student_count`, `updated_on`, `institute_id`) VALUES (1,'CS-2025','2025-02-27 14:37:34.519255','A comprehensive Electronics program for undergraduate students.',500,'ENGLISH','MSC','Garden',1823.60,103,'2025-02-27 15:04:18.603516',1),(2,'CS-2025','2025-02-27 14:37:41.620173','A comprehensive Outdoors program for undergraduate students.',57,'ENGLISH','BSC','Clothing',835.64,77,'2025-02-27 15:04:42.414409',1),(3,'CS-2025','2025-02-27 14:37:43.307205','A comprehensive Clothing program for undergraduate students.',52,'ENGLISH','PGD','Music',2825.74,475,'2025-02-27 15:05:00.488414',1),(4,'CS-2025','2025-02-27 14:38:00.389963','A comprehensive Automotive program for undergraduate students.',90,'ENGLISH','HND','Industrial',282.33,55,'2025-02-27 15:05:37.062442',2),(5,'CS-2025','2025-02-27 14:38:01.768245','A comprehensive Garden program for undergraduate students.',214,'ENGLISH','HND','Electronics',531.80,180,'2025-02-27 15:05:51.340729',2),(6,'CS-2025','2025-02-27 14:38:03.970582','A comprehensive Beauty program for undergraduate students.',252,'ENGLISH','PHD','Movies',3000.00,911,'2025-02-27 15:06:05.736851',2),(7,'CS-2025','2025-02-27 15:12:55.753461','A comprehensive Health program for undergraduate students.',44,'ENGLISH','BSC','Grocery',851.47,413,'2025-02-27 15:14:05.121786',3),(8,'CS-2025','2025-02-27 15:12:56.908448','A comprehensive Automotive program for undergraduate students.',55,'ENGLISH','PHD','Outdoors',2000.00,453,'2025-02-27 15:14:20.487616',4),(9,'CS-2025','2025-02-27 15:12:58.061623','A comprehensive Music program for undergraduate students.',941,'ENGLISH','BSC','Sports',1101.41,48,'2025-02-27 15:14:43.959366',5),(10,'CS-2025','2025-02-27 15:13:02.635364','A comprehensive Books program for undergraduate students.',30,'ENGLISH','PHD','Toys',474.20,89,'2025-02-27 15:17:01.360980',3),(11,'CS-2025','2025-02-27 15:13:03.571783','A comprehensive Books program for undergraduate students.',388,'ENGLISH','HND','Music',1218.75,770,'2025-02-27 15:17:24.930557',4),(12,'CS-2025','2025-02-27 15:13:04.427728','A comprehensive Shoes program for undergraduate students.',62,'ENGLISH','BSC','Tools',3288.12,302,'2025-02-27 15:17:44.031815',5),(13,'CS-2025','2025-02-27 15:13:09.014190','A comprehensive Sports program for undergraduate students.',155,'ENGLISH','PHD','Baby',758.60,21,'2025-02-27 15:36:36.224382',4),(14,'CS-2025','2025-02-27 15:13:10.080097','A comprehensive Games program for undergraduate students.',47,'ENGLISH','BSC','Outdoors',834.70,60,'2025-02-27 15:36:54.546511',3),(15,'CS-2025','2025-02-27 15:13:11.437842','A comprehensive Jewelery program for undergraduate students.',114,'ENGLISH','HND','Music',2000.00,501,'2025-02-27 15:37:09.025226',4);
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program_subject`
--

DROP TABLE IF EXISTS `program_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_subject` (
  `program_id` bigint NOT NULL,
  `subject_id` bigint NOT NULL,
  PRIMARY KEY (`program_id`,`subject_id`),
  KEY `FKixwb6nirxkn0h0jjmk54iv6l2` (`subject_id`),
  CONSTRAINT `FK6u3q288n6sweoanwg12pyp2qm` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`),
  CONSTRAINT `FKixwb6nirxkn0h0jjmk54iv6l2` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_subject`
--

LOCK TABLES `program_subject` WRITE;
/*!40000 ALTER TABLE `program_subject` DISABLE KEYS */;
INSERT INTO `program_subject` (`program_id`, `subject_id`) VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(2,6),(3,7),(3,8),(3,9),(4,10),(4,11),(4,12),(5,13),(5,14),(5,15),(6,16),(6,17),(6,18),(7,19),(7,20),(7,21),(8,22),(8,23),(8,24),(9,25),(9,26),(9,27),(10,28),(10,29),(10,30),(11,31),(11,32),(11,33),(12,34),(12,35),(12,36),(13,37),(13,38),(13,39),(14,40),(14,41),(14,42),(15,43),(15,44),(15,45);
/*!40000 ALTER TABLE `program_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualification`
--

DROP TABLE IF EXISTS `qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qualification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `awarding_body` varchar(255) NOT NULL,
  `completed_at` datetime(6) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `discipline` varchar(255) DEFAULT NULL,
  `duration_in_days` int NOT NULL,
  `level` enum('MSC','BSC','HND','PGD','PHD') NOT NULL,
  `name` varchar(255) NOT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `lecturer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK567l13dyafxk7jxjw6rm1f60c` (`lecturer_id`),
  CONSTRAINT `FK567l13dyafxk7jxjw6rm1f60c` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` (`id`, `awarding_body`, `completed_at`, `created_on`, `discipline`, `duration_in_days`, `level`, `name`, `updated_on`, `lecturer_id`) VALUES (1,'Goyette - Harris','1991-04-04 00:00:00.000000','2025-02-27 16:28:11.181397','Outdoors',118,'MSC','Integration','2025-02-27 16:29:07.053614',1),(2,'Stroman - Volkman','1991-04-04 00:00:00.000000','2025-02-27 16:28:14.334311','Electronics',504,'PHD','Program','2025-02-27 16:28:14.334311',1),(3,'Witting, O\'Hara and Cummerata','1991-04-04 00:00:00.000000','2025-02-27 16:28:17.810549','Toys',398,'PGD','Optimization','2025-02-27 16:29:53.371652',1),(4,'Bartoletti - Nicolas','1991-04-04 00:00:00.000000','2025-02-27 16:30:15.135706','Jewelery',299,'HND','Implementation','2025-02-27 16:30:15.135706',1),(5,'Grimes Inc','1991-04-04 00:00:00.000000','2025-02-27 16:30:26.181056','Garden',908,'HND','Tactics','2025-02-27 16:30:26.181056',2),(6,'Botsford, Grimes and Effertz','1991-04-04 00:00:00.000000','2025-02-27 16:30:32.599058','Beauty',769,'BSC','Functionality','2025-02-27 16:30:32.599058',2),(7,'Kautzer - Mohr','1991-04-04 00:00:00.000000','2025-02-27 16:30:37.565578','Garden',265,'MSC','Factors','2025-02-27 16:30:37.565578',2),(8,'Johnston LLC','1991-04-04 00:00:00.000000','2025-02-27 16:37:01.917834','Outdoors',632,'PGD','Creative','2025-02-27 16:37:01.917834',3),(9,'Auer, Kulas and Hagenes','1991-04-04 00:00:00.000000','2025-02-27 16:37:06.842469','Health',947,'HND','Intranet','2025-02-27 16:37:06.842469',3),(10,'Wehner Inc','1991-04-04 00:00:00.000000','2025-02-27 16:37:16.460489','Electronics',745,'PHD','Markets','2025-02-27 16:37:16.460489',3),(11,'Armstrong, Padberg and Kovacek','1991-04-04 00:00:00.000000','2025-02-27 16:37:22.843197','Garden',508,'BSC','Configuration','2025-02-27 16:37:22.843197',4),(12,'Heaney, Durgan and Kunde','1991-04-04 00:00:00.000000','2025-02-27 16:37:27.789214','Clothing',39,'MSC','Program','2025-02-27 16:37:27.789214',4),(13,'Anderson - Ankunding','1991-04-04 00:00:00.000000','2025-02-27 16:37:59.119369','Shoes',10,'PHD','Directives','2025-02-27 16:37:59.119369',4),(14,'Krajcik Group','1991-04-04 00:00:00.000000','2025-02-27 16:38:14.725795','Jewelery',93,'BSC','Communications','2025-02-27 16:38:14.725795',5),(15,'Moore, Murphy and Torp','1991-04-04 00:00:00.000000','2025-02-27 16:38:25.141537','Automotive',571,'HND','Infrastructure','2025-02-27 16:38:25.141537',5),(16,'Marks - Zulauf','1991-04-04 00:00:00.000000','2025-02-27 16:38:29.570907','Movies',819,'PGD','Implementation','2025-02-27 16:38:29.570907',5),(17,'Kozey, Smitham and Moore','1991-04-04 00:00:00.000000','2025-02-27 16:38:33.631070','Shoes',953,'PGD','Interactions','2025-02-27 16:38:33.631070',6),(18,'Kreiger - Stamm','1991-04-04 00:00:00.000000','2025-02-27 16:38:37.678251','Garden',292,'BSC','Response','2025-02-27 16:38:37.678251',6),(19,'Rohan - Fadel','1991-04-04 00:00:00.000000','2025-02-27 16:38:43.716190','Movies',791,'MSC','Brand','2025-02-27 16:38:43.716190',6),(20,'Hammes - Farrell','1991-04-04 00:00:00.000000','2025-02-27 16:38:48.347166','Books',787,'MSC','Web','2025-02-27 16:38:48.347166',7),(21,'McCullough - Schowalter','1991-04-04 00:00:00.000000','2025-02-27 16:40:03.866035','Beauty',75,'PGD','Usability','2025-02-27 16:40:03.866035',7),(22,'Boyle, Hamill and Bednar','1991-04-04 00:00:00.000000','2025-02-27 16:40:16.450384','Baby',650,'MSC','Intranet','2025-02-27 16:40:16.450384',7),(23,'Tremblay Group','1991-04-04 00:00:00.000000','2025-02-27 16:40:24.283214','Clothing',66,'PHD','Solutions','2025-02-27 16:40:24.283214',8),(24,'Zboncak, Bradtke and Simonis','1991-04-04 00:00:00.000000','2025-02-27 16:40:28.248633','Grocery',252,'HND','Implementation','2025-02-27 16:40:28.248633',8),(25,'Becker - Cummings','1991-04-04 00:00:00.000000','2025-02-27 16:40:31.504660','Health',166,'BSC','Integration','2025-02-27 16:40:31.504660',8),(26,'McKenzie, Rempel and Brown','1991-04-04 00:00:00.000000','2025-02-27 16:40:34.826203','Automotive',414,'BSC','Factors','2025-02-27 16:40:34.826203',9),(27,'Emmerich LLC','1991-04-04 00:00:00.000000','2025-02-27 16:40:39.942143','Shoes',377,'PGD','Interactions','2025-02-27 16:40:39.942143',9),(28,'Wolff Inc','1991-04-04 00:00:00.000000','2025-02-27 16:40:43.575883','Industrial',133,'BSC','Intranet','2025-02-27 16:40:43.575883',9),(29,'Kuhic, Quigley and Walter','1991-04-04 00:00:00.000000','2025-02-27 16:40:52.382415','Movies',181,'MSC','Research','2025-02-27 16:40:52.382415',10),(30,'Langworth, Johnston and Carroll','1991-04-04 00:00:00.000000','2025-02-27 16:40:56.014108','Beauty',91,'BSC','Markets','2025-02-27 16:40:56.014108',10),(31,'Kovacek LLC','1991-04-04 00:00:00.000000','2025-02-27 16:40:59.944518','Tools',535,'PGD','Markets','2025-02-27 16:40:59.945518',10),(32,'Kovacek - Lind','1991-04-04 00:00:00.000000','2025-02-27 16:41:06.516473','Grocery',629,'MSC','Program','2025-02-27 16:41:06.516473',11),(33,'Mayert LLC','1991-04-04 00:00:00.000000','2025-02-27 16:41:10.270539','Electronics',500,'BSC','Directives','2025-02-27 16:41:10.270539',11),(34,'Cummings - Rippin','1991-04-04 00:00:00.000000','2025-02-27 16:41:13.811894','Automotive',517,'HND','Solutions','2025-02-27 16:41:13.811894',11),(35,'O\'Kon, Purdy and Reinger','1991-04-04 00:00:00.000000','2025-02-27 16:41:21.462727','Toys',136,'MSC','Interactions','2025-02-27 16:41:21.462727',12),(36,'Balistreri - Yost','1991-04-04 00:00:00.000000','2025-02-27 16:41:25.213745','Garden',152,'BSC','Group','2025-02-27 16:41:25.213745',12),(37,'Reichert Group','1991-04-04 00:00:00.000000','2025-02-27 16:41:29.310252','Kids',95,'PGD','Functionality','2025-02-27 16:41:29.310252',12),(38,'Rodriguez LLC','1991-04-04 00:00:00.000000','2025-02-27 16:41:36.178687','Tools',175,'MSC','Infrastructure','2025-02-27 16:41:36.178687',13),(39,'Greenfelder LLC','1991-04-04 00:00:00.000000','2025-02-27 16:41:40.093884','Beauty',205,'PHD','Accountability','2025-02-27 16:41:40.093884',13),(40,'Walter, Pfannerstill and Goyette','1991-04-04 00:00:00.000000','2025-02-27 16:41:43.685922','Industrial',241,'HND','Functionality','2025-02-27 16:41:43.685922',13),(41,'Wunsch Group','1991-04-04 00:00:00.000000','2025-02-27 16:41:49.774372','Clothing',419,'PGD','Paradigm','2025-02-27 16:41:49.774372',14),(42,'Kuvalis LLC','1991-04-04 00:00:00.000000','2025-02-27 16:41:53.258589','Games',231,'HND','Accounts','2025-02-27 16:41:53.258589',14),(43,'Auer, Lubowitz and Jones','1991-04-04 00:00:00.000000','2025-02-27 16:41:56.805460','Industrial',856,'BSC','Security','2025-02-27 16:41:56.805460',14),(44,'Hermann and Sons','1991-04-04 00:00:00.000000','2025-02-27 16:42:03.507591','Outdoors',499,'BSC','Security','2025-02-27 16:42:03.508680',15),(45,'Quigley - Rice','1991-04-04 00:00:00.000000','2025-02-27 16:42:07.519614','Sports',691,'PGD','Solutions','2025-02-27 16:42:07.519614',15),(46,'Schulist Group','1991-04-04 00:00:00.000000','2025-02-27 16:42:42.527356','Health',463,'HND','Identity','2025-02-27 16:42:42.527356',15);
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `is_assigned` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `no_of_credits` int NOT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `lecturer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpq5bne0271fskcowqjau4hx1o` (`lecturer_id`),
  CONSTRAINT `FKpq5bne0271fskcowqjau4hx1o` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` (`id`, `created_on`, `description`, `is_assigned`, `name`, `no_of_credits`, `updated_on`, `lecturer_id`) VALUES (1,'2025-02-27 14:44:18.203919','A comprehensive Sports subject for undergraduate students.',_binary '\0','Kids',2,'2025-02-27 16:16:35.096127',1),(2,'2025-02-27 14:44:27.488909','A comprehensive Automotive subject for undergraduate students.',_binary '\0','Industrial',3,'2025-02-27 16:25:27.634552',1),(3,'2025-02-27 14:44:31.048365','A comprehensive Shoes subject for undergraduate students.',_binary '\0','Movies',4,'2025-02-27 16:25:45.639102',1),(4,'2025-02-27 14:44:34.759829','A comprehensive Automotive subject for undergraduate students.',_binary '','Baby',2,'2025-02-27 16:25:55.088397',1),(5,'2025-02-27 14:44:38.482616','A comprehensive Industrial subject for undergraduate students.',_binary '','Shoes',3,'2025-02-27 16:26:02.065427',1),(6,'2025-02-27 14:44:49.635704','A comprehensive Home subject for undergraduate students.',_binary '','Outdoors',3,'2025-02-27 16:26:20.656584',1),(7,'2025-02-27 14:44:52.932070','A comprehensive Home subject for undergraduate students.',_binary '\0','Movies',4,'2025-02-27 16:26:28.800499',1),(8,'2025-02-27 14:44:57.308966','A comprehensive Games subject for undergraduate students.',_binary '\0','Kids',4,'2025-02-27 16:26:35.384147',2),(9,'2025-02-27 14:45:00.895445','A comprehensive Clothing subject for undergraduate students.',_binary '\0','Baby',1,'2025-02-27 16:27:08.528805',2),(10,'2025-02-27 14:45:05.855139','A comprehensive Shoes subject for undergraduate students.',_binary '\0','Tools',3,'2025-02-27 16:27:14.790722',2),(11,'2025-02-27 14:45:09.994247','A comprehensive Sports subject for undergraduate students.',_binary '','Toys',3,'2025-02-27 16:31:34.396277',3),(12,'2025-02-27 14:45:13.600896','A comprehensive Grocery subject for undergraduate students.',_binary '','Garden',2,'2025-02-27 16:31:40.466805',3),(13,'2025-02-27 14:45:18.028897','A comprehensive Electronics subject for undergraduate students.',_binary '\0','Automotive',4,'2025-02-27 16:31:49.737343',4),(14,'2025-02-27 14:45:21.814937','A comprehensive Electronics subject for undergraduate students.',_binary '','Baby',3,'2025-02-27 16:31:56.469803',4),(15,'2025-02-27 14:45:30.526069','A comprehensive Clothing subject for undergraduate students.',_binary '\0','Baby',2,'2025-02-27 16:32:04.688162',4),(16,'2025-02-27 14:45:34.447601','A comprehensive Sports subject for undergraduate students.',_binary '','Shoes',3,'2025-02-27 16:32:13.724909',4),(17,'2025-02-27 14:45:37.939478','A comprehensive Grocery subject for undergraduate students.',_binary '\0','Music',2,'2025-02-27 16:32:23.666588',5),(18,'2025-02-27 14:45:41.910626','A comprehensive Clothing subject for undergraduate students.',_binary '\0','Baby',4,'2025-02-27 16:32:31.565170',5),(19,'2025-02-27 14:45:45.288815','A comprehensive Home subject for undergraduate students.',_binary '','Health',3,'2025-02-27 16:32:37.543002',5),(20,'2025-02-27 14:45:48.807413','A comprehensive Health subject for undergraduate students.',_binary '','Games',2,'2025-02-27 16:32:46.593550',6),(21,'2025-02-27 14:45:52.228663','A comprehensive Toys subject for undergraduate students.',_binary '\0','Movies',3,'2025-02-27 16:32:53.179945',6),(22,'2025-02-27 14:45:56.572783','A comprehensive Outdoors subject for undergraduate students.',_binary '\0','Sports',4,'2025-02-27 16:32:59.674969',6),(23,'2025-02-27 14:46:01.631429','A comprehensive Garden subject for undergraduate students.',_binary '\0','Kids',4,'2025-02-27 16:33:05.061956',6),(24,'2025-02-27 14:46:05.463549','A comprehensive Jewelery subject for undergraduate students.',_binary '','Music',2,'2025-02-27 16:33:19.555375',7),(25,'2025-02-27 14:46:09.135659','A comprehensive Beauty subject for undergraduate students.',_binary '','Baby',4,'2025-02-27 16:33:25.930413',7),(26,'2025-02-27 14:46:15.000710','A comprehensive Jewelery subject for undergraduate students.',_binary '\0','Baby',2,'2025-02-27 16:33:32.193438',7),(27,'2025-02-27 14:46:18.924096','A comprehensive Toys subject for undergraduate students.',_binary '\0','Kids',3,'2025-02-27 16:33:41.686658',8),(28,'2025-02-27 14:46:23.834876','A comprehensive Tools subject for undergraduate students.',_binary '','Industrial',4,'2025-02-27 16:33:48.466267',8),(29,'2025-02-27 14:46:27.271208','A comprehensive Garden subject for undergraduate students.',_binary '\0','Grocery',2,'2025-02-27 16:33:54.615826',8),(30,'2025-02-27 14:46:30.681632','A comprehensive Books subject for undergraduate students.',_binary '','Health',1,'2025-02-27 16:34:03.567055',9),(31,'2025-02-27 14:46:36.821131','A comprehensive Shoes subject for undergraduate students.',_binary '','Toys',2,'2025-02-27 16:34:09.060431',9),(32,'2025-02-27 14:46:40.686400','A comprehensive Movies subject for undergraduate students.',_binary '\0','Home',3,'2025-02-27 16:34:15.848008',9),(33,'2025-02-27 14:46:44.378572','A comprehensive Garden subject for undergraduate students.',_binary '\0','Computers',3,'2025-02-27 16:34:22.497347',10),(34,'2025-02-27 14:46:47.914623','A comprehensive Garden subject for undergraduate students.',_binary '\0','Industrial',4,'2025-02-27 16:34:28.134531',10),(35,'2025-02-27 14:46:54.061918','A comprehensive Baby subject for undergraduate students.',_binary '\0','Garden',1,'2025-02-27 16:34:41.094122',10),(36,'2025-02-27 14:46:58.085082','A comprehensive Clothing subject for undergraduate students.',_binary '\0','Electronics',2,'2025-02-27 16:34:49.664210',10),(37,'2025-02-27 14:47:02.647060','A comprehensive Clothing subject for undergraduate students.',_binary '\0','Music',2,'2025-02-27 16:34:55.760694',11),(38,'2025-02-27 14:47:07.595883','A comprehensive Electronics subject for undergraduate students.',_binary '','Outdoors',3,'2025-02-27 16:35:01.481191',11),(39,'2025-02-27 14:47:11.240758','A comprehensive Sports subject for undergraduate students.',_binary '\0','Industrial',4,'2025-02-27 16:35:07.744648',11),(40,'2025-02-27 14:47:15.328793','A comprehensive Computers subject for undergraduate students.',_binary '\0','Toys',2,'2025-02-27 16:35:24.886370',12),(41,'2025-02-27 14:47:19.327411','A comprehensive Movies subject for undergraduate students.',_binary '\0','Beauty',3,'2025-02-27 16:35:31.766885',12),(42,'2025-02-27 14:47:23.009652','A comprehensive Garden subject for undergraduate students.',_binary '\0','Automotive',2,'2025-02-27 16:35:37.789858',12),(43,'2025-02-27 15:01:29.512327','A back-end Shoes subject for USB students.',_binary '','Shoes',2,'2025-02-27 15:01:29.512327',11),(44,'2025-02-27 15:01:33.911983','A comprehensive Toys subject for undergraduate students.',_binary '','Games',3,'2025-02-27 16:35:48.270441',13),(45,'2025-02-27 15:01:38.235207','A comprehensive Beauty subject for undergraduate students.',_binary '\0','Baby',4,'2025-02-27 16:35:53.774255',13),(46,'2025-02-27 15:01:42.032140','A comprehensive Health subject for undergraduate students.',_binary '\0','Industrial',2,'2025-02-27 16:36:03.952275',14),(47,'2025-02-27 15:01:49.042192','A comprehensive Books subject for undergraduate students.',_binary '\0','Movies',2,'2025-02-27 16:36:07.088959',14),(48,'2025-02-27 15:01:54.117504','A comprehensive Health subject for undergraduate students.',_binary '\0','Garden',3,'2025-02-27 16:36:14.264703',14),(49,'2025-02-27 15:01:57.691246','A comprehensive Kids subject for undergraduate students.',_binary '\0','Music',3,'2025-02-27 16:36:20.132566',15),(50,'2025-02-27 15:02:01.264298','A comprehensive Beauty subject for undergraduate students.',_binary '','Home',2,'2025-02-27 16:36:27.923022',15),(51,'2025-02-27 15:02:06.390182','A haptic Music subject for EXE students.',_binary '\0','Industrial',3,'2025-02-27 15:02:06.390182',7);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-27 18:18:28
