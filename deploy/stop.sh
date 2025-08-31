#!/bin/bash
set -e

echo "Останавливаем Kafka + сервисы..."
docker compose -f "$(dirname "$0")/docker-compose.yml" down -v
