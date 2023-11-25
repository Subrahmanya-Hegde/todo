package com.hegde.todo.msq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducerService implements Producer {

    private final KafkaTemplate kafkaTemplate;

    @Override
    public CompletableFuture<SendResult> send(String topic, String message) {
        return kafkaTemplate.send(topic, message);
    }
}
