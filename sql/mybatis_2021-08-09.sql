# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.35)
# Database: mybatis
# Generation Time: 2021-08-09 15:37:21 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tbl_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_dept`;

CREATE TABLE `tbl_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `tbl_dept` WRITE;
/*!40000 ALTER TABLE `tbl_dept` DISABLE KEYS */;

INSERT INTO `tbl_dept` (`id`, `dept_name`)
VALUES
	(1,'RD'),
	(2,'QA'),
	(3,'PM');

/*!40000 ALTER TABLE `tbl_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tbl_employee
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_employee`;

CREATE TABLE `tbl_employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `d_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_emp_dept` (`d_id`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`d_id`) REFERENCES `tbl_dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `tbl_employee` WRITE;
/*!40000 ALTER TABLE `tbl_employee` DISABLE KEYS */;

INSERT INTO `tbl_employee` (`id`, `last_name`, `gender`, `email`, `d_id`)
VALUES
	(1,'tom','0','tom@126.com',1),
	(3,'jerry_update3','1','jerry_update@qq.com',2),
	(5,'jerry','1','jerry@qq.com',1),
	(6,'jerry','1','jerry@qq.com',2);

/*!40000 ALTER TABLE `tbl_employee` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
