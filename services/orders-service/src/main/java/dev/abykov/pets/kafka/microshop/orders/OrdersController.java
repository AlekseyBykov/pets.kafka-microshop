package dev.abykov.pets.kafka.microshop.orders;

import dev.abykov.pets.kafka.microshop.messaging.events.OrderEvent;
import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final KafkaTemplate<String, OrderEvent> orderKafkaTemplate;

    @PostMapping
    public String createOrder(@RequestBody(required = false) Map<String, Object> payload) {
        String orderId = UUID.randomUUID().toString();

        String json = payload != null ? payload.toString() : "default";

        OrderEvent event = new OrderEvent(orderId, json);

        orderKafkaTemplate.send(Topics.ORDERS, orderId, event);

        log.info("New order created: {}", event);

        return orderId;
    }
}
