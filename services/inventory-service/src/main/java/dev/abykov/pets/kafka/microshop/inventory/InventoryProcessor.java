package dev.abykov.pets.kafka.microshop.inventory;

import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class InventoryProcessor {

    private final KafkaTemplate<String, String> kafka;

    public InventoryProcessor(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    @KafkaListener(topics = Topics.ORDERS, groupId = "inventory-service")
    public void onOrderCreated(String message) {
        log.info("Inventory check for order: {}", message);

        boolean inStock = new Random().nextBoolean();

        String event = inStock
                ? "{\"status\":\"INVENTORY_RESERVED\",\"order\":" + message + "}"
                : "{\"status\":\"OUT_OF_STOCK\",\"order\":" + message + "}";

        kafka.send(Topics.INVENTORY, event);

        log.info("Inventory event published: {}", event);
    }
}
