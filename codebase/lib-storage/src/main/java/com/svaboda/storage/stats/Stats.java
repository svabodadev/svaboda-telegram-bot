package com.svaboda.storage.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.svaboda.storage.support.SerializationHelper.serialize;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Stats {
    final static String TIMESTAMP = "timestamp";
    private String timestamp;
    private String payload;

    static Stats from(Statistics statistics) {
        return new Stats(
                statistics.generatedAt(),
                serialize(statistics)
        );
    }
}
