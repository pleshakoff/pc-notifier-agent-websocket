package com.parcom.notifier_agent_websocket;


import com.parcom.asyncdto.NotificationAgentDto;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {


    @Value("${parcom.kafka.bootstrap-address}")
    private String bootstrapAddress;


    @Value("${parcom.kafka.topic.notificationAgent}")
    private String notificationAgentTopic;


    @Value("${parcom.kafka.group.notifierAgent}")
    private String notifierAgentGroup;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(notificationAgentTopic, 1, (short) 1);
    }



    @Bean
    public ConsumerFactory<String, NotificationAgentDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, notifierAgentGroup);
        JsonDeserializer<NotificationAgentDto> jsonDeserializer = new JsonDeserializer<>(NotificationAgentDto.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationAgentDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NotificationAgentDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
