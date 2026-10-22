-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 22, 2026 at 12:43 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `banking_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `account_number` varchar(10) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `account_type` varchar(20) NOT NULL,
  `balance` decimal(12,2) NOT NULL DEFAULT 0.00,
  `branch` varchar(50) NOT NULL,
  `date_opened` date NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Active',
  `initial_deposit` decimal(12,2) DEFAULT NULL,
  `employer_name` varchar(255) DEFAULT NULL,
  `employer_address` varchar(255) DEFAULT NULL,
  `cheque_book_number` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_number`, `customer_id`, `account_type`, `balance`, `branch`, `date_opened`, `status`, `initial_deposit`, `employer_name`, `employer_address`, `cheque_book_number`) VALUES
('ACC00001', 4, 'SavingsAccount', 1200.00, 'Gaborone Main', '2026-10-22', 'Active', 1000.00, NULL, NULL, NULL),
('ACC00002', 4, 'InvestmentAccount', 770.00, 'Gaborone Main', '2026-10-22', 'Active', 500.00, NULL, NULL, NULL),
('ACC00003', 5, 'SavingsAccount', 1250.00, 'Gaborone Main', '2026-10-22', 'Active', 1250.00, NULL, NULL, NULL),
('ACC00004', 5, 'ChequeAccount', 3500.00, 'Gaborone Main', '2026-10-22', 'Active', 3500.00, 'Botswana Employer 2 Ltd', 'Plot 2, Gaborone', NULL),
('ACC00005', 6, 'SavingsAccount', 1500.00, 'Gaborone Main', '2026-10-22', 'Active', 1500.00, NULL, NULL, NULL),
('ACC00006', 6, 'InvestmentAccount', 700.00, 'Gaborone Main', '2026-10-22', 'Active', 700.00, NULL, NULL, NULL),
('ACC00007', 6, 'ChequeAccount', 4000.00, 'Gaborone Main', '2026-10-22', 'Active', 4000.00, 'Botswana Employer 3 Ltd', 'Plot 3, Gaborone', NULL),
('ACC00008', 7, 'SavingsAccount', 1750.00, 'Gaborone Main', '2026-10-22', 'Active', 1750.00, NULL, NULL, NULL),
('ACC00009', 7, 'InvestmentAccount', 800.00, 'Gaborone Main', '2026-10-22', 'Active', 800.00, NULL, NULL, NULL),
('ACC00010', 8, 'SavingsAccount', 2000.00, 'Gaborone Main', '2026-10-22', 'Active', 2000.00, NULL, NULL, NULL),
('ACC00011', 8, 'ChequeAccount', 5000.00, 'Gaborone Main', '2026-10-22', 'Active', 5000.00, 'Botswana Employer 5 Ltd', 'Plot 5, Gaborone', NULL),
('ACC00012', 9, 'SavingsAccount', 2250.00, 'Gaborone Main', '2026-10-22', 'Active', 2250.00, NULL, NULL, NULL),
('ACC00013', 9, 'InvestmentAccount', 1000.00, 'Gaborone Main', '2026-10-22', 'Active', 1000.00, NULL, NULL, NULL),
('ACC00014', 9, 'ChequeAccount', 5500.00, 'Gaborone Main', '2026-10-22', 'Active', 5500.00, 'Botswana Employer 6 Ltd', 'Plot 6, Gaborone', NULL),
('ACC00015', 10, 'SavingsAccount', 2500.00, 'Gaborone Main', '2026-10-22', 'Active', 2500.00, NULL, NULL, NULL),
('ACC00016', 10, 'InvestmentAccount', 1100.00, 'Gaborone Main', '2026-10-22', 'Active', 1100.00, NULL, NULL, NULL),
('ACC00017', 11, 'SavingsAccount', 2750.00, 'Gaborone Main', '2026-10-22', 'Active', 2750.00, NULL, NULL, NULL),
('ACC00018', 11, 'ChequeAccount', 6500.00, 'Gaborone Main', '2026-10-22', 'Active', 6500.00, 'Botswana Employer 8 Ltd', 'Plot 8, Gaborone', NULL),
('ACC00019', 12, 'SavingsAccount', 3000.00, 'Gaborone Main', '2026-10-22', 'Active', 3000.00, NULL, NULL, NULL),
('ACC00020', 12, 'InvestmentAccount', 1300.00, 'Gaborone Main', '2026-10-22', 'Active', 1300.00, NULL, NULL, NULL),
('ACC00021', 12, 'ChequeAccount', 7000.00, 'Gaborone Main', '2026-10-22', 'Active', 7000.00, 'Botswana Employer 9 Ltd', 'Plot 9, Gaborone', NULL),
('ACC00022', 13, 'SavingsAccount', 3250.00, 'Gaborone Main', '2026-10-22', 'Active', 3250.00, NULL, NULL, NULL),
('ACC00023', 13, 'InvestmentAccount', 1400.00, 'Gaborone Main', '2026-10-22', 'Active', 1400.00, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `address` varchar(150) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `id_number` varchar(30) DEFAULT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `date_registered` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `surname`, `address`, `date_of_birth`, `id_number`, `phone_number`, `email`, `date_registered`) VALUES
(4, 'Neo', 'Kgosi', 'Plot 12, Gaborone', NULL, NULL, NULL, NULL, '2026-10-22'),
(5, 'Lesedi', 'Moeng', 'Block 8, Francistown', NULL, NULL, NULL, NULL, '2026-10-22'),
(6, 'Tumelo', 'Sebego', 'Plot 301, Maun', NULL, NULL, NULL, NULL, '2026-10-22'),
(7, 'Kefilwe', 'Ndlovu', 'Plot 45, Palapye', NULL, NULL, NULL, NULL, '2026-10-22'),
(8, 'Thabo', 'Molefe', 'Block 3, Lobatse', NULL, NULL, NULL, NULL, '2026-10-22'),
(9, 'Boitumelo', 'Dikgang', 'Plot 77, Serowe', NULL, NULL, NULL, NULL, '2026-10-22'),
(10, 'Onkarabile', 'Mosweu', 'Plot 9, Kasane', NULL, NULL, NULL, NULL, '2026-10-22'),
(11, 'Masego', 'Phiri', 'Block 14, Selebi-Phikwe', NULL, NULL, NULL, NULL, '2026-10-22'),
(12, 'Gofaone', 'Tau', 'Plot 220, Mahalapye', NULL, NULL, NULL, NULL, '2026-10-22'),
(13, 'Naledi', 'Segwabe', 'Plot 56, Molepolole', NULL, NULL, NULL, NULL, '2026-10-22');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `account_number` varchar(10) NOT NULL,
  `date_time` datetime NOT NULL,
  `type` varchar(30) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `balance_after` decimal(12,2) NOT NULL,
  `description` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `account_number`, `date_time`, `type`, `amount`, `balance_after`, `description`) VALUES
(5, 'ACC00001', '2026-10-22 12:29:28', 'Deposit', 1000.00, 1000.00, NULL),
(6, 'ACC00002', '2026-10-22 12:29:28', 'Initial deposit', 500.00, 500.00, NULL),
(7, 'ACC00003', '2026-10-22 12:29:28', 'Deposit', 1250.00, 1250.00, NULL),
(8, 'ACC00004', '2026-10-22 12:29:28', 'Salary', 3500.00, 3500.00, NULL),
(9, 'ACC00005', '2026-10-22 12:29:28', 'Deposit', 1500.00, 1500.00, NULL),
(10, 'ACC00006', '2026-10-22 12:29:28', 'Initial deposit', 700.00, 700.00, NULL),
(11, 'ACC00007', '2026-10-22 12:29:28', 'Salary', 4000.00, 4000.00, NULL),
(12, 'ACC00008', '2026-10-22 12:29:28', 'Deposit', 1750.00, 1750.00, NULL),
(13, 'ACC00009', '2026-10-22 12:29:28', 'Initial deposit', 800.00, 800.00, NULL),
(14, 'ACC00010', '2026-10-22 12:29:28', 'Deposit', 2000.00, 2000.00, NULL),
(15, 'ACC00011', '2026-10-22 12:29:28', 'Salary', 5000.00, 5000.00, NULL),
(16, 'ACC00012', '2026-10-22 12:29:28', 'Deposit', 2250.00, 2250.00, NULL),
(17, 'ACC00013', '2026-10-22 12:29:28', 'Initial deposit', 1000.00, 1000.00, NULL),
(18, 'ACC00014', '2026-10-22 12:29:28', 'Salary', 5500.00, 5500.00, NULL),
(19, 'ACC00015', '2026-10-22 12:29:28', 'Deposit', 2500.00, 2500.00, NULL),
(20, 'ACC00016', '2026-10-22 12:29:28', 'Initial deposit', 1100.00, 1100.00, NULL),
(21, 'ACC00017', '2026-10-22 12:29:28', 'Deposit', 2750.00, 2750.00, NULL),
(22, 'ACC00018', '2026-10-22 12:29:28', 'Salary', 6500.00, 6500.00, NULL),
(23, 'ACC00019', '2026-10-22 12:29:28', 'Deposit', 3000.00, 3000.00, NULL),
(24, 'ACC00020', '2026-10-22 12:29:28', 'Initial deposit', 1300.00, 1300.00, NULL),
(25, 'ACC00021', '2026-10-22 12:29:29', 'Salary', 7000.00, 7000.00, NULL),
(26, 'ACC00022', '2026-10-22 12:29:29', 'Deposit', 3250.00, 3250.00, NULL),
(27, 'ACC00023', '2026-10-22 12:29:29', 'Initial deposit', 1400.00, 1400.00, NULL),
(28, 'ACC00002', '2026-10-22 12:37:23', 'Deposit', 400.00, 900.00, NULL),
(29, 'ACC00001', '2026-10-22 12:37:49', 'Deposit', 200.00, 1200.00, NULL),
(30, 'ACC00002', '2026-10-22 12:37:59', 'Deposit', 100.00, 1000.00, NULL),
(31, 'ACC00002', '2026-10-22 12:38:16', 'Withdrawal', 230.00, 770.00, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL,
  `customer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `username`, `password_hash`, `role`, `customer_id`) VALUES
(1, 'Neo Kgosi', 'neo', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 4),
(2, 'Lesedi Moeng', 'lesedi', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 5),
(3, 'Tumelo Sebego', 'tumelo', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 6),
(4, 'Kefilwe Ndlovu', 'kefilwe', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 7),
(5, 'Thabo Molefe', 'thabo', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 8),
(6, 'Boitumelo Dikgang', 'boitumelo', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 9),
(7, 'Onkarabile Mosweu', 'onkarabile', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 10),
(8, 'Masego Phiri', 'masego', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 11),
(9, 'Gofaone Tau', 'gofaone', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 12),
(10, 'Naledi Segwabe', 'naledi', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'customer', 13),
(11, 'Bank Teller', 'teller', 'd277f25b136e402488431a226b652b2bff919188028af250d9dab860e2c436fd', 'teller', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_number`),
  ADD UNIQUE KEY `one_type_per_customer` (`customer_id`,`account_type`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `account_number` (`account_number`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `fk_user_customer` (`customer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`account_number`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_user_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
