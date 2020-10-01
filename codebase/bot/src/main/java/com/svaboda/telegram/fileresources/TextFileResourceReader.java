package com.svaboda.telegram.fileresources;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
class TextFileResourceReader {

    private final String resourcePath;
    private final String fileExtension;

    TextFileResourceReader(FileResourcesProperties properties) {
        this.resourcePath = properties.path();
        this.fileExtension = properties.fileExtension();
    }

    Try<String> readFrom(String filename) {
        return Try.of(() -> filename)
                .map(this::asFullPath)
                .map(this::asStream)
                .mapTry(InputStream::readAllBytes)
                .map(String::new);
    }

    private String asFullPath(String filename) {
        return resourcePath+filename+fileExtension;
    }

    private InputStream asStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
}
