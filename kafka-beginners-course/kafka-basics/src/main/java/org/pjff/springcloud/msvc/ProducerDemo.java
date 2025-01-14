package org.pjff.springcloud.msvc;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//Vid 44
public class ProducerDemo {
    //Vid 45,Paso 1
    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        //Paso 2
        log.info("I am a Kafka Producer");

        // Paso 4,create Producer properties
        Properties properties = new Properties();

        //Paso 3,esto es para localhost
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        //Paso 5
        properties.setProperty("key.serializer",StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());

        //Paso 6, create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //Paso 7, create a producer record
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("demo_java", "Hola valentina");

        //Paso 8, send data - asynchronous
        producer.send(producerRecord);

        //Paso 10, flush data - synchronous
        producer.flush();
        //Paso 11, flush and close producer
        producer.close();
    }
}
