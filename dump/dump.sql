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
-- Dumping data for table `bolle_spedizioni`
--

LOCK TABLES `bolle_spedizioni` WRITE;
/*!40000 ALTER TABLE `bolle_spedizioni` DISABLE KEYS */;
INSERT INTO `bolle_spedizioni` VALUES ('1','2010-10-10',5443),('1','2012-11-11',1233),('1','2012-11-11',3453),('1','2012-11-11',40001),('1','2014-12-10',2345),('108','2012-12-12',2492),('14','2012-10-10',8777),('15','2012-10-13',1777),('15','2012-10-13',1888),('16','2012-10-13',1777),('16','2012-10-13',1889),('17','2012-10-13',1889),('17','2012-10-13',2777),('18','2012-10-13',1889),('18','2012-10-13',2777),('19','2012-10-13',1889),('19','2012-10-13',2777),('2','2012-11-12',1255),('2','2012-11-12',12881281),('2','2013-10-21',4444),('2','2013-10-21',5555),('20','2012-10-13',34),('21','2012-10-13',1889),('21','2012-10-13',2777),('22','2012-10-13',1889),('22','2012-10-13',2777),('23','2012-07-15',33),('23','2012-10-13',1889),('23','2012-10-13',2777),('24','2012-07-15',2239),('24','2012-07-15',3455),('24','2012-07-15',4737),('25','2012-07-15',2239),('25','2012-07-15',3455),('25','2012-07-15',4737),('26','2012-07-15',2339),('26','2012-07-15',3455),('26','2012-07-15',4737),('27','2012-07-15',2339),('27','2012-07-15',3455),('27','2012-07-15',4737),('28','2012-07-15',2339),('28','2012-07-15',3455),('28','2012-07-15',4737),('29','2012-07-15',2339),('29','2012-07-15',3455),('29','2012-07-15',4737),('29','2012-12-01',3),('3','2012-12-12',3333),('30','2012-10-22',35),('31','2012-10-22',36),('32','2012-10-22',3332),('33','2012-10-22',3332),('34','2012-10-22',37),('35','2012-10-22',3332),('36','2012-10-22',3332),('37','2012-10-22',3332),('38','2012-10-22',3332),('39','2012-10-22',3332),('39','2012-11-12',1),('4','2012-12-12',6666),('40','2012-10-22',3332),('40','2012-11-22',2),('41','2012-10-22',3332),('42','2012-12-12',4),('43','2012-11-22',4334),('44','2012-12-12',5),('45','2012-12-11',3425),('47','2012-12-12',1),('57','2012-12-12',1244),('58','2012-12-12',1265),('59','2012-12-12',1),('63','2012-12-12',6),('64','2012-12-12',44),('76','2012-06-02',2342),('77','2012-10-10',123123);
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
INSERT INTO `fatture` VALUES (1,'2012-06-16','Bonifico Bancario-60','400.00','8.00','400.00','90.72','522.72',0,0,NULL),(2,'2012-06-16','Assegno Bancario-90','177220.00','8711.00','177220.00','37042.00','205551.00',0,0,'jiueuieuwiuskdasjdkl aa'),(3,'2012-06-16','Bonifico Bancario-30','1343.00','0.00','1343.00','282.03','1625.03',0,1,'note note note note'),(4,'2012-06-16','Contante-0','3848.00','0.00','0.00','808.08','4656.08',1,1,'note fattura 4, spedizione 108'),(5,'2012-06-23','Contante-0','5424.00','33.00','0.00','1132.11','6523.11',1,1,'nessuna'),(6,'2012-06-23','Contante-0','121212.00','0.00','0.00','25454.52','146666.52',1,1,'nessunissima'),(7,'2012-06-25','Bonifico Bancario-7','47070.00','714.20','47070.00','9734.72','56090.52',0,0,NULL),(8,'2012-06-25','Contante-30','3964.00','0.00','3964.00','836.64','4820.64',0,1,'saaassaadsd  sdprova'),(9,'2012-06-30','Contante-0','0.00','0.00','0.00','0.00','0.00',0,0,NULL),(10,'2012-06-30','Bonifico Bancario-0','0.00','0.00','0.00','0.00','0.00',1,0,NULL),(11,'2012-08-26','Contante-0','5000.00','4.00','0.00','1049.16','6045.16',1,0,'fattura fortettaria'),(12,'2012-08-27','Contante-0','50000.00','0.00','0.00','10500.00','60500.00',1,0,NULL);
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
  PRIMARY KEY (`numero`,`data`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fattureacq`
--

LOCK TABLES `fattureacq` WRITE;
/*!40000 ALTER TABLE `fattureacq` DISABLE KEYS */;
INSERT INTO `fattureacq` VALUES (1,'2012-01-01','Contante-30','2422.00','2.00','21.00','2929.00',1,'Varie',NULL,2393),(2,'2012-01-01','Bonifico Bancario-0','1212.00','33.00','42.00','2222.00',0,'Terzi',NULL,2366),(3,'2012-12-12','Rimessa Diretta-0','3.00','3.00','3.00','3.00',0,'Terzi',NULL,2391),(4,'2012-12-12','Bonifico Bancario-0','3.00','3.00','3.00','3.00',0,'Terzi',NULL,2398),(5,'2012-12-12','Bonifico Bancario-0','4343.00','0.00','21.00','334.00',1,'Terzi','note note note',2394),(6,'2012-12-12','Rimessa Diretta-0','33333.00','2.00','232.00','32423.00',0,'Terzi','null a 75 gg',2391),(7,'2012-12-12','Assegno Bancario-45','5000.00','100.00','200.00','5100.00',1,'Terzi','prova acqiostp',2394),(10,'2012-04-03','Contante-0','232.40','0.00','21.00','443.00',1,'Terzi',NULL,2393),(10,'2012-05-20','Bonifico Bancario-44','10.00','5.00','5.00','10.00',0,'Terzi',NULL,2393),(20,'2012-06-15','Assegno Bancario-0','100.00','20.00','10.10','300.00',0,'Polizze',NULL,2365),(33,'2012-12-12','Contante-0','988.00','8778.00','8787.00','878.00',0,'Terzi',NULL,2404),(44,'2012-03-12','Assegno Bancario-120','43938.00','23.00','2303.00','543354.00',0,'Manutenzione',NULL,2393);
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
) ENGINE=InnoDB AUTO_INCREMENT=2406 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitori`
--

LOCK TABLES `fornitori` WRITE;
/*!40000 ALTER TABLE `fornitori` DISABLE KEYS */;
INSERT INTO `fornitori` VALUES (22,'r3retert',NULL,'partita iva',NULL,'daaaaffdc',NULL,NULL,NULL,'werwerew@aaaa.it',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2365,'cataldo e figli maschi','cataldo cataldi','09213819328','18209389111','aldocaldo',NULL,NULL,NULL,'mstechnoteam@gmail.com','70033','corato','BA','italia','ffff',NULL,'nome 1','aaaaaaa@gmail.com','tel 1','nome 2','email@email.com','tel 2'),(2366,'ciccio',NULL,'01234567890','01234567890','ccdfgdfgfvv',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2373,'efsfsdf',NULL,'aaaaaaaadaa','vbgnfhcjvnb',NULL,'fsdfsd4728',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'prova',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2378,'provaovaovaovaaaa',NULL,'00394448598','eeeeeeeee00','via via via via','0003r899vv',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2391,'dddd','pingo','qqqqqqqffff',NULL,NULL,'3402354667','0803423456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2392,'dddsdasd',NULL,'ssswwww3324',NULL,'ssssssssfffff',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2393,'azienda prova spa','tizio','00222345946','00222345946','via aaaa 59',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2394,'senza piva',NULL,NULL,'0codfiscale',NULL,NULL,NULL,NULL,NULL,'70033',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2397,'cataldòj','talludd','12345678910',NULL,'aldocaldo',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2398,'bla bla bla spa','nessuno','00445654445','00445654445','via bla, 49','0808728540','0808986724','0808727265','blablabla@bla.it','70033','corato','BA','italia','unicredit banca','IT1234567890QWERTYUIOPASDFG','dffdg',NULL,NULL,NULL,NULL,NULL),(2399,NULL,NULL,'11111111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2401,NULL,NULL,'22222222222',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2404,'prova prova prova','prova','44444444444',NULL,NULL,'00804939r934',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'12',NULL,'1212','12',NULL,'12'),(2405,'proa prova','prova aa','33333333333',NULL,NULL,'3333333333','34534534435','3453453453',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `targa` (`targa`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mezzi`
--

LOCK TABLES `mezzi` WRITE;
/*!40000 ALTER TABLE `mezzi` DISABLE KEYS */;
INSERT INTO `mezzi` VALUES (1,'BA75AAE',NULL),(4,'BA78HGC','dddfggfdgfd'),(5,'BA747TR','opdskfopdskofpsdfds'),(8,'BA55POR',NULL),(9,'Vettore',NULL);
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
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimcontante`
--

LOCK TABLES `movimcontante` WRITE;
/*!40000 ALTER TABLE `movimcontante` DISABLE KEYS */;
INSERT INTO `movimcontante` VALUES (1,'2012-06-29','banca','670.83',''),(2,'2012-06-29','fasj','1000.00',''),(3,'2012-06-29','ikukj','4000.00',''),(4,'2012-06-28','eee','100000.34',''),(5,'2012-08-03','bancaccc','111.00','VER'),(6,'2012-08-03','banca mia','400.00','PRE'),(7,'2012-08-03','','412.00','VER'),(8,'2012-08-03','mia banca','600.00','PRE'),(9,'2012-08-03','','1000.00','PRE'),(10,'2012-08-03','','2000.00','VER'),(11,'2012-08-03','banca','670.83','PRE'),(12,'2012-08-03','banca','100.00','VER'),(13,'2012-08-03','banca','100.00','PRE');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimenti`
--

LOCK TABLES `movimenti` WRITE;
/*!40000 ALTER TABLE `movimenti` DISABLE KEYS */;
INSERT INTO `movimenti` VALUES (9,3,'2012-06-16','VEN','Contante','1000.00'),(10,3,'2012-06-16','VEN','Bonifico Bancario','600.03'),(11,3,'2012-06-16','VEN','RIBA','25.00'),(15,5,'2012-12-12','ACQ','Bonifico Bancario','34.00'),(16,5,'2012-12-12','ACQ','Assegno Bancario','300.00'),(17,10,'2012-04-03','ACQ','Contante','443.00'),(24,1,'2012-01-01','ACQ','Contante','2929.00'),(25,5,'2012-06-23','VEN','Contante','0.11'),(26,5,'2012-06-23','VEN','Bonifico Bancario','6000.00'),(27,5,'2012-06-23','VEN','Assegno Bancario','500.00'),(28,5,'2012-06-23','VEN','RIBA','23.00'),(37,6,'2012-06-23','VEN','Contante','146666.00'),(38,6,'2012-06-23','VEN','RIBA','0.52'),(42,4,'2012-06-16','VEN','Contante','4456.08'),(43,4,'2012-06-16','VEN','Bonifico Bancario','200.00'),(44,8,'2012-06-25','VEN','Contante','3000.00'),(45,8,'2012-06-25','VEN','Bonifico Bancario','1000.00'),(46,8,'2012-06-25','VEN','Assegno Bancario','820.64'),(47,7,'2012-12-12','ACQ','Assegno Bancario','5100.00');
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
INSERT INTO `spedizioni` VALUES (1,'2010-10-10','2010-10-10','errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr',2365,NULL,NULL,0,'0.00','0.00','66.00',0,21,'13.86',0,'0.00','79.86',NULL,0,7,'2012-06-25','0.00','66.00'),(1,'2011-11-11','2011-11-11',NULL,2391,8,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(1,'2012-11-11','2012-11-11','irireiwòejkjadljaskljajalsdjlasjakljdlakjdlajdajlj',2365,9,'Pedane',0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(1,'2013-10-20','2013-10-20','ajajja',2391,8,'Pezzi',50,'10.00','2.00','600.00',3,20,'124.40',1,'40.00','746.40',NULL,0,NULL,NULL,'4000.00','622.00'),(1,'2014-12-10','2014-12-10','aaaaaaa',2365,NULL,NULL,0,'0.00','0.00','2000.00',0,21,'420.00',0,'0.00','2420.00',NULL,0,8,'2012-06-25','0.00','2000.00'),(2,'2011-11-11','2011-11-11','11',2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(2,'2012-11-12','2012-11-12','djalslkdsjlajdsks',2365,8,'Bancali',0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(2,'2013-10-21','2013-10-21','prova prova prova',2391,NULL,NULL,0,'0.00','0.00','4542.00',0,21,'953.82',0,'0.00','5495.82',NULL,0,NULL,NULL,'0.00','4542.00'),(2,'2014-10-20','2014-10-20',NULL,2393,NULL,NULL,0,'0.00','0.00','1000.77',0,21,'210.16',0,'0.00','1210.93',NULL,0,NULL,NULL,'0.00','1000.77'),(3,'2011-11-11','2011-11-11',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(3,'2012-12-12','2012-12-12','jygyu',2365,2,'q',4,'5.00','0.00','20.00',0,21,'8.40',1,'20.00','48.40',NULL,1,8,'2012-06-25','2000.00','40.00'),(3,'2013-05-26','2013-05-26','aaaaaaa',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(4,'2011-12-13','2011-12-13','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(4,'2012-12-12','2012-12-12','ioewuqoiòueoifjiifoeuiuqeuquqruq saduadsu ausd ueqrqipoieqpriqro dasiadiòao michele stolfa',2365,2,'Carrelli',0,'0.00','0.00','1500.00',0,21,'315.00',0,'0.00','1815.00',NULL,0,8,'2012-06-25','0.00','1500.00'),(4,'2013-05-13','2013-05-13',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(5,'2011-12-12','2011-12-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(5,'2012-10-20','2012-10-20',NULL,2366,NULL,NULL,100,'5.00','10.00','1500.00',0,21,'321.30',1,'30.00','1851.30',NULL,0,11,'2012-08-26','3000.00','1530.00'),(5,'2013-05-26','2013-05-26','da fatturare',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(6,'2012-10-20','2012-10-20',NULL,2366,3,NULL,100,'5.00','10.00','1500.00',0,21,'321.30',1,'30.00','1851.30',NULL,0,NULL,NULL,'3000.00','1530.00'),(6,'2013-05-26','2013-05-26','fatt',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(7,'2012-10-20','2012-10-20',NULL,2366,9,NULL,100,'5.00','10.00','1500.00',0,21,'321.30',1,'30.00','1851.30',NULL,0,11,'2012-08-26','3000.00','1530.00'),(7,'2013-05-26','2013-05-26','prova michele 2',2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(8,'2012-12-12','2012-12-12',NULL,2392,9,NULL,0,'0.00','0.00','2000.00',10,21,'378.00',0,'0.00','2178.00',NULL,0,NULL,NULL,'0.00','1800.00'),(8,'2013-05-26','2013-05-26',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(9,'2012-12-12','2012-12-12','asdas',2366,2,NULL,0,'0.00','0.00','2000.00',10,21,'378.00',0,'0.00','2178.00',NULL,1,NULL,NULL,'22000.00','1800.00'),(9,'2013-05-26','2013-05-26','da inserire a fatturare',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(10,'2012-10-10','2012-10-10',NULL,2391,5,NULL,0,'0.00','0.00','3424.00',10,21,'647.14',0,'0.00','3728.74',NULL,0,NULL,NULL,'0.00','3081.60'),(10,'2013-05-26','2013-05-26','da inserire e fatturare 2',2366,NULL,NULL,0,'0.00','0.00','23432.00',0,21,'4920.72',0,'0.00','28352.72',NULL,0,11,'2012-08-26','0.00','23432.00'),(11,'2012-12-31','2012-12-31',NULL,2391,5,NULL,0,'0.00','0.00','1000.00',10,21,'189.00',0,'0.00','1089.00',NULL,0,NULL,NULL,'0.00','900.00'),(11,'2013-05-26','2013-05-26','da inserire e fatturare 3',2366,NULL,NULL,0,'0.00','0.00','4332.00',0,21,'909.72',0,'0.00','5241.72',NULL,0,11,'2012-08-26','0.00','4332.00'),(12,'2012-12-12','2012-12-31',NULL,2397,2,NULL,200,'2.00','0.00','400.00',2,21,'90.72',1,'40.00','522.72',NULL,0,1,'2012-06-16','4000.00','432.00'),(12,'2013-05-26','2013-05-26','da inserire e fatturare 4',2366,NULL,NULL,0,'0.00','0.00','23432.00',0,21,'4920.72',0,'0.00','28352.72',NULL,0,11,'2012-08-26','0.00','23432.00'),(13,'2012-05-17','2012-05-17',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(13,'2013-05-26','2013-05-26','da inserire e fatturare 5',2366,NULL,NULL,0,'0.00','0.00','3543.00',0,21,'744.03',0,'0.00','4287.03',NULL,0,11,'2012-08-26','0.00','3543.00'),(14,'2012-10-10','2012-10-10',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(14,'2013-05-26','2013-05-26','da inserire e fatturare 6',2366,NULL,NULL,0,'0.00','0.00','33443.00',0,21,'7023.03',0,'0.00','40466.03',NULL,0,11,'2012-08-26','0.00','33443.00'),(15,'2012-10-13','2012-10-13','piipipipipipipipipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Kg',0,'0.00','0.00','3000.00',0,21,'630.00',0,'0.00','3630.00',NULL,0,2,'2012-06-16','0.00','3000.00'),(15,'2013-05-26','2013-05-26','descrizione',2366,NULL,NULL,0,'0.00','0.00','23432.00',0,21,'4920.72',0,'0.00','28352.72',NULL,0,11,'2012-08-26','0.00','23432.00'),(16,'2012-10-13','2012-10-13','piipipipipipipipipipipipipipipipip ppipipipipipipipipupupupupuwerwerwerwerwerwerwerewr',2365,2,'Kg',0,'0.00','0.00','3500.00',0,21,'735.00',0,'0.00','4235.00',NULL,0,7,'2012-06-25','0.00','3500.00'),(16,'2013-05-25','2013-05-25',NULL,2366,NULL,NULL,0,'0.00','0.00','34323.00',0,21,'7207.83',0,'0.00','41530.83',NULL,0,11,'2012-08-26','0.00','34323.00'),(17,'2012-10-13','2012-10-13','piipipipipwerwerwerwerwerwerwerwerweripipipipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Pezzi',0,'0.00','0.00','4500.00',2,21,'926.10',0,'0.00','5336.10',NULL,0,7,'2012-06-25','0.00','4410.00'),(17,'2013-05-26','2013-05-26','dddd',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(18,'2012-10-13','2012-10-13','piipipipipipipwerwerwerwerweripipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Pezzi',0,'0.00','0.00','4500.00',2,21,'926.10',0,'0.00','5336.10',NULL,1,7,'2012-06-25','0.00','4410.00'),(18,'2013-05-25','2013-05-25','ffffffg444',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(19,'2012-10-13','2012-10-13','piipipipipwerwerwerwerwerweripipipipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Pezzi',0,'0.00','0.00','4500.00',2,21,'926.10',0,'0.00','5336.10',NULL,0,7,'2012-06-25','0.00','4410.00'),(19,'2013-12-12','2013-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(20,'2012-10-13','2012-10-13','werwerwerwerwerwerwerwerwerwerwerwerwerewrwerpipipipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Pezzi',0,'0.00','0.00','4500.00',2,21,'926.10',0,'0.00','5336.10',NULL,0,7,'2012-06-25','0.00','4410.00'),(20,'2013-12-12','2013-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(21,'2012-10-13','2012-10-13','piipipipipiwerwerwerwerwerwerwerwewerewpipipipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Pezzi',0,'0.00','0.00','4500.00',2,21,'926.10',0,'0.00','5336.10',NULL,0,7,'2012-06-25','0.00','4410.00'),(21,'2013-12-12','2013-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(22,'2012-10-13','2012-10-13','piipipipipiwerwerwerwerwerwerwerwerwerwerewrewrpipipipipipipipipipipip ppipipipipipipipipupupupupu',2365,2,'Pezzi',0,'0.00','0.00','4500.00',2,21,'926.10',0,'0.00','5336.10',NULL,1,7,'2012-06-25','0.00','4410.00'),(22,'2013-12-12','2013-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(23,'2012-07-15','2012-07-15','aaiosdjaiwerwerwerwerwerwerwero jiofjdsofjdio sjfiojio sfjfiosdjfij sdofjiod',2365,NULL,NULL,0,'0.00','0.00','4355.00',2,21,'896.26',0,'0.00','5164.16',NULL,0,7,'2012-06-25','0.00','4267.90'),(23,'2013-12-12','2013-12-12','prova fattuazion',2378,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(24,'2012-07-15','2012-07-15','aaiosdjaiwerwerwerwerwerwerwerewrero jiofjdsofjdio sjfiojio sfjfiosdjfij sdofjiod',2365,NULL,NULL,0,'0.00','0.00','4355.00',2,21,'896.26',0,'0.00','5164.16',NULL,1,7,'2012-06-25','0.00','4267.90'),(25,'2012-07-15','2012-07-15','aaiosdjaio jiofjdsofjdio sjfiojio sfjfiosdjfij sdofjiod',2365,NULL,'Carrelli',0,'0.00','0.00','43555.00',5,22,'9103.00',0,'0.00','50480.24','	',0,2,'2012-06-16','0.00','41377.25'),(26,'2012-07-15','2012-07-15','aaiosdjaio jiofjdsofjdio sjfiojio sfjfiosdjfij sdofjiod',2365,NULL,'Carrelli',0,'0.00','0.00','43555.00',5,22,'9103.00',0,'0.00','50480.24','	',0,2,'2012-06-16','0.00','41377.25'),(27,'2012-07-15','2012-07-15','aaiosdjaio jiofjdsofjdio sjfiojio sfjfiosdjfij sdofjiod',2365,NULL,'Carrelli',0,'0.00','0.00','43555.00',5,22,'9103.00',0,'0.00','50480.24','	',0,2,'2012-06-16','0.00','41377.25'),(28,'2012-07-15','2012-07-15','aaiosdjaio jiofjdsosdfs3432432iojio sfjfiosdjfij sdofjiod',2365,NULL,'Carrelli',0,'0.00','0.00','43555.00',5,22,'9103.00',0,'0.00','50480.24','	',0,2,'2012-06-16','0.00','41377.25'),(29,'2012-12-01','2012-12-01',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(30,'2012-10-22','2012-10-22','fgffgwerwerwerwerwerwerwerwerwerwerwerwerwerwerwerwerwer',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(31,'2012-10-22','2012-10-22','fgffgwerwerwerwerwerwerwerwerwerwerwerwerwerewr',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(32,'2012-10-22','2012-10-22','fgffgwerwerwerwerwerwerwerwerwerewrewrewrewrwerew',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(33,'2012-10-22','2012-10-22','fgffgwerwerwerwerwerwerwerwerwerwerwerewrewrwerw',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(34,'2012-10-22','2012-10-22','fgffg',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(35,'2012-10-22','2012-10-22','fgffg',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(36,'2012-10-22','2012-10-22','fgffg',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(37,'2012-10-22','2012-10-22','fgffg',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,7,'2012-06-25','0.00','444.00'),(38,'2012-10-22','2012-10-22','fgffg',2365,NULL,NULL,0,'0.00','0.00','444.00',0,21,'93.24',0,'0.00','537.24',NULL,0,8,'2012-06-25','0.00','444.00'),(39,'2012-11-12','2012-11-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(40,'2012-11-22','2012-11-22',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(41,'2012-12-12','2012-12-12',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,1,'2012-06-16','0.00','0.00'),(42,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(43,'2012-12-12','2012-12-12',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(44,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(45,'2012-12-11','2012-12-11',NULL,2397,2,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00','sfsdfsdfsdfds',1,1,'2012-06-16','0.00','0.00'),(46,'2012-12-12','2012-12-12',' ciao ciao',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(47,'2012-12-12','2012-12-12',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(48,'2012-12-12','2012-12-12',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,1,'2012-06-16','0.00','0.00'),(49,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(50,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(51,'2012-11-11','2012-11-11','52',2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(52,'2012-11-11','2012-11-11',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(53,'2012-12-21','2012-12-21',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(54,'2012-12-21','2012-12-21',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(55,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(56,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(57,'2012-12-12','2012-12-12','niovoa',2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(58,'2012-12-12','2012-12-12','niovoa',2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(59,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(60,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(61,'2012-12-12','2012-12-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(62,'2012-12-12','2012-12-12',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(63,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(64,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(65,'2012-05-26','2012-05-26','prova forfait',2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(66,'2012-12-13','2012-12-13','ffdgdfg',2397,NULL,NULL,0,'0.00','0.00','212210.30',0,21,'44564.16',0,'0.00','256774.46',NULL,1,NULL,NULL,'0.00','212210.30'),(67,'2012-12-13','2012-12-13',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(68,'2012-12-13','2012-12-13','qqqqqqqqqqqqqqqqqqqqqqqqqqqq',2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(69,'2012-12-12','2012-12-12',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(70,'2012-12-12','2012-12-12','eeeeeeeee33eeee',2397,NULL,'q',0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(71,'2012-12-12','2012-12-12','rrrrrrrrrrrrrrr4444',2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(72,'2012-12-14','2012-12-14','14',2397,NULL,NULL,0,'0.00','0.00','1343.00',0,21,'282.03',0,'0.00','1625.03',NULL,0,3,'2012-06-16','0.00','1343.00'),(73,'2012-12-14','2012-12-14','yyyyyyyyyyyyyyyyy',2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(74,'2012-12-12','2012-12-12',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,11,'2012-08-26','0.00','0.00'),(75,'2012-05-26','2012-05-26','werwerwerwerwerwerwerwerwerwerwerwerwerer',2365,NULL,NULL,0,'0.00','0.00','4242.00',0,21,'890.82',0,'0.00','5132.82',NULL,1,7,'2012-06-25','0.00','4242.00'),(76,'2012-06-02','2012-06-02','prova prova',2373,2,'Carrelli',0,'0.00','0.00','223.00',0,21,'46.83',0,'0.00','269.83','note di prova prova',0,NULL,NULL,'0.00','223.00'),(77,'2012-10-10','2012-10-10','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(78,'2012-12-10','2012-12-10',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(79,'2012-10-04','2012-10-04',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,2,'2012-06-16','0.00','0.00'),(80,'2012-10-12','2012-10-12',NULL,2391,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(81,'2012-12-12','2012-12-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(82,'2012-12-12','2012-12-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(83,'2012-11-13','2012-11-13',NULL,2393,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(84,'2012-11-11','2012-11-11',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,9,'2012-06-30','0.00','0.00'),(85,'2012-12-12','2012-12-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(86,'2012-12-13','2012-12-13',NULL,2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(87,'2012-12-11','2012-12-11','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(88,'2012-11-12','2012-11-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(89,'2012-11-13','2012-11-13','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(90,'2012-11-12','2012-11-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(91,'2012-11-12','2012-11-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(92,'2012-11-12','2012-11-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffff',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(93,'2012-12-12','2012-12-12','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,12,'2012-08-27','0.00','0.00'),(94,'2012-12-12','2012-12-12',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(95,'2012-12-12','2012-12-12',NULL,2398,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(96,'2012-12-12','2012-12-12',NULL,2366,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,11,'2012-08-26','0.00','0.00'),(97,'2012-12-12','2012-12-12',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,1,NULL,NULL,'0.00','0.00'),(98,'2012-12-12','2012-12-12',NULL,2378,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(99,'2012-12-12','2012-12-12',NULL,2392,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(100,'2012-12-12','2012-12-12',NULL,2393,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(101,'2012-12-12','2012-12-12',NULL,2393,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(102,'2012-12-12','2012-12-12',NULL,2393,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(103,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(104,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(105,'2012-12-12','2012-12-12',NULL,2365,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,8,'2012-06-25','0.00','0.00'),(106,'2012-12-12','2012-12-12',NULL,2393,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(107,'2012-12-12','2012-12-12','descrizione',2392,2,NULL,0,'0.00','0.00','3848.00',0,21,'808.08',0,'0.00','4656.08',NULL,0,4,'2012-06-16','0.00','3848.00'),(108,'2012-12-12','2012-12-12',NULL,2394,NULL,NULL,0,'0.00','0.00','2434.00',0,21,'511.14',0,'0.00','2945.14',NULL,0,5,'2012-06-23','0.00','2434.00'),(109,'2012-12-12','2012-12-12','dsdfsdfdsfsdf',2394,NULL,NULL,0,'0.00','0.00','121212.00',0,21,'25454.52',0,'0.00','146666.52','nessunissima',0,6,'2012-06-23','0.00','121212.00'),(110,'2012-12-12','2012-12-12',NULL,2397,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,10,'2012-06-30','0.00','0.00'),(111,'2012-12-12','2012-12-12',NULL,2392,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(112,'2012-12-12','2012-12-12',NULL,2401,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(113,'2012-02-12','2012-02-12',NULL,2401,NULL,NULL,0,'0.00','0.00','0.00',0,21,'0.00',0,'0.00','0.00',NULL,0,NULL,NULL,'0.00','0.00'),(114,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(115,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(116,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(117,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(118,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(119,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(120,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(121,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(122,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(123,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(124,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(125,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(126,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(127,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(128,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(129,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(130,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(131,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(132,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(133,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(134,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(135,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(136,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(137,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(138,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(139,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(140,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(141,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(142,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(143,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(144,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(145,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(146,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(147,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(148,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(149,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80'),(150,'2012-08-27','2012-08-27','prova con più di 2 pagine',2399,8,'Cc',3,'0.40','0.20','1.80',0,21,'88.58',21,'420.00','510.38',NULL,0,NULL,NULL,'2000.00','421.80');
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

-- Dump completed on 2012-09-08 21:46:11
