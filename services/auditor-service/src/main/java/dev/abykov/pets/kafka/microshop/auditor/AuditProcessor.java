package dev.abykov.pets.kafka.microshop.auditor;

import dev.abykov.pets.kafka.microshop.messaging.events.OrderEvent;
import dev.abykov.pets.kafka.microshop.messaging.events.PaymentEvent;
import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuditProcessor {

    @KafkaListener(topics = Topics.ORDERS, groupId = "auditor-service")
    public void onOrderEvent(OrderEvent event) {
        log.info("[AUDIT] Order event: {}", event);
    }

    @KafkaListener(topics = Topics.PAYMENTS, groupId = "auditor-service")
    public void onPaymentEvent(PaymentEvent event) {
        log.info("[AUDIT] Payment event: {}", event);
    }
}
