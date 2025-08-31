#!/bin/bash
set -e

echo "Собираем JAR-ы..."
mvn clean package -DskipTests

echo "Запускаем Kafka + сервисы..."
docker compose -f "$(dirname "$0")/docker-compose.yml" up --build -d
