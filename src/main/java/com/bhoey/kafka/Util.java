package com.bhoey.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.time.LocalDateTime;

public final class Util {

    private Util() {}

    public static String yyyymmdd_hhmmss(){
        return Constants.YYYYMMDD_HHMMSS.format(LocalDateTime.now());
    }

    public static String asString(final ProducerRecord record){
        final StringBuilder sb = new StringBuilder();

        sb.append("[topic:").append(record.topic()).append("] ")
          .append("[key:")  .append(record.key())  .append("] ")
          .append("[value:").append(record.value()).append("]");

        return sb.toString();
    }

    public static String asString(ConsumerRecord record){
        StringBuilder sb = new StringBuilder();

        sb.append("Received record:")
          .append(" [topic:")    .append(record.topic())    .append("]")
          .append(" [partition:").append(record.partition()).append("]")
          .append(" [offset:")   .append(record.offset())   .append("]")
          .append(" [key:")      .append(record.key())      .append("]")
          .append(" [value:")    .append(record.value())    .append("]");

        return sb.toString();
    }

    public static String asString(final RecordMetadata rmd){
        final StringBuilder sb = new StringBuilder();

        sb.append("[topic:")    .append(rmd.topic())    .append("] ")
          .append("[partition:").append(rmd.partition()).append("] ")
          .append("[offset:")   .append(rmd.offset())   .append("]");

        return sb.toString();
    }
}
