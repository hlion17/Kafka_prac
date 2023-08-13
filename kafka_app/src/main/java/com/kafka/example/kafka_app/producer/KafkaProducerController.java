package com.kafka.example.kafka_app.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaTemplate<String, String> sender;

    @PostMapping("/send")
    public void send(@RequestBody KafkaProducerRequest req) {
        sender.send(req.getTopic(), req.getKey(), req.getMessage());
    }

}
