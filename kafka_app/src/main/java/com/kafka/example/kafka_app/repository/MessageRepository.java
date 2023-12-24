package com.kafka.example.kafka_app.repository;

import com.kafka.example.kafka_app.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

}
