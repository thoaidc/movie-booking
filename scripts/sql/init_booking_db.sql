
CREATE DATABASE IF NOT EXISTS `mb_booking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT ENCRYPTION='N';
USE `mb_booking`;

DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int,
    `show_id` int NOT NULL,
    `total_amount` DECIMAL(10,2) NOT NULL,
    `create_time` timestamp NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `booking_seat`;
CREATE TABLE `booking_seat` (
    `id` int NOT NULL AUTO_INCREMENT,
    `booking_id` int NOT NULL,
    `seat_id` int NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_booking_seat (booking_id, seat_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
