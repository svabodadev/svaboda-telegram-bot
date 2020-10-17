package com.svaboda.utils;

import org.springframework.util.StringUtils;

public interface ArgsValidation {

    static String notEmpty(String arg) {
        if (StringUtils.isEmpty(arg) || arg.isBlank()) throw new IllegalArgumentException("Param is empty");
        return arg;
    }

    static <T> T notNull(T arg) {
        if (arg == null) throw new IllegalArgumentException("Param is null");
        return arg;
    }

    static <T extends Number> T positive(T arg) {
        if (arg == null || arg.longValue() <= 0) throw new IllegalArgumentException("Number must be positive");
        return arg;
    }

}
