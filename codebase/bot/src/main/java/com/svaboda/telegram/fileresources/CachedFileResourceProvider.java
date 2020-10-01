package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.commands.Command;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.TelegramResource;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static com.svaboda.telegram.commands.Commands.TOPICS_COMMAND_NAME;

class CachedFileResourceProvider implements ResourceProvider<String> {

    private static final Logger LOG = LoggerFactory.getLogger(CachedFileResourceProvider.class);
    private static final String TOPICS_ENRICHMENT_LINE = "\n/"+ TOPICS_COMMAND_NAME;

    private final Map<String,TelegramResource<String>> cache = new ConcurrentHashMap<>();
    private final TextFileResourceReader textFileResourceReader;

    CachedFileResourceProvider(TextFileResourceReader textFileResourceReader) {
        this.textFileResourceReader = textFileResourceReader;
    }

    @Override
    public Try<TelegramResource<String>> provideBy(Command command) {
        return Try.of(command::resourceId)
                .map(filename -> cache.computeIfAbsent(
                        filename,
                        __ -> resolveContentBy(command)
                    )
                );
    }

    private TelegramResource<String> resolveContentBy(Command command) {
        return textFileResourceReader.readFrom(command.resourceId())
                .map(enrichWithTopicsCommand(command))
                .peek(output -> System.out.println("### toupu"+output))
                .map(TelegramResource::new)
                .recoverWith(failure -> Try.failure(new ReadingFileException("Unable to read file"+command.resourceId(), failure)))
                .onFailure(failure -> LOG.error(failure.getMessage(), failure))
                .get();//todo recovery with other resource?

    }

    private Function<String,String> enrichWithTopicsCommand(Command command) {
        return text -> TOPICS_COMMAND_NAME.equals(command.name()) ? text : text + TOPICS_ENRICHMENT_LINE;
    }
}
