package com.kafka.example.kafka_app.consumer.config;

import com.kafka.example.kafka_app.consumer.MySubscriberFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySubscriberConfig {

    @Bean
    public MySubscriberFactory dispatcherFactory(KafkaConsumer<String, String> kafkaConsumer) {
        return new MySubscriberFactory(kafkaConsumer);
    }

}
