# pets.kafka-microshop

This is a demo microservices project that demonstrates event-driven architecture with **Kafka** and **Redpanda**.  
Currently under active development.

## **Services**

- orders-service
- payments-service
- shipping-service
- notification-service
- reporting-service
- inventory-service
- auditor-service

## **Infrastructure**
- **Redpanda** – Kafka-compatible event streaming platform
- **Jaeger** – distributed tracing with OpenTelemetry
- **Docker Compose** – for local development and testing

### Ports
- Redpanda Console: http://localhost:8080
- Jaeger UI: http://localhost:16686

## **How to run**

```bash
cd deploy
docker-compose up --build
```
## Observability

All services are instrumented with **OpenTelemetry**.
Traces are exported to Jaeger, which allows to observe end-to-end flows across microservices.

### Tracing flow example

```text
orders-service: POST /api/orders
    └─ payments-service
        └─ shipping-service
            └─ auditor-service
            └─ reporting-service
    └─ notification-service
```

Traces can be inspected in [Jaeger UI](http://localhost:16686) by selecting the corresponding service (for example, `orders-service`).

## Status

Work in progress

## License

MIT License
