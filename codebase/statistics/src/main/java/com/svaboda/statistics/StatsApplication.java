package com.svaboda.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.svaboda.statistics",
        "com.svaboda.storage",
        "com.svaboda.httpclient",
        "com.svaboda.diagnostic",
})
public class StatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatsApplication.class, args);
    }
}
