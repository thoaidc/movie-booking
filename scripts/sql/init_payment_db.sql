CREATE DATABASE IF NOT EXISTS `mb_payment` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT ENCRYPTION='N';
USE `mb_payment`;

DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `booking_id` INT NOT NULL,
    `transaction_id` VARCHAR(255),
    `amount` DECIMAL(10,2) NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    `payment_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_payment_booking (booking_id),
    INDEX idx_payment_status (status),
    INDEX idx_payment_transaction (transaction_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `payment_id` INT NOT NULL,
    `amount` DECIMAL(10,2) NOT NULL,
    `reason` TEXT,
    `refund_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_refund_payment FOREIGN KEY (payment_id) REFERENCES payment(id),
    INDEX idx_refund_payment (payment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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