package com.music.push.configure;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaTopicConfigure {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.topic.user-joined}")
    private String userJoinedTopic;

    @Value("${spring.kafka.topic.subscription-request}")
    private String subscriptionRequestTopic;

    @Value("${spring.kafka.topic.subscription-reply}")
    private String subscriptionReplyTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(userJoinedTopic, 3, (short)1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(subscriptionRequestTopic, 3, (short)1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(subscriptionReplyTopic, 3, (short)1);
    }

}
