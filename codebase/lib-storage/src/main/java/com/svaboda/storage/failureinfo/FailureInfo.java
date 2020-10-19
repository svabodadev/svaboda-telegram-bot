package com.svaboda.storage.failureinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailureInfo {

    private final static int MAX_STACKTRACE_LENGTH = 200;

    String type;
    String cause;
    String message;
    String stacktrace;
    String generatedAt;

    public static FailureInfo from(Throwable throwable) {
        return new FailureInfo(
                throwable.getClass().getSimpleName(),
                asCause(throwable),
                throwable.getMessage(),
                asStacktrace(throwable),
                Instant.now().toString()
        );
    }

    private static String asCause(Throwable throwable) {
        final var cause = throwable.getCause();
        return cause == null ? "unknown" : cause.getClass().getSimpleName();
    }

    private static String asStacktrace(Throwable throwable) {
        final var stacktrace = Arrays.toString(throwable.getStackTrace());
        return stacktrace.length() > MAX_STACKTRACE_LENGTH ? stacktrace.substring(0, MAX_STACKTRACE_LENGTH) : stacktrace;
    }
}
