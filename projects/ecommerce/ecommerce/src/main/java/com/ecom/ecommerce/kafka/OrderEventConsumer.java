package com.ecom.ecommerce.kafka;

import com.ecom.ecommerce.constant.KafkaTopics;
import com.ecom.ecommerce.dto.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = KafkaTopics.ORDER_CREATED,
            groupId = "notification-service"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        log.info(
                "Received OrderCreatedEvent: orderId={}, userId={}, total={}",
                event.getOrderId(),
                event.getUserId(),
                event.getTotalAmount()
        );

        // Later:
        // send email
        // send push notification
        // generate invoice
    }
}