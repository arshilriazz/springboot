CREATE DATABASE  IF NOT EXISTS `student_fee`;
USE `student_fee`;

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roll` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `semester` varchar(45) DEFAULT NULL,
  `fee_due` varchar(45),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO `student` VALUES
	(1,'1604-16-733-025','Fazil','Ali','fazilali@gmail.com','Semester_1','No_Due'),
	(2,'1604-16-733-026','Shaik','Aziz','msa@gmail.com','Semester_1','Due'),
	(3,'1604-16-733-028','Arshil','Riaz','arshilriaz@gmail.com','Semester_1','Due');

drop table if exists user;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
insert into user values
(1, 'admin', '$2a$10$p6R9L/YJCtrw0.vx5twf7ujOw.xSXHVZ1w19Fk8J3iAdNKrh50zLG', 'ROLE_ADMIN'),
(2, '1604-16-733-025', '$2a$10$p6R9L/YJCtrw0.vx5twf7ujOw.xSXHVZ1w19Fk8J3iAdNKrh50zLG', 'ROLE_USER'),
(3, '1604-16-733-026', '$2a$10$p6R9L/YJCtrw0.vx5twf7ujOw.xSXHVZ1w19Fk8J3iAdNKrh50zLG', 'ROLE_USER'),
(4, '1604-16-733-028', '$2a$10$p6R9L/YJCtrw0.vx5twf7ujOw.xSXHVZ1w19Fk8J3iAdNKrh50zLG', 'ROLE_USER');