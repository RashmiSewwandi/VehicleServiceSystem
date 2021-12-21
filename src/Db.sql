
DROP DATABASE IF EXISTS BetharaAutoService;
CREATE DATABASE IF NOT EXISTS BetharaAutoService;
SHOW DATABASES ;
USE BetharaAutoService;

DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer(
    custId VARCHAR(60),
    custName VARCHAR(100) NOT NULL DEFAULT 'Unknown',
    custAddress VARCHAR(100),
    contactNumber VARCHAR(10),
    email VARCHAR(50),
    date VARCHAR(20) ,
    CONSTRAINT PRIMARY KEY (custId)
    );
SHOW TABLES ;
DESCRIBE customer;

DROP TABLE IF EXISTS `Appointment`;
CREATE TABLE IF NOT EXISTS `Appointment`(
    custId VARCHAR(60),
    custName VARCHAR(100),
    appointment VARCHAR(10),
    time VARCHAR(20),
    date VARCHAR(20),
    CONSTRAINT PRIMARY KEY (appointment),
    CONSTRAINT FOREIGN KEY (custId) REFERENCES customer(custId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Appointment`;

DROP TABLE IF EXISTS vehicle;
CREATE TABLE IF NOT EXISTS vehicle(
    appointment VARCHAR(10),
    custName VARCHAR(100),
    vehicleNumber VARCHAR(10),
    vehicleModel VARCHAR(20),
    fualType VARCHAR(20),
    date VARCHAR(20),
    CONSTRAINT PRIMARY KEY (vehicleNumber),
    CONSTRAINT FOREIGN KEY (appointment) REFERENCES `Appointment`(appointment) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE vehicle;

DROP TABLE IF EXISTS employee;
CREATE TABLE IF NOT EXISTS employee(
    empId VARCHAR(50),
    empName VARCHAR(50),
    empAddress VARCHAR(50),
    tpNumber VARCHAR(15),
    email VARCHAR(50),
    CONSTRAINT PRIMARY KEY (empId)
    );
SHOW TABLES ;
DESCRIBE employee;

DROP TABLE IF EXISTS Empsalary;
CREATE TABLE IF NOT EXISTS Empsalary(
    SID VARCHAR(10),
    empId VARCHAR(50),
    empName VARCHAR(50),
    tpNumber VARCHAR(15),
    salary DOUBLE DEFAULT 0.00,
    date VARCHAR(20),
    CONSTRAINT PRIMARY KEY  (SID ),
    CONSTRAINT FOREIGN KEY (empId) REFERENCES employee(empId)ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE Empsalary;


DROP TABLE IF EXISTS addToService;
CREATE TABLE IF NOT EXISTS addToService(
    SId VARCHAR(10),
    vehicleNumber VARCHAR(50),
    vehicleModel VARCHAR(50),
    fualType VARCHAR(15),
    serviceType VARCHAR(50),
    CONSTRAINT PRIMARY KEY (SId),
    CONSTRAINT FOREIGN KEY (vehicleNumber) REFERENCES vehicle(vehicleNumber) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE addToService;

DROP TABLE IF EXISTS vehicleReturn;
CREATE TABLE IF NOT EXISTS vehicleReturn(
    NIC VARCHAR(20),
    custName VARCHAR(100) NOT NULL DEFAULT 'Unknown',
    vehicleNumber VARCHAR(10),
    vehicleModel VARCHAR(50),
    description VARCHAR (50),
    nextDate DATE,
    CONSTRAINT PRIMARY KEY(vehicleNumber)
    );
SHOW TABLES ;
DESCRIBE vehicleReturn;

DROP TABLE IF EXISTS payment;
CREATE TABLE IF NOT EXISTS payment(
    paymentId VARCHAR(10),
    custId VARCHAR(50),
    paymentdate VARCHAR(20),
    paymenttime VARCHAR(20),
    CONSTRAINT PRIMARY KEY (paymentId),
    CONSTRAINT FOREIGN KEY (custId) REFERENCES customer(custId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE payment;

DROP TABLE IF EXISTS serviceDetail;
CREATE TABLE IF NOT EXISTS serviceDetail(
    vehicleNumber VARCHAR(15),
    paymentId VARCHAR(16),
    serviceType VARCHAR (20),
    itemReplace VARCHAR(20),
    serviceCharge VARCHAR(100),
    CONSTRAINT PRIMARY KEY (vehicleNumber,paymentId),
    CONSTRAINT FOREIGN KEY (vehicleNumber) REFERENCES vehicle(vehicleNumber ) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (paymentId ) REFERENCES payment(paymentId ) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE serviceDetail;
