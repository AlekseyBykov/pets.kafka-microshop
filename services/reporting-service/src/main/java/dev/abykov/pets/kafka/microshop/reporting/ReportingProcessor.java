package dev.abykov.pets.kafka.microshop.reporting;

import dev.abykov.pets.kafka.microshop.messaging.events.OrderEvent;
import dev.abykov.pets.kafka.microshop.messaging.events.PaymentEvent;
import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportingProcessor {

    @KafkaListener(topics = Topics.ORDERS, groupId = "reporting-service")
    public void onOrder(OrderEvent order) {
        log.info("[REPORTING] Order event received: {}", order);
    }

    @KafkaListener(topics = Topics.PAYMENTS, groupId = "reporting-service")
    public void onPayment(PaymentEvent payment) {
        log.info("[REPORTING] Payment event received: {}", payment);
    }
}
