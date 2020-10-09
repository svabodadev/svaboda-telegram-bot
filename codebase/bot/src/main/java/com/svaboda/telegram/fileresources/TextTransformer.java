package com.svaboda.telegram.fileresources;

import com.svaboda.telegram.commands.Command;
import com.svaboda.telegram.commands.CommandsProperties;
import com.svaboda.telegram.domain.ResourceTransformer;
import com.svaboda.telegram.domain.ResourcesProperties;
import io.vavr.control.Try;

import java.util.List;
import java.util.function.Function;

import static com.svaboda.telegram.support.ArgsValidation.notNull;

class TextTransformer implements ResourceTransformer<String> {

    private static final List<String> CUT_PATTERNS = List.of("\n", "  ", ". ");
    private static final String SPACE = " ";
    private static final String CUT_SUFFIX = " [...]";
    private static final int MIN_CONTENT_SIZE = 500;

    private final ResourcesProperties resourcesProperties;
    private final int shortenedMaxResourceSize;

    TextTransformer(ResourcesProperties resourcesProperties, CommandsProperties commandsProperties) {
        this.resourcesProperties = notNull(resourcesProperties);
        this.shortenedMaxResourceSize = evaluateShortenedMaxResourceSize(commandsProperties);
    }

    @Override
    public Try<String> asContent(Command command, String resource) {
        return Try.of(() -> resource)
                .map(withProperSize())
                .map(enrichWithHeader())
                .map(enrichWithLink(command))
                .map(enrichWithTopicsCommand(command));
    }

    private Function<String,String> enrichWithHeader() {
        return text -> resourcesProperties.header() + text;
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

    private Function<String,String> withProperSize() {
        return text -> text.length() > shortenedMaxResourceSize ? toProperSize(text) : text;
    }

    private String toProperSize(String text) {
        final var cutIndex = CUT_PATTERNS.stream()
                .map(pattern -> text.lastIndexOf(pattern, shortenedMaxResourceSize))
                .filter(value -> value >= MIN_CONTENT_SIZE)
                .max(Integer::compare)
                .orElseGet(
                        () -> {
                            final var index = text.lastIndexOf(SPACE, shortenedMaxResourceSize);
                            return index >= MIN_CONTENT_SIZE ? index : shortenedMaxResourceSize;
                        }
                );
        return text.substring(0, cutIndex) + CUT_SUFFIX;
    }

    private int evaluateShortenedMaxResourceSize(CommandsProperties commandsProperties) {
        return commandsProperties.commands().stream()
                .map(command -> command.externalLink()
                        .map(String::length)
                        .orElse(0)
                )
                .max(Integer::compare)
                .map(result -> result + resourcesProperties.goToArticleLine().length())
                .map(result -> result + resourcesProperties.topicEnrichmentLine().length())
                .map(result -> result + resourcesProperties.header().length())
                .map(result -> result + CUT_SUFFIX.length())
                .map(result -> resourcesProperties.maxResourceSize() - result)
                .filter(maxLen -> maxLen > MIN_CONTENT_SIZE)
                .orElse(MIN_CONTENT_SIZE);
    }

}
