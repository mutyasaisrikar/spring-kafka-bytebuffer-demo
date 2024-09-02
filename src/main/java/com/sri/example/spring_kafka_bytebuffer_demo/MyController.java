package com.sri.example.spring_kafka_bytebuffer_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final MyProducer myProducer;

    @PutMapping("send")
    public void send() {
        myProducer.send();
    }
}
