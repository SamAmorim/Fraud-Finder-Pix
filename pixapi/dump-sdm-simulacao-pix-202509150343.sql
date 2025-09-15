/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.7.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: sdm-simulacao-pix
-- ------------------------------------------------------
-- Server version	11.8.3-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `chaves_pix`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `chaves_pix` (
  `id` uuid NOT NULL,
  `chave` varchar(255) NOT NULL,
  `id_conta` uuid NOT NULL,
  `id_tipo_chave` smallint(6) NOT NULL,
  `cadastrada_em` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `chaves_pix_unique` (`chave`),
  KEY `chaves_pix_contas_FK` (`id_conta`),
  KEY `chaves_pix_tipo_chave_pix_FK` (`id_tipo_chave`),
  CONSTRAINT `chaves_pix_contas_FK` FOREIGN KEY (`id_conta`) REFERENCES `contas` (`id`),
  CONSTRAINT `chaves_pix_tipos_chave_pix_FK` FOREIGN KEY (`id_tipo_chave`) REFERENCES `tipos_chave_pix` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Chaves PIX';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chaves_pix`
--

LOCK TABLES `chaves_pix` WRITE;
/*!40000 ALTER TABLE `chaves_pix` DISABLE KEYS */;
/*!40000 ALTER TABLE `chaves_pix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` uuid NOT NULL,
  `nome` varchar(100) NOT NULL,
  `id_natureza` int(11) NOT NULL,
  `registro_nacional` varchar(100) NOT NULL,
  `nascido_em` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clientes_registro_nacional_unique` (`registro_nacional`),
  KEY `clientes_naturezas_FK` (`id_natureza`),
  CONSTRAINT `clientes_naturezas_FK` FOREIGN KEY (`id_natureza`) REFERENCES `naturezas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Pessoas físicas/jurídicas/governamentais proprietárias de contas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contas`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `contas` (
  `id` uuid NOT NULL,
  `saldo` decimal(10,2) NOT NULL,
  `aberta_em` datetime NOT NULL,
  `agencia` char(4) NOT NULL,
  `numero` char(10) NOT NULL,
  `id_tipo` smallint(6) NOT NULL,
  `id_cliente` uuid NOT NULL,
  `ispb_instituicao` char(8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contas_tipos_FK` (`id_tipo`),
  KEY `contas_instituicoes_FK` (`ispb_instituicao`),
  KEY `contas_clientes_FK` (`id_cliente`),
  CONSTRAINT `contas_clientes_FK` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`),
  CONSTRAINT `contas_instituicoes_FK` FOREIGN KEY (`ispb_instituicao`) REFERENCES `instituicoes` (`ispb`),
  CONSTRAINT `contas_tipos_conta_FK` FOREIGN KEY (`id_tipo`) REFERENCES `tipos_conta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Contas bancárias';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contas`
--

LOCK TABLES `contas` WRITE;
/*!40000 ALTER TABLE `contas` DISABLE KEYS */;
/*!40000 ALTER TABLE `contas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enderecos`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `enderecos` (
  `id` uuid NOT NULL,
  `id_cliente` uuid NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` smallint(6) DEFAULT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  `bairro` varchar(100) DEFAULT NULL,
  `cep` char(9) NOT NULL,
  `id_municipio` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `endereco_pessoas_FK` (`id_cliente`),
  KEY `enderecos_municipios_FK` (`id_municipio`),
  CONSTRAINT `enderecos_clientes_FK` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `enderecos_municipios_FK` FOREIGN KEY (`id_municipio`) REFERENCES `municipios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Endereços de pessoas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enderecos`
--

LOCK TABLES `enderecos` WRITE;
/*!40000 ALTER TABLE `enderecos` DISABLE KEYS */;
/*!40000 ALTER TABLE `enderecos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `estados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_ibge` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `sigla` char(2) NOT NULL,
  `id_regiao` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `estados_regioes_FK` (`id_regiao`),
  CONSTRAINT `estados_regioes_FK` FOREIGN KEY (`id_regiao`) REFERENCES `regioes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Estados';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finalidade_pix`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `finalidade_pix` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finalidade_pix`
--

LOCK TABLES `finalidade_pix` WRITE;
/*!40000 ALTER TABLE `finalidade_pix` DISABLE KEYS */;
/*!40000 ALTER TABLE `finalidade_pix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instituicoes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `instituicoes` (
  `ispb` char(8) NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`ispb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Instituições financeiras';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instituicoes`
--

LOCK TABLES `instituicoes` WRITE;
/*!40000 ALTER TABLE `instituicoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `instituicoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `municipios`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `municipios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_ibge` int(11) NOT NULL,
  `nome` int(11) NOT NULL,
  `id_estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `municipios_estados_FK` (`id_estado`),
  CONSTRAINT `municipios_estados_FK` FOREIGN KEY (`id_estado`) REFERENCES `estados` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Municípios';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipios`
--

LOCK TABLES `municipios` WRITE;
/*!40000 ALTER TABLE `municipios` DISABLE KEYS */;
/*!40000 ALTER TABLE `municipios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `naturezas`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `naturezas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Naturezas jurídicas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naturezas`
--

LOCK TABLES `naturezas` WRITE;
/*!40000 ALTER TABLE `naturezas` DISABLE KEYS */;
INSERT INTO `naturezas` VALUES
(1,'Pessoa Física');
/*!40000 ALTER TABLE `naturezas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regioes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `regioes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Regiões de agrupamento de unidades federativas/estados';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regioes`
--

LOCK TABLES `regioes` WRITE;
/*!40000 ALTER TABLE `regioes` DISABLE KEYS */;
/*!40000 ALTER TABLE `regioes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_chave_pix`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_chave_pix` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Tipos de chaves PIX';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_chave_pix`
--

LOCK TABLES `tipos_chave_pix` WRITE;
/*!40000 ALTER TABLE `tipos_chave_pix` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipos_chave_pix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_conta`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_conta` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Tipos de contas bancárias';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_conta`
--

LOCK TABLES `tipos_conta` WRITE;
/*!40000 ALTER TABLE `tipos_conta` DISABLE KEYS */;
INSERT INTO `tipos_conta` VALUES
(1,'Conta Corrente');
/*!40000 ALTER TABLE `tipos_conta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_iniciacao_pix`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_iniciacao_pix` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Forma de iniciação da transação PIX';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_iniciacao_pix`
--

LOCK TABLES `tipos_iniciacao_pix` WRITE;
/*!40000 ALTER TABLE `tipos_iniciacao_pix` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipos_iniciacao_pix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transacoes`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `transacoes` (
  `id` uuid NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `data` datetime NOT NULL,
  `mensagem` varchar(100) DEFAULT NULL,
  `id_conta_origem` uuid NOT NULL,
  `id_conta_destino` uuid NOT NULL,
  `id_tipo_iniciacao_pix` smallint(6) NOT NULL,
  `id_finalidade_pix` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `transacoes_contas_FK` (`id_conta_origem`),
  KEY `transacoes_contas_FK_1` (`id_conta_destino`),
  KEY `transacoes_tipos_iniciacao_pix_FK` (`id_tipo_iniciacao_pix`),
  KEY `transacoes_finalidade_pix_FK` (`id_finalidade_pix`),
  CONSTRAINT `transacoes_contas_destino_FK` FOREIGN KEY (`id_conta_destino`) REFERENCES `contas` (`id`),
  CONSTRAINT `transacoes_contas_origem_FK` FOREIGN KEY (`id_conta_origem`) REFERENCES `contas` (`id`),
  CONSTRAINT `transacoes_finalidade_pix_FK` FOREIGN KEY (`id_finalidade_pix`) REFERENCES `finalidade_pix` (`id`),
  CONSTRAINT `transacoes_tipos_iniciacao_pix_FK` FOREIGN KEY (`id_tipo_iniciacao_pix`) REFERENCES `tipos_iniciacao_pix` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Tabela de transações PIX';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transacoes`
--

LOCK TABLES `transacoes` WRITE;
/*!40000 ALTER TABLE `transacoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `transacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sdm-simulacao-pix'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-09-15  3:44:01
