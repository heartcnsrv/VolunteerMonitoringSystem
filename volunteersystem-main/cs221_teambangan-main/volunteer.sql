-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: volunteer
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `community_service`
--

DROP TABLE IF EXISTS `community_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_service` (
  `service_id` int NOT NULL,
  `org_id` int NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `service_type` varchar(100) NOT NULL,
  `description` text,
  `category` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE KEY `ak_service_name` (`service_name`),
  KEY `fk_community_service_org_id` (`org_id`),
  CONSTRAINT `fk_community_service_org_id` FOREIGN KEY (`org_id`) REFERENCES `organization` (`org_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_service`
--

LOCK TABLES `community_service` WRITE;
/*!40000 ALTER TABLE `community_service` DISABLE KEYS */;
INSERT INTO `community_service` VALUES (1,1,'City Beneath the Stars','Meal Distribution','Providing meals to the homeless in the city center.','Disaster Response'),(2,1,'Read to Rise','Educational Program','Offering free literacy programs for children and adults.','Education'),(3,1,'Roots for Tomorrow','Environmental Action','Planting trees in urban areas to improve air quality.','Environment'),(4,1,'Pulse of Care','Health Screening','Offering free health screenings and basic medical care.','Health'),(5,1,'Paws & Hearts','Animal Rescue','Rescuing animals from shelters and finding them homes.','Animal Welfare'),(6,1,'Code the Future','Educational Program','Free workshops to teach kids how to code and use technology.','Education'),(7,1,'Crystal Clear Waters','Water Purification','Installing water filtration systems in rural areas.','Health'),(8,1,'Lead the Way','Youth Leadership','Leadership development programs for young people.','Education'),(9,1,'The Giving Table','Meal Distribution','Collecting and distributing food to families in need.','Disaster Response'),(10,1,'Safe Streets Initiative','Disaster Relief','Educating communities on crime prevention and safety measures.','Public Safety');
/*!40000 ALTER TABLE `community_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `org_id` int NOT NULL,
  `org_name` varchar(255) NOT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `website` varchar(255) DEFAULT NULL,
  `mission_statement` text,
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `ak_org_name` (`org_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1,'City Outreach','123-456-7890','123 Main St, Cityville','pass123','www.cityoutreach.org','Providing support and services to the underprivileged.');
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `schedule_id` int NOT NULL,
  `service_id` int NOT NULL,
  `schedule_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `max_volunteers` int DEFAULT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `fk_schedule_service_id` (`service_id`),
  CONSTRAINT `fk_schedule_service_id` FOREIGN KEY (`service_id`) REFERENCES `community_service` (`service_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,1,'2025-03-20','10:00:00','14:00:00','City Park Amphitheater, Cityville',50),(2,2,'2025-03-22','09:00:00','12:00:00','Main Branch Library, Booktown',30),(3,3,'2025-03-25','08:00:00','12:00:00','Oakwood Community Garden, GreenCity',20),(4,4,'2025-03-27','09:00:00','13:00:00','Health Clinic Parking Lot, Healthville',40),(5,5,'2025-03-29','11:00:00','15:00:00','Pet Adoption Center, Petland',25),(6,6,'2025-03-31','13:00:00','17:00:00','TechReach HQ, TechCity',20),(7,7,'2025-04-03','08:00:00','12:00:00','Waterside School District, Waterside',50),(8,8,'2025-04-05','10:00:00','14:00:00','Leadtown Youth Center',30),(9,9,'2025-04-07','12:00:00','16:00:00','Feeding Hands Community Center, Foodville',60),(10,10,'2025-04-10','09:00:00','13:00:00','SafeTown Municipal Building',40);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteer`
--

DROP TABLE IF EXISTS `volunteer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer` (
  `volunteer_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `status` enum('Active','Inactive') DEFAULT 'Active',
  `date_joined` date NOT NULL,
  PRIMARY KEY (`volunteer_id`),
  UNIQUE KEY `ak_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer`
--

LOCK TABLES `volunteer` WRITE;
/*!40000 ALTER TABLE `volunteer` DISABLE KEYS */;
INSERT INTO `volunteer` VALUES (1,'Alice','Smith','alice.smith@email.com','alice123','555-1234','Active','2025-03-01'),(2,'Bob','Jones','bob.jones@email.com','bob123','555-5678','Active','2025-03-05'),(3,'Charlie','Brown','charlie.brown@email.com','charlie123','555-9876','Inactive','2025-02-20'),(4,'Diana','White','diana.white@email.com','diana123','555-1112','Active','2025-03-15'),(5,'Eva','Green','eva.green@email.com','eva123','555-2223','Active','2025-03-18'),(6,'Frank','Black','frank.black@email.com','frank123','555-3334','Inactive','2025-01-10'),(7,'Grace','Miller','grace.miller@email.com','grace123','555-4445','Active','2025-03-12'),(8,'Hank','Davis','hank.davis@email.com','hank123','555-5556','Active','2025-02-25'),(9,'Ivy','Martinez','ivy.martinez@email.com','ivy123','555-6667','Inactive','2025-03-01'),(10,'Jack','Wilson','jack.wilson@email.com','jack123','555-7778','Active','2025-03-07');
/*!40000 ALTER TABLE `volunteer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteer_hours`
--

DROP TABLE IF EXISTS `volunteer_hours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer_hours` (
  `hours_id` int NOT NULL AUTO_INCREMENT,
  `volunteer_id` int NOT NULL,
  `schedule_id` int NOT NULL,
  `hours_logged` int NOT NULL,
  `date_logged` date NOT NULL,
  PRIMARY KEY (`hours_id`),
  KEY `fk_schedule_id_hours` (`schedule_id`),
  KEY `fk_volunteer_participation` (`volunteer_id`,`schedule_id`),
  CONSTRAINT `fk_schedule_id_hours` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`schedule_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_volunteer_id_hours` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer` (`volunteer_id`),
  CONSTRAINT `fk_volunteer_participation` FOREIGN KEY (`volunteer_id`, `schedule_id`) REFERENCES `volunteer_participation` (`volunteer_id`, `schedule_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer_hours`
--

LOCK TABLES `volunteer_hours` WRITE;
/*!40000 ALTER TABLE `volunteer_hours` DISABLE KEYS */;
INSERT INTO `volunteer_hours` VALUES (1,1,1,6,'2025-03-20'),(2,2,2,3,'2025-03-22'),(3,3,3,5,'2025-03-25'),(4,4,4,4,'2025-03-27'),(5,5,5,3,'2025-03-29'),(6,6,6,5,'2025-03-31'),(7,7,7,6,'2025-04-03'),(8,8,8,3,'2025-04-05'),(9,9,9,4,'2025-04-07'),(10,10,10,5,'2025-04-10'),(11,1,5,5,'2025-06-23');
/*!40000 ALTER TABLE `volunteer_hours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteer_participation`
--

DROP TABLE IF EXISTS `volunteer_participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer_participation` (
  `volunteer_id` int NOT NULL,
  `schedule_id` int NOT NULL,
  `confirmation_status` enum('Pending','Confirmed') DEFAULT 'Pending',
  `role` enum('Coordinator','Assistant','Attendee') NOT NULL,
  PRIMARY KEY (`volunteer_id`,`schedule_id`),
  KEY `fk_schedule_id` (`schedule_id`),
  CONSTRAINT `fk_schedule_id` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`schedule_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_volunteer_id` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer` (`volunteer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer_participation`
--

LOCK TABLES `volunteer_participation` WRITE;
/*!40000 ALTER TABLE `volunteer_participation` DISABLE KEYS */;
INSERT INTO `volunteer_participation` VALUES (1,1,'Confirmed','Coordinator'),(1,2,'Pending','Attendee'),(1,3,'Pending','Attendee'),(1,4,'Pending','Attendee'),(1,5,'Pending','Attendee'),(2,2,'Pending','Assistant'),(3,3,'Confirmed','Attendee'),(4,4,'Confirmed','Coordinator'),(5,5,'Pending','Assistant'),(6,6,'Confirmed','Attendee'),(7,7,'Pending','Coordinator'),(8,8,'Confirmed','Attendee'),(9,9,'Confirmed','Assistant'),(10,10,'Pending','Attendee');
/*!40000 ALTER TABLE `volunteer_participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'volunteer'
--
/*!50003 DROP PROCEDURE IF EXISTS `AddVolunteer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddVolunteer`(
    IN p_first_name VARCHAR(255),
    IN p_last_name VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_phone_number VARCHAR(15)
)
BEGIN
    -- Check if email already exists
    IF EXISTS (SELECT 1 FROM volunteer WHERE email = p_email) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Email already exists for another volunteer';
	ELSE
        INSERT INTO volunteer (
            volunteer_id, first_name, last_name, email, password, phone_number, status, date_joined
        ) VALUES (
            NULL, p_first_name, p_last_name, p_email, p_password, p_phone_number, 'Active', CURDATE()
        );
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-12 23:17:02
