package dev.abykov.pets.kafka.microshop.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrdersTopics topics;

    @PostMapping
    public String createOrder(@RequestBody(required = false) String payload) {
        String orderId = UUID.randomUUID().toString();

        String event = "{\"orderId\":\"" + orderId + "\",\"payload\":" +
                (payload != null ? payload : "\"default\"") + "}";

        kafkaTemplate.send(topics.getOrders(), orderId, event);

        log.info("Новый заказ создан: {}", event);

        return orderId;
    }
}
