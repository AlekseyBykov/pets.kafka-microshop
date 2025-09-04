#!/bin/bash

docker compose -f "$(dirname "$0")/docker-compose.yml" logs -f orders-service payments-service
