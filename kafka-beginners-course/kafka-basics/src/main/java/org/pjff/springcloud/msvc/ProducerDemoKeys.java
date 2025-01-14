package org.pjff.springcloud.msvc;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

//Vid 46
public class ProducerDemoKeys {
    //Vid 45
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getSimpleName());

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
        for (int j=0; j<2; j++){
            //Vid 46
            for (int i=0; i<10; i++ ) {
                //Vid 47
                // create a producer record

                String topic = "demo_java";
                String value = "hello valentins " + Integer.toString(i);
                String key = "id_" + Integer.toString(i);

                // create a producer record
                ProducerRecord<String, String> producerRecord =
                        //Vid 47,(topic,key,value)
                        new ProducerRecord<String, String>(topic,key,value);

                //Paso , send data - asynchronous
                producer.send(producerRecord, new Callback() {
                    //Vid 46
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        // executes every time a record is successfully sent or an exception is thrown
                        if (e == null) {
                            // the record was successfully sent
                            log.info("Key: "+ key + " | Partition: " + metadata.partition());
                        } else {
                            log.error("Error al producirlo", e);
                        }
                    }
                });
        }

        }

        //flush data - synchronous
        producer.flush();
        //flush and close producer
        producer.close();
    }
}

