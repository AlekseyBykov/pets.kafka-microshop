package dev.abykov.pets.kafka.microshop.payments;

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

    private final KafkaTemplate<String, String> kafka;
    private final PaymentTopics topics;
    private final Random random = new Random();

    @KafkaListener(topics = "${app.topics.orders}", groupId = "payments-service")
    public void onOrderCreated(String message) {
        log.info("Получен заказ для оплаты: {}", message);

        boolean approved = random.nextBoolean();
        String orderId = extractOrderId(message);

        String event = approved
                ? "{\"orderId\":\"" + orderId + "\",\"status\":\"AUTHORIZED\"}"
                : "{\"orderId\":\"" + orderId + "\",\"status\":\"DECLINED\"}";

        kafka.send(topics.getPayments(), orderId, event);

        log.info("Отправлен результат оплаты [{}]: {}", approved ? "AUTHORIZED" : "DECLINED", event);
    }

    private String extractOrderId(String message) {
        int start = message.indexOf("\"orderId\":\"") + 11;
        int end = message.indexOf("\"", start);

        return (start > 10 && end > start)
                ? message.substring(start, end)
                : "UNKNOWN";
    }
}
