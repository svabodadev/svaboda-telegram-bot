package com.svaboda.storage.mongo;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


@Configuration
@EnableConfigurationProperties({MongoProperties.class})
class MongoConfiguration {

    @Bean
    MongoTemplate mongoTemplate(MongoProperties databaseProperties) {
        final var dbFactory = new SimpleMongoClientDatabaseFactory(databaseProperties.url());
        final var dbRefResolver = new DefaultDbRefResolver(dbFactory);
        final var mappingContext = new MongoMappingContext();
        mappingContext.setAutoIndexCreation(true);
        mappingContext.afterPropertiesSet();
        return new MongoTemplate(dbFactory, new MappingMongoConverter(dbRefResolver, mappingContext));
    }
}
