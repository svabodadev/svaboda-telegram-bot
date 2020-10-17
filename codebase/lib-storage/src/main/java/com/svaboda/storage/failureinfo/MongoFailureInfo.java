package com.svaboda.storage.failureinfo;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
class MongoFailureInfo implements FailureInfoRepository {
    private final static Query ALL = new Query();
    private final MongoTemplate mongoTemplate;

    @Override
    public Try<Void> save(FailureInfo failureInfo) {
        return Try.run(() -> mongoTemplate.save(failureInfo))
                .peek(__ -> log.info("FailureInfo saved"))
                .onFailure(failure -> log.error("Error occurred on saving FailureInfo", failure));
    }

    @Override
    public Try<List<FailureInfo>> loadAll() {
        return Try.of(() -> mongoTemplate.find(ALL, FailureInfo.class));
    }
}
