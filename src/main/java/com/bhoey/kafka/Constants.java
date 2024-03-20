package com.bhoey.kafka;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Constants {

    // Only put immutable and thread-safe values here

    public static final Duration CONSUMER_POLL_TIMEOUT = Duration.ofSeconds(3);
    public static final Duration CONSUMER_COMMIT_SYNC_TIMEOUT = Duration.ofSeconds(10);

    public static final Duration PRODUCER_ASYNC_DONE_CHECK_INTERVAL = Duration.ofMillis(10);

    public static final DateTimeFormatter YYYYMMDD_HHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                                                             .withZone(ZoneId.systemDefault());
}
