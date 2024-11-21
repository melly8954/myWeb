# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin/packaging-oci-image.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#appendix.configuration-metadata.annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#using.devtools)
* [Mustache](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#web.servlet.spring-mvc.template-engines)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
* [Validation](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#io.validation)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)


### VM Options
```
IntelliJ Ultimate version 에서 active profile 에 local/dev/prod 추가

IntelliJ Community version 에서 VM 옵션 추가
-Dspring.profiles.active=local
-Dspring.profiles.active=dev
-Dspring.profiles.active=prod
```


### Mysql 명령어
```
create database myWeb_db character set utf8mb4 collate utf8mb4_general_ci;
create user 'psw_web'@'%' identified by 'web123123!';
grant all privileges on myWeb_db.* to 'psw_web'@'%' with grant option;
flush privileges;

```

### SQL DDL
```
-- 1. user_tbl
-- myweb_db.user_tbl definition

CREATE TABLE `user_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `loginId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `photo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginId` (`loginId`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- 2. board_tbl
-- myweb_db.board_tbl definition

CREATE TABLE `board_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `viewQty` int DEFAULT '0',
  `likeQty` int DEFAULT '0',
  `createId` bigint unsigned DEFAULT NULL,
  `createDt` datetime DEFAULT NULL,
  `updateId` bigint unsigned DEFAULT NULL,
  `updateDt` datetime DEFAULT NULL,
  `deleteId` bigint unsigned DEFAULT NULL,
  `deleteDt` datetime DEFAULT NULL,
  `deleteFlag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_tbl_user_tbl_createId` (`createId`),
  KEY `board_tbl_user_tbl_updateId` (`updateId`),
  KEY `board_tbl_user_tbl_deleteId` (`deleteId`),
  KEY `board_tbl_id_IDX` (`id`,`deleteFlag`) USING BTREE,
  KEY `board_tbl_title_IDX` (`title`,`deleteFlag`) USING BTREE,
  CONSTRAINT `board_tbl_user_tbl_createId` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_tbl_user_tbl_deleteId` FOREIGN KEY (`deleteId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `board_tbl_user_tbl_updateId` FOREIGN KEY (`updateId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- 3. board_like_tbl
-- myweb_db.board_like_tbl definition

CREATE TABLE `board_like_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createId` bigint unsigned NOT NULL,
  `boardId` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_like_tbl_user_tbl_createId` (`createId`),
  KEY `board_like_tbl_tbl_IDX` (`tbl`,`createId`,`boardId`) USING BTREE,
  CONSTRAINT `board_like_tbl_user_tbl_createId` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- 4. board_comment_tbl
-- myweb_db.board_comment_tbl definition

CREATE TABLE `board_comment_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `boardId` bigint unsigned NOT NULL,
  `comment` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createDt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createId` bigint DEFAULT NULL,
  `likeQty` int NOT NULL DEFAULT '0',
  `updateDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleteYn` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_comment_tbl_board_tbl_FK` (`tbl`,`boardId`),
  KEY `board_comment_tbl_member_tbl_createId` (`createId`),
  KEY `board_comment_tbl_boardId_IDX` (`tbl`,`boardId`,`deleteYn`) USING BTREE,
  KEY `board_comment_tbl_id_IDX` (`id`,`deleteYn`) USING BTREE,
  CONSTRAINT `board_comment_tbl_user_tbl_FK` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- 5. board_file_tbl
-- myweb_db.board_file_tbl definition

CREATE TABLE `board_file_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `boardId` bigint unsigned NOT NULL DEFAULT '0',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ord` int unsigned NOT NULL DEFAULT '1',
  `fileType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uniqName` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `length` int unsigned NOT NULL DEFAULT '0',
  `deleteYn` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_file_tbl_id_IDX` (`id`,`deleteYn`) USING BTREE,
  KEY `board_file_tbl_boardId_IDX` (`tbl`,`boardId`) USING BTREE,
  KEY `board_file_tbl_boardId_deleteYn_IDX` (`tbl`,`boardId`,`deleteYn`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- 6. comment_like_tbl
-- myweb_db.comment_like_tbl definition

CREATE TABLE `comment_like_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `commentId` bigint unsigned NOT NULL,
  `createId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_like_tbl_boardId_IDX` (`commentId`) USING BTREE,
  KEY `comment_like_tbl_createId_IDX` (`createId`) USING BTREE,
  CONSTRAINT `comment_like_tbl_board_comment_tbl_FK` FOREIGN KEY (`commentId`) REFERENCES `board_comment_tbl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_like_tbl_user_tbl_FK` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

```










### 리눅스 프로세스 제거
```
ps -ef | grep sbsj
java sbsjprj 프로세스 번호를 확인하고
kill -9 프로세스번호
```

### java 실행 명령어
```
nohup java -jar -server -Dspring.profiles.active=local /home/ubuntu/sbsj/sbsj-0.0.1-SNAPSHOT.jar &
```
