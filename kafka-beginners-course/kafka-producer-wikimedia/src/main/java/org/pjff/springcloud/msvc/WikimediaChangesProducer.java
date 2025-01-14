package org.pjff.springcloud.msvc;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


//Vid 59
public class WikimediaChangesProducer {

    public static void main(String[] args) throws InterruptedException  {

        //create Producer properties
        Properties properties = new Properties();

        //esto es para localhost
        properties.setProperty("bootstrap.servers","127.0.0.1:9092");

        //
        properties.setProperty("key.serializer",StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());

        //create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
       // KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        String topic = "wikimedia.recentchanges";
        //String topic = "test-topic";

        EventHandler eventHandler = new WikimediaChangeHandler(producer, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();

        // start the producer in another thread
        eventSource.start();

        // we produce for 10 minutes and block the program until then
        TimeUnit.MINUTES.sleep(10);
    }
}
