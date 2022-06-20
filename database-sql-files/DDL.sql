SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DROP SCHEMA IF EXISTS `armada`;
CREATE SCHEMA IF NOT EXISTS `armada` DEFAULT CHARACTER SET utf8mb4 ;

DROP USER IF EXISTS `admin`@`localhost`;
CREATE USER IF NOT EXISTS 'admin'@'localhost'IDENTIFIED BY 'admin';
GRANT ALL privileges ON `armada`.* TO 'admin'@'localhost' ;

USE `armada`;

CREATE TABLE `armada`.`Personnel`(
	`personnelID` int NOT NULL auto_increment,
	`first_name` varchar(20) NOT NULL,
	`last_name` varchar(20) NOT NULL,
	`card_number` INT NULL,
	`department_number`INT NULL,
	`department_name` varchar(20) NOT NULL,
	`gender` varchar(10),
	CONSTRAINT `Personnel_pk` PRIMARY KEY (`personnelID`)
);

CREATE TABLE `armada`.`Rules`(
    `ruleID` int NOT NULL auto_increment,
	`rule_name` varchar(20),
	`rule_desc` varchar(100),
	CONSTRAINT `Rules_pk` PRIMARY KEY (`ruleID`)
    

);

CREATE TABLE `armada`.`Cards`(
	`card_number` INT NOT NULL auto_increment,
	`issue_date` date,
	`personnelID` int NOT NULL,
	`ruleID` INT,
	CONSTRAINT `Cards_pk` PRIMARY KEY (`card_number`),
    CONSTRAINT `Personnel_fk` FOREIGN KEY (`personnelID`) REFERENCES Personnel(personnelID),
    CONSTRAINT `Rules_fk`FOREIGN KEY (`ruleID`) REFERENCES Rules(ruleID)

);

CREATE TABLE `armada`.`Account`(
	`accountID` INT NOT NULL AUTO_INCREMENT,
	`account_name` varchar(20) NOT NULL,
	`account_password` varchar(20) NOT NULL,
	CONSTRAINT `Account_pk` PRIMARY KEY (`accountID`)
);

CREATE TABLE `armada`.`Devices`(
	`device_name` varchar(20) NOT NULL,
	`serial_number` INT NOT NULL,
	`comm_type` varchar(10),
	`ip_address` INT UNSIGNED NOT NULL,
	`serial_port_number` INT,
	CONSTRAINT `Devices_pk` PRIMARY KEY (serial_number)

);

use `armada`;
DELIMITER $$

CREATE TRIGGER after_cardnumber_insert
AFTER INSERT
ON Cards FOR EACH ROW
BEGIN
    IF NEW.card_number IS NOT NULL THEN
        UPDATE armada.personnel SET card_number = NEW.card_number WHERE personnel.personnelID=new.personnelID;
        
    END IF;
END$$





