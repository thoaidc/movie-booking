#!/bin/bash
set -e

echo "Waiting for databases to be fully initialized..."
sleep 10

echo "Initializing movie database..."
mysql -h movie-db -u root -proot < /scripts/sql/init_movie_db.sql

echo "Initializing user database..."
mysql -h user-db -u root -proot < /scripts/sql/init_user_db.sql

echo "Initializing payment database..."
mysql -h payment-db -u root -proot < /scripts/sql/init_payment_db.sql

echo "Initializing booking database..."
mysql -h booking-db -u root -proot < /scripts/sql/init_booking_db.sql

echo "Initializing notification database..."
mysql -h notification-db -u root -proot < /scripts/sql/init_notification_db.sql

echo "All databases have been initialized successfully!"
