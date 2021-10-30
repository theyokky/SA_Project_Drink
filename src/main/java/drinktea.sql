-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 29, 2021 at 08:38 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `drinktea`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `Cus_id` int(5) NOT NULL,
  `Cus_name` varchar(255) COLLATE utf8_thai_520_w2 NOT NULL,
  `Cus_point` int(2) DEFAULT NULL,
  `Cus_right` int(5) DEFAULT NULL,
  `Cus_tel` varchar(10) COLLATE utf8_thai_520_w2 DEFAULT NULL,
  `Cus_status` tinyint(1) DEFAULT NULL,
  `Cus_userName` varchar(14) COLLATE utf8_thai_520_w2 NOT NULL,
  `Cus_passWord` varchar(14) COLLATE utf8_thai_520_w2 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_thai_520_w2;

-- --------------------------------------------------------

--
-- Table structure for table `orderin`
--

CREATE TABLE `orderin` (
  `O_id` int(5) NOT NULL,
  `O_status` tinyint(1) DEFAULT NULL,
  `O_date` date NOT NULL,
  `Cus_id` int(5) NOT NULL,
  `Staff_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_thai_520_w2;

-- --------------------------------------------------------

--
-- Table structure for table `orderlist`
--

CREATE TABLE `orderlist` (
  `Od_id` int(5) NOT NULL,
  `quantity` int(5) DEFAULT NULL,
  `price` double(5,2) DEFAULT NULL,
  `O_id` int(5) DEFAULT NULL,
  `P_id` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_thai_520_w2;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `P_id` int(5) NOT NULL,
  `P_price` double(5,2) DEFAULT NULL,
  `P_name` varchar(255) COLLATE utf8_thai_520_w2 DEFAULT NULL,
  `P_size` enum('S','M','L') COLLATE utf8_thai_520_w2 DEFAULT NULL,
  `P_makeDate` date NOT NULL,
  `P_expirationDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_thai_520_w2;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `Staff_id` int(5) NOT NULL,
  `Staff_name` varchar(255) COLLATE utf8_thai_520_w2 NOT NULL,
  `Staff_lastname` varchar(255) COLLATE utf8_thai_520_w2 NOT NULL,
  `Staff_tel` varchar(10) COLLATE utf8_thai_520_w2 DEFAULT NULL,
  `Staff_userName` varchar(14) COLLATE utf8_thai_520_w2 DEFAULT NULL,
  `Staff_passWord` varchar(14) COLLATE utf8_thai_520_w2 DEFAULT NULL,
  `Staff_role` enum('Admin','Cashier') COLLATE utf8_thai_520_w2 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_thai_520_w2;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`Cus_id`),
  ADD UNIQUE KEY `Cus_tel` (`Cus_tel`);

--
-- Indexes for table `orderin`
--
ALTER TABLE `orderin`
  ADD PRIMARY KEY (`O_id`),
  ADD KEY `Cus_id` (`Cus_id`),
  ADD KEY `Staff_id` (`Staff_id`);

--
-- Indexes for table `orderlist`
--
ALTER TABLE `orderlist`
  ADD KEY `O_id` (`O_id`),
  ADD KEY `P_id` (`P_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`P_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`Staff_id`),
  ADD UNIQUE KEY `Staff_tel` (`Staff_tel`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orderin`
--
ALTER TABLE `orderin`
  ADD CONSTRAINT `orderin_ibfk_1` FOREIGN KEY (`Cus_id`) REFERENCES `customers` (`Cus_id`),
  ADD CONSTRAINT `orderin_ibfk_2` FOREIGN KEY (`Staff_id`) REFERENCES `staff` (`Staff_id`);

--
-- Constraints for table `orderlist`
--
ALTER TABLE `orderlist`
  ADD CONSTRAINT `orderlist_ibfk_1` FOREIGN KEY (`O_id`) REFERENCES `orderin` (`O_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `orderlist_ibfk_2` FOREIGN KEY (`P_id`) REFERENCES `product` (`P_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
