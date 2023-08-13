package com.kafka.example.kafka_app.producer;

import lombok.Data;

@Data
public class KafkaProducerRequest {
    private String topic;
    private String key;
    private String message;
}
