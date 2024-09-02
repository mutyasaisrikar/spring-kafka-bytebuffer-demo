package com.sri.example.spring_kafka_bytebuffer_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class TestKafkaConfig {

    @Bean
    public List<MyKafkaModel> myKafkaModels() {
        return new ArrayList<>();
    };

    @SneakyThrows
    @KafkaListener(topics = "my-topic")
    public void consume(ConsumerRecord<String, ByteBuffer> message) {

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] value = message.value().array();
        log.info("Received payload bytes: {}", value);
        String content = new String(value);
        log.info("Received payload string: {}", content);
        MyKafkaModel event = objectMapper.readValue(content, MyKafkaModel.class);
        log.info("event received: {}", event);
        myKafkaModels().add(event);
    }

    @Bean
    public NewTopic myTopic() {
        return new NewTopic("my-topic", 1, (short) 1);
    }
}
