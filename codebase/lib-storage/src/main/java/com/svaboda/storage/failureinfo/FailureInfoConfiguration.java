package com.svaboda.storage.failureinfo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
class FailureInfoConfiguration {

    @Bean
    FailureInfoRepository FailureInfoRepository(MongoTemplate mongoTemplate) {
        return new MongoFailureInfo(mongoTemplate);
    }

}
