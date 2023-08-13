package com.kafka.example.kafka_app.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KafkaConsumerController {

    private final KafkaSubscriber subscriber;

    @GetMapping("/messages/{topic}")
    public Map<String, String> getKafkaMessages(@PathVariable("topic") String topic) {
        return subscriber.getMessage(topic);
    }

}
