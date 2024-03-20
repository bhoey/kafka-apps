package com.bhoey.kafka.consumers;

import com.bhoey.kafka.Constants;
import com.bhoey.kafka.Util;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class BasicConsumer_ExplicitCommit_Sync {
    final private static Logger log = LoggerFactory.getLogger(BasicConsumer_ExplicitCommit_Sync.class.getSimpleName());

    final private static String topic = "test-topic";

    public static void main(String[] args) throws Exception {

        final Properties props = new Properties();
        props.load(ClassLoader.getSystemResourceAsStream("kafka-common.properties"));
        props.load(ClassLoader.getSystemResourceAsStream("kafka-consumer.properties"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, BasicConsumer_ExplicitCommit_Sync.class.getSimpleName());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, BasicConsumer_ExplicitCommit_Sync.class.getSimpleName());

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(List.of(topic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Constants.CONSUMER_POLL_TIMEOUT);
                for (ConsumerRecord<String, String> record : records) {

                    log.info("Received: " + Util.asString(record));

                    try {
                        consumer.commitSync(Constants.CONSUMER_COMMIT_SYNC_TIMEOUT);
                    } catch (TimeoutException e){
                        log.error("Timed out trying to commit offsets:", e);
                        // Perform any rollback actions here
                        continue;

                    } catch (Exception e){
                        log.error("Exception hit when committing offsets: ", e);
                        // Perform any rollback actions here
                        continue;
                    }
                    log.info("commitSync succeeded");
                }
            }
        }
    }
}
