package org.pjff.springcloud.msvc;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//Vid 44
public class ConsumerDemo {
    //Vid 45,Paso
    private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class.getSimpleName());

    public static void main(String[] args) {
        //Paso
        //log.info("I am a Kafka Producer");

        //Vid 48
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-java-application";
        String topic = "demo_java";

        //Paso ,create Producer properties
        Properties properties = new Properties();

        //Paso ,esto es para localhost
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");
        //Vid 48, create consumer configs
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));

        //Vid 48, poll for new data
        while (true) {
            //Vid 48,
            log.info("Polling");
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));
            //Vid 48,
            for (ConsumerRecord<String, String> record : records) {
                log.info("Key: " + record.key() + ", Value: " + record.value());
                log.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }//Fin while

    }
}
