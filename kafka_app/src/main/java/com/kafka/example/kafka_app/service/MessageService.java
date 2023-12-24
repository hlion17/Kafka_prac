package com.kafka.example.kafka_app.service;

import com.google.gson.Gson;
import com.kafka.example.kafka_app.dto.MessageDto;
import com.kafka.example.kafka_app.entity.MessageEntity;
import com.kafka.example.kafka_app.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void saveMessage(String message, String topic) {

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setTopicName(topic);
        messageEntity.setPayload(new Gson().toJson(messageDto));
        messageEntity.setCreationTime(LocalDateTime.now());

        messageRepository.save(messageEntity);
    }
}
