package com.spring.producerconsumer.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerController {

    private final Counter kafkaEventsCounter;

    // Micrometer injects MeterRegistry automatically
    public ConsumerController(MeterRegistry meterRegistry) {
        this.kafkaEventsCounter = meterRegistry.counter("kafka_events_received_total");
    }

    @KafkaListener(topics = "testy", groupId = "metrics-consumer-group")
    public void listen(String eventData) {
        System.out.println(
                "Thread: " + Thread.currentThread().getName() +
                        " | Received event: " + eventData
        );

        // ✅ This is what Prometheus will scrape
        kafkaEventsCounter.increment();
    }
}
