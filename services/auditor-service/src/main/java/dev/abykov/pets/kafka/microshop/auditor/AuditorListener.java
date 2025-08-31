package dev.abykov.pets.kafka.microshop.auditor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuditorListener {

    @KafkaListener(topics = "${app.topics.dlt-orders}", groupId = "auditor-service")
    public void onOrdersDlq(String message) {
        log.error("Сообщение в DLQ ORDERS: {}", message);
    }

    @KafkaListener(topics = "${app.topics.dlt-payments}", groupId = "auditor-service")
    public void onPaymentsDlq(String message) {
        log.error("Сообщение в DLQ PAYMENTS: {}", message);
    }

    @KafkaListener(topics = "${app.topics.dlt-inventory}", groupId = "auditor-service")
    public void onInventoryDlq(String message) {
        log.error("Сообщение в DLQ INVENTORY: {}", message);
    }
}
