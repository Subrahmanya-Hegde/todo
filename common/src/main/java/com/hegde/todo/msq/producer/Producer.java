package com.hegde.todo.msq.producer;

import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;

import java.util.concurrent.CompletableFuture;

//TODO : Explore and make it better.
public interface Producer<K, V> {
    CompletableFuture<SendResult<K, V>> pushEvent(String topic, String message);

    Message<String> constructMessage(String message, String destination, String eventName);
}
