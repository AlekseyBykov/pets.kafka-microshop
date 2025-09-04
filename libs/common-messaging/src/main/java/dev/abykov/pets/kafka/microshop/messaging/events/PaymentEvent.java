package dev.abykov.pets.kafka.microshop.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

    private String orderId;
    private String status; // AUTHORIZED / DECLINED
}
