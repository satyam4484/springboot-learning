package com.ecom.ecommerce.kafka;

import com.ecom.ecommerce.constant.KafkaTopics;
import com.ecom.ecommerce.dto.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {

        kafkaTemplate.send(
                KafkaTopics.ORDER_CREATED,
                String.valueOf(event.getOrderId()),
                event
        );

        log.info(
                "Published OrderCreatedEvent for orderId={}",
                event.getOrderId()
        );
    }
}