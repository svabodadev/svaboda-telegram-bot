package com.svaboda.storage.stats;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


//todo provide proper implementation
@Slf4j
@RequiredArgsConstructor
class MongoStats implements StatsRepository {

    private final static Query ALL = new Query();
    private final MongoTemplate mongoTemplate;

    @Override
    public Try<Void> save(Statistics statistics) {
        return Try.run(() -> mongoTemplate.save(Stats.from(statistics)))
                .peek(__ -> log.info("Stats saved"))
                .onFailure(failure -> log.error("Error occurred on saving Stats", failure));
    }

    @Override
    public Try<Void> saveAll(List<Statistics> statistics) {
        return Try.run(() -> statistics.forEach(it -> mongoTemplate.save(Stats.from(it))))
                .peek(__ -> log.info("Stats saved"))
                .onFailure(failure -> log.error("Error occurred on saving Stats", failure));
    }

    @Override
    public Try<List<Stats>> loadAllByTimestamps(List<String> timestamps) {
        final var query = new Query()
                .addCriteria(Criteria.where(Stats.TIMESTAMP).in(timestamps));
        return Try.of(() -> mongoTemplate.find(query, Stats.class));
    }
}
