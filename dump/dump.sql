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
  `bolla` varchar(10) NOT NULL,
  PRIMARY KEY (`num_spedizione`,`data_spedizione`,`bolla`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bolle_spedizioni`
--

LOCK TABLES `bolle_spedizioni` WRITE;
/*!40000 ALTER TABLE `bolle_spedizioni` DISABLE KEYS */;
INSERT INTO `bolle_spedizioni` VALUES ('1','2010-12-10','2342/sdf'),('1','2010-12-10','324'),('1','2012-10-01','2748'),('1','2012-10-01','6533'),('10','2012-12-12','9999'),('11','2012-01-01','0234'),('11','2012-01-01','3243/v'),('11','2012-01-01','3442/bf'),('12','2012-01-01','1111/b'),('12','2012-01-01','23432/a'),('13','2012-10-10','11193/c'),('13','2012-10-10','34839/a');
/*!40000 ALTER TABLE `bolle_spedizioni` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `fatture`
--

LOCK TABLES `fatture` WRITE;
/*!40000 ALTER TABLE `fatture` DISABLE KEYS */;
INSERT INTO `fatture` VALUES (1,'2012-10-01','Contante-30','30.60','3.06','30.60','3155.78','18183.32',0,0,NULL),(2,'2012-10-01','Contante-0','15000.00','0.00','150.00','3181.50','18331.50',1,0,'seconda fattura'),(3,'2012-10-03','Contante-0','4.00','0.00','0.00','0.84','4.84',0,0,NULL),(4,'2012-10-03','RIBA-120','200000.00','0.00','0.00','42000.00','242000.00',1,1,NULL),(5,'2012-10-03','Contante-0','7.00','0.00','0.00','1.47','8.47',0,0,NULL),(6,'2012-10-03','Bonifico Bancario-70','100000.00','10000.00','99999.90','39899.98','229899.88',0,0,NULL),(7,'2012-10-10','Contante-0','30.10','0.30','450.00','100.76','580.56',0,0,NULL),(8,'2012-10-10','Contante-0','12312.00','0.00','0.00','2585.52','14897.52',1,0,NULL),(9,'2012-10-11','Rimessa diretta-0','54832.00','0.00','0.00','11514.72','66346.72',1,0,NULL),(10,'2012-10-11','Rimessa diretta-0','23432.00','0.00','0.00','4920.72','28352.72',1,0,NULL),(12,'2012-10-11','Rimessa diretta-0','2342.00','0.00','0.00','491.82','2833.82',0,1,NULL),(13,'2012-10-11','Accredito c/c-45','30000.00','0.00','0.00','6300.00','36300.00',1,1,NULL),(15,'2012-10-12','Rimessa diretta-0','0.00','0.00','0.00','0.00','0.00',0,0,NULL);
/*!40000 ALTER TABLE `fatture` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`numero`,`data`,`fornitore`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fattureacq`
--

LOCK TABLES `fattureacq` WRITE;
/*!40000 ALTER TABLE `fattureacq` DISABLE KEYS */;
INSERT INTO `fattureacq` VALUES (1,'2011-01-11','Contante-0','343.00','0.00','72.03','415.03',0,'Terzi',NULL,1),(1,'2012-01-01','Contante-0','8768.00','786.00','1676.22','9658.22',0,'Terzi',NULL,1),(1,'2012-10-01','Contante-0','32.34','3.00','6.16','35.50',1,'Terzi',NULL,1),(2,'2012-10-02','Bonifico Bancario-45','5500.00','130.00','1127.70','6497.70',0,'Varie',NULL,1),(3,'2012-01-01','Assegno Bancario-45','8838.00','32.00','1849.26','10655.26',0,'Terzi',NULL,1),(4,'2012-10-09','Accredito c/c-0','15000.00','34.00','3142.86','18108.86',1,'Terzi',NULL,1),(23,'2012-10-11','Bonifico Bancario-0','2328.00','29.00','482.79','2781.79',0,'Terzi',NULL,1),(44,'2012-10-11','Contante-0','36363.00','2.00','7635.81','43996.81',1,'Terzi',NULL,1),(44,'2012-12-11','Contante-0','10000.00','1200.00','1848.00','10648.00',1,'Terzi',NULL,1),(77,'2012-10-11','Bonifico Bancario-0','3737.00','33.00','777.84','4481.84',1,'Terzi',NULL,1),(99,'2012-10-10','Contante-0','73737.00','0.00','15484.77','89221.77',1,'Terzi',NULL,1),(100,'2012-10-10','Contante-0','10000.00','1000.00','1890.00','10890.00',0,'Terzi',NULL,1);
/*!40000 ALTER TABLE `fattureacq` ENABLE KEYS */;
UNLOCK TABLES;

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
  `codfiscale` varchar(16) DEFAULT NULL,
  `indirizzo_leg` varchar(50) DEFAULT NULL,
  `telefono1` varchar(15) DEFAULT NULL,
  `telefono2` varchar(15) DEFAULT NULL,
  `fax` varchar(15) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `cap_leg` varchar(5) DEFAULT NULL,
  `citta_leg` varchar(20) DEFAULT NULL,
  `provincia_leg` varchar(10) DEFAULT NULL,
  `nazione_leg` varchar(20) DEFAULT NULL,
  `banca` varchar(30) DEFAULT NULL,
  `iban` varchar(27) DEFAULT NULL,
  `nome_ref_1` varchar(30) DEFAULT NULL,
  `email_ref_1` varchar(40) DEFAULT NULL,
  `tel_ref_1` varchar(15) DEFAULT NULL,
  `nome_ref_2` varchar(30) DEFAULT NULL,
  `email_ref_2` varchar(40) DEFAULT NULL,
  `tel_ref_2` varchar(15) DEFAULT NULL,
  `iscrizione_albo` varchar(20) DEFAULT NULL,
  `indirizzo_op` varchar(50) DEFAULT NULL,
  `cap_op` varchar(5) DEFAULT NULL,
  `citta_op` varchar(20) DEFAULT NULL,
  `provincia_op` varchar(10) DEFAULT NULL,
  `nazione_op` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cod`),
  UNIQUE KEY `codfiscale` (`codfiscale`),
  UNIQUE KEY `piva` (`piva`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitori`
--

LOCK TABLES `fornitori` WRITE;
/*!40000 ALTER TABLE `fornitori` DISABLE KEYS */;
INSERT INTO `fornitori` VALUES (1,'Stolfa srl','Michele Stolfa','11111111111','2222222222222222','via Gigante 106',NULL,NULL,NULL,'misto89@hotmail.it','70033','corato','BA','italia','unicredit',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'non so cosa sia',NULL,NULL,NULL,NULL,NULL),(2,'Prova',NULL,'22222222222',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `fornitori` ENABLE KEYS */;
UNLOCK TABLES;

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
  `scad_bollo` varchar(30) DEFAULT NULL,
  `scad_revisione` varchar(30) DEFAULT NULL,
  `scad_atp` varchar(30) DEFAULT NULL,
  `scad_assicurazione` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `targa` (`targa`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mezzi`
--

LOCK TABLES `mezzi` WRITE;
/*!40000 ALTER TABLE `mezzi` DISABLE KEYS */;
INSERT INTO `mezzi` VALUES (9,'Vettore',NULL,NULL,NULL,NULL,NULL),(11,'CK986TT','daewoo matiz 800 SE','settembre','dicembre',NULL,'novembre');
/*!40000 ALTER TABLE `mezzi` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimcontante`
--

LOCK TABLES `movimcontante` WRITE;
/*!40000 ALTER TABLE `movimcontante` DISABLE KEYS */;
INSERT INTO `movimcontante` VALUES (1,'2012-10-01','banca','1000.00','PRE'),(2,'2012-10-01','banca','2000.00','PRE'),(3,'2012-10-01','banca','100.00','VER');
/*!40000 ALTER TABLE `movimcontante` ENABLE KEYS */;
UNLOCK TABLES;

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
  `forncliente` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimenti`
--

LOCK TABLES `movimenti` WRITE;
/*!40000 ALTER TABLE `movimenti` DISABLE KEYS */;
INSERT INTO `movimenti` VALUES (42,44,'2012-12-11','ACQ','Contante','10000.00',1),(43,44,'2012-12-11','ACQ','Bonifico Bancario','648.00',1),(44,4,'2012-10-03','VEN','RIBA','242000.00',1),(46,44,'2012-10-11','ACQ','Contante','43996.81',1),(47,1,'2012-10-01','ACQ','Contante','35.00',1),(48,1,'2012-10-01','ACQ','Accredito c/c','0.50',1),(49,77,'2012-10-11','ACQ','Contante','0.84',1),(50,77,'2012-10-11','ACQ','Bonifico Bancario','4481.00',1),(51,4,'2012-10-09','ACQ','Contante','0.86',1),(52,4,'2012-10-09','ACQ','Accredito c/c','18108.00',1),(53,99,'2012-10-10','ACQ','Contante','89221.77',1),(57,12,'2012-10-11','VEN','Contante','2833.82',1),(61,13,'2012-10-11','VEN','Accredito c/c','36300.00',1);
/*!40000 ALTER TABLE `movimenti` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `spedizioni`
--

LOCK TABLES `spedizioni` WRITE;
/*!40000 ALTER TABLE `spedizioni` DISABLE KEYS */;
INSERT INTO `spedizioni` VALUES (1,'2010-12-10','2010-12-10',NULL,1,NULL,NULL,0,'0.00','0.00','2342.00',0,21,'491.82',0,'0.00','2833.82',NULL,0,12,'2012-10-11','0.00','2342.00'),(1,'2012-10-01','2012-10-01','prima spedizione',1,11,'q.li',34,'0.40','0.50','30.60',10,21,'3155.78',10,'15000.00','18183.32',NULL,0,1,'2012-10-01','150000.00','15027.54'),(1,'2013-01-01','2013-01-01','dewcrgdfgdf',1,NULL,NULL,0,'0.00','0.00','3243.00',0,21,'681.03',0,'0.00','3924.03',NULL,0,4,'2012-10-03','0.00','3243.00'),(1,'2014-01-01','2014-01-01',NULL,2,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(2,'2012-10-01','2012-10-01','seconda spedizione',1,9,'Pd',10,'0.00','0.00','15000.00',0,21,'3181.50',1,'150.00','18331.50',NULL,0,2,'2012-10-01','15000.00','15150.00'),(2,'2013-10-11','2013-10-11',NULL,1,NULL,NULL,0,'0.00','0.00','23838.00',0,21,'5005.98',0,'0.00','28843.98',NULL,0,NULL,NULL,'0.00','23838.00'),(2,'2014-01-01','2014-01-01',NULL,2,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(3,'2012-10-01','2012-10-01','terza spedizione',1,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,4,'2012-10-03','0.00','0.00'),(3,'2014-01-02','2014-01-02','3',2,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(4,'2012-10-01','2012-10-01',NULL,1,11,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,4,'2012-10-03','0.00','0.00'),(4,'2014-02-02','2014-02-02','4',2,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(5,'2012-10-02','2012-10-02','terza spedizione da fatturare',1,9,NULL,0,'0.00','0.00','20000.00',0,21,'4200.00',0,'0.00','24200.00',NULL,0,4,'2012-10-03','0.00','20000.00'),(6,'2012-10-03','2012-10-03',NULL,1,NULL,'Pd',10,'0.40','0.00','4.00',0,21,'0.84',0,'0.00','4.84',NULL,0,3,'2012-10-03','0.00','4.00'),(7,'2012-10-03','2012-10-03',NULL,1,NULL,'Cc',5,'0.30','0.20','2.50',0,21,'0.52',0,'0.00','3.02',NULL,0,4,'2012-10-03','0.00','2.50'),(8,'2012-10-06','2012-10-06',NULL,1,NULL,'q.li',100,'10.00','0.00','1000.00',0,21,'210.00',0,'0.00','1210.00',NULL,0,4,'2012-10-03','0.00','1000.00'),(9,'2012-02-12','2012-02-12',NULL,1,NULL,NULL,10,'0.40','0.30','7.00',0,21,'1.47',0,'0.00','8.47',NULL,0,5,'2012-10-03','0.00','7.00'),(10,'2012-12-12','2012-12-12','prova descrizione',1,11,'q.li',100,'500.00','500.00','100000.00',10,21,'39899.98',10,'99999.90','229899.88','note note note note',0,6,'2012-10-03','999999.00','189999.90'),(11,'2012-01-01','2012-01-01','2e',1,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(12,'2012-01-01','2012-01-01','!\"£$%&/()=?^ìèé*+[]ç@òà#°ù§,.;:-_<> \'| \\',1,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(13,'2012-10-10','2012-10-10','descizionefdlgkjfl jljgl kjlkfjdfklgjkljdkldfjg fgf\'\'\'\'\' dfg\'dfg\'dfg\'dfg\'df',1,11,'Pd',43,'0.40','0.30','30.10',1,21,'100.76',3,'450.00','580.56',NULL,1,7,'2012-10-10','15000.00','479.80'),(14,'2012-10-10','2012-10-10','prova',1,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(15,'2012-10-10','2012-10-10','eewrr',1,NULL,NULL,0,'0.00','0.00','12312.00',0,21,'2585.52',0,'0.00','14897.52',NULL,0,8,'2012-10-10','0.00','12312.00'),(16,'2012-10-11','2012-10-11','pprpf',1,NULL,NULL,0,'0.00','0.00','54832.00',0,21,'11514.72',0,'0.00','66346.72',NULL,0,9,'2012-10-11','0.00','54832.00'),(17,'2012-10-11','2012-10-11',NULL,1,NULL,NULL,0,'0.00','0.00','23432.00',0,21,'4920.72',0,'0.00','28352.72',NULL,0,10,'2012-10-11','0.00','23432.00'),(18,'2012-10-12','2012-10-12','prova check pagata',1,NULL,NULL,0,'0.00','0.00','15480.00',0,21,'3250.80',0,'0.00','18730.80',NULL,0,NULL,NULL,'0.00','15480.00'),(19,'2012-10-11','2012-10-11',NULL,1,NULL,NULL,0,'0.00','0.00','30000.00',0,21,'6300.00',0,'0.00','36300.00',NULL,0,13,'2012-10-11','0.00','30000.00');
/*!40000 ALTER TABLE `spedizioni` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-10-12 17:21:46
