package com.svaboda.storage.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.svaboda.storage.support.SerializationHelper.serialize;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Stats {

    private int uniqueChats;
    private String statistics;
    private String generatedAt;

    static Stats from(Statistics statistics) {
        return new Stats(
                statistics.uniqueChats(),
                serialize(statistics.statistics()),
                statistics.generatedAt()
        );
    }

}
