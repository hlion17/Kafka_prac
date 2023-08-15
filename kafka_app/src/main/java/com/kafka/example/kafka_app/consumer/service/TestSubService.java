package com.kafka.example.kafka_app.consumer.service;

import com.kafka.example.kafka_app.consumer.MySubscriber;
import com.kafka.example.kafka_app.consumer.MySubscriberFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TestSubService {

    private final String TOPIC_NAME = "test-event";

    @Bean
    public MySubscriber testEventSubscriber(MySubscriberFactory dispatcherFactory) {
        return dispatcherFactory.make(TOPIC_NAME, this::eventHandlers);
    }

    public void eventHandlers(String message) {
        System.out.println("Message Receive Test: " + message);
    }

}
