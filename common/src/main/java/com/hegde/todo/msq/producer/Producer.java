package com.hegde.todo.msq.producer;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface Producer<K, V> {
    CompletableFuture<SendResult<K, V>> send(String topic, String message);
}
