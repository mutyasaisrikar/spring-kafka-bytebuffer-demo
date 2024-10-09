package com.sri.example.spring_kafka_bytebuffer_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

import static java.util.UUID.randomUUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, ByteBuffer> kafkaTemplate;

    @SneakyThrows
    public void send() {
        MyKafkaModel model = new MyKafkaModel();
        model.setName("sri");
        model.setAge(25);
        model.setGuid(randomUUID().toString());
        kafkaTemplate.send("my-topic", model.getGuid(), ByteBuffer.wrap(objectMapper.writeValueAsBytes(model)))
            .whenComplete((result, throwable) -> {
                if (result != null) {
                    ByteBuffer byteBuffer = result.getProducerRecord().value();
                    log.info("Published to Kafka successfully with result {}", result);
                    log.info("Byte array (byteBuffer.array()) derived from ByteBuffer is {}", byteBuffer.array());
                    log.info("Published message as string is {}.", new String(byteBuffer.array()));
                } else {
                    log.error("Unable to publish event to Kafka.", throwable);
                }
            });
    }
}
