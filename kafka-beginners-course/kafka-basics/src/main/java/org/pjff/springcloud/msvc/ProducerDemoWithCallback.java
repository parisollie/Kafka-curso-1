package org.pjff.springcloud.msvc;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

//Vid 46
public class ProducerDemoWithCallback {
    //Vid 45
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getSimpleName());

    public static void main(String[] args) {

        log.info("I am a Kafka Producer");

        // create Producer properties
        Properties properties = new Properties();

        //Paso ,esto es para localhost
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        //Paso
        properties.setProperty("key.serializer",StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());

        //Paso , create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


        //Vid 47
        for (int i=0; i<30; i++ ) {
            // create a producer record
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<String, String>("demo_java", "hello world " + Integer.toString(i));

            //Paso , send data - asynchronous
            producer.send(producerRecord, new Callback() {
                //Vid 47
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        log.info("Received new metadata. \n" +
                                "Topic:" + metadata.topic() + "\n" +
                                "Partition: " + metadata.partition() + "\n" +
                                "Offset: " + metadata.offset() + "\n" +
                                "Timestamp: " + metadata.timestamp());
                    } else {
                        log.error("Error al producirlo", e);
                    }
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //flush data - synchronous
        producer.flush();
        //flush and close producer
        producer.close();
    }
}
