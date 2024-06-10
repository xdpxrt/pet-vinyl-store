package ru.xdpxrt.vinyl.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static ru.xdpxrt.vinyl.cons.Config.BIRTHDAY_TOPIC;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic birthdayTopic() {
        return TopicBuilder
                .name(BIRTHDAY_TOPIC)
                .build();
    }
}