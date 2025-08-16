CREATE DATABASE IF NOT EXISTS `mb_user` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT ENCRYPTION='N';
USE `mb_user`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id` int NOT NULL AUTO_INCREMENT,
    `fullname` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `email` varchar(100) NOT NULL,
    `phone` varchar(20) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `mb_user`.`users` (`fullname`, `email`, `phone`, `password`, `username`)
VALUES ('abc', 'sferfer@gmail.com', '2134234234', '$2a$12$9NEesgjknxfzCnyOqaq3T.tD3IT9RuY./MAxf8RMxBLTzuDf6k6Qy', 'thoai');
