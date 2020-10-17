package com.svaboda.storage.stats;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
class MongoStats implements StatsRepository {

    private final static Query ALL = new Query();
    private final MongoTemplate mongoTemplate;

    @Override
    public Try<Void> save(Statistics statistics) {
        return Try.run(() -> saveOne(statistics))
                .peek(__ -> log.info("Stats saved"))
                .onFailure(failure -> log.error("Error occurred on saving Stats", failure));
    }

    @Override
    public Try<List<Statistics>> loadAll() {
        return Try.of(() -> mongoTemplate.find(ALL, Statistics.class));
    }

    private void saveOne(Statistics statistics) {
        mongoTemplate.save(Stats.from(statistics));
    }
}
