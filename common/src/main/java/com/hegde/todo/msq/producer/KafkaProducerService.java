package com.hegde.todo.msq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducerService implements Producer {

    private final KafkaTemplate kafkaTemplate;

    @Override
    public CompletableFuture pushEvent(String topic, String message) {
        return kafkaTemplate.send(constructMessage(message, topic, "default"));
    }

    @Override
    public Message<String> constructMessage(String message, String destination, String eventName) {
        return MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, destination)
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .setHeader("X-EVENT-NAME", eventName)
                .build();
    }
}
