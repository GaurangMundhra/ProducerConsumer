package com.spring.producerconsumer.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/producer")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = "*",
        methods = {RequestMethod.POST, RequestMethod.OPTIONS}
)
public class ProducerController {

    private static final String TOPIC_NAME = "testy";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/event")
    public void sendEventToKafka(@RequestBody Map<String, String> payload) {
        String event = payload.get("event");
        kafkaTemplate.send(TOPIC_NAME, event);
        System.out.println("Sent event to Kafka: " + event);
    }
}
