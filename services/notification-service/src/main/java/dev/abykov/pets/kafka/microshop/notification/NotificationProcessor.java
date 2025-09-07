package dev.abykov.pets.kafka.microshop.notification;

import dev.abykov.pets.kafka.microshop.messaging.events.PaymentEvent;
import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationProcessor {

    @KafkaListener(topics = Topics.PAYMENTS, groupId = "notification-service")
    public void onPaymentEvent(PaymentEvent event) {
        log.info("[NOTIFICATION] Sending notification for payment: {}", event);
    }
}
