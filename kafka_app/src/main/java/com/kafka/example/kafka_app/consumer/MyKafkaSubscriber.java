package com.kafka.example.kafka_app.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

@Component
public class MyKafkaSubscriber implements CommandLineRunner {

    private String topic;
    private Consumer<String> handler;

    private final KafkaConsumer<String, String> consumer;

    public MyKafkaSubscriber(KafkaConsumer<String, String> kafkaConsumer) {
        this.consumer = kafkaConsumer;
    }

    @PostConstruct
    public void init() {
        this.handler = (e) -> {
            System.out.println("Handle Message: " + e);
        };
        this.topic = "test-event";
    }

    @Override
    public void run(String... args) {
        System.out.println(">>>> consumer start <<<<");
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                if (record.value() != null) {
                    TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                    OffsetAndMetadata oam = new OffsetAndMetadata(record.offset() + 1);
                    Map<TopicPartition, OffsetAndMetadata> commitInfo = Collections.singletonMap(tp, oam);
                    consumer.commitSync(commitInfo);

                    // print result log
                    Headers headers = record.headers();

                    for (Header header : headers) {
                        System.out.println(header);
                    }

                    String infoString = String.format("messageHash: %s, meessage: %s, partition: %s, offset: %s, timestamp: %s",
                            record.value().hashCode(),
                            record.value(),
                            record.partition(),
                            record.offset(),
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(record.timestamp()), TimeZone.getDefault().toZoneId()).format(DateTimeFormatter.ISO_DATE_TIME)
                    );
                    System.out.println(infoString);

                    // do handler method
                    System.out.println(">>>> Execute Message Handler <<<<");
                    handler.accept(record.value());
                } else {
                    System.err.println("Cannot get message from kafka server");
                }
            }
        }
    }

}
