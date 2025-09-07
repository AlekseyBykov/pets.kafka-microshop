package dev.abykov.pets.kafka.microshop.shipping;

import dev.abykov.pets.kafka.microshop.messaging.events.PaymentEvent;
import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShippingProcessor {

    @KafkaListener(topics = Topics.PAYMENTS, groupId = "shipping-service")
    public void onPaymentReceived(PaymentEvent event) {
        log.info("Payment received: {}", event);

        if ("AUTHORIZED".equals(event.getStatus())) {
            log.info("Shipping scheduled for order {}", event.getOrderId());
        } else {
            log.info("Shipping skipped for order {} (payment declined)", event.getOrderId());
        }
    }
}
