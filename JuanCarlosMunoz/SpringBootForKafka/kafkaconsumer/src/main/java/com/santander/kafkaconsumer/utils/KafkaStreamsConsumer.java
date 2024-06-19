package com.santander.kafkaconsumer.utils;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Properties;

@Component
public class KafkaStreamsConsumer {

    @Bean
    public KafkaStreams kafkaStreams() {
        StreamsBuilder builder = new StreamsBuilder();
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        return new KafkaStreams(builder.build(), props);
    }

//    public Flux<String> kafkaStream(KafkaStreams kafkaStreams) {
//        kafkaStreams.start();
//        StreamsBuilder builder = new StreamsBuilder();
//        KStream<Integer, String> stream = builder.stream("santander");
//        return Flux.fromStream(stream.mapValues(String).toStream().iterator());
//    }
}
