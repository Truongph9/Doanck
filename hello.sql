-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 23, 2024 lúc 02:17 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `hello`
--
CREATE DATABASE IF NOT EXISTS `hello` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `hello`;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `qlhanghoa2`
--

CREATE TABLE `qlhanghoa2` (
  `Id` int(11) NOT NULL,
  `Ten` varchar(255) DEFAULT NULL,
  `Gia` double DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DaNhap` int(11) DEFAULT NULL,
  `NgayNhap` varchar(10) DEFAULT NULL,
  `DaXuat` int(11) DEFAULT NULL,
  `NgayXuat` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `salt`) VALUES
(12, 'thuan', '346bd8b7b287aff1c629328860395c046103cd48f04f58e8c5efa02cb36cadc5', 'EVNTmtpqgb5hzs8d7ikCxw==');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `qlhanghoa2`
--
ALTER TABLE `qlhanghoa2`
  ADD PRIMARY KEY (`Id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
