package com.kafka.example.kafka_app.subscriber;

import com.kafka.example.kafka_app.consumer.MySubscriber;
import com.kafka.example.kafka_app.consumer.MySubscriberFactory;
import com.kafka.example.kafka_app.repository.MessageRepository;
import com.kafka.example.kafka_app.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestMessageSubscriber {
    private final String TOPIC_NAME = "test-event";
    private final MessageRepository messageRepository;
    private final MessageService messageService;

    @Bean
    public MySubscriber testEventSubscriber(MySubscriberFactory dispatcherFactory) {
        return dispatcherFactory.make(TOPIC_NAME, this::eventHandlers);
    }

    public void eventHandlers(String message) {
        log.info("Message Receive Test: " + message);
        messageService.saveMessage(message, TOPIC_NAME);
    }
}
