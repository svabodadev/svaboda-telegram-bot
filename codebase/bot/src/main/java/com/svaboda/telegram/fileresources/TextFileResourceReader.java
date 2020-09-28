package com.svaboda.telegram.fileresources;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
class TextFileResourceReader {

    private final static String DEFAULT_TEXT_FILE_EXT = ".txt";

    private final String resourcePath;

    Try<String> readFrom(String filename) {
        return Try.of(() -> filename)
                .map(this::asFullPath)
                .map(this::asStream)
                .mapTry(InputStream::readAllBytes)
                .map(String::new);
    }

    private String asFullPath(String filename) {
        return resourcePath+filename+DEFAULT_TEXT_FILE_EXT;
    }

    private InputStream asStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
}
