
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

CREATE TABLE token_entry (
    processor_name VARCHAR(255) NOT NULL,
    segment INTEGER NOT NULL,
    token BLOB,
    token_type VARCHAR(255),
    timestamp VARCHAR(255),
    owner VARCHAR(255),
    PRIMARY KEY (processor_name, segment)
);

CREATE TABLE association_value_entry (
    id BIGINT NOT NULL AUTO_INCREMENT,
    association_key VARCHAR(255) NOT NULL,
    association_value VARCHAR(255) NOT NULL,
    saga_id VARCHAR(255) NOT NULL,
    saga_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_assoc_val (association_key, association_value),
    INDEX idx_saga_id (saga_id)
);

CREATE TABLE saga_entry (
    saga_id VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    saga_type VARCHAR(255) NOT NULL,
    serialized_saga BLOB,
    PRIMARY KEY (saga_id)
);

CREATE TABLE domain_event_entry (
    global_index BIGINT AUTO_INCREMENT,
    event_identifier VARCHAR(255) NOT NULL,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255),
    timestamp VARCHAR(255) NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255),
    payload BLOB NOT NULL,
    meta_data BLOB,
    PRIMARY KEY (global_index),
    UNIQUE KEY uq_aggregate_seq (aggregate_identifier, sequence_number),
    UNIQUE KEY uq_event_identifier (event_identifier)
);

CREATE TABLE snapshot_event_entry (
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number BIGINT NOT NULL,
    type VARCHAR(255),
    timestamp VARCHAR(255) NOT NULL,
    event_identifier VARCHAR(255) NOT NULL,
    payload_type VARCHAR(255) NOT NULL,
    payload_revision VARCHAR(255),
    payload BLOB NOT NULL,
    meta_data BLOB,
    PRIMARY KEY (aggregate_identifier, sequence_number),
    UNIQUE KEY uq_snapshot_event_identifier (event_identifier)
);

CREATE TABLE association_value_entry_seq (next_val BIGINT);
INSERT INTO association_value_entry_seq VALUES (1);
