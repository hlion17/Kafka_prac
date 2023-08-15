package com.kafka.example.kafka_app.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

@Slf4j
public class MySubscriber implements CommandLineRunner {
    final String topicName;
    Consumer<String> eventHandler;
    KafkaConsumer<String, String> kafkaConsumer;
    public MySubscriber(String dispatcherId,
                        Consumer<String> eventHandler,
                        KafkaConsumer<String, String> kafkaConsumer) {
        this.topicName = dispatcherId;
        this.eventHandler = eventHandler;
        this.kafkaConsumer = kafkaConsumer;
    }

    @PostConstruct
    public void initialize() {
        log.info("MyKafkaSubscriber is started to subscribe {} Topic", topicName);
        kafkaConsumer.subscribe(Collections.singletonList(topicName));
    }

    @Override
    public void run(String... args) {
        while (true) {
            log.info("kafkaConsumer polling {} ...", kafkaConsumer.subscription());
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<String, String> record : records) {
                if (record.value() != null) {
                    TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                    OffsetAndMetadata oam = new OffsetAndMetadata(record.offset() + 1);
                    Map<TopicPartition, OffsetAndMetadata> commitInfo = Collections.singletonMap(tp, oam);
                    kafkaConsumer.commitSync(commitInfo);

                    // print result log
                    Headers headers = record.headers();

                    for (Header header : headers) {
                        String headerInfo = String.format("key = %s, value = %s", header.key(), new String(header.value()));
                        log.info("Consumer Record Header Info: {}", headerInfo);
                    }

                    String infoString = String.format("messageHash: %s, meessage: %s, partition: %s, offset: %s, timestamp: %s",
                            record.value().hashCode(),
                            record.value(),
                            record.partition(),
                            record.offset(),
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(record.timestamp()), TimeZone.getDefault().toZoneId()).format(DateTimeFormatter.ISO_DATE_TIME)
                    );
                    log.info("Consumer Record Info: {}", infoString);

                    // do handler method
                    log.info("Execute Message Handler");
                    eventHandler.accept(record.value());
                } else {
                    log.error("Cannot get message from kafka server: Consumer record is null");
                }
            }
        }
    }
}
