package com.kafka.example.kafka_app.consumer;

import com.kafka.example.kafka_app.consumer.MySubscriber;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.function.Consumer;

public class MySubscriberFactory {
    private KafkaConsumer<String, String> kafkaConsumer;

    public MySubscriberFactory(KafkaConsumer<String, String> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public MySubscriber make(String topicName, Consumer<String> handler) {
        return new MySubscriber(topicName, handler, kafkaConsumer);
    }

}
