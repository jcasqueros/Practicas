package com.viewnext.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerEx {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerEx.class);

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

		consumer.subscribe(Collections.singletonList("FirstTopic"));

		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
					System.out.printf("New message: Key = %s, Value = %s, Partition = %d, Offset = %d%n",
							record.key(), record.value(), record.partition(), record.offset());
				}
			}
		} catch (Exception e) {
			logger.error("ERROR: There was an error when trying to read the messages.", e);
		} finally {
			consumer.close();
		}
	}
}



/*PS C:\Kafka\bin\windows>
 * .\kafka-console-consumer.bat --topic FirstTopic --bootstrap-server localhost:9092 --from-beginning
 * 
 * 
{"message": "Testing this"}
{"message": "Testing that"}
{"message": "Christ Almighty, y r u like this"}
"PLS WORK"

*
*
*PS C:\Kafka\bin\windows>
*.\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --delete --group my-group
Deletion of requested consumer groups ('my-group') was successful.


PS C:\Kafka\bin\windows>
.\kafka-console-producer.bat --topic FirstTopic --bootstrap-server localhost:9092                                                                                   >"Sending msg from the command line*/
