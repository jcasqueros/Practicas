
package com.santander.kafkaconsumer.web;

import com.santander.kafkaconsumer.utils.KafkaStreamsConsumer;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaStreamsConsumer kafkaStreamsConsumer;

    @Autowired
    public KafkaController(KafkaStreamsConsumer kafkaStreamsConsumer) {
        this.kafkaStreamsConsumer = kafkaStreamsConsumer;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public KafkaStreams getStream() {
        return kafkaStreamsConsumer.kafkaStreams();
    }

    @GetMapping()
    public String hi() {
        return "kafkaStreamsConsumer.kafkaStreams()";
    }
}
