package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.domain.Command;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.TelegramResource;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CachedFileResourceProvider implements ResourceProvider<String> {

    private static final Logger LOG = LoggerFactory.getLogger(CachedFileResourceProvider.class);

//    private final Map<String,TelegramResource<String>> cache;
    private final TextFileResourceReader textFileResourceReader;

    CachedFileResourceProvider(TextFileResourceReader textFileResourceReader) {
        this.textFileResourceReader = textFileResourceReader;
//        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public Try<TelegramResource<String>> provideBy(Command command) {
        return Try.of(command::filename)
//                .map(filename -> cache.computeIfAbsent(
//                        filename,
//                        __ -> new TelegramResource<>(textFileResourceReader.readFrom(filename)
//                                .recoverWith(failure -> Try.failure(new ReadingFileException("Unable to read file"+filename, failure)))
//                                .onFailure(failure -> LOG.error(failure.getMessage(), failure))
//                                .get()//todo recovery with other resource?
//                        )
//                    )
//                );
                .map(filename -> new TelegramResource<>(textFileResourceReader.readFrom(filename)
                                .recoverWith(failure -> Try.failure(new ReadingFileException("Unable to read file"+filename, failure)))
                                .onFailure(failure -> LOG.error(failure.getMessage(), failure))
                                .get()//todo recovery with other resource?
                    )
                );
    }
}
