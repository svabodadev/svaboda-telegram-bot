package com.svaboda.storage.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public interface SerializationHelper {

    static String serialize(Object obj) {
        try {
            return Mapper.MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error occurred on serialization" +obj.getClass().getSimpleName(), ex);
        }
    }

    class Mapper {
        private final static ObjectMapper MAPPER = new ObjectMapper()
                .setVisibility(FIELD, ANY)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(NON_ABSENT)
                .registerModule(new JavaTimeModule());
    }
}
