package ru.xdpxrt.vinyl.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static ru.xdpxrt.vinyl.cons.Config.ORDERS_TOPIC;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder
                .name(ORDERS_TOPIC)
                .build();
    }
}