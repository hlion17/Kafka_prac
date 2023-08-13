package com.kafka.example.kafka_app.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class KafkaSubscriber {
    private final KafkaConsumer<String, String> consumer;

    public Map<String, String> getMessage(String topic) {
        consumer.subscribe(Collections.singletonList(topic));

        Map<String, String> map;
        while (true) {
            map = new HashMap<>();
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, String> record : records) {
                if (record.value() != null) {
                    TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                    OffsetAndMetadata oam = new OffsetAndMetadata(record.offset() + 1);
                    Map<TopicPartition, OffsetAndMetadata> commitInfo = Collections.singletonMap(tp, oam);
                    consumer.commitSync(commitInfo);
                    Headers headers = record.headers();
                    for (Header header : headers) {
                        System.out.println(header);
                    }
                    long timestamp = record.timestamp();
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
                    map.put("messageHash", String.valueOf(record.value().hashCode()));
                    map.put("message", record.value());
                    map.put("partition", String.valueOf(record.partition()));
                    map.put("offset", String.valueOf(record.offset()));
                    map.put("timestamp", localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
                } else {
                    System.err.println("Cannot get message from kafka server");
                }
            }
            return map;
        }
    }
}
