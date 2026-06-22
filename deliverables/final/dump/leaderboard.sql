-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: leaderboard
-- ------------------------------------------------------
-- Server version	8.0.46-0ubuntu0.22.04.2

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
-- Table structure for table `results_2p`
--

DROP TABLE IF EXISTS `results_2p`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `results_2p` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(30) NOT NULL,
  `pp` int NOT NULL,
  `food` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results_2p`
--

LOCK TABLES `results_2p` WRITE;
/*!40000 ALTER TABLE `results_2p` DISABLE KEYS */;
/*!40000 ALTER TABLE `results_2p` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results_3p`
--

DROP TABLE IF EXISTS `results_3p`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `results_3p` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(30) NOT NULL,
  `pp` int NOT NULL,
  `food` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results_3p`
--

LOCK TABLES `results_3p` WRITE;
/*!40000 ALTER TABLE `results_3p` DISABLE KEYS */;
/*!40000 ALTER TABLE `results_3p` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results_4p`
--

DROP TABLE IF EXISTS `results_4p`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `results_4p` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(30) NOT NULL,
  `pp` int NOT NULL,
  `food` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results_4p`
--

LOCK TABLES `results_4p` WRITE;
/*!40000 ALTER TABLE `results_4p` DISABLE KEYS */;
/*!40000 ALTER TABLE `results_4p` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results_5p`
--

DROP TABLE IF EXISTS `results_5p`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `results_5p` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(30) NOT NULL,
  `pp` int NOT NULL,
  `food` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results_5p`
--

LOCK TABLES `results_5p` WRITE;
/*!40000 ALTER TABLE `results_5p` DISABLE KEYS */;
/*!40000 ALTER TABLE `results_5p` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-19 18:33:42
