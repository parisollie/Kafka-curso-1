package org.pjff.springcloud.msvc;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//Vid 49
public class ConsumerDemoWithShutDown {
    //Vid 45
    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutDown.class.getSimpleName());

    public static void main(String[] args) {

        log.info("I am a Kafka Producer");

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

        //Vid 49,get a reference to the main thread
        final Thread mainThread = Thread.currentThread();

        //Vid 49
        // adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
                consumer.wakeup();
                // join the main thread to allow the execution of the code in the main thread
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //Vid 49
        try {
            // subscribe consumer to our topic(s)
            consumer.subscribe(Arrays.asList(topic));
            //Vid 48, poll for new data
            while (true) {
                //Vid 48,
                //log.info("Polling");
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(1000));
                //Vid 48,
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset:" + record.offset());
                }
            }//Fin while
            // Vid 49
        } catch (WakeupException e) {
            log.info("Consumer is starting to shut down");
            // we ignore this as this is an expected exception when closing a consumer
        } catch (Exception e) {
            log.error("Unexpected exception in the consumer", e);
        } finally {
            consumer.close(); // this will also commit the offsets if need be.
            log.info("The consumer is now gracefully closed.");
        }



    }
}

