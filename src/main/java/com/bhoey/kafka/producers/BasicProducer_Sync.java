package com.bhoey.kafka.producers;

import com.bhoey.kafka.Util;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import java.util.concurrent.Future;


public class BasicProducer_Sync {

    final private static Logger log = LoggerFactory.getLogger(BasicProducer_Sync.class);

    final private static String topic = "test-topic";

    public static void main(final String[] args) throws Exception {

        final Properties props = new Properties();
        props.load(ClassLoader.getSystemResourceAsStream("kafka-common.properties"));
        props.load(ClassLoader.getSystemResourceAsStream("kafka-producer.properties"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, BasicProducer_Sync.class.getSimpleName());

        try(KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {

            final String key = "theKey"; // Note: A null key provides better partition balance though it sacrifices record ordering
            final String value = "theValue-" + Util.yyyymmdd_hhmmss();

            final ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

            log.info("Sending: "  + Util.asString(record));

            Future<RecordMetadata> send_fut = producer.send(record);
            try {
                RecordMetadata rmd = send_fut.get(); // blocks until message ack'd
                log.info("Sent: " + Util.asString(rmd));
            } catch (Exception e) {
                log.error("Send failed for record {}", record, e);
            }
        }
    }
}
