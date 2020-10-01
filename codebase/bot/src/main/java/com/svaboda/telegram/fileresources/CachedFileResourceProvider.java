package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.commands.Command;
import com.svaboda.telegram.domain.ResourceProvider;
import com.svaboda.telegram.domain.ResourcesProperties;
import com.svaboda.telegram.domain.TelegramResource;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static com.svaboda.telegram.support.ArgsValidation.notNull;

class CachedFileResourceProvider implements ResourceProvider<String> {

    private static final Logger LOG = LoggerFactory.getLogger(CachedFileResourceProvider.class);

    private final Map<String,TelegramResource<String>> cache = new ConcurrentHashMap<>();
    private final TextFileResourceReader textFileResourceReader;
    private final ResourcesProperties resourcesProperties;

    CachedFileResourceProvider(TextFileResourceReader textFileResourceReader, ResourcesProperties resourcesProperties) {
        this.textFileResourceReader = notNull(textFileResourceReader);
        this.resourcesProperties = notNull(resourcesProperties);
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
                .map(enrichWithLink(command))
                .map(enrichWithTopicsCommand(command))
                .map(TelegramResource::new)
                .recoverWith(failure -> Try.failure(
                            new ReadingFileException("Unable to resolve resource"+command.resourceId(), failure)
                        )
                )
                .onFailure(failure -> LOG.error(failure.getMessage(), failure))
                .get();//todo recovery with other resource?
    }

    private Function<String,String> enrichWithTopicsCommand(Command command) {
        return text -> command.isTopicsCommand() ? text
                : text + resourcesProperties.topicEnrichmentLine();
    }

    private Function<String,String> enrichWithLink(Command command) {
        return text -> command.externalLink()
                    .map(link -> text + resourcesProperties.goToArticleLine() + link)
                    .orElse(text);
    }

}
