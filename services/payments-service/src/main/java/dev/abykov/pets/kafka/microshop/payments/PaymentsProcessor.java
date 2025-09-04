package dev.abykov.pets.kafka.microshop.payments;

import dev.abykov.pets.kafka.microshop.messaging.events.OrderEvent;
import dev.abykov.pets.kafka.microshop.messaging.events.PaymentEvent;
import dev.abykov.pets.kafka.microshop.payments.PaymentTopics;
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
    private final PaymentTopics topics;

    @KafkaListener(topics = "${app.topics.orders}", groupId = "payments-service")
    public void onOrderCreated(OrderEvent event) {
        log.info("Получен заказ для оплаты: {}", event);

        boolean approved = new Random().nextBoolean();

        PaymentEvent payment = approved
                ? new PaymentEvent(event.getOrderId(), "AUTHORIZED")
                : new PaymentEvent(event.getOrderId(), "DECLINED");

        paymentKafkaTemplate.send(topics.getPayments(), event.getOrderId(), payment);

        log.info("Отправлен результат оплаты [{}]: {}", payment.getStatus(), payment);
    }
}
