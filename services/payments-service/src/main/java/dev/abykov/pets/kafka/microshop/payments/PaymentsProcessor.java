package dev.abykov.pets.kafka.microshop.payments;

import dev.abykov.pets.kafka.microshop.messaging.events.OrderEvent;
import dev.abykov.pets.kafka.microshop.messaging.events.PaymentEvent;
import dev.abykov.pets.kafka.microshop.messaging.exchange.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsProcessor {

    private final KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;

    @KafkaListener(
            topics = Topics.ORDERS,
            groupId = "payments-service"
    )
    public void onOrderCreated(OrderEvent event) {
        log.info("Received order for payment: {}", event);

        boolean approved = new Random().nextBoolean();

        PaymentEvent payment = approved
                ? new PaymentEvent(event.getOrderId(), "AUTHORIZED")
                : new PaymentEvent(event.getOrderId(), "DECLINED");

        paymentKafkaTemplate.send(Topics.PAYMENTS, event.getOrderId(), payment);

        log.info("Payment result [{}] sent: {}", payment.getStatus(), payment);
    }
}
