package com.ecom.ecommerce.config;

import com.ecom.ecommerce.constant.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderCreatedTopic() {

        return new NewTopic(
                KafkaTopics.ORDER_CREATED,
                3,
                (short) 1
        );
    }
}