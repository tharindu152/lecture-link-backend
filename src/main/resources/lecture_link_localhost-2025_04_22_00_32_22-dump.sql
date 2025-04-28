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
  `current_rating` int DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `division` varchar(500) NOT NULL,
  `email` varchar(255) NOT NULL,
  `maps_location` varchar(1500) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ratings_received` int DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `subscribed` bit(1) DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institute`
--

LOCK TABLES `institute` WRITE;
/*!40000 ALTER TABLE `institute` DISABLE KEYS */;
INSERT INTO `institute` (`id`, `created_on`, `current_rating`, `description`, `division`, `email`, `maps_location`, `name`, `password`, `ratings_received`, `status`, `subscribed`, `telephone`, `updated_on`) VALUES (1,'2025-04-20 01:19:10.809006',5,'ECU in Colombo is one of the only universities in Sri Lanka where you can obtain an international degree. Whether you choose to study your qualification in Sri Lanka or transition from first-year studies to your second year in Australia, ECU offers a seamless pathway to completing your degree and enjoying a truly global experience.','Kotte','info@ecu.edu1.lk','6.906514817749689, 79.9021367','Edith Cowan University - Sri Lanka','JSpm1D6lRvkwPrO',1,'ACTIVE',_binary '','+94112555123','2025-04-21 17:16:22.425118'),(2,'2025-04-20 10:27:50.652630',5,'The Overseas Campus of Ceylon is dedicated to fostering growth, excellence, and innovation across all areas of our organization. As we look ahead, we remain steadfast in our goal to empower our teams with the tools, knowledge, and inspiration they need to excel.','Maharagama','info@overseascampus.edu1.lk','6.862186695535949, 79.90425352294366','Overseas Campus of Ceylon','JSpm1D6lRvkwPr1',2,'ACTIVE',_binary '\0','0776418991','2025-04-21 16:18:29.488369'),(5,'2025-04-20 16:53:27.885295',4,'With the modern facilities offered, Summerset is the place to study in the 21st century. Summerset students can study Language Courses, Information Technology Courses, Business Management Courses, Hospitality Management Courses and Health Care Courses etc..  We can put you in the front seat where ever you go.','Maharagama','admin@summerset1.lk','6.849297336791056, 79.9238418432685','Summerset Campus','JSpm1D6lRvkwPr2',3,'ACTIVE',_binary '','0112850960','2025-04-21 22:17:20.349670'),(7,'2025-04-21 01:38:23.417955',4,'Since its inception, the group has grown leaps & bounds and achieved exemplary recognition from the Industry & Academia. At BIET, we are committed to provide a value driven culture along with creating a professional environment.','Dehiwala','info@biet.edu.lk','6.882417972910693, 79.85948317435276','British Institute of Engineering & Technology','JSpm1D6lRvkwPr3',1,'ACTIVE',_binary '\0','0718734226','2025-04-21 01:43:54.971157'),(8,'2025-04-21 01:54:35.778849',4,'At LPEC, we empower students to realize their full potential through a personalized, self-directed approach to education. Join us today and discover the world of opportunities that awaits you.','Kesbewa','lpec.general@gmail.com','6.977827214636852, 79.92985928594739','LPEC Campus','JSpm1D6lRvkwPr4',1,'ACTIVE',_binary '','0112907420','2025-04-21 23:12:18.055598'),(9,'2025-04-21 02:09:30.205285',3,'A contemporary educational establishment providing courses in practical training across various fields. We equip students with the necessary expertise for employment in Sri Lanka and the global arena. Our teaching methodology blends classroom teachings with extensive hands-on practice, ensuring graduates understand not just the theory but also the real-world application of their skills.','Kaduwela','info@europeancitycampus.lk','7.033030172875329, 79.89870424809436','European city campus','JSpm1D6lRvkwPr5',1,'ACTIVE',_binary '\0','+94112555123','2025-04-21 02:10:44.528924');
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
  `current_rating` int DEFAULT NULL,
  `division` varchar(500) NOT NULL,
  `email` varchar(255) NOT NULL,
  `field_of_work` varchar(255) DEFAULT NULL,
  `hourly_pay_rate` decimal(19,2) DEFAULT NULL,
  `is_assigned` bit(1) DEFAULT NULL,
  `language` enum('ENGLISH','SINHALA','TAMIL') DEFAULT NULL,
  `lecturing_experience` int DEFAULT NULL,
  `maps_location` varchar(1500) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `preference` varchar(255) DEFAULT NULL,
  `ratings_received` int DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `subscribed` bit(1) DEFAULT NULL,
  `time_preference` enum('WEEKDAY','WEEKEND','FLEXIBLE') DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` (`id`, `contact_no`, `created_on`, `current_rating`, `division`, `email`, `field_of_work`, `hourly_pay_rate`, `is_assigned`, `language`, `lecturing_experience`, `maps_location`, `name`, `password`, `preference`, `ratings_received`, `status`, `subscribed`, `time_preference`, `updated_on`) VALUES (1,'+94766046486','2025-04-20 01:19:54.724663',4,'Maharagama','chanaka152@gmail.com','Software Engineering',3000.00,_binary '','ENGLISH',3,'6.865954562481234, 79.90339948899265','Chanaka Bandara','TxugJHHIIGzMd3b','Computer Science, Business Management',0,'ACTIVE',_binary '','WEEKEND','2025-04-20 22:26:53.483587'),(2,'','2025-04-20 02:52:03.497815',3,'Kotte','a.jayarathn@ecu.edu.au','Software Engineering',3000.00,_binary '','ENGLISH',2,'6.840385929764893, 79.96600347419248','Akila Jayarathna','TxugJHHIIGzMd31','Computer Science',4,'ACTIVE',_binary '','WEEKDAY','2025-04-20 15:33:28.753808'),(3,'','2025-04-20 20:44:04.247452',5,'Homagama','vimukt@summerset.lk','Project Management, Data Science',2000.00,_binary '','ENGLISH',9,'6.905897000307382, 79.97041042962894','Vimukthi Sripa','TxugJHHIIGzMd32','Cyber Security, Data Science',1,'ACTIVE',_binary '','WEEKEND','2025-04-21 22:54:21.656144'),(4,NULL,'2025-04-20 21:13:13.106629',4,'Seethawaka','gangadar@summerset.lk','Project Management',2000.00,_binary '','ENGLISH',1,'6.956909244433099, 79.92125894149095','Gangadara Athukorala','TxugJHHIIGzMd33','Project Management, Cyber Security',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-20 22:22:17.540197'),(5,NULL,'2025-04-20 21:19:41.012603',5,'Padukka','chiranth@summerset.lk','Software Engineering',2000.00,_binary '','ENGLISH',8,'6.8455171181712355, 80.10359304437353','Chirantha Jananath Thiwanka Kithulwatta','TxugJHHIIGzMd34','Computer Science',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-20 22:23:23.684938'),(6,NULL,'2025-04-20 23:22:31.806681',1,'Kolonnawa','maneesh@summerset.lk','Project Management',2000.00,_binary '','ENGLISH',3,'6.961475420258692, 80.21079135895982','Maneesha Dedigama','TxugJHHIIGzMd35','Information Technology, Project Management',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-21 01:17:04.608901'),(7,NULL,'2025-04-20 23:33:08.299224',1,'Homagama','ardithy@summerset.lk','Software Engineering',2500.00,_binary '','ENGLISH',1,'6.821504872657783, 80.04227027430144','Akila Adithya Sugathadasa','TxugJHHIIGzMd36','Computer Science, Business Management',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-21 01:20:57.938735'),(8,NULL,'2025-04-20 23:44:24.832123',1,'Moratuwa','Yehash@summerset.lk','Human Resource Management, Information Technology, Business Analysis',2000.00,_binary '','ENGLISH',4,'6.788735497589927, 79.89170998502411','Yehasha Harshani','TxugJHHIIGzMd37','Human Resource Management, Information Technology',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-21 01:30:06.593295'),(9,NULL,'2025-04-21 00:07:14.600799',1,'Thimbirigasyaya','Sherin@summerset.lk','Software Engineering',2000.00,_binary '','ENGLISH',8,'6.894538988686971, 79.8800423155316','Sherina Sally','TxugJHHIIGzMd38','Computer Science',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-21 01:31:37.690440'),(10,'778552643','2025-04-21 00:16:00.429629',3,'Kaduwela','nvsbimsara@gmail.com','Textile Process & Apparel Engineering',1500.00,_binary '','ENGLISH',1,'7.111763926876942, 79.89721758964309','Sajani Bimsara','TxugJHHIIGzMd39','Business Management',1,'ACTIVE',_binary '\0','WEEKDAY','2025-04-21 01:52:42.108579'),(11,NULL,'2025-04-21 00:21:27.864782',5,'Ratmalana','mahanamahew@yahoo.com','Law, Accademics',10000.00,_binary '','ENGLISH',20,'6.706049248362063, 80.06985648169287','Prof. Prathiba Mahanamahewa','TxugJHHIIGzMd40','Law',1,'ACTIVE',_binary '\0','WEEKEND','2025-04-21 10:03:29.177866');
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
  KEY `FKgdrmi3wawef5by19x2a38q3p2` (`institute_id`),
  CONSTRAINT `FKgdrmi3wawef5by19x2a38q3p2` FOREIGN KEY (`institute_id`) REFERENCES `institute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logo`
--

LOCK TABLES `logo` WRITE;
/*!40000 ALTER TABLE `logo` DISABLE KEYS */;
INSERT INTO `logo` (`id`, `logo_path`, `institute_id`) VALUES (7,'institutes/3bea8ff0-2af0-4a85-84a5-fee9e8cd7191',2),(8,'institutes/6ddc185a-dbf7-4bf9-99f8-02d2087fb4fa',5),(9,'institutes/fecc52bc-701d-4a6d-9fe4-a57e1f5f5f9d',7),(11,'institutes/37786388-18bb-4827-81b5-95b6845a2bde',9),(14,'institutes/146c65ec-2feb-47f9-93b9-9d566e1f7cc9',1),(16,'institutes/05ac428f-3bba-406a-a0aa-5eebe2115724',8);
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` (`id`, `picture_path`, `lecturer_id`) VALUES (2,'lecturers/083c04b6-9e04-4c2c-b5a0-675778e17552',2),(3,'lecturers/46683508-feaf-43d0-8f83-dce2be25c54a',3),(4,'lecturers/6edd9126-dc6d-4ab9-a1e6-7c88e0ecce8c',4),(5,'lecturers/93ba83ad-cce0-484d-bc7e-5ce3f0774254',5),(6,'lecturers/42b2d9ba-0671-41af-a324-ce24d74eed8e',1),(7,'lecturers/217adb44-2131-48e2-b546-d9541b2ed413',6),(8,'lecturers/77a74932-7a81-4164-857d-17d42110048b',7),(9,'lecturers/50f227d5-5d13-4cb5-91e3-f08e90946418',8),(10,'lecturers/0f25c998-1110-45f6-98fe-96b3a4ddc033',9),(11,'lecturers/483f5c28-3f0a-4bd5-a1f3-cfa2fdc65877',10),(12,'lecturers/b989099b-17c0-4f5b-bdb9-6848af0e4690',11);
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
  `hourly_pay_rate` decimal(19,2) NOT NULL,
  `language` enum('ENGLISH','SINHALA','TAMIL') NOT NULL,
  `level` enum('DOCTORATE','MASTERS','POSTGRADUATE','BACHELORS','HND','HNC') NOT NULL,
  `name` varchar(255) NOT NULL,
  `student_count` int DEFAULT NULL,
  `time_preference` enum('WEEKEND','WEEKDAY','FLEXIBLE') NOT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `institute_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK82uci5wjbamys0vveihnn8nba` (`institute_id`),
  CONSTRAINT `FK82uci5wjbamys0vveihnn8nba` FOREIGN KEY (`institute_id`) REFERENCES `institute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` (`id`, `batch_id`, `created_on`, `description`, `duration_in_days`, `hourly_pay_rate`, `language`, `level`, `name`, `student_count`, `time_preference`, `updated_on`, `institute_id`) VALUES (1,'MBA1','2025-04-20 11:08:14.879768','The Master of Business Administration in Supply Chain Management equips students with advanced knowledge and skills in logistics, procurement, operations, and strategic supply chain planning. It prepares professionals to optimize resources, manage global supply networks, and drive efficiency and innovation across business processes, ensuring competitive advantage in dynamic markets.',730,2300.00,'ENGLISH','MASTERS','Master of Business Administration in Supply Chain Management',40,'WEEKEND','2025-04-20 11:11:00.081867',2),(2,'BCOM1','2025-04-20 11:25:48.797090','The Bachelor of Commerce (B.Com) degree provides a strong foundation in business, finance, accounting, economics, and management. It equips students with analytical, communication, and problem-solving skills, preparing them for diverse careers in commerce, banking, entrepreneurship, and corporate sectors, or for further studies in professional and postgraduate programs.',1095,3300.00,'ENGLISH','BACHELORS','Bachelor of Commerce',160,'WEEKEND','2025-04-20 13:15:43.473438',1),(3,'CS1','2025-04-20 11:27:03.142616','The Bachelor of Computer Science degree offers comprehensive knowledge in programming, algorithms, data structures, databases, software engineering, and computer systems. It equips students with critical thinking and problem-solving skills, preparing them for careers in software development, IT, cybersecurity, data science, and advanced studies in computing and technology fields.',730,3300.00,'ENGLISH','BACHELORS','Bachelor of Computer Science',40,'WEEKEND','2025-04-20 15:00:39.882202',1),(5,'HND B1','2025-04-20 17:00:52.463846','The OTHM Level 5 Higher National Diploma (HND) in Information Technology provides learners with comprehensive knowledge in software development, networking, databases, and IT systems. It equips students with practical and theoretical skills needed for careers in the tech industry or progression to advanced studies in computing and information technology fields.',365,2000.00,'ENGLISH','HND','OTHM HND Information Technology',50,'WEEKEND','2025-04-20 20:17:42.268231',5),(6,'PGD B1','2025-04-20 22:56:29.429330','The Postgraduate Diploma in Information Technology equips graduates with advanced knowledge in areas such as software development, networking, cybersecurity, data management, and IT project management. It is designed to enhance technical and analytical skills, preparing students for professional roles in the IT industry or further academic progression in computing fields.',365,2000.00,'ENGLISH','POSTGRADUATE','PostGraduate Diploma in Information Technology',50,'WEEKEND','2025-04-20 23:13:57.990496',5),(7,'HDET','2025-04-21 01:45:43.745178','The Higher Diploma in Engineering Technology provides in-depth knowledge and practical skills in areas such as electronics, mechanics, and industrial systems. It prepares students for technical roles in engineering fields by focusing on problem-solving, system design, and applied technology, bridging the gap between theoretical engineering concepts and real-world applications.',270,1500.00,'ENGLISH','HND','Higher Diploma in Engineering Technology',20,'WEEKDAY','2025-04-21 01:49:00.132417',7),(8,'HDME','2025-04-21 01:46:52.463892','The Higher Diploma in Mechanical Engineering equips students with practical and theoretical knowledge in mechanics, thermodynamics, materials science, and machine design. It focuses on developing technical problem-solving skills and understanding mechanical systems, preparing graduates for careers in manufacturing, automotive, energy, and other engineering industries or for further academic advancement.',365,2000.00,'ENGLISH','HND','Higher Diploma in Mechanical Engineering',50,'WEEKEND','2025-04-21 01:51:13.177571',7),(9,'BBA1','2025-04-21 01:58:20.465204','The Bachelor of Business Administration (BBA) provides comprehensive knowledge in business principles, including management, marketing, finance, and human resources. It develops leadership, analytical, and communication skills, preparing students for diverse careers in business and entrepreneurship. The program lays a strong foundation for strategic decision-making and future postgraduate or professional studies.',1095,2500.00,'ENGLISH','BACHELORS','Bachelor of Business Administration',200,'WEEKDAY','2025-04-21 02:01:00.293130',8),(10,'BIT1','2025-04-21 01:59:15.043127','The Bachelor of Information Technology (BIT) provides in-depth knowledge of computer systems, programming, networking, databases, and cybersecurity. It equips students with technical and problem-solving skills to develop and manage IT solutions. Graduates are prepared for careers in software development, system administration, IT support, and emerging technology-driven industries.',1095,2500.00,'ENGLISH','BACHELORS','Bachelor of Information Technology',200,'WEEKDAY','2025-04-21 02:02:06.136614',8),(11,'LAW1','2025-04-21 02:12:06.588763','The PhD in Law program is a research-intensive degree focused on advanced legal theory, policy, and practice. It enables scholars to explore complex legal issues, contribute original research, and influence legal development. Graduates pursue careers in academia, legal consultancy, judiciary, or high-level policy and legislative advisory roles globally.',1825,8000.00,'ENGLISH','DOCTORATE','PhD in Law',5,'WEEKEND','2025-04-21 02:13:36.041938',9),(12,'FDIT1','2025-04-21 23:47:49.828707','The Foundation Diploma in Information Technology provides essential knowledge and skills in computing, programming, and digital systems. It prepares students for further study or entry-level roles in the IT field. The program builds a strong foundation in technology concepts, problem-solving, and practical applications in today\'s digital environment.',365,2000.00,'ENGLISH','HNC','Foundation Diploma Information Technology',50,'WEEKEND','2025-04-21 23:51:08.116141',8);
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
INSERT INTO `program_subject` (`program_id`, `subject_id`) VALUES (1,1),(2,3),(2,5),(3,6),(3,12),(5,32),(5,33),(5,34),(5,35),(5,36),(6,37),(6,38),(6,39),(6,40),(6,41),(6,42),(6,43),(7,44),(8,44),(9,45),(10,46),(11,47),(12,48),(12,49);
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
  `level` enum('DOCTORATE','MASTERS','POSTGRADUATE','BACHELORS','HND','HNC') NOT NULL,
  `name` varchar(255) NOT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `lecturer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK567l13dyafxk7jxjw6rm1f60c` (`lecturer_id`),
  CONSTRAINT `FK567l13dyafxk7jxjw6rm1f60c` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` (`id`, `awarding_body`, `completed_at`, `created_on`, `discipline`, `duration_in_days`, `level`, `name`, `updated_on`, `lecturer_id`) VALUES (1,'Kotelawala Defence University','2015-10-21 00:00:00.000000','2025-04-20 01:30:47.514317','Business Management',1460,'BACHELORS','BSc in Management and Technical Sciences','2025-04-20 02:28:04.554766',1),(2,'University of Colombo','2022-12-14 00:00:00.000000','2025-04-20 01:31:51.510446','Business Management',730,'MASTERS','MBA in Human Resources Management','2025-04-20 01:31:51.510446',1),(3,'University of Colombo School of Computing','2021-04-08 00:00:00.000000','2025-04-20 02:55:22.999793','Computer Science',1095,'MASTERS','Master\'s degree, Information Technology','2025-04-20 02:58:01.987104',2),(4,'University of Colombo','2019-06-20 00:00:00.000000','2025-04-20 02:56:11.406059','Computer Science',1095,'BACHELORS','Bachelor of Information and Communication Technology','2025-04-20 02:56:11.406059',2),(5,'Informatics Institute of Technology','2025-09-19 00:00:00.000000','2025-04-20 16:50:23.151273','Computer Science',1095,'MASTERS','MSc IT','2025-04-20 16:50:23.151273',1),(6,'Institute of Software Engineering','2023-12-08 00:00:00.000000','2025-04-20 16:51:33.430060','Software Engineering',210,'POSTGRADUATE','Dip in Java','2025-04-20 16:51:33.430060',1),(7,'Keele University','2017-01-10 00:00:00.000000','2025-04-20 20:48:55.841300','Business Management',730,'MASTERS','MSc in Project Management','2025-04-20 20:48:55.841300',3),(8,'SLIIT','2015-02-16 00:00:00.000000','2025-04-20 20:49:49.336878','Information Technology',1460,'BACHELORS','B.Sc. Special Hons in Information Technology','2025-04-20 20:49:49.336878',3),(9,'Cardiff Metropolitan University','2025-02-11 00:00:00.000000','2025-04-20 21:16:51.238332','Business Management',1095,'MASTERS','MBA in Project Management','2025-04-20 21:16:51.238332',4),(10,'NSBM Green University','2023-02-07 00:00:00.000000','2025-04-20 21:17:46.260190','Computer Science',1460,'BACHELORS','Management Information Systems','2025-04-20 21:17:46.260190',4),(11,'University of Kelaniya Sri Lanka','2024-10-11 00:00:00.000000','2025-04-20 21:25:03.202391','Business Management',30,'HNC','Certificate in ERP Basics with SAP','2025-04-20 21:25:03.202391',5),(12,'Sabaragamuwa University of Sri Lanka','2023-01-20 00:00:00.000000','2025-04-20 21:26:41.807111','Computer Science',1095,'MASTERS','Master of Philosophy in Computer Science','2025-04-20 21:26:41.807111',5),(13,'University of Kelaniya Sri Lanka','2020-02-13 00:00:00.000000','2025-04-20 21:27:33.292463','Software Engineering',1460,'BACHELORS','Bachelor of Science Honours in Software Engineering','2025-04-20 21:27:33.292463',5),(14,'IMBS Green Campus - Sri Lanka','2019-03-14 00:00:00.000000','2025-04-20 21:28:29.425092','Business Management',730,'HND','Diploma in Human Resource Management','2025-04-20 21:28:29.425092',5),(15,'University of Kelaniya Sri Lanka','2016-07-15 00:00:00.000000','2025-04-20 21:29:16.665472','Computer Science',365,'HNC','Certificate course in Advanced Networking','2025-04-20 21:29:16.665472',5),(16,'University of Kelaniya Sri Lanka','2026-09-18 00:00:00.000000','2025-04-20 23:26:52.571646','Business Administration',730,'MASTERS','Master of Business Administration','2025-04-20 23:26:52.571646',6),(17,'Rajarata University of Sri Lanka','2020-03-11 00:00:00.000000','2025-04-20 23:28:16.853836','Information Technology',1095,'BACHELORS','B.Sc. Information and Communication Technology','2025-04-20 23:28:16.853836',6),(18,'Project Management Institute','2024-02-13 00:00:00.000000','2025-04-20 23:31:37.250348','Project Management',60,'HNC','Certificate of Project Management Training for PMP Certification','2025-04-20 23:31:37.250348',6),(19,'University of Plymouth','2016-02-10 00:00:00.000000','2025-04-20 23:39:43.833517','Software Engineering',730,'BACHELORS','B.Sc. in Software Engineering','2025-04-20 23:39:43.834522',7),(20,'ESOFT Metro Campus','2017-08-17 00:00:00.000000','2025-04-20 23:40:36.870841','Business Administration',1,'HND','Diploma in Business Management','2025-04-20 23:40:36.870841',7),(21,'SLIIT','2024-01-10 00:00:00.000000','2025-04-20 23:48:56.844332','Information Technology',730,'MASTERS','MSc. in Information Management','2025-04-20 23:48:56.844332',8),(22,'University of Moratuwa','2020-07-16 00:00:00.000000','2025-04-20 23:49:44.593475','Information Technology',1460,'BACHELORS','BSc.(Hons) Information Technology and Management','2025-04-20 23:49:44.593475',8),(23,'Chartered Institute of Personnel Management (CIPM)','2015-10-14 00:00:00.000000','2025-04-20 23:50:40.146008','Human Resource Management',365,'HND','Chartered Institute of Personnel Management','2025-04-20 23:50:40.146008',8),(24,'University of Colombo School of Computing','2018-07-19 00:00:00.000000','2025-04-21 00:10:41.923066','Computer Science',730,'MASTERS','Master\'s degree, Computer Science','2025-04-21 00:10:41.923066',9),(25,'University of Sri Jayewardenepura','2014-01-29 00:00:00.000000','2025-04-21 00:11:26.921163','Computer Science',1460,'BACHELORS','Bachelor\'s degree, Computer Science','2025-04-21 00:11:26.921163',9),(26,'University of Moratuwa','2017-08-17 00:00:00.000000','2025-04-21 00:18:49.737740','Textile Engineering',1460,'BACHELORS','BSc Textile Process Engineering','2025-04-21 00:18:49.737740',10),(27,'University of Kelaniya','2020-09-16 00:00:00.000000','2025-04-21 00:19:42.010219','Business Administration',1825,'MASTERS','Master of Business Administration','2025-04-21 00:19:42.010219',10),(28,'University of Queensland, Australia','2006-07-13 00:00:00.000000','2025-04-21 00:25:07.835788','Law',1825,'DOCTORATE','Ph.D in Law, Information Technology Law','2025-04-21 00:25:07.835788',11);
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
  `is_assigned` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `no_of_credits` int NOT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `lecturer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpq5bne0271fskcowqjau4hx1o` (`lecturer_id`),
  CONSTRAINT `FKpq5bne0271fskcowqjau4hx1o` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` (`id`, `created_on`, `description`, `is_assigned`, `name`, `no_of_credits`, `updated_on`, `lecturer_id`) VALUES (1,'2025-04-20 11:11:00.027432','Business Statistics involves the application of statistical methods to analyze and interpret data for informed business decision-making. It includes topics like data collection, probability, regression, and hypothesis testing, helping organizations identify trends, forecast outcomes, and improve operations through quantitative analysis and evidence-based strategies in various business environments.',_binary '','Business Statistics',4,'2025-04-20 11:11:00.027432',1),(3,'2025-04-20 11:29:44.619032','Strategic Management involves the formulation, implementation, and evaluation of strategies to achieve organizational goals. It focuses on analyzing internal and external environments, setting objectives, and allocating resources efficiently. Strategic management enables businesses to adapt to change, gain competitive advantage, and ensure long-term success through effective planning and decision-making.',_binary '','Strategic Management',3,'2025-04-20 11:29:44.619032',1),(5,'2025-04-20 13:15:43.439336','Human Resource Management (HRM) focuses on recruiting, developing, and managing an organization’s workforce. It involves employee relations, performance management, training, compensation, and compliance with labor laws. HRM plays a strategic role in fostering a positive work environment, enhancing productivity, and aligning human capital with organizational goals for sustained success.',_binary '','Human Resource Management',2,'2025-04-20 13:15:43.439336',1),(6,'2025-04-20 13:25:41.378057','Object-Oriented Analysis and Design (OOAD) involves analyzing and designing a system using object-oriented concepts like classes and objects to improve modularity, scalability, and maintainability in software development.',_binary '','Object Oriented Analysis and Design',2,'2025-04-20 13:25:41.378057',2),(12,'2025-04-20 15:00:39.820215','Object-Oriented Programming with C++ focuses on concepts like classes, objects, inheritance, polymorphism, and encapsulation, enabling modular, reusable, and maintainable code development using the C++ programming language.',_binary '','Object Oriented Programming with C++',2,'2025-04-20 15:02:57.880992',1),(32,'2025-04-20 19:55:24.508581','Cyber Security focuses on protecting computer systems, networks, and data from cyber threats, including hacking, malware, and data breaches. It covers risk management, encryption, ethical hacking, and security protocols, equipping students with the skills to safeguard digital assets and ensure the confidentiality, integrity, and availability of information in organizations.',_binary '','Cyber Security',1,'2025-04-20 22:12:36.489165',3),(33,'2025-04-20 19:57:22.298396','Principles of Computer Programming introduces fundamental programming concepts such as algorithms, control structures, data types, and syntax. Students learn problem-solving through coding, typically using languages like Python or C. The subject builds a strong foundation for software development, enabling learners to write, test, and debug simple programs effectively and efficiently.',_binary '','Principles of Computer Programming',1,'2025-04-20 22:15:15.053864',1),(34,'2025-04-20 19:59:37.584299','Systems Analysis and Design focuses on understanding business requirements and designing effective information systems. It covers techniques for gathering requirements, modeling processes, and designing system architecture. Students learn to analyze existing systems, propose improvements, and create system specifications, preparing them to bridge the gap between users’ needs and technical solutions.',_binary '','Systems Analysis and Design',2,'2025-04-20 22:17:51.559812',4),(35,'2025-04-20 20:07:41.223957','Web and Mobile Applications explores the design, development, and deployment of applications for web browsers and mobile devices. Students learn programming languages, responsive design, user interface principles, and backend integration. The subject equips learners with practical skills to build functional, user-friendly, and cross-platform applications for real-world use in various industries.',_binary '','Web and Mobile Applications',2,'2025-04-20 22:16:37.237616',5),(36,'2025-04-20 20:17:42.234209','Computer and Network Technology provides foundational knowledge of computer systems, hardware, operating systems, and networking concepts. It covers topics such as network configuration, protocols, cybersecurity basics, and troubleshooting. The subject prepares students to understand, maintain, and support IT infrastructure essential for communication, data exchange, and system functionality in modern organizations.',_binary '','Computer and Network Technology',2,'2025-04-20 22:21:14.128675',3),(37,'2025-04-20 23:01:09.714837','Managing Digital Information focuses on the effective collection, organization, storage, and protection of digital data. It covers data governance, security, privacy, and digital tools for information management. The subject equips learners with the skills to handle digital resources efficiently, ensuring accurate, accessible, and secure information flow within organizations.',_binary '','Managing Digital Information',2,'2025-04-21 01:17:00.522305',6),(38,'2025-04-20 23:07:13.126235','Software Engineering involves the systematic design, development, testing, and maintenance of software applications. It covers methodologies, project management, quality assurance, and user requirements. The subject equips students with the skills to build reliable, efficient, and scalable software solutions, ensuring they meet user needs and industry standards throughout the development lifecycle.',_binary '','Software Engineering',2,'2025-04-21 01:24:03.438563',7),(39,'2025-04-20 23:08:06.356140','Database Systems focuses on the design, implementation, and management of databases to store, organize, and retrieve data efficiently. It covers relational models, SQL, normalization, and database security. Students learn how to build and manage robust databases, supporting effective data handling and decision-making in various business and technological environments.',_binary '','Database Systems',2,'2025-04-21 01:28:40.269184',3),(40,'2025-04-20 23:09:33.528652','Advanced Systems Analysis and Design builds on foundational concepts to explore complex system development processes. It covers advanced modeling techniques, enterprise-level system architecture, requirements engineering, and design methodologies. The subject prepares students to lead and manage the analysis and design of sophisticated information systems that align with organizational goals and strategies.',_binary '','Advanced systems Analysis and Design',3,'2025-04-21 01:30:05.920847',8),(41,'2025-04-20 23:11:53.121734','Management Information Systems (MIS) focuses on the use of information technology to support managerial decision-making, coordination, and control. It covers system components, data management, and business applications. MIS enables organizations to analyze operations, improve efficiency, and make strategic decisions by integrating technology with business processes and information flow.',_binary '','Management Information Systems',3,'2025-04-21 01:30:53.143865',4),(42,'2025-04-20 23:13:07.254366','Network Information Systems focus on the integration and management of computer networks to support communication, data sharing, and information flow within and between organizations. The subject covers networking fundamentals, protocols, infrastructure, and security. It equips students to design, implement, and manage networked systems that enhance organizational efficiency and connectivity.',_binary '','Network Information Systems',3,'2025-04-21 01:32:14.614111',3),(43,'2025-04-20 23:13:57.956305','IT Project Management involves planning, executing, and overseeing technology projects to meet specific goals within scope, time, and budget. It covers methodologies like Agile and Waterfall, risk management, resource allocation, and team coordination. The subject equips students to lead IT projects effectively, ensuring successful delivery and alignment with business objectives.',_binary '','IT Project Management',3,'2025-04-21 01:31:36.981634',9),(44,'2025-04-21 01:49:00.086067','Project Management involves planning, organizing, and overseeing projects to achieve specific objectives within defined timeframes and budgets. It covers project lifecycle, scope, scheduling, risk management, and resource allocation. The subject equips students with skills to manage teams, ensure quality outcomes, and successfully deliver projects across various industries and sectors.',_binary '','Project Management',1,'2025-04-21 01:49:00.086067',10),(45,'2025-04-21 02:01:00.250597','Business Information Systems (BIS) explores the integration of technology, data, and processes to support organizational decision-making and operations. Topics include database management, enterprise systems, e-commerce, and IT strategy. Students learn to analyze, design, and implement systems that enhance efficiency, innovation, and competitive advantage in modern businesses. Practical applications align with real-world business challenges.',_binary '','Business Information System',3,'2025-04-21 02:01:00.250597',1),(46,'2025-04-21 02:02:06.102869','Cyber Security focuses on protecting digital systems, networks, and data from cyber threats such as hacking, malware, and data breaches. It covers topics like cryptography, ethical hacking, risk management, and security protocols. The subject prepares students to identify vulnerabilities, implement defenses, and maintain secure information environments in organizations.',_binary '','Cyber Security',3,'2025-04-21 02:02:06.102869',1),(47,'2025-04-21 02:13:36.006664','Research Methodology for PhD equips scholars with the tools and techniques needed to design, conduct, and analyze academic research. It covers qualitative and quantitative methods, data collection, analysis, ethics, and literature review. The course ensures rigorous, systematic inquiry, enabling scholars to produce credible, original contributions to their field of study.',_binary '','Research Methodology',4,'2025-04-21 02:13:36.006664',11),(48,'2025-04-21 23:50:08.723376','Mobile Communication explores the principles and technologies behind wireless communication systems, including cellular networks, mobile protocols, and data transmission. The subject covers topics like 4G/5G, signal propagation, and mobile device architecture. It equips students with the knowledge to understand, design, and manage mobile communication systems in a connected world.',_binary '','Mobile Communication',1,'2025-04-21 23:50:08.723376',4),(49,'2025-04-21 23:51:08.083264','Social Media for Business examines how platforms like Facebook, Instagram, LinkedIn, and Twitter are used for marketing, branding, and customer engagement. The subject covers content strategy, analytics, and social media tools. It prepares students to create effective campaigns that enhance brand presence and drive business growth in the digital space.',_binary '','Social Media for Business',1,'2025-04-21 23:51:08.083264',4);
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

-- Dump completed on 2025-04-22  0:32:22
