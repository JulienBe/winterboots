# Winter boots

## Basic spring boot crud application

### How to compile it ?

`docker-compose -f docker/docker-compose.yaml  up -d database` to start the database which is needed for the tests

`gradle build` will produce a jar here `./build/libs/winterboots-0.0.1-SNAPSHOT.jar` 

### How to run it ?

`docker-compose -f docker/docker-compose.yaml up --build`

### What does the db looks like for the moment ?

The database is persisted in the `docker/data/` folder.

```sql
MariaDB [toyota]> show tables;
+--------------------+
| Tables_in_toyota   |
+--------------------+
| hibernate_sequence |
| product            |
| user               |
+--------------------+
4 rows in set (0.001 sec)

MariaDB [toyota]> describe product;
+-------------+---------------+------+-----+---------+-------+
| Field       | Type          | Null | Key | Default | Extra |
+-------------+---------------+------+-----+---------+-------+
| id          | bigint(20)    | NO   | PRI | NULL    |       |
| name        | varchar(255)  | YES  |     | NULL    |       |
| next_id     | bigint(20)    | NO   |     | NULL    |       |
| previous_id | bigint(20)    | NO   |     | NULL    |       |
| price       | decimal(12,2) | YES  |     | NULL    |       |
+-------------+---------------+------+-----+---------+-------+
5 rows in set (0.001 sec)

MariaDB [toyota]> describe user;
+-------+--------------+------+-----+---------+-------+
| Field | Type         | Null | Key | Default | Extra |
+-------+--------------+------+-----+---------+-------+
| id    | bigint(20)   | NO   | PRI | NULL    |       |
| email | varchar(255) | YES  |     | NULL    |       |
| name  | varchar(255) | YES  |     | NULL    |       |
+-------+--------------+------+-----+---------+-------+
```

### In the future: security

Implements at the app level: https://spring.io/guides/topicals/spring-security-architecture#_authentication_and_access_control

User role and encryption at the database level

Secured helm charts and secrets the deployment level

### In the future: resiliency

At the database level: Look into galera clusters, or at least active / standby

At the deployment level: liveness probes that would query reliable healthchecks


### What would the database look like ?

```sql
DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (24),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product_list`
--

DROP TABLE IF EXISTS `order_product_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_product_list` (
  `order_id` bigint(20) NOT NULL,
  `product_list_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_i69ycibqg94pqb8wmvst5qpeu` (`product_list_id`),
  CONSTRAINT `FKqnqy39b3wm1r0hf75dtipvr1d` FOREIGN KEY (`product_list_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product_list`
--

LOCK TABLES `order_product_list` WRITE;
/*!40000 ALTER TABLE `order_product_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_product_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `next_id` bigint(20) NOT NULL,
  `previous_id` bigint(20) NOT NULL,
  `price` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (17,'Toyota MR2',18,-1,6000.00),(18,'Toyota MR2',19,17,7000.00),(19,'Toyota MR2',20,18,7100.00);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (11,'bertozzijulien@gmail.com','Julien!'),(21,'notjulien@gmail.com','NotJulien');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-18 15:55:00
```