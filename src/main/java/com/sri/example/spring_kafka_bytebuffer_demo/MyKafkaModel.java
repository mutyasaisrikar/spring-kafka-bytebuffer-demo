package com.sri.example.spring_kafka_bytebuffer_demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyKafkaModel {
    private String name;
    private int age;
    private String guid;
}
