-- MySQL dump 10.13  Distrib 5.1.41, for Win32 (ia32)
--
-- Host: localhost    Database: pugliatrasporti
-- ------------------------------------------------------
-- Server version	5.1.41

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
-- Current Database: `pugliatrasporti`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pugliatrasporti` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `pugliatrasporti`;

--
-- Table structure for table `bolle_spedizioni`
--

DROP TABLE IF EXISTS `bolle_spedizioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bolle_spedizioni` (
  `num_spedizione` varchar(10) NOT NULL,
  `data_spedizione` date NOT NULL,
  `bolla` int(10) NOT NULL,
  PRIMARY KEY (`num_spedizione`,`data_spedizione`,`bolla`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fatture`
--

DROP TABLE IF EXISTS `fatture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fatture` (
  `numero` int(11) NOT NULL,
  `data` date NOT NULL,
  `metodopagamento` varchar(30) NOT NULL DEFAULT 'Contante-0',
  `importo` decimal(9,2) NOT NULL,
  `sconto` decimal(9,2) NOT NULL,
  `provvigione` decimal(9,2) NOT NULL,
  `iva` decimal(9,2) NOT NULL,
  `totale` decimal(9,2) NOT NULL,
  `forfait` tinyint(1) NOT NULL DEFAULT '0',
  `pagata` tinyint(1) NOT NULL DEFAULT '0',
  `note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`numero`,`data`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fattureacq`
--

DROP TABLE IF EXISTS `fattureacq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fattureacq` (
  `numero` int(11) NOT NULL,
  `data` date NOT NULL,
  `metodopagamento` varchar(30) NOT NULL,
  `importo` decimal(9,2) NOT NULL,
  `sconto` decimal(9,2) NOT NULL DEFAULT '0.00',
  `iva` decimal(9,2) NOT NULL,
  `totale` decimal(9,2) NOT NULL,
  `pagata` tinyint(1) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `note` varchar(200) DEFAULT NULL,
  `fornitore` int(11) NOT NULL,
  PRIMARY KEY (`numero`,`data`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fornitori`
--

DROP TABLE IF EXISTS `fornitori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornitori` (
  `cod` int(10) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `titolare` varchar(20) DEFAULT NULL,
  `piva` varchar(11) DEFAULT NULL,
  `codfiscale` varchar(11) DEFAULT NULL,
  `indirizzo` varchar(50) DEFAULT NULL,
  `telefono1` varchar(15) DEFAULT NULL,
  `telefono2` varchar(15) DEFAULT NULL,
  `fax` varchar(15) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `cap` varchar(5) DEFAULT NULL,
  `citta` varchar(20) DEFAULT NULL,
  `provincia` varchar(10) DEFAULT NULL,
  `nazione` varchar(20) DEFAULT NULL,
  `banca` varchar(30) DEFAULT NULL,
  `iban` varchar(27) DEFAULT NULL,
  `nome_ref_1` varchar(30) DEFAULT NULL,
  `email_ref_1` varchar(40) DEFAULT NULL,
  `tel_ref_1` varchar(15) DEFAULT NULL,
  `nome_ref_2` varchar(30) DEFAULT NULL,
  `email_ref_2` varchar(40) DEFAULT NULL,
  `tel_ref_2` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`cod`),
  UNIQUE KEY `codfiscale` (`codfiscale`),
  UNIQUE KEY `piva` (`piva`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mezzi`
--

DROP TABLE IF EXISTS `mezzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mezzi` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `targa` varchar(10) NOT NULL,
  `marca` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `targa` (`targa`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `mezzi` (`targa`) VALUES ('Vettore');

--
-- Table structure for table `movimcontante`
--

DROP TABLE IF EXISTS `movimcontante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimcontante` (
  `numero` int(11) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `banca` varchar(50) DEFAULT NULL,
  `importo` decimal(9,2) NOT NULL,
  `tipo` varchar(11) NOT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movimenti`
--

DROP TABLE IF EXISTS `movimenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimenti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num_doc` int(11) NOT NULL,
  `data` date NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `met_pag` varchar(30) NOT NULL,
  `valore` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `spedizioni`
--

DROP TABLE IF EXISTS `spedizioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spedizioni` (
  `numero` int(11) NOT NULL,
  `data_carico` date NOT NULL,
  `data_documento` date DEFAULT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `forn_cliente` int(10) NOT NULL,
  `mezzo` int(3) DEFAULT NULL,
  `um` varchar(10) DEFAULT NULL,
  `qta` int(10) NOT NULL DEFAULT '0',
  `traz` decimal(7,2) NOT NULL DEFAULT '0.00',
  `distrib` decimal(7,2) NOT NULL DEFAULT '0.00',
  `importo` decimal(10,2) NOT NULL DEFAULT '0.00',
  `sconto` int(2) NOT NULL DEFAULT '0',
  `perciva` int(2) NOT NULL DEFAULT '21',
  `iva` decimal(7,2) NOT NULL DEFAULT '0.00',
  `percprovvigione` int(2) NOT NULL DEFAULT '0',
  `importoprovvigione` decimal(10,2) NOT NULL DEFAULT '0.00',
  `totale` decimal(10,2) NOT NULL DEFAULT '0.00',
  `note` text,
  `rientrata` tinyint(1) NOT NULL DEFAULT '0',
  `fattura` int(11) DEFAULT NULL,
  `data_fattura` date DEFAULT NULL,
  `valoremerce` decimal(10,2) NOT NULL DEFAULT '0.00',
  `imponibile` decimal(9,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`numero`,`data_carico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-08 21:41:57
